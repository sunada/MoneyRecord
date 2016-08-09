package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum ChargeMode implements IntEnum<ChargeMode>{
    //Z: 不收费用，如货币基金
    FRONT(0, "front"), BACK(1, "back"), C(2, "c"), Z(3, "z");
    private int index;
    private String name;

    private ChargeMode(int index, String name){
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
        for (ChargeMode p : ChargeMode.values()){
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
