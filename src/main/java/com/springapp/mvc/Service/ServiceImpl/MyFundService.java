package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.MyFundDao;
import com.springapp.mvc.Model.*;
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
@Service("myFoundService")
public class MyFundService {
    @Resource
    private MyFundDao myFundDao;
    private static Logger log = LoggerFactory.getLogger(MyFundService.class);
//    private Deal deal;
    private MyFund myFund;

    @Autowired
    public void setMyFund(MyFund myFund){ this.myFund = myFund; }
//    @Autowired
//    public void setDeal(Deal deal){ this.deal = deal; }

    public Map<String, List<BigDecimal>> addUp(ArrayList<MyFund> myFunds){
        Map<String, List<BigDecimal>> map = new HashMap<String, List<BigDecimal>>();
        String str;
        BigDecimal amount;
        BigDecimal cost;
        BigDecimal tmp;
        BigDecimal amountAll = BigDecimal.ZERO;
        BigDecimal costAll = BigDecimal.ZERO;
        BigDecimal sum = sum();

        for(MyFund fund : myFunds){
            List<BigDecimal> list = new ArrayList<BigDecimal>();
            str = fund.getRisk().getName();
            tmp = fund.getNet().multiply(fund.getShare());
            if(map.containsKey(str)) {
                list = map.get(str);
                amount = list.get(0).add(tmp);
                cost = list.get(2).add(fund.getCost());
                list.set(0, amount);
                list.set(1, amount.divide(sum, 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
                list.set(2, cost);
                list.set(3, amount.subtract(cost));
                list.set(4, amount.subtract(cost).divide(cost, 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
            }else{
                amount = tmp;
                cost = fund.getCost();
                list.add(amount);
                list.add(amount.divide(sum, 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
                list.add(cost);
                list.add(amount.subtract(cost));
                list.add(amount.subtract(cost).divide(cost, 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
            }
            map.put(str, list);
            amountAll = amountAll.add(tmp);
            costAll = costAll.add(fund.getCost());
        }
        List<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(amountAll);
        list.add(BigDecimal.valueOf(100));
        list.add(costAll);
        list.add(amountAll.subtract(costAll));
        list.add(amountAll.subtract(costAll).divide(costAll, 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
        map.put("总计", list);
        return map;
    }

    public boolean saveFund(MyFund fund){
        return myFundDao.save(fund);
    }

    public ArrayList<MyFund> readMyFund() {
        ArrayList<MyFund> funds = (ArrayList)myFundDao.getMyFundList();
        return funds;
    }

    public ArrayList<MyFund> readMyHistoryFund() {
        ArrayList<MyFund> funds = (ArrayList)myFundDao.getMyHistoryFundList();
        return funds;
    }

    public MyFund readMyFundByCB(String code, String belongTo){
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("belongTo", belongTo);
        return myFundDao.getMyFundByCB(map);
    }

    public BigDecimal sum(){ return myFundDao.sum();}

    public boolean updateMyFund(MyFund fund){
        return myFundDao.updateMyFund(fund);
    }

    public boolean updateNet(MyFund myFund){
        NetDay netDay = new NetDay();
        Date date = netDay.getNetDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        GetNet getNet = new GetNet();
        BigDecimal net;
        BigDecimal share;
        Map<String, Object> map = new HashMap<String, Object>();
        boolean res = true;

        if(myFund.getBelongTo().contains("蛋卷")){
            net = getNet.getDanjuanNet(myFund.getCode());
            if(!net.equals(BigDecimal.ZERO)){
                myFund.setNet(net);
                map.put("code", myFund.getCode());
                map.put("net", net);
                map.put("date", new Date());
                myFundDao.updateNet(map);
            }
        }else if (myFund.getCode().length() == 6) {
            net = getNet.getFundNet(myFund.getCode(), date);
            while(net.equals(BigDecimal.valueOf(0))){
                cal.add(Calendar.DATE, -1);
                date = cal.getTime();
                date = netDay.getNetDay(date);
                net = getNet.getFundNet(myFund.getCode(), date);
            }
            long day = 2;
            log.debug(myFund.getName() + " update net" );
            if(myFund.getDate() != null) {
                day = (date.getTime() - myFund.getDate().getTime()) / (24 * 60 * 60 * 1000);
            }

            if(day >= 1){
                myFund.setNet(net);
                map.put("code", myFund.getCode());
                map.put("net", net);
                map.put("date", date);
                res = myFundDao.updateNet(map);

                if(myFund.getRisk() == Risk.LOW){
                    share = myFund.getShare().multiply(net).divide(BigDecimal.valueOf(10000)).add(myFund.getShare());
                    res = res&updateMoneyFundShare(myFund.getCode(),share);
                }
            }
        }
        return res;
    }

    public boolean updateMoneyFundShare(String code, BigDecimal share){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("share", share);
        return myFundDao.updateMoneyFundShare(map);
    }

    public Map<String, BigDecimal> sumByRisk(){
        List<Map<String, Object>> map = myFundDao.sumByRisk();
        String risk = "";
        BigDecimal amount = BigDecimal.ZERO;
        Map<String, BigDecimal> val = new HashMap<String, BigDecimal>();
        for(Map<String, Object> m : map){
            for(String k : m.keySet()){
                if(k.equals("risk")) {
                    risk = (String)m.get(k);
                }else {
                    amount = (BigDecimal)m.get(k);
                }
            }
            val.put(risk, amount);
        }
        return val;
    }

    //在fund_deals表中新增一条交易数据，并修改myfunds表中的份额和成本或插入一条持有基金数据
//    public boolean updateOneAipDeal(Fund fund, Date date, Date rdate){
////        log.debug("updateOneAipDeal: " + date.getDate() + " " + rdate.getDate() + " " + fund.getCode());
//        GetNet getNet = new GetNet();
//        BigDecimal net = getNet.getFundNet(fund.getCode(), rdate);
//        BigDecimal prate = fund.getPurchaseRate().divide(BigDecimal.valueOf(100));
//        BigDecimal clean = fund.getAmount().divide(prate.add(BigDecimal.valueOf(1)),2,BigDecimal.ROUND_HALF_EVEN);
//
//        deal.setCode(fund.getCode());
//        deal.setBelongTo(fund.getBelongTo());
//        deal.setName(fund.getName());
//        deal.setDate(date);
//        deal.setDateReal(rdate);
//        deal.setNet(net);
//        deal.setShare(clean.divide(net,2, BigDecimal.ROUND_HALF_EVEN));
//        deal.setCost(fund.getAmount().subtract(clean));
//        deal.setAmount(fund.getAmount());
//        deal.setDealType(DealType.FAIP);
//        myFundDao.updateFundDeal(deal);
//        MyFund myFundSql = readMyFundByCB(fund.getCode(), fund.getBelongTo());
//        //若定投的基金在myfunds表中无数据，则插入一条
//        if(myFundSql == null){
//            log.debug("In FundsController.save, {}", fund.toString());
//            //insert a new fund to myfunds
//            myFund.setCode(fund.getCode());
//            myFund.setName(fund.getName());
//            myFund.setShare(deal.getShare());
//            myFund.setNet(net);
//            myFund.setCost(deal.getAmount());
//            myFund.setDate(rdate);
//            myFund.setBelongTo(deal.getBelongTo());
//            myFund.setPurchaseRate(prate);
//            myFund.setRedemptionRate(fund.getPurchaseRate());
//            myFund.setChargeMode(fund.getChargeMode());
//            myFund.setDividendMode(fund.getDividendMode());
//            myFund.setRisk(fund.getRisk());
//            return saveFund(myFund);
//        }else{
//            //若有，则更新份额和成本
//            myFundSql.setShare(deal.getShare().add(myFundSql.getShare()));
//            myFundSql.setCost(deal.getAmount().add(myFundSql.getCost()));
//            myFundSql.setDate(rdate);
//            myFundSql.setNet(net);
//            return myFundDao.updateMyFund(myFundSql);
//        }
//
//    }

    public Date calRealAipDate(Date date){
        NetDay netDay = new NetDay();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        while(!netDay.isWorkDay(date)){
            cal.add(Calendar.DAY_OF_MONTH, 1);
            date = cal.getTime();
        }
        return date;
    }

//    public boolean updateAipDeals(List<Fund> funds){
//        Date nearDealDate;
//        NetDay netDay = new NetDay();
//        for(Fund fund : funds){
////            log.debug("updateAipDeals: " + fund.getCode());
//            //先为每个code灌一条定投数据到表中，保证nearDealDate是上次定投的日期
//            nearDealDate = myFundDao.getNearDealDate(fund.getCode());
//
//            int inter;
//            int mul = 1;
//            if(fund.getInterval() == Interval.MONTH){
//                inter = Calendar.MONTH;
//            }else{
//                inter = Calendar.WEEK_OF_YEAR;
//                if(fund.getInterval() == Interval.TWOWEEKS){
//                    mul = 2;
//                }
//            }
//
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String str = sdf.format(nearDealDate);
//                if(str.equals("1971-12-12")){
//                    nearDealDate = netDay.getNextAipDate(fund.getStartTime(), fund);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//            Date date = new Date();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            long todayDay = cal.get(Calendar.DAY_OF_YEAR);
//            long nextDealDay;
//            Date nextDealDate;
//            Date realNextDealDate;
//
//            cal.setTime(nearDealDate);
//            cal.add(inter, mul);
//            nextDealDay = cal.get(Calendar.DAY_OF_YEAR);
//            nextDealDate = cal.getTime();
//            while(todayDay - nextDealDay > 0){
//                realNextDealDate = calRealAipDate(nextDealDate);
//                updateOneAipDeal(fund, nextDealDate,realNextDealDate);
//                cal.setTime(nextDealDate);
//                cal.add(inter, mul);
//                nextDealDay = cal.get(Calendar.DAY_OF_YEAR);
//                nextDealDate = cal.getTime();
//            }
//        }
//        return true;
//    }

//    public boolean updateFundDeal(Deal deal){
//        return myFundDao.updateFundDeal(deal);
//    }
}
