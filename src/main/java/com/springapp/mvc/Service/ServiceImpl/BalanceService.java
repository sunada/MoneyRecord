package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.BalanceDao;
import com.springapp.mvc.Model.Balance;
import com.springapp.mvc.Model.Expense;
import com.springapp.mvc.Model.Income;
import com.springapp.mvc.Model.Salary;
import com.springapp.mvc.Util.ConstantInterface;
import org.springframework.stereotype.Service;
import sun.security.krb5.Config;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016/10/10.
 */
@Service
public class BalanceService {
    @Resource
    private BalanceDao balanceDao;
    @Resource
    private Income income;
    @Resource
    private Expense expense;
    @Resource
    private Balance balance;

    public List<Salary> getSalaryList(){
        return balanceDao.getSalaryList();
    }

    public int saveSalary(Salary salary){
        return balanceDao.saveSalary(salary);
    }

    public int saveExpense(Expense expense){
        return balanceDao.saveExpense(expense);
    }

    public List<Expense> getExpenseMap(){
        return balanceDao.getExpense();
    }

    public List<Balance> getBalanceList(List<Salary> salaryList, List<Expense> expenseList){
        ArrayList<Balance> balanceList = new ArrayList<Balance>();
        String date = "";
        ArrayList<Income> incomeList = null;
        Map<String, ArrayList<Income>> incomeMap = new HashMap<String, ArrayList<Income>>();

        for(Salary s : salaryList){
            Income i = new Income();
            if(!date.equals(s.getDate())) {
                incomeList = new ArrayList<Income>();
                date = s.getDate();
            }
            i.setOwner(s.getOwner());
            i.setHouseFundsCompany(s.getHouseFundsCompany());
            i.setHouseFunds(s.getHouseFunds());
            i.setAfterTax(s.getAfterTax());
            i.setIncomeAll();
            i.setDate(s.getDate());
            if(incomeMap.containsKey(date)){
                incomeMap.get(date).add(i);
            }else {
                incomeList.add(i);
                incomeMap.put(date, incomeList);
            }
        }

        Map<String, Expense> expenseMap = new HashMap<String, Expense>();
        for(Expense e : expenseList){
            if(!expenseMap.containsKey(e.getDate())){
                expenseMap.put(e.getDate(), e);
            }
        }

        for(String key : incomeMap.keySet()){
            Balance b = new Balance();
            b.setDate(key);
            b.setIncomeList(incomeMap.get(key));
            b.setExpense(expenseMap.get(key));
            balanceList.add(b);
        }


        for(String key : expenseMap.keySet()){
            if(!incomeMap.containsKey(key)){
                Balance b = new Balance();
                b.setDate(key);
                b.setIncomeList(null);
                b.setExpense(expenseMap.get(key));
                balanceList.add(b);
            }
        }

        for(Balance b : balanceList){
            BigDecimal incomeSum = incomeSum(b.getIncomeList());
            BigDecimal expenseSum = BigDecimal.ZERO;
            if(b.getExpense() != null){
                expenseSum = b.getExpense().getDailyExpense().add(b.getExpense().getMortgage());
            }
            b.setLeft(incomeSum.add(expenseSum));
            Balance tmp = balanceDao.getBalance(b.getDate());
            b.setBudget(tmp.getBudget());
            b.setMonthBudgetLeftSum(tmp.getMonthBudgetLeftSum());
            b.setMonthBudgetLeft(tmp.getMonthBudgetLeft());
        }
        return balanceList;
    }

    public BigDecimal incomeSum(List<Income> incomeList){
        if(incomeList == null){
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Income i : incomeList){
            sum = sum.add(i.getIncomeAll());
        }
        return sum;
    }

    public BigDecimal salarySum(List<Salary> salaryList){
        if(salaryList == null){
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Salary i : salaryList){
            sum = sum.add(i.getHouseFundsCompany()).add(i.getHouseFunds().add(i.getAfterTax()));
        }
        return sum;
    }

    public int updateBalance(String date, Salary salary){
        BigDecimal income = salary.getAfterTax().add(salary.getHouseFundsCompany()).add(salary.getHouseFunds());
        balance = balanceDao.getBalance(date);
        if(balance != null) {
            balance.setLeft(balance.getLeft().add(income));
            balance.setMonthBudgetLeftSum(balance.getMonthBudgetLeftSum().add(income));
            return balanceDao.updateBalance(balance);
        }else{
            balance = new Balance();
            balance.setDate(date);
            balance.setLeft(income);
            balance.setBudget(ConstantInterface.Budget);
            balance.setMonthBudgetLeftSum(income);
            balance.setMonthBudgetLeft(ConstantInterface.Budget);
            return balanceDao.saveBalance(balance);
        }
    }

    public int updateBalance(String date, Expense expense){
        BigDecimal exp = expense.getDailyExpense();
        balance = balanceDao.getBalance(date);
        if(balance != null) {
            balance.setLeft(balance.getLeft().add(exp));
            balance.setMonthBudgetLeftSum(balance.getMonthBudgetLeftSum().add(exp));
            balance.setMonthBudgetLeft(balance.getMonthBudgetLeft().add(exp));
            return balanceDao.updateBalance(balance);
        }else{
            balance = new Balance();
            balance.setDate(date);
            balance.setLeft(exp);
            balance.setMonthBudgetLeft(ConstantInterface.Budget.add(exp));
            balance.setMonthBudgetLeftSum(BigDecimal.ZERO);
            return balanceDao.saveBalance(balance);
        }
    }
}
