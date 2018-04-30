package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/16.
 */
@Repository
public class MonthAssetLeft {
    private String month;
    private MonthAsset monthAsset;
    private BigDecimal left;
    private BigDecimal amountIncrease;
    private BigDecimal otherIncome;
    private BigDecimal usdIncrease;
    private BigDecimal hkdIncrease;
    private BigDecimal otherIncrease;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public MonthAsset getMonthAsset() {
        return monthAsset;
    }

    public void setMonthAsset(MonthAsset monthAsset) {
        this.monthAsset = monthAsset;
    }

    public BigDecimal getLeft() {
        return left;
    }

    public void setLeft(BigDecimal left) {
        this.left = left;
    }

    public BigDecimal getAmountIncrease() {
        return amountIncrease;
    }

    public void setAmountIncrease(BigDecimal amountIncrease) {
        this.amountIncrease = amountIncrease;
    }

    public BigDecimal getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(BigDecimal otherIncome) {
        this.otherIncome = otherIncome;
    }

    public BigDecimal getUsdIncrease() {
        return usdIncrease;
    }

    public void setUsdIncrease(BigDecimal usdIncrease) {
        this.usdIncrease = usdIncrease;
    }

    public BigDecimal getHkdIncrease() {
        return hkdIncrease;
    }

    public void setHkdIncrease(BigDecimal hkdIncrease) {
        this.hkdIncrease = hkdIncrease;
    }

    public BigDecimal getOtherIncrease() {
        return otherIncrease;
    }

    public void setOtherIncrease(BigDecimal otherIncrease) {
        this.otherIncrease = otherIncrease;
    }
}
