package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.AssetType;
import com.springapp.mvc.Model.Deal;
import com.springapp.mvc.Model.DealType;
import com.springapp.mvc.Model.MyFund;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/11/10.
 */
@Repository("dealDao")
public class DealDao {
    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    @Resource
    private SqlSession sqlSession;


    public List<Deal> getDeals(Map<String, String> map, String fundOrStock){
        List<Deal> list = new ArrayList<Deal>();
        try{
            if(fundOrStock.equals("fund")) {
                list = sqlSession.selectList("Deals.getFundDeals", map);
            }else{
                list = sqlSession.selectList("Deals.getStockDeals", map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateDeal(Deal deal, AssetType assetType){
        int res = -1;
        try{
            if(assetType == AssetType.FUND) {
                res = sqlSession.update("Deals.updateFundDeal", deal);
            }else{
                res = sqlSession.update("Deals.updateStockDeal", deal);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public Date getNearDealDate(String code, String belongTo, Date startTime){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("code", code);
        param.put("belong_to", belongTo);
        param.put("start_time", startTime);
        Map<String, Date> map = sqlSession.selectOne("Deals.getNearDealDate", param);
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(map == null) {
            try {
                date = sdf.parse("1971-12-12");
                return date;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map.get("date");

    }

    public boolean delete(int id, String fundOrStock){
        int res = -1;
        try{
            if(fundOrStock.equals("fund")) {
                res = sqlSession.delete("Deals.deleteFundDeal", id);
            }else{
                res = sqlSession.delete("Deals.deleteStockDeal", id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public BigDecimal sumCost(String code, String fundOrStock){
        BigDecimal res = BigDecimal.ZERO;
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        try{
            if(fundOrStock.equals("fund")) {
                map = sqlSession.selectOne("Deals.sumFundCost", code);
            }else{
                map = sqlSession.selectOne("Deals.sumStockCost", code);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(map == null){
            return BigDecimal.ZERO;
        }
        for(String key : map.keySet()){
            res = map.get(key);
        }
        return res;
    }

    public Map getAccountNewDealDate(AssetType type){
//        List<Map<String, Date>> list = new ArrayList<Map<String, Date>>();
        @SuppressWarnings("rawtypes")
        Map map = null;
        FblMapResultHandler fbl = new FblMapResultHandler();
        try{
            if(type.equals("fund")){
//                list = sqlSession.selectList("Deals.getAccountFundNewDealDate");
                sqlSession.select("Deals.getAccountFundNewDealDate",fbl);
                map =fbl.getMappedResults();
            }else{
//                list = sqlSession.selectList("Deals.getAccountStockNewDealDate");
                sqlSession.select("Deals.getAccountStockNewDealDate",fbl);
                map =fbl.getMappedResults();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public int hasContract(String contract, Date date){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contract", contract);
        map.put("date", date);
        return sqlSession.selectOne("Deals.hasContract", map);
    }
}
