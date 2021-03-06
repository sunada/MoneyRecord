package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.DealDao;
import com.springapp.mvc.Dao.MyFundDao;
import com.springapp.mvc.Model.*;
import com.springapp.mvc.Model.Currency;
import com.springapp.mvc.Util.GetNet;
import com.springapp.mvc.Util.NetDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/22.
 */
@Service("dealService")
public class DealService {
    private Deal deal;
    private DealDao dealDao;
    private MyFund myFund;
    private MyFundService myFundService;
    @Resource
    private StockService stockService;

    @Autowired
    public void setDeal(Deal deal){ this.deal = deal;}
    @Autowired
    public void setDealDao(DealDao dealDao) { this.dealDao = dealDao; }
    @Autowired
    public void setMyfund(MyFund myfund) { this.myFund = myfund; }
    @Autowired
    public void setMyFundService(MyFundService myFundService) { this.myFundService =  myFundService; }

    public ArrayList<Deal> readDeals(String code, String name, String belongTo, String currency, String fundOrStock){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("name", name);
        map.put("belongTo", belongTo);
        map.put("currency", currency);
        ArrayList<Deal> deals = (ArrayList)dealDao.getDeals(map, fundOrStock);
        return deals;
    }

    public BigDecimal sumDealsAmount(String code, String name, String belongTo, String fundOrStock, Currency currency){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("belongTo", belongTo);
//        map.put("name", name);
        if(currency == null) map.put("currency","");
        else map.put("currency", currency.toString());
        BigDecimal sum = dealDao.sumDealsAmount(map, fundOrStock);
        return sum;
    }

    public List<Deal> getStrategyDeals(String strategyCode){
        List<Stock> stocks = stockService.getAllStrategyStocks(strategyCode);

        List<Deal> deals = new ArrayList<Deal>();
        for(Stock stock : stocks){
            deals.addAll(readDeals(stock.getCode(),stock.getName(), stock.getBelongTo(), stock.getCurrency().toString(),stock.getType().getName()));
        }
        return deals;
    }

    //在fund_deals表中新增一条交易数据，并修改myfunds表中的份额和成本或插入一条持有基金数据
    public boolean updateOneAipDeal(Fund fund, Date date, Date rdate){
//        log.debug("updateOneAipDeal: " + date.getDate() + " " + rdate.getDate() + " " + fund.getCode());
        GetNet getNet = new GetNet();
        BigDecimal net = getNet.getFundNet(fund.getCode(), rdate);
        BigDecimal prate = fund.getPurchaseRate().divide(BigDecimal.valueOf(100));
        BigDecimal clean = fund.getAmount().multiply(new BigDecimal(-1)).divide(prate.add(BigDecimal.valueOf(1)), 2, BigDecimal.ROUND_HALF_EVEN);

        if(net.equals(BigDecimal.ZERO)){
            return false;
        }
        deal.setCode(fund.getCode());
        deal.setBelongTo(fund.getBelongTo());
        deal.setName(fund.getName());
        deal.setDate(date);
        deal.setDateReal(rdate);
        deal.setNet(net);
        deal.setShare(clean.divide(net,2, BigDecimal.ROUND_HALF_EVEN));
        deal.setCost(fund.getAmount().multiply(prate).multiply(new BigDecimal(-1)));
        deal.setAmount(fund.getAmount());
        deal.setDealType(DealType.FAIP);
        dealDao.updateDeal(deal, AssetType.FUND);
        MyFund myFundSql = myFundService.readMyFundByCB(fund.getCode(), fund.getBelongTo());
        //若定投的基金在myfunds表中无数据，则插入一条
        if(myFundSql == null){
//            log.debug("In FundsController.save, {}", fund.toString());
            //insert a new fund to myfunds
            myFund.setCode(fund.getCode());
            myFund.setName(fund.getName());
            myFund.setShare(deal.getShare());
            myFund.setNet(net);
            myFund.setCost(deal.getAmount().multiply(new BigDecimal(-1)));
            myFund.setDate(rdate);
            myFund.setBelongTo(deal.getBelongTo());
            myFund.setPurchaseRate(prate);
            myFund.setRedemptionRate(fund.getPurchaseRate());
            myFund.setChargeMode(fund.getChargeMode());
            myFund.setDividendMode(fund.getDividendMode());
            myFund.setRisk(fund.getRisk());
            return myFundService.saveFund(myFund);
        }else{
            //若有，则更新份额和成本
            myFundSql.setShare(deal.getShare().add(myFundSql.getShare()));
            myFundSql.setCost(deal.getAmount().multiply(new BigDecimal(-1)).add(myFundSql.getCost()));
            myFundSql.setDate(rdate);
            myFundSql.setNet(net);
            return myFundService.updateMyFund(myFundSql);
        }

    }

    public boolean updateAipDeals(List<Fund> funds){
        Date nearDealDate;
        NetDay netDay = new NetDay();
        for(Fund fund : funds){
            if(fund.getCode().startsWith("-")){
                continue;
            }
//            log.debug("updateAipDeals: " + fund.getCode());
            //先为每个code灌一条定投数据到表中，保证nearDealDate是上次定投的日期
            nearDealDate = dealDao.getNearDealDate(fund.getCode(),fund.getBelongTo(),fund.getStartTime(),fund.getAmount());

            int inter;
            int mul = 1;
            if(fund.getInterval() == Interval.MONTH){
                inter = Calendar.MONTH;
            }else{
                inter = Calendar.WEEK_OF_YEAR;
                if(fund.getInterval() == Interval.TWOWEEKS){
                    mul = 2;
                }
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = sdf.format(nearDealDate);
                if(str.equals("1971-12-12")){
                    nearDealDate = netDay.getNextAipDate(fund.getStartTime(), fund);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            Calendar cal = Calendar.getInstance().getInstance();
            cal.setTime(nearDealDate);
            cal.add(inter,mul);
            Date nextAipDate = cal.getTime();
            Date today = new Date();
            while(today.after(nextAipDate)){
                Date realNextAipDate = myFundService.calRealAipDate(nextAipDate);
                updateOneAipDeal(fund, nextAipDate,realNextAipDate);
                cal.setTime(nextAipDate);
                cal.add(inter, mul);
                nextAipDate = cal.getTime();
            }
        }
        return true;
    }

    public boolean updateFundDeal(Deal deal){
        return dealDao.updateDeal(deal, AssetType.FUND);
    }

    public boolean delete(int id, String fundOrStock){
        return dealDao.delete(id, fundOrStock);
    }

    public BigDecimal sumCost(String code, String fundOrStock){
        return dealDao.sumCost(code, fundOrStock);
    }

    public Map<String, Date> getAccountNewDealDate(AssetType type){
        return dealDao.getAccountNewDealDate(type);
    }


}
