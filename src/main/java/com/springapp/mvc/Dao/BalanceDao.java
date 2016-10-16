package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Balance;
import com.springapp.mvc.Model.Expense;
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

    public List<Salary> getSalaryList(String date){
        return sqlSession.selectList("Balance.getSalaryListByDate", date);
    }

    public int saveSalary(Salary salary){ return sqlSession.insert("Balance.saveSalary", salary); }

    public List<Expense> getExpense(){
        List<Expense> expenseList = sqlSession.selectList("Balance.getExpenseList");
        return expenseList;
    }

    public Expense getExpense(String date){
        Expense expense = sqlSession.selectOne("Balance.getExpenseListByDate", date);
        return expense;
    }

    public int saveExpense(Expense expense){
        return sqlSession.insert("Balance.saveExpense", expense);
    }

    public int updateBalance(Balance balance){
        return sqlSession.update("Balance.updateBalance", balance);
    }

    public Balance getBalance(String date){
        return sqlSession.selectOne("Balance.getBalance", date);
    }

    public int saveBalance(Balance balance){
        return sqlSession.insert("Balance.insertBalance", balance);
    }

}
