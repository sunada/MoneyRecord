package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Fund;
import com.springapp.mvc.Model.Loan;
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
@Repository("loanDao")
public class LoanDao {
    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    @Resource
    private SqlSession sqlSession;

    public List<Loan> getLoanList(boolean valid){
        List<Loan> loanList = new ArrayList<Loan>();
        try{
            if(valid) {
                loanList = sqlSession.selectList("Loans.getValidLoanList");
            }else{
                loanList = sqlSession.selectList("Loans.getNotValidLoanList");
            }
//            log.debug("in FundDao: "  + fundList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return loanList;
    }

    public boolean save(Loan loan){
        int res = -1;
        try{
            res = sqlSession.insert("Loans.insertLoan", loan);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean update(Loan loan){
        int res = -1;
        try{
            res = sqlSession.update("Loans.update", loan);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public HashMap<String, BigDecimal> sum(){
        HashMap<String, BigDecimal> map = sqlSession.selectOne("Loans.getSum");
        return map;
    }

    public List<Map<String, Object>> sumByBelongTo(){
        List<Map<String, Object>> map = sqlSession.selectList("Loans.getSumByBelongTo");
        return map;
    }

    public List<Map<String, Object>> sumByRisk(){
        List<Map<String, Object>> map = sqlSession.selectList("Loans.sumByRisk");
        return map;
    }
}
