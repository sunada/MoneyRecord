package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum Currency implements IntEnum<Currency>{
    RMB(0, "rmb"), USA(1, "usa"), JAP(2, "jap");
    private int index;
    private String name;

    private Currency(int index, String name){
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
        for (Currency p : Currency.values()){
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
