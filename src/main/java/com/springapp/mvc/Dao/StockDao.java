package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Model.Currency;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2015/11/10.
 */
@Repository("stockDao")
public class StockDao {
    private static Logger log = LoggerFactory.getLogger(StockDao.class);

    @Resource
    private SqlSession sqlSession;

    public List<Stock> getStockList(Currency currency){
        List<Stock> stockList = new ArrayList<Stock>();
        try{
            stockList = sqlSession.selectList("Stocks.getStockList", currency);
        }catch (Exception e){
            e.printStackTrace();
        }
        return stockList;
    }

    public List<Stock> getStockList(Map<String, Object> map){
        List<Stock> stockList = new ArrayList<Stock>();
        try{
            stockList = sqlSession.selectList("Stocks.getStockListMap", map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return stockList;
    }

    public List<Stock> getHistoryStockList(){
        List<Stock> stockList = new ArrayList<Stock>();
        try{
            stockList = sqlSession.selectList("Stocks.getHistoryStockList");
        }catch (Exception e){
            e.printStackTrace();
        }
        return stockList;
    }

    public Stock getStockByCB(String code, String belongTo){
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("belongTo", belongTo);
        return sqlSession.selectOne("Stocks.getStockByCB", map);
    }

    public boolean save(Stock stock){
        int res = -1;
        try{
            res = sqlSession.insert("Stocks.insertStock", stock);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean updateStock(Stock stock){
        int res = -1;
        try{
            res = sqlSession.update("Stocks.updateStock", stock);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public BigDecimal sum(Currency currency){
        HashMap<String, BigDecimal> map = sqlSession.selectOne("Stocks.getSum", currency);

        BigDecimal sum = new BigDecimal(0.00);
        for(String key : map.keySet()){
            sum = map.get(key);
        }
        return sum;
    }

    public List<Map<String, Object>> sumByBelongTo(Currency currency){
        List<Map<String, Object>> map = sqlSession.selectList("Stocks.sumByBelongTo", currency);
        return map;
    }

    public Map sumByType(){
        FblMapResultHandler fbl = new FblMapResultHandler();
        sqlSession.select("Stocks.sumByType",fbl);
        @SuppressWarnings("rawtypes")
        Map map =fbl.getMappedResults();
        return map;
    }

    public List<Map<String, Object>> sumByRisk(Currency currency){
        List<Map<String, Object>> map = sqlSession.selectList("Stocks.sumByRisk", currency);
        return map;
    }

    public boolean updateCurrent(Map<String, Object> netMap){
        int res = -1;
        try{
            res = sqlSession.update("Stocks.updateCurrent", netMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean delete(String code){
        int res = -1;
        try{
            res = sqlSession.delete("Stocks.delete", code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean picture(ArrayList<Stock> stocks, Map<String, Object> map){
        int res = -1;
        try{
            res = sqlSession.insert("Stocks.picture", stocks);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        res = sqlSession.update("Stocks.updatePicture", map);
        return res != -1;
    }

    public Date getLatestPictureDate(AssetType type){
        Date date = sqlSession.selectOne("MyFunds.getLatestPictureDate", type);
        return date;
    }

    public List<Stock> getStrategyStocks(String strategyCode){
        return sqlSession.selectList("Stocks.getStrategyStocks", strategyCode);
    }

    public List<Strategy> getStrategys(){
        return sqlSession.selectList("Stocks.getStrategys");
    }

    public int strategyAdd(Strategy strategy){
        return sqlSession.insert("Stocks.strategyAdd", strategy);
    }

    public int strategyUpgrade(Strategy strategy) { return sqlSession.update("Stocks.strategyUpgrade", strategy);}
}
