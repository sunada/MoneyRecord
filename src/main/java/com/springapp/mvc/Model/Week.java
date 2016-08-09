package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2016/1/10.
 */
public enum Week implements IntEnum<Risk>{
    MON(0, "Mon"), TUE(1, "Tue"), WEN(2, "Wen"), THU(3, "Thu"), FRI(4, "Fri"), EXC(-1, "Exc");
    private int index;
    private String name;

    private Week(int index, String name){
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
        for (Week p : Week.values()){
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
