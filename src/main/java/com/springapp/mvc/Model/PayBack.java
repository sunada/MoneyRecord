package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum PayBack implements IntEnum<PayBack>{
//    EPI: equal principal and interest 等额本息
//    PI: principal and interest 一次还本付息
//    IF: interest first, principal later 先息后本
    EPI(0, "epi"), PI(1, "pi"), IF(2, "if");
    private int index;
    private String name;

    private PayBack(int index, String name){
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
        for (PayBack p : PayBack.values()){
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
