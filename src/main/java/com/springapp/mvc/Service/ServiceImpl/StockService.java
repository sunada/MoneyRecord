package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.DealDao;
import com.springapp.mvc.Dao.HistoryAssetDao;
import com.springapp.mvc.Dao.StockDao;
import com.springapp.mvc.Model.Strategy;
import com.springapp.mvc.Model.*;
import com.springapp.mvc.Model.Currency;
import com.springapp.mvc.Util.HuGangTongStocks;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Resource
    private Strategy strategy;
    @Resource
    private DealService dealService;

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

    public ArrayList<Stock> read(Currency currency){
        return (ArrayList<Stock>)stockDao.getStockList(currency);
    }

    public ArrayList<Stock> read(Currency currency, int flag){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currency", currency);
        map.put("flag", flag);
        return (ArrayList<Stock>)stockDao.getStockList(map);
    }

    //costFromfile < 0时，表示不是从文件导入的交易记录
//    public boolean saveStock(Stock stock, Date date, DealType dealType, BigDecimal costFromfile){
//        BigDecimal cost;  //单次交易的费用
//        //表示是从页面保存的交易记录，需自己计算交易费用
//        if(costFromfile.compareTo(BigDecimal.ZERO) == 0) {
//            cost = calDealCost(stock.getCode(), stock.getCost(), stock.getShare().abs(), dealType);
//        }else{
//            cost = costFromfile;
//        }
//        BigDecimal share = stock.getShare();
//        Stock stockSql = stockDao.getStockByCB(stock.getCode(), stock.getBelongTo());
//        BigDecimal amount= BigDecimal.ZERO;
//        BigDecimal costAll;
//        //买入之前持有这支证券
//        if(stockSql != null) {
//            if(dealType != DealType.SINTERESTTAX) {
//                share = share.add(stockSql.getShare());
//            }
//            if(costFromfile.compareTo(BigDecimal.ZERO) <= 0){
//                amount = BigDecimal.ZERO.subtract(stock.getCost().multiply(stock.getShare()).add(cost));
//            }else{
//                amount = stock.getAmount();
//            }
//
//            //当交易类型为红利补缴时，share可能为0，此时不需要新存入historyAsset
//            if(share.compareTo(BigDecimal.ZERO) == 0 && dealType != DealType.SINTERESTTAX){
//                costAll = BigDecimal.ZERO;
//                historyAsset.setCode(stockSql.getCode());
//                historyAsset.setName(stockSql.getName());
//                historyAsset.setBelongTo(stockSql.getBelongTo());
//                historyAsset.setRisk(stockSql.getRisk());
//                historyAsset.setCost(stockSql.getCost().multiply(stockSql.getShare()).add(cost));
//                historyAsset.setProfit(amount.subtract(stockSql.getCost().multiply(stockSql.getShare())));
//                historyAsset.setAssetType(AssetType.STOCK);
//                historyAsset.setEnd(date);
//                historyAssetDao.save(historyAsset);
//                //cost = stockSql.getCost().multiply(stockSql.getShare()).add(cost);
//                //stockDao.delete(stockSql.getCode());
//            }else if(share.compareTo(BigDecimal.ZERO) < 0){
//                return false;
//            }else{
//                costAll = stockSql.getCost().multiply(stockSql.getShare()).subtract(amount).divide(share, 3, BigDecimal.ROUND_HALF_EVEN);
//            }
//            //if(share.compareTo(BigDecimal.ZERO) != 0){
//            if(true){
//                stockSql.setCost(costAll);
//                stockSql.setShare(share);
//                stockDao.updateStock(stockSql);
//                updateStrategy(stock,amount);
//            }
//        }else{ //买入时该证券的数量为0
//            if(dealType == DealType.SBUY){
//                BigDecimal tmp = stock.getCost();
//                //计算成本单价时，会引入误差，使利润变大
//                if(costFromfile.compareTo(BigDecimal.ZERO) == 0) {
//                    amount = stock.getShare().multiply(stock.getCost()).add(cost);
//                    stock.setCost(amount.divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
//                }else{
//                    stock.setCost(stock.getAmount().abs().divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
//                }
//                stockDao.save(stock);
//                stock.setCost(tmp);
//            }else if(dealType == DealType.SINTERESTTAX){
//                stock.setCost(stock.getAmount().abs().divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
//                stock.setShare(BigDecimal.ZERO);
//                stockDao.save(stock);
//            }else if(dealType == DealType.SSELL || dealType == DealType.INTEREST){
//                return false;
//            }
//        }
//        //return saveStockDeal(stock, date, cost, amount, dealType);
//        return true;
//    }

    //保存或更新单支股票的持仓数据
    public boolean saveStock(Stock stock, Date date, DealType dealType, BigDecimal costFromfile){
        //若非以下五种类型的交易，不影响证券的成功或持有数量
        if(dealType != DealType.SINTERESTTAX && dealType != DealType.LINTERESTTAX && dealType != DealType.INTEREST
                && dealType != DealType.SBUY && dealType != DealType.SSELL && dealType != DealType.IPO){
            return true;
        }
        BigDecimal cost;  //单次交易的费用
        //表示是从页面保存的交易记录，需自己计算交易费用
        if(costFromfile.compareTo(BigDecimal.ZERO) == 0 && dealType != dealType.IPO) {
            cost = calDealCost(stock.getCode(), stock.getCost(), stock.getShare().abs(), dealType);
        }else{
            cost = costFromfile;
        }
        BigDecimal share = BigDecimal.ZERO;
        Stock stockSql = stockDao.getStockByCB(stock.getCode(),stock.getBelongTo(),stock.getCurrency());
//        if(stock.getCode() != "") stockDao.getStockByCB(stock.getCode(),stock.getBelongTo(),stock.getCurrency());
//        else stockSql = stockDao.getStockByNB(stock.getName(), stock.getCurrency(), stock.getBelongTo());
        BigDecimal amount= BigDecimal.ZERO;
        BigDecimal costAll = BigDecimal.ZERO;
        //之前持有过这支证券
        if(stockSql != null) {
            if(dealType != DealType.SINTERESTTAX && dealType != DealType.INTEREST) {
                share = stock.getShare().add(stockSql.getShare());
            }else{
                share = stockSql.getShare();
            }
            if(share.compareTo(BigDecimal.ZERO) < 0){
                return false;
            }

            if(dealType != DealType.SINTERESTTAX && dealType != DealType.INTEREST && costFromfile.compareTo(BigDecimal.ZERO) <= 0){
                amount = BigDecimal.ZERO.subtract(stock.getCost().multiply(stock.getShare()).add(cost));
            }else{
                amount = stock.getAmount();
            }
            BigDecimal sumAmount = dealService.sumDealsAmount(stock.getCode(),stock.getName(),
                    stock.getBelongTo(), stock.getType().getName(),stock.getCurrency());

            if(sumAmount != null) sumAmount = sumAmount.add(amount);
            else return false;

            //操作后不持有该股票
            if(share.compareTo(BigDecimal.ZERO) == 0) {
                HistoryAsset historySql = historyAssetDao.getHistoryAsset(stockSql.getCode(), stockSql.getBelongTo());
                if (historySql == null) {
                    costAll = BigDecimal.ZERO;
                    historyAsset.setCode(stockSql.getCode());
                    historyAsset.setName(stockSql.getName());
                    historyAsset.setBelongTo(stockSql.getBelongTo());
                    historyAsset.setRisk(stockSql.getRisk());
//                    historyAsset.setCost(costAll);
                    historyAsset.setProfit(sumAmount);
                    historyAsset.setAssetType(stock.getType());
//                    historyAsset.setEnd(date);
                    historyAssetDao.save(historyAsset);
                } else {
                    historySql.setProfit(sumAmount);
                    historyAssetDao.updateProfit(historySql);
                }
            }else{
                costAll = sumAmount.abs().divide(share, 3, BigDecimal.ROUND_HALF_EVEN);
            }
//            }else if(dealType != DealType.SINTERESTTAX){  //红利税也会影响成本呀，为啥红利税要排除掉？
//                costAll = sumAmount.abs().divide(share, 3, BigDecimal.ROUND_HALF_EVEN);
//            }

            if(true){
                stockSql.setCost(costAll);  //若清仓证券，则成本清零
                stockSql.setShare(share);
                stockDao.updateStock(stockSql);
                updateStrategy(stock,amount);
            }
            //if(share.compareTo(BigDecimal.ZERO) != 0){
        }else{ //未持有该证券
//            if(dealType == DealType.SBUY || dealType == DealType.SINTERESTTAX || dealType == DealType.IPO){
//                BigDecimal tmp = stock.getCost();
//                //手动输入交易记录。计算成本单价时，会引入误差，使利润变大
//                if(costFromfile.compareTo(BigDecimal.ZERO) == 0) {
//                    amount = stock.getShare().multiply(stock.getCost()).add(cost);
//                    stock.setCost(amount.divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
//                }else{
//                    stock.setCost(stock.getAmount().abs().divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
//                }
//                stockDao.save(stock);
//                stock.setCost(tmp);
//            }else if(dealType == DealType.SSELL || dealType == DealType.INTEREST){
//                return false;
//            }
            BigDecimal tmp = stock.getCost();
            if(costFromfile.compareTo(BigDecimal.ZERO) == 0) {
                amount = stock.getShare().multiply(stock.getCost()).add(cost);
                stock.setCost(amount.divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
            }else{
                stock.setCost(stock.getAmount().abs().divide(stock.getShare(), 3, BigDecimal.ROUND_HALF_EVEN));
            }
            stockDao.save(stock);
            stock.setCost(tmp);
        }
        //return saveStockDeal(stock, date, cost, amount, dealType);
        return true;
    }

    public boolean saveStockByFile(File file){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            String[] sep = line.split("\t");
            if(sep.length < 1){
                return false;
            }
            if(sep[1].equals("成交时间")){
                return saveStockByFileGuojin(br);
            }
            return saveStockByFileHuatai(file);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveStockByFileGuojin(BufferedReader br){
        String line = "";
        try{
            while((line = br.readLine()) != null) {
                String[] sep = line.split("\t");
                if (sep[3].equals("国金金腾通")) {
                    continue;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date dealDate = sdf.parse(sep[0]);
                String time = sep[1];
                int len = sep[2].length();
                if (len >= 6) {
                    deal.setCode(sep[2]);
                } else {
                    String tmp = "";
                    len = 6 - len;
                    while (len-- > 0) {
                        tmp += "0";
                    }
                    deal.setCode(tmp + sep[2]);
                }

                if (hasContract(deal.getBelongTo(), time, dealDate, deal.getCode(),deal.getAmount())) {
                    continue;
                }
                deal.setBelongTo("国金39997769");
                deal.setDate(dealDate);

                deal.setName(sep[3]);
                deal.setContract(time);
                deal.setShare(new BigDecimal(sep[5]));

                if (getStockType(deal.getCode()) == SecurityType.SHLOAN) {
                    deal.setShare(deal.getShare().multiply(BigDecimal.TEN));
                }
                deal.setNet(new BigDecimal(sep[6]));
                BigDecimal cost = new BigDecimal(sep[10]);
                cost = cost.add(new BigDecimal(sep[11]));
                cost = cost.add(new BigDecimal(sep[12]));
                deal.setCost(cost);
                deal.setAmount(new BigDecimal(sep[9]));

                if (sep[4].equals("证券买入")) {
                    deal.setDealType(DealType.SBUY);
                } else if (sep[4].equals("证券卖出")) {
                    deal.setDealType(DealType.SSELL);
                } else if (sep[4].equals("股息入帐")) {
                    deal.setDealType(DealType.INTEREST);
                } else if (sep[4].equals("股息红利税补缴")) {
                    deal.setDealType(DealType.SINTERESTTAX);
                } else{
                    deal.setDealType(DealType.OTHERS);
                }

                stock.setCode(deal.getCode());
                stock.setName(deal.getName());
                stock.setCost(deal.getNet());
                stock.setCurrent(deal.getNet());
                stock.setBelongTo(deal.getBelongTo());
                stock.setShare(deal.getShare());
                stock.setAmount(deal.getAmount());
                stock.setCurrency(Currency.CNY);
                stock.setRisk(Risk.HIGH);
                stock.setType(AssetType.STOCK);
                if (saveStock(stock, deal.getDate(), deal.getDealType(), deal.getCost())) {
                    if (!saveDeal(deal)) {
                        return false;
                    }
                }
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveStockByFileHuatai(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while ((line = br.readLine()) != null) { //循环读取行
                String[] segments = line.split("\t"); //按tab分割

                if(segments.length > 18){
                    if(segments[19].equals("沪港通") || segments[19].equals("深港通") || segments[20].equals("沪港通")
                        || segments[20].equals("深港通")){
                        deal = saveStockByFileHuataiHugangtong(segments);
                        stock.setCurrency(Currency.HKD);
                    }
                }else {
                    deal.setAmount(new BigDecimal(segments[10]));
                    deal.setShare(new BigDecimal(segments[4]));
                    if (deal.getAmount().compareTo(BigDecimal.ZERO) == 0 || deal.getShare().compareTo(BigDecimal.ZERO) == 0){
                        if(segments[1].equals("申购配号") || segments[1].equals("基金资金拨出"))   continue;
                    }
                    stock.setCurrency(Currency.CNY);
                    deal.setCurrency(Currency.CNY);

                    if (segments[11].equals("0156915917") || segments[11].equals("A447655138") || segments[11].equals("156915917")) {
                        deal.setBelongTo("华010600052829");
                    } else if (segments[11].equals("A473653724") || segments[11].equals("0157570856")) {
                        deal.setBelongTo("华666600196751");
                    } else {
                        continue;
                    }
                    String contract = segments[3];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date dealDate = sdf.parse(segments[0]);

                    String code = segments[14];
                    while(code.length() < 6){
                        code = "0" + code;
                    }
                    if (hasContract(deal.getBelongTo(), contract, dealDate, code, deal.getAmount())) {
                        continue;
                    }
                    deal.setCode(code);
                    deal.setDate(dealDate);
                    deal.setName(segments[2]);
                    deal.setContract(segments[3]);

                    if (getStockType(deal.getCode()) == SecurityType.SHLOAN) {
                        deal.setShare(deal.getShare().multiply(BigDecimal.TEN));
                    }
                    deal.setNet(new BigDecimal(segments[5]));
                    BigDecimal cost = new BigDecimal(segments[7]);
                    cost = cost.add(new BigDecimal(segments[8]));
                    cost = cost.add(new BigDecimal(segments[9]));
                    deal.setCost(cost);
                }

                if(deal == null) continue;
                if(segments[1].equals("证券买入")){
                    deal.setDealType(DealType.SBUY);
                }else if(segments[1].equals("新股申购确认缴款")){
                    deal.setDealType(DealType.IPO);
                }else if(segments[1].equals("证券卖出")) {
                    deal.setDealType(DealType.SSELL);
                }else if(segments[1].equals("股息入帐")) {
                    deal.setDealType(DealType.INTEREST);
                }else if (segments[1].equals("股息红利税补缴")) {
                    deal.setDealType(DealType.SINTERESTTAX);
                }else if(segments[1].equals("港股通组合费收取")) {
                    continue;
                }else{
                    deal.setDealType(DealType.OTHERS);
                }

                stock.setCode(deal.getCode());
                stock.setName(deal.getName());
                stock.setCost(deal.getCost());
                stock.setCurrent(deal.getNet());
                stock.setBelongTo(deal.getBelongTo());
                stock.setShare(deal.getShare());
                stock.setAmount(deal.getAmount());
                stock.setType(AssetType.STOCK);
                stock.setRisk(Risk.HIGH);
                if(saveStock(stock, deal.getDate(), deal.getDealType(), deal.getCost())){
                    if(!saveDeal(deal)){
                        return false;
                    }
                }
            }
            br.close();
            file.exists();
        }catch (Exception e){
            //log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Deal saveStockByFileHuataiHugangtong(String[] segments){
        deal.setAmount(new BigDecimal(segments[14]));
        deal.setShare(new BigDecimal(segments[4]));
        deal.setCurrency(Currency.HKD);
        if (deal.getAmount().compareTo(BigDecimal.ZERO) == 0 || deal.getShare().compareTo(BigDecimal.ZERO) == 0)
            return null;
        if (segments[17].equals("0156915917") || segments[17].equals("A447655138") || segments[17].equals("156915917")) {
            deal.setBelongTo("华010600052829");
        } else if (segments[17].equals("A473653724") || segments[17].equals("0157570856")) {
            deal.setBelongTo("华666600196751");
        } else {
            return null;
        }
        String contract = segments[3];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dealDate = sdf.parse(segments[0]);

            if (hasContract(deal.getBelongTo(), contract, dealDate, segments[2], deal.getAmount())) {
                return null;
            }
//            String code = HuGangTongStocks.INSTANCE.getStocks().get(deal.getName());
            deal.setName(segments[2]);
            String code = getCodeFromName(deal.getName());
            if(code == null) return null;
            deal.setCode(code);
            deal.setDate(dealDate);
            deal.setContract(segments[3]);
            deal.setNet(new BigDecimal(segments[5]));
            deal.setCurrency(Currency.HKD);
            BigDecimal cost = new BigDecimal(segments[7]);
            cost = cost.add(new BigDecimal(segments[8]));
            cost = cost.add(new BigDecimal(segments[9]));
            cost = cost.add(new BigDecimal(segments[10]));
            cost = cost.add(new BigDecimal(segments[11]));
            cost = cost.add(new BigDecimal(segments[12]));
            cost = cost.add(new BigDecimal(segments[13]));
            deal.setCost(cost);
        }catch (Exception e){
            e.printStackTrace();
        }
        return deal;
    }

    public String getCodeFromName(String name){
        String code = dealDao.getCodeFromName(name);
        if(code == null || code.equals("")){
//            Map<String, String> stocks = getStocksFromWebHu();
            Map<String, String> stocks = getStocksFromWebHuShen();
            return stocks.get(name);
        }
        return code;
    }

    //保存沪港通代码及名称
    public Map<String, String> getStocksFromWebHu() {
        Map<String, String> stocks = new HashMap<String, String>();
        String url = "http://www.sse.com.cn/services/hkexsc/disclo/eligible/";
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), "UTF8", url);
            String tmp = doc.data().split(";")[20];
            tmp = tmp.substring(33);
            JSONObject json = JSONObject.fromObject(tmp);
            String list = json.getString("list");
            String[] seps = json.getString("list").split("]");

            for (String s : seps) {
                String code = s.replaceAll("[^(0-9)]*", "");
                Pattern p = Pattern.compile("([\\u4e00-\\u9fa5]+)");
                Matcher m = p.matcher(s);
                if (m.find()) {
                    String name = m.group(0);
                    stocks.put(name, code);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("code", code);
                    map.put("name", name);
                    sqlSession.update("Deals.insertHuGangTong", map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return stocks;
        }
    }

    //保存沪港通&深港通代码及名称
//    http://www.szse.cn/main/szhk/ggtywxx/bdzqmd/
    public Map<String, String> getStocksFromWebHuShen(){
        Map<String, String> stocks = new HashMap<String, String>();
        sqlSession.delete("Deals.deleteHuGangTong");
        try{
//            BufferedReader br = new BufferedReader(new FileReader("D:\\港股通标的证券名单.csv"));
            BufferedReader br = new BufferedReader(new FileReader("D:\\haha5.csv"));
            String line = "";
            String[] sep = line.split(",");
            if(sep.length < 1){
                return stocks;
            }
            Deal deal = null;
            while ((line = br.readLine()) != null) { //循环读取行
                String[] segments = line.split(","); //按tab分割
                if(segments.length < 2) continue;
                String code = segments[0];
                String name = segments[1];
                Map<String, String> map = new HashMap<String, String>();
                map.put("code", code);
                map.put("name", name);
                stocks.put(name, code);
                sqlSession.update("Deals.insertHuGangTong", map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return stocks;
        }
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

    public boolean updateStrategy(Stock stock, BigDecimal amount){
        String strategyCode = getStrategyCode(stock.getCode(),stock.getName(),stock.getBelongTo(),stock.getCurrency());
        return updateStrategy(amount, strategyCode);
    }

    public boolean updateStrategy(String stockCode, String belongTo, String strategyCode,Currency currency){
        stock = stockDao.getStockByCB(stockCode, belongTo,currency);
        strategyAdd(stockCode, belongTo, strategyCode);
        BigDecimal amount = stock.getCost().multiply(stock.getShare());
        amount = BigDecimal.ZERO.subtract(amount);
        return updateStrategy(amount, strategyCode);
    }

    public boolean updateStrategy(BigDecimal amount, String strategyCode){
        strategy = getStrategyByCode(strategyCode);
        if(strategy == null){
            return true;
        }
        strategy.setCash(strategy.getCash().add(amount));
        stockDao.strategyUpdate(strategy);
        return true;
    }

    public Strategy getStrategyByCode(String code){
        return stockDao.getStrategyByCode(code);
    }

    public String getStrategyCode(String code, String name, String belongTo, Currency currency){
//        if(code != "") return stockDao.getStockByCB(code,belongTo).getStragetyCode();
        return stockDao.getStockByCB(code, belongTo,currency).getStragetyCode();
    }

    public List<Stock> getStocksWithoutStrategy(){
        return stockDao.getStocksWithoutStrategy();
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
        BigDecimal sum = sum(Currency.CNY);
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

    public Map addUpByType() {
        Map map = stockDao.sumByType();
        BigDecimal amount = new BigDecimal(0);
        for(Object key : map.keySet()){
            amount = amount.add((BigDecimal)map.get(key));
        }
        map.put("总计",amount);
        return map;
    }

    public Stock readStockByCB(String code, String belongTo,Currency currency){
        return stockDao.getStockByCB(code, belongTo,currency);
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
        Stock stockSql = stockDao.getStockByCB(code, belongTo,Currency.CNY);
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

    public boolean hasContract(String belongTo, String contract, Date date, String code, BigDecimal amount){
        if(dealDao.hasContract(belongTo, contract, date, code, amount) > 0){
            return true;
        }
        return false;
    }

//    public boolean hasContractByName(String belongTo, String contract, Date date, String name, BigDecimal amount){
//        if(dealDao.hasContractByName(belongTo, contract, date, name, amount) > 0){
//            return true;
//        }
//        return false;
//    }

    public boolean picture(Date date){
        ArrayList<Stock> stocks = read(Currency.CNY, 0);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("type", AssetType.STOCK);
        return stockDao.picture(stocks, map);
    }

    public Date getLatestPictureDate(){
        return stockDao.getLatestPictureDate(AssetType.STOCK);
    }

    public List<Stock> getStrategyStocks(String strategyCode){
        return stockDao.getStrategyStocks(strategyCode);
    }

    public List<Stock> getAllStrategyStocks(String strategyCode){
        return stockDao.getAllStrategyStocks(strategyCode);
    }

    public List<Strategy> getStrategys(){
        return stockDao.getStrategys();
    }

    public int strategyAdd(Strategy strategy){
        return stockDao.strategyAdd(strategy);
    }

    public int strategyAdd(String code, String belongTo, String strategyCode){
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("belongTo", belongTo);
        map.put("strategyCode", strategyCode);
        return stockDao.strategyAdd(map);
    }

    public int strategyUpdate(Strategy strategy){ return stockDao.strategyUpdate(strategy);}

    public Map<String, BigDecimal> getStrategyValue() {return stockDao.getStrategyValue();}
}
