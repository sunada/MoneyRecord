package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum DealType implements IntEnum<DealType>{
//    fbuy: 买入基金；faip: 基金定投；fredemp: 基金赎回；sbuy: 证券买入；ssell: 证券卖出；fcash：基金现金分红 freinve: 基金红利再投资
    FBUY(0, "fbuy"), FAIP(1, "faip"), FREDEMP(2, "fredemp"), SBUY(3, "sbuy"), SSELL(4, "ssell"),
    FCASH(5, "fcash"), FREINVE(6, "freinve"),OTHERS(7, "others"), INTEREST(8, "interest");
    private int index;
    private String name;

    private DealType(int index, String name){
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(int index){
        for (DealType p : DealType.values()){
            if(index == p.getIndex()){
                return p.name;
            }
        }
        return null;
    }

    @Override
    public int getIntValue(){
        return this.index;
    }
}
