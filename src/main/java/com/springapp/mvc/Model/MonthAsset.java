package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/3.
 */
@Repository
public class MonthAsset {
    private String month;
    private BigDecimal amount;
    private BigDecimal stocks;
    private BigDecimal funds;
    private BigDecimal p2p;
    private BigDecimal usd;
    private BigDecimal hkd;
    private BigDecimal socialInsurance;

    public BigDecimal getStocks() {
        return stocks;
    }

    public void setStocks(BigDecimal stocks) {
        this.stocks = stocks;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }

    public BigDecimal getP2p() {
        return p2p;
    }

    public void setP2p(BigDecimal p2p) {
        this.p2p = p2p;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    public BigDecimal getHkd() {
        return hkd;
    }

    public void setHkd(BigDecimal hkd) {
        this.hkd = hkd;
    }

    public BigDecimal getSocialInsurance() {
        return socialInsurance;
    }

    public void setSocialInsurance(BigDecimal socialInsurance) {
        this.socialInsurance = socialInsurance;
    }
}
