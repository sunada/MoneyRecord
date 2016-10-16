package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/10/16.
 */
@Repository
public class Income {
    private String date;
    private String owner;
    private BigDecimal incomeAll;
    private BigDecimal afterTax;
    private BigDecimal houseFundsCompany;
    private BigDecimal houseFunds;

    public BigDecimal getIncomeAll() {
        return incomeAll;
    }

    public void setIncomeAll() {
        this.incomeAll = afterTax.add(houseFunds).add(houseFundsCompany);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getAfterTax() {
        return afterTax;
    }

    public void setAfterTax(BigDecimal afterTax) {
        this.afterTax = afterTax;
    }

    public BigDecimal getHouseFundsCompany() {
        return houseFundsCompany;
    }

    public void setHouseFundsCompany(BigDecimal houseFundsCompany) {
        this.houseFundsCompany = houseFundsCompany;
    }

    public BigDecimal getHouseFunds() {
        return houseFunds;
    }

    public void setHouseFunds(BigDecimal houseFunds) {
        this.houseFunds = houseFunds;
    }
}
