package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Salary;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016/10/11.
 */
@Repository
public class BalanceDao {
    @Resource
    private SqlSession sqlSession;

    public List<Salary> getSalaryList(){
        return sqlSession.selectList("Balance.getSalaryList");
    }

    public int saveSalary(Salary salary){ return sqlSession.insert("Balance.saveSalary", salary); }

    public Map<String, Object> getExpenseMap(){
        HashMap<String, Object> map = sqlSession.selectOne("Balance.getExpenseList");
        return map;
    }
    public int saveExpense(Date date, BigDecimal dailyExpense, BigDecimal mortgage){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("dailyExpense", dailyExpense);
        map.put("mortgage", mortgage);
        return sqlSession.insert("Balance.saveExpense", map);
    }
}
