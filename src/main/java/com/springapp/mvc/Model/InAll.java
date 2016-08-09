package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/4/25.
 */
@Repository("all")
public class InAll {
    private Risk risk;
    private String attr;
    private BigDecimal amount;

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
