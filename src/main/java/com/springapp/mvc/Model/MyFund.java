package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/22.
 */
//持有基金
@Repository
public class MyFund {
    private String code;                    //基金代号
    private String name;                    //基金名称
    private BigDecimal share;               //基金份额
    private BigDecimal net;                 //基金净值
    private Date date;                      //基金净值日期
    private BigDecimal profit;              //基金盈亏
    private BigDecimal cost;              //基金成本
    private BigDecimal purchaseRate;            //申购费率
    private BigDecimal redemptionRate;          //赎回费率
    private ChargeMode chargeMode;              //收费方式
    private DividendMode dividendMode;         //分红方式
    private String belongTo;                    //属于哪个户头id

    public BigDecimal getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(BigDecimal purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public BigDecimal getRedemptionRate() {
        return redemptionRate;
    }

    public void setRedemptionRate(BigDecimal redemptionRate) {
        this.redemptionRate = redemptionRate;
    }

    public ChargeMode getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(ChargeMode chargeMode) {
        this.chargeMode = chargeMode;
    }

    public DividendMode getDividendMode() {
        return dividendMode;
    }

    public void setDividendMode(DividendMode dividendMode) {
        this.dividendMode = dividendMode;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    private Risk risk;                          //风险等级

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

}
