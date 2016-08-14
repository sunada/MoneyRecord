package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/28.
 */
@Repository
public class Deal {
    private int id;
    private String code;
    private String belongTo;
    private String name;
    private Date date;
    private Date dateReal;
    private BigDecimal net;
    private BigDecimal cost;
    private BigDecimal amount;
    private BigDecimal share;
    private DealType dealType;        //是否是基金定投交易 0 基金买入 1 基金定投 2 基金赎回 3 证券买入 4 证券卖出
    private String contract;            //文件导入证券交易记录时的合同号，用于避免重复导入

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public DealType getDealType() {
        return dealType;
    }

    public void setDealType(DealType dealType) {
        this.dealType = dealType;
    }

    public Date getDateReal() {
        return dateReal;
    }

    public void setDateReal(Date dateReal) {
        this.dateReal = dateReal;
    }


}
