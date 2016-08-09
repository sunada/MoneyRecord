package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/11/5.
 */
public enum AccountType implements IntEnum<AccountType> {
    //0：信用卡；1：借记卡；2：基金；3：A股；4：借贷
    CREDIT(0,"credit"),DEPOSIT(1, "deposit"),
    FUND(2, "fund"),SECURITIES(3, "securities"),
    LOAN(4, "loan");

    private int index;
    private String name;

    private AccountType(int index, String name){
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

    public static String fromIndex(int index){
        for (AccountType p : AccountType.values()){
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

