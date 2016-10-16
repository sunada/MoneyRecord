package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/16.
 */
@Repository
public class Balance {
    private String date;
    private Expense expense;
    private ArrayList<Income> incomeList;
    private BigDecimal budget;
    private BigDecimal left;
    private BigDecimal monthBudgetLeft;

    public BigDecimal getMonthBudgetLeft() {
        return monthBudgetLeft;
    }

    public void setMonthBudgetLeft(BigDecimal monthBudgetLeft) {
        this.monthBudgetLeft = monthBudgetLeft;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public ArrayList<Income> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(ArrayList<Income> incomeList) {
        this.incomeList = incomeList;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getLeft() {
        return left;
    }

    public void setLeft(BigDecimal left) {
        this.left = left;
    }


}
