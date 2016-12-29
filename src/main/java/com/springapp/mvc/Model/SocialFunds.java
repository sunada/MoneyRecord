package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/26.
 */
@Repository
public class SocialFunds {
    BigDecimal hHouseFund;
    BigDecimal hMediFund;
    BigDecimal wHouseFund;
    BigDecimal wMediFund;

    public BigDecimal gethHouseFund() {
        return hHouseFund;
    }

    public void sethHouseFund(BigDecimal hHouseFund) {
        this.hHouseFund = hHouseFund;
    }

    public BigDecimal gethMediFund() {
        return hMediFund;
    }

    public void sethMediFund(BigDecimal hMediFund) {
        this.hMediFund = hMediFund;
    }

    public BigDecimal getwHouseFund() {
        return wHouseFund;
    }

    public void setwHouseFund(BigDecimal wHouseFund) {
        this.wHouseFund = wHouseFund;
    }

    public BigDecimal getwMediFund() {
        return wMediFund;
    }

    public void setwMediFund(BigDecimal wMediFund) {
        this.wMediFund = wMediFund;
    }
}
