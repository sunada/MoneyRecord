package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/24.
 */
@Repository
public class Loan {
    //项目编号及金额、平台、利率、时长单位、时长、收益方式、计息开始时间、收本金时间、计算年划收益率、风险、累计收益
    //    已收本息、待收本息
    private String code;
    private BigDecimal amount;
    private String belongTo;
    private BigDecimal interestRate;
//    private LoanInterval inter;
//    private BigDecimal interLong;
    private PayBack approach;
    private Date startTime;
    private Date endTime;
    private BigDecimal realInterestRate;
    private Risk risk;
    private BigDecimal interest;
    private BigDecimal hadPI;
    private BigDecimal waitPI;
    private int valid;

    public BigDecimal getWaitPI() {
        return waitPI;
    }

    public void setWaitPI(BigDecimal waitPI) {
        this.waitPI = waitPI;
    }

    public BigDecimal getHadPI() {
        return hadPI;
    }

    public void setHadPI(BigDecimal hadPI) {
        this.hadPI = hadPI;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
//
//    public BigDecimal getInterLong() {
//        return interLong;
//    }
//
//    public void setInterLong(BigDecimal interLong) {
//        this.interLong = interLong;
//    }

    public PayBack getApproach() {
        return approach;
    }

    public void setApproach(PayBack approach) {
        this.approach = approach;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getRealInterestRate() {
        return realInterestRate;
    }

    public void setRealInterestRate(BigDecimal realInterestRate) {
        this.realInterestRate = realInterestRate;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

}
