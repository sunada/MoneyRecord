package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Currency;
import com.springapp.mvc.Model.Loan;
import com.springapp.mvc.Model.Stock;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
