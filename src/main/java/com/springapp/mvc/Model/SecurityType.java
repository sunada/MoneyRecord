package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/7.
 */
public enum SecurityType implements IntEnum<SecurityType>{
    SFUND(0, "sfund"), SHSTOCK(1, "shstock"), SZSTOCK(2, "shstock"), SHLOAN(3, "shloan"), SZLOAN(4, "szloan"), UNKNOW(5, "unknown");
    private int index;
    private String name;

    private SecurityType(int index, String name){
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
        for (SecurityType p : SecurityType.values()){
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
