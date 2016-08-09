package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.LoanDao;
import com.springapp.mvc.Model.Loan;
import com.springapp.mvc.Model.MyFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/24.
 */
@Service("loanService")
public class LoanService {

    private LoanDao loanDao;

    @Autowired
    public void setLoanDao(LoanDao loanDao){this.loanDao = loanDao;}

    public ArrayList<Loan> read(boolean valid){ return (ArrayList<Loan>)loanDao.getLoanList(valid); }

    public boolean saveLoan(Loan loan){
        return loanDao.save(loan);
    }

    public boolean update(Loan loan) { return loanDao.update(loan); }

    public HashMap<String, BigDecimal> sum(){ return loanDao.sum();}

    public Map<String, BigDecimal> sumByRisk(){
        List<Map<String, Object>> map = loanDao.sumByRisk();
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

    public Map<String, BigDecimal> sumByBelongTo() {
        List<Map<String, Object>> map = loanDao.sumByBelongTo();

        Map<String, BigDecimal> g = new HashMap<String, BigDecimal>();
        String belongTo = "";
        BigDecimal amount = BigDecimal.valueOf(0);
        BigDecimal val = BigDecimal.valueOf(0);

        for(Map<String, Object> m : map){
            for(String k : m.keySet()){
                if(k.equals("belongTo")) {
                    belongTo = (String)m.get(k);
                }else {
                    amount = (BigDecimal)m.get(k);
                }
            }
            val = val.add(amount);
            if(g.containsKey(belongTo)){
                amount = amount.add(g.get(belongTo));
            }

            g.put(belongTo, amount);
        }
        return g;
    }
}
