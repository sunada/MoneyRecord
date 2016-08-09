package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum AssetType implements IntEnum<AssetType>{
//    fbuy: 买入基金；faip: 基金定投；fredemp: 基金赎回；sbuy: 证券买入；ssell: 证券卖出；fcash：基金现金分红 freinve: 基金红利再投资
    FUND(0, "fund"), STOCK(1, "stock"), LOAN(2, "loan");
    private int index;
    private String name;

    private AssetType(int index, String name){
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
        for (AssetType p : AssetType.values()){
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
