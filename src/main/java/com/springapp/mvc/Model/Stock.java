package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/4/25.
 */
@Repository
public class Stock {
//    代码、股票名、所属账号、成本价、现价、份额、市值、风险等级
    private String code;
    private String name;
    private String belongTo;
    private BigDecimal cost;
    private BigDecimal current;
    private BigDecimal share;
    private BigDecimal amount;
    private Risk risk;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current = current;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }
}
