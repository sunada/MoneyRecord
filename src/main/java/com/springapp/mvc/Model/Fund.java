package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/5.
 */

@Repository
public class Fund {
    private String code;                           //基金代号
    private String name;                          //基金名称
    private BigDecimal purchaseRate;            //申购费率
    private BigDecimal redemptionRate;          //赎回费率
    private ChargeMode chargeMode;              //收费方式
    private DividendMode dividendMode;         //分红方式
    private String belongTo;                    //属于哪个户头id
    private Risk risk;                          //风险等级
    private BigDecimal amount;                   //定投金额
    private Interval interval;                  //定投时间间隔：每周（=1周）、每两周（=2周）、每月（=4周）
    private int date;                           //若为月定投，定时日期
    private Week week;                          //若为周定投，定时星期
    private Date startTime;                    //定投开始时间
    private boolean valid;                    //定投计划是否有效

    public String getBelongTo(){
        return belongTo;
    }

    public void setBelongTo(String belongTo){
        this.belongTo = belongTo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public DividendMode getDividendMode(){return dividendMode;}

    public void setDividendMode(DividendMode dividendMode) {this.dividendMode = dividendMode;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Risk getRisk(){return risk;}

    public void setRisk(Risk risk){this.risk = risk;}

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public boolean getValid(){ return valid;}

    public void setValid(boolean valid) { this.valid = valid; }
}
