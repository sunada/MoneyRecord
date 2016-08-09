package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum DividendMode implements IntEnum<DividendMode>{
    CASH(0, "cash"), REINVESTMENT(1, "reinvestment");
    private int index;
    private String name;

    private DividendMode(int index, String name){
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
        for (DividendMode p : DividendMode.values()){
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
