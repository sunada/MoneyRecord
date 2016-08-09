package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum LoanInterval implements IntEnum<LoanInterval>{
    MONTH(0, "month"), DAY(1, "day"), YEAR(2, "year");
    private int index;
    private String name;

    private LoanInterval(int index, String name){
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
        for (LoanInterval p : LoanInterval.values()){
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
