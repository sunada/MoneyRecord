package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum Interval implements IntEnum<Interval>{
    MONTH(0, "month"), ONEWEEK(1, "oneWeek"), TWOWEEKS(2, "twoWeeks");
    private int index;
    private String name;

    private Interval(int index, String name){
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
        for (Interval p : Interval.values()){
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
