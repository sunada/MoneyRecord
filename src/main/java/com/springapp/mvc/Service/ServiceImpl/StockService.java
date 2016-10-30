package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.DealDao;
import com.springapp.mvc.Dao.HistoryAssetDao;
import com.springapp.mvc.Dao.StockDao;
import com.springapp.mvc.Model.*;
import com.springapp.mvc.Model.Currency;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/24.
 */
@Service("stockService")
public class StockService {
    private static Logger log = LoggerFactory.getLogger(StockService.class);

    private StockDao stockDao;
    private Deal deal;
    private DealDao dealDao;
    @Resource
    private SqlSession sqlSession;
    private HistoryAsset historyAsset;
    private HistoryAssetDao historyAssetDao;

    @Resource
    private Stock stock;

    private List<String> etfs = new ArrayList<String>(){{add("510500");add("510300");}};
    private List<String> debts = new ArrayList<String>(){{add("122811");add("124977");}};
    private List<String> money_fund = new ArrayList<String>(){{add("-1");}};

    @Autowired
    public void setStockDao(StockDao stockDao){this.stockDao = stockDao;}
    @Autowired
    public void setDeal(Deal deal){this.deal = deal;}
    @Autowired
    public void setDealDao(DealDao dealDao){this.dealDao = dealDao;}
    @Autowired
    public void setHistoryAsset(HistoryAsset historyAsset){this.historyAsset = historyAsset;}
    @Autowired
    public void setHistoryAssetDao(HistoryAssetDao historyAssetDao){ this.historyAssetDao = historyAssetDao;}

    public ArrayList<Stock> read(Currency currency){ return (ArrayList<Stock>)stockDao.getStockList(currency); }

    public ArrayList<Stock> readHistory(){ return (ArrayList<Stock>)stockDao.getHistoryStockList(); }

    //costFromfile < 0时，表示不是从文件导入的交易记录
    public boolean saveStock(Stock stock, Date date, DealType dealType, BigDecimal costFromfile){
        BigDecimal cost;  //单次交易的费用
        //表示是从页面保存的交易记录，需自己计算交易费用
        if(costFromfile.compareTo(BigDecimal.ZERO) == 0) {
            cost = calDealCost(stock.getCode(), stock.getCost(), stock.getShare().abs(), dealType);
        }else{
            cost = costFromfile;
        }
        BigDecimal share = stock.getShare();
        Stock stockSql = stockDao.getStockByCB(stock.getCode(), stock.getBelongTo());
        BigDecimal amount= BigDecimal.ZERO;
        BigDecimal costAll;
        //买入之前持有这支证券
        if(stockSql != null) {
            share = share.add(stockSql.getShare());
            if(costFromfile.compareTo(BigDecimal.ZERO) < 0){
                amount = BigDecimal.ZERO.subtract(stock.getCost().multiply(stock.getShare()).add(cost));
            }else{
                amount = stock.getAmount();
            }

            if(share.compareTo(BigDecimal.ZERO) == 0){
                costAll = BigDecimal.ZERO;
                historyAsset.setCode(stockSql.getCode());
                historyAsset.setName(stockSql.getName());
                historyAsset.setBelongTo(stockSql.getBelongTo());
                historyAsset.setRisk(stockSql.getRisk());
                historyAsset.setCost(stockSql.getCost().multiply(stockSql.getShare()).add(cost));
                historyAsset.setProfit(BigDecimal.ZERO.subtract(stockSql.getCost().multiply(stockSql.getShare())).add(amount));
                historyAsset.setAssetType(AssetType.STOCK);
                historyAsset.setEnd(date);
                historyAssetDao.save(historyAsset);
                //cost = stockSql.getCost().multiply(stockSql.getShare()).add(cost);
                //stockDao.delete(stockSql.getCode());
            }else if(share.compareTo(BigDecimal.ZERO) < 0){
                return false;
            }else{
                costAll = stockSql.getCost().multiply(stockSql.getShare()).subtract(amount).divide(share, 3, BigDecimal.ROUND_HALF_EVEN);
            }
            //if(share.compareTo(BigDecimal.ZERO) != 0){
            if(true){
                stockSql.setCost(costAll);
                stockSql.setShare(share);
                stockDao.updateStock(stockSql);
            }
        }else{ //买入时该证券的数量为0
            if(dealType == DealType.SBUY){
                BigDecimal tmp = stock.getCost();
                //计算成本单价时，会引入误差，使利润变大
                if(costFromfile.compareTo(BigDecimal.ZERO) == 0) {
                    amount = stock.getShare().multiply(stock.getCost()).add(cost);
                    stock.setCost(amount.divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
                }else{
                    stock.setCost(stock.getAmount().abs().divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
                }
                stockDao.save(stock);
                stock.setCost(tmp);
            }else if(dealType == DealType.SSELL || dealType == DealType.INTEREST){
                return false;
            }
        }
        //return saveStockDeal(stock, date, cost, amount, dealType);
        return true;
    }

    public boolean saveStockByFile(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while ((line = br.readLine()) != null) { //循环读取行
                String[] segments = line.split("\t"); //按tab分割

                if(segments[11].equals("0156915917") || segments[11].equals("A447655138")){
                    deal.setBelongTo("华010600052829");
                }else if(segments[11].equals("A473653724") || segments[11].equals("0157570856")){
                    deal.setBelongTo("华666600196751");
                }else{
                    continue;
                }
                String contract = segments[3];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date dealDate = sdf.parse(segments[0]);
                if(hasContract(contract, dealDate)){
                    continue;
                }
                deal.setCode(segments[14]);
                deal.setDate(dealDate);
                deal.setName(segments[2]);
                deal.setContract(segments[3]);
                deal.setShare(new BigDecimal(segments[4]));
                if(getStockType(deal.getCode()) == SecurityType.SHLOAN){
                    deal.setShare(deal.getShare().multiply(BigDecimal.TEN));
                }
                deal.setNet(new BigDecimal(segments[5]));
                BigDecimal cost = new BigDecimal(segments[7]);
                cost = cost.add(new BigDecimal(segments[8]));
                cost = cost.add(new BigDecimal(segments[9]));
                deal.setCost(cost);
                deal.setAmount(new BigDecimal(segments[10]));

                if(segments[1].equals("证券买入")){
                    deal.setDealType(DealType.SBUY);
                }else if(segments[1].equals("证券卖出")) {
                    deal.setDealType(DealType.SSELL);
                }else if(segments[1].equals("股息入帐")) {
                    deal.setDealType(DealType.INTEREST);
                }else{
                    deal.setDealType(DealType.OTHERS);
                }

                stock.setCode(deal.getCode());
                stock.setName(deal.getName());
                stock.setCost(deal.getNet());
                stock.setCurrent(deal.getNet());
                stock.setBelongTo(deal.getBelongTo());
                stock.setShare(deal.getShare());
                stock.setAmount(deal.getAmount());
                stock.setCurrency(Currency.RMB);
                if(saveStock(stock, deal.getDate(), deal.getDealType(), deal.getCost())){
                    if(!saveDeal(deal)){
                        return false;
                    }
                }
            }
            br.close();
        }catch (Exception e){
            //log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveDeal(Deal deal){
        return dealDao.updateDeal(deal, AssetType.STOCK);
    }

    public boolean saveStockDeal(Stock stock, Date date, BigDecimal cost, BigDecimal amount, DealType dealType){
        deal.setCode(stock.getCode());
        deal.setName(stock.getName());
        deal.setDate(date);
        deal.setNet(stock.getCost());
        BigDecimal share = stock.getShare();
        deal.setShare(share);
        deal.setCost(cost);
        deal.setAmount(BigDecimal.ZERO.subtract(amount));
        deal.setBelongTo(stock.getBelongTo());
        deal.setDealType(dealType);
        return dealDao.updateDeal(deal, AssetType.STOCK);
    }

    public BigDecimal sum(Currency currency){ return stockDao.sum(currency);}

    public Map<String, BigDecimal> sumByBelongTo(Currency currency){
        List<Map<String, Object>> map = stockDao.sumByBelongTo(currency);
        String belongTo = "";
        BigDecimal amount = BigDecimal.ZERO;
        Map<String, BigDecimal> val = new HashMap<String, BigDecimal>();
        for(Map<String, Object> m : map){
            for(String k : m.keySet()){
                if(k.equals("belongTo")) {
                    belongTo = (String)m.get(k);
                }else {
                    amount = (BigDecimal)m.get(k);
                }
            }
            val.put(belongTo, amount);
        }
        return val;
    }

    public boolean update(Stock stock) {
        if(stock.getRmbCost() == null){
            stock.setRmbCost(stock.getCost());
        }
        return stockDao.updateStock(stock);
    }

    public Map<String, BigDecimal> sumByRisk(Currency currency){
        List<Map<String, Object>> map = stockDao.sumByRisk(currency);
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

    public boolean updateCurrent(Map<String, Object> netMap){ return stockDao.updateCurrent(netMap);}

    //stock统计用的数据
    public Map<String, List<BigDecimal>> addUp(ArrayList<Stock> stocks){
        BigDecimal sum = sum(Currency.RMB);
        Map<String, List<BigDecimal>> map = new HashMap<String, List<BigDecimal>>();
        String str;
        BigDecimal amount;
        BigDecimal amountAll = BigDecimal.valueOf(0);
        BigDecimal tmp;
        BigDecimal cost;
        BigDecimal costAll = BigDecimal.valueOf(0);
        for(Stock stock : stocks){
            List<BigDecimal> list = new ArrayList<BigDecimal>();
            if(stock.getRisk() != null) {
                str = stock.getRisk().getName();
            }else{
                str = Risk.UNKNOW.getName();
            }
            tmp = stock.getCurrent().multiply(stock.getShare());
            if(map.containsKey(str)) {
                list = map.get(str);
                amount = list.get(0).add(tmp);

                cost = list.get(2).add(stock.getCost().multiply(stock.getShare()));
                list.set(0, amount);
                list.set(1, amount.divide(sum, 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
                list.set(2, cost);
                list.set(3, amount.subtract(cost));
                list.set(4, amount.subtract(cost).divide(cost, 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
            }else{
                amount = tmp;
                cost = stock.getCost().multiply(stock.getShare());
                list.add(amount);
                list.add(amount.divide(sum, 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
                list.add(cost);
                list.add(amount.subtract(cost));
                list.add(amount.subtract(cost).divide(cost, 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
            }
            map.put(str, list);
            amountAll = amountAll.add(tmp);
            costAll = costAll.add(stock.getCost().multiply((stock.getShare())));
        }
        try {
            List<BigDecimal> list = new ArrayList<BigDecimal>();
            list.add(amountAll);
            list.add(amountAll.divide(sum, 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
            list.add(costAll);
            list.add(amountAll.subtract(costAll));
            list.add(amountAll.subtract(costAll).divide(costAll, 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
            map.put("总计", list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public Stock readStockByCB(String code, String belongTo){
        return stockDao.getStockByCB(code, belongTo);
    }

    //计算单位交易成本价
    public BigDecimal calDealCost(String code, BigDecimal price, BigDecimal share, DealType buyOrSell){

        BigDecimal cost = BigDecimal.valueOf(0);
        BigDecimal brokerage = BigDecimal.valueOf(0);
        BigDecimal transferFee = BigDecimal.valueOf(0);
        BigDecimal stampDuty = BigDecimal.valueOf(0);

        BigDecimal brokerageStockRate = BigDecimal.valueOf(0.0002);
        BigDecimal brokerageEtfRate = BigDecimal.valueOf(0.0003);
        BigDecimal brokerageABsRate = BigDecimal.valueOf(0.0003);
        BigDecimal lowestABFee = BigDecimal.valueOf(0.1);
        BigDecimal lowestStockBrokerage = BigDecimal.valueOf(5);
        BigDecimal stampDutyRate = BigDecimal.valueOf(0.001);
        BigDecimal transferRate = BigDecimal.valueOf(0.000025);
        BigDecimal lowestTransferFee = BigDecimal.ONE;

        if(etfs.contains(code)){
            brokerage = share.multiply(price).multiply(brokerageEtfRate);
        }else if(money_fund.contains(code)){
            return BigDecimal.ZERO;
        }else if(code.startsWith("150") || code.startsWith("168")){
            brokerage = share.multiply(price).multiply(brokerageABsRate);
        }else if(debts.contains(code)){
            brokerage = cost;
        }else{
            brokerage = share.multiply(price).multiply(brokerageStockRate);
            brokerage = brokerage.compareTo(lowestStockBrokerage) > 1 ? brokerage:lowestStockBrokerage;
        }

        if(buyOrSell == DealType.SSELL){
            if(etfs.contains(code) || code.startsWith("150")){
                stampDuty = BigDecimal.ZERO;
            }else {
                stampDuty = share.multiply(price).multiply(stampDutyRate);
            }
        }

        if(code.startsWith("6")){
            transferFee = share.multiply(price).multiply(transferRate);
//            transferFee = transferFee.compareTo(lowestTransferFee) > 0 ? transferFee:lowestTransferFee;
        }

        cost = brokerage.add(stampDuty).add(transferFee);
        if(code.startsWith("150") && cost.compareTo(lowestABFee) < 0){
            cost = lowestABFee;
        }
        cost = cost.setScale(2,BigDecimal.ROUND_HALF_UP);
        return cost;
    }

    //计算某证券的交易成本价
    public BigDecimal calStockCost(String code, String belongTo, BigDecimal price, BigDecimal share, DealType dealType){
        BigDecimal cost = calDealCost(code, price, share, dealType);
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal costAll = BigDecimal.ZERO;
        Stock stockSql = stockDao.getStockByCB(code, belongTo);
        if (dealType == DealType.SBUY) {
            amount = price.multiply(share).add(cost);
            share = stockSql.getShare().subtract(share);
            if(share.compareTo(BigDecimal.ZERO) != 0){
                costAll = stockSql.getCost().multiply(stockSql.getShare()).subtract(amount).divide(share, 3, BigDecimal.ROUND_HALF_EVEN);
            }else{
                costAll = BigDecimal.ZERO;
            }
        }else if(dealType == DealType.SSELL){
            amount = price.multiply(share).subtract(cost);
            share = stockSql.getShare().add(share);
            costAll = stockSql.getCost().multiply(stockSql.getShare()).add(amount).divide(share, 3, BigDecimal.ROUND_HALF_EVEN);
        }

        return costAll;
    }

    public boolean delete(String code){ return stockDao.delete(code); }

    public SecurityType getStockType(String code){
        if(code.startsWith("122") || code.startsWith("124")){
            return SecurityType.SHLOAN;
        }else if(code.startsWith("112")){
            return SecurityType.SZLOAN;
        }else if(code.startsWith("150") || code.startsWith("510")){
            return SecurityType.SFUND;
        }else if(code.startsWith("600") || code.startsWith("601")){
            return SecurityType.SHSTOCK;
        }else if(code.startsWith("000")){
            return SecurityType.SZSTOCK;
        }else{
            return SecurityType.UNKNOW;
        }
    }

    public boolean hasContract(String contract, Date date){
        if(dealDao.hasContract(contract, date) > 0){
            return true;
        }
        return false;
    }

    public boolean picture(Date date){
        ArrayList<Stock> stocks = read(Currency.RMB);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("type", AssetType.STOCK);
        return stockDao.picture(stocks, map);
    }
}
