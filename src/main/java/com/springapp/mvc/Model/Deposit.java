package com.springapp.mvc.Model;

import com.sun.istack.internal.NotNull;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/13.
 */
@Repository
public class Deposit extends Account{
    private String tailNum;
    private boolean lost;
    private List<FixedDeposit> deposits;

    public void setType(AccountType type) {this.type = AccountType.DEPOSIT;}

    public List<FixedDeposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<FixedDeposit> deposits) {
        this.deposits = deposits;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost){this.lost = lost;}

    public String getTailNum() {
        return tailNum;
    }
    public void setTailNum(String tailNum) {
        this.tailNum = tailNum;
    }

    public void setType(){this.type = AccountType.CREDIT;}

    public String toString(){
        return "" + this.getAccountId() + "|" + this.getOwner() + "|"  +
                this.getAccount() +  "|" + this.getTailNum() + "|" + this.getBalance() + "|" + this.isLost();

    }
}
