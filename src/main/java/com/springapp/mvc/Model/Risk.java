package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum  Risk implements IntEnum<Risk>{
    LOW(0, "low"), MIDLOW(1, "midlow"), MID(2, "mid"), MIDHIGH(3, "midhigh"),HIGH(4, "high"),UNKNOW(5, "unknown");
    private int index;
    private String name;

    private Risk(int index, String name){
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
        for (Risk p : Risk.values()){
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
