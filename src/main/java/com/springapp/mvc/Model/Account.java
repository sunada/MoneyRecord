package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/10/25.
 */
@Repository
public class Account {
    private Integer accountId;
    //hibernate中才有该注释
    //@NotNull(message="bank不能为空")
    private String account;  //户头，比如天天基金，华泰证券
    private String owner;
    private BigDecimal balance;
    protected AccountType type;  //0：信用卡；1：借记卡；2：基金；3：A股；4：网贷

    public AccountType getType(){return type;}

    public void setType(AccountType type) {this.type = type;}

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String toString(){
        return "" + this.getAccountId() + "|" + this.getOwner() + "|"  +
                this.getAccount() +  "|" + this.getBalance();

    }
}
