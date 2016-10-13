package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Salary;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

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
}
