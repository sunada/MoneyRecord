package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/10.
 */
@Repository
public class Salary {
    private int id;
    private String owner;
    private String date;
    private BigDecimal beforeTax;
    private BigDecimal afterTax;
    private BigDecimal houseFundsCompany;
    private BigDecimal houseFunds;
    private BigDecimal medicareCompany;
    private BigDecimal medicare;
    private BigDecimal pensionInsuranceCompany;
    private BigDecimal pensionInsurance;
    private BigDecimal unemployInsurance;
    private BigDecimal unemployInsuranceCompany;
    private BigDecimal tax;
    private BigDecimal insuranceBase;
    private BigDecimal fundBase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getUnemployInsuranceCompany() {
        return unemployInsuranceCompany;
    }

    public void setUnemployInsuranceCompany(BigDecimal unemployInsuranceCompany) {
        this.unemployInsuranceCompany = unemployInsuranceCompany;
    }

    public BigDecimal getInsuranceBase() {
        return insuranceBase;
    }

    public void setInsuranceBase(BigDecimal insuranceBase) {
        this.insuranceBase = insuranceBase;
    }

    public BigDecimal getFundBase() {
        return fundBase;
    }

    public void setFundBase(BigDecimal fundBase) {
        this.fundBase = fundBase;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getBeforeTax() {
        return beforeTax;
    }

    public void setBeforeTax(BigDecimal beforeTax) {
        this.beforeTax = beforeTax;
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

    public BigDecimal getMedicareCompany() {
        return medicareCompany;
    }

    public void setMedicareCompany(BigDecimal medicareCompany) {
        this.medicareCompany = medicareCompany;
    }

    public BigDecimal getMedicare() {
        return medicare;
    }

    public void setMedicare(BigDecimal medicare) {
        this.medicare = medicare;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getPensionInsurance() {
        return pensionInsurance;
    }

    public void setPensionInsurance(BigDecimal pensionInsurance) {
        this.pensionInsurance = pensionInsurance;
    }

    public BigDecimal getPensionInsuranceCompany() {
        return pensionInsuranceCompany;
    }

    public void setPensionInsuranceCompany(BigDecimal pensionInsuranceCompany) {
        this.pensionInsuranceCompany = pensionInsuranceCompany;
    }

    public BigDecimal getUnemployInsurance() {
        return unemployInsurance;
    }

    public void setUnemployInsurance(BigDecimal unemployInsurance) {
        this.unemployInsurance = unemployInsurance;
    }
}
