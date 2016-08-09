package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Deal;
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
@Repository("myFundDao")
public class MyFundDao {
    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    @Resource
    private SqlSession sqlSession;

    public List<MyFund> getMyFundList(){
        List<MyFund> myFundList = new ArrayList<MyFund>();
        try{
            myFundList = sqlSession.selectList("MyFunds.getMyFundList");
        }catch (Exception e){
            log.debug(e.toString());
        }
        return myFundList;
    }

    public List<MyFund> getMyHistoryFundList(){
        List<MyFund> myFundList = new ArrayList<MyFund>();
        try{
            myFundList = sqlSession.selectList("MyFunds.getMyHistoryFundList");
        }catch (Exception e){
            log.debug(e.toString());
        }
        return myFundList;
    }

//    public List<Deal> getFundDeals(Map<String, String> map){
//        List<Deal> list = new ArrayList<Deal>();
//        try{
//            list = sqlSession.selectList("Deals.getFundDeals", map);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }

    public MyFund getMyFundByCB(Map<String,String> map){
        return sqlSession.selectOne("MyFunds.getMyFundByCB", map);
    }

    public List<Map<String, Object>> sumByRisk(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = sqlSession.selectList("MyFunds.sumByRisk");
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean save(MyFund myFund){
        int res = -1;
        try{
            res = sqlSession.insert("MyFunds.insertMyFund", myFund);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean updateMyFund(MyFund myFund){
        int res = -1;
        try{
            res = sqlSession.update("MyFunds.updateMyFund", myFund);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public BigDecimal sum(){
        HashMap<String, BigDecimal> map = sqlSession.selectOne("MyFunds.getSum");

        BigDecimal sum = new BigDecimal(0.00);
        for(String key : map.keySet()){
            sum = map.get(key);
        }
        return sum;
    }

    public boolean updateNet(Map<String, Object> map){
        int res = -1;

        try{
            res = sqlSession.update("MyFunds.updateNet", map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean updateMoneyFundShare(Map<String, Object> map){
        int res = -1;
        try{
            res = sqlSession.update("MyFunds.updateMoneyFundShare", map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != 1;
    }

//    public boolean updateFundDeal(Deal deal){
//        int res = -1;
//
//        try{
//            res = sqlSession.update("Deals.updateFundDeal", deal);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return res != -1;
//    }

//    public Date getNearDealDate(String code){
//        Map<String, Date> map = sqlSession.selectOne("Deals.getNearDealDate", code);
//        Date date;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        if(map == null) {
//            try {
//                date = sdf.parse("1971-12-12");
//                return date;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return map.get("date");
//
//    }

}
