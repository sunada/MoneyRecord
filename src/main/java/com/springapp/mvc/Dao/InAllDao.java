package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Deal;
import com.springapp.mvc.Model.Fund;
import com.springapp.mvc.Model.InAll;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016/4/25.
 */
@Repository("allDao")
public class InAllDao {
    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    @Resource
    private SqlSession sqlSession;

    public List<InAll> getAllList(){
        List<InAll> allList = new ArrayList<InAll>();
        try{
            allList = sqlSession.selectList("InAlls.getAllList");
        }catch (Exception e){
            e.printStackTrace();
        }
        return allList;
    }

    public BigDecimal sum(){
        HashMap<String, BigDecimal> map = sqlSession.selectOne("InAlls.getSum");

        BigDecimal sum = new BigDecimal(0.00);
        for(String key : map.keySet()){
            sum = map.get(key);
        }
        return sum;
    }

    public List<Deal> getFundDealList(Map<String, Object> map){
        return sqlSession.selectList("Deals.getFundListStartEnd", map);
    }

    public List<Deal> getStockDealList(Map<String, Object> map){
        return sqlSession.selectList("Deals.getStockListStartEnd", map);
    }
}
