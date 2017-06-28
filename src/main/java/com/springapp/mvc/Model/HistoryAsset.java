package com.springapp.mvc.Model;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/5.
 */

@Repository
public class HistoryAsset {
    private String code;                 //代号
    private String name;                 //名称
    private String belongTo;            //平台
//    private BigDecimal cost;            //本金
    private BigDecimal profit;          //利润
//    private Date start;                 //开始时间
//    private Date end;
    private Risk risk;
    private AssetType assetType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public BigDecimal getCost() {
//        return cost;
//    }

//    public void setCost(BigDecimal cost) {
//        this.cost = cost;
//    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Risk getRisk(){return risk;}

    public void setRisk(Risk risk){this.risk = risk;}

//    public Date getStart() {
//        return start;
//    }
//
//    public void setStart(Date start) {
//        this.start = start;
//    }
//
//    public Date getEnd(){return end;}
//
//    public void setEnd(Date end){this.end = end;}

    public AssetType getAssetType(){return assetType;}

    public void setAssetType(AssetType assetType){ this.assetType = assetType;}
}
