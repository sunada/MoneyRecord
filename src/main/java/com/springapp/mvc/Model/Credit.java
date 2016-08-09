package com.springapp.mvc.Model;

/**
 * Created by Administrator on 2015/9/13.
 */
public class Credit extends Account{
    private String finalRepaymentDate; //固定最后还款日
    private String billDate;  //记账日
    private int inter;      //记账日后inter天为最后还款日
    private String tailNum;

    public String getTailNum() {
        return tailNum;
    }

    public void setTailNum(String tailNum) {
        this.tailNum = tailNum;
    }

    public int getInter(){return inter;}
    public void setInter(int inter){this.inter = inter;}

    public void setType() {this.type = AccountType.DEPOSIT;}

    public String getFinalRepaymentDate() {
        return finalRepaymentDate;
    }

    public void setFinalRepaymentDate(String finalRepaymentDate) {
        this.finalRepaymentDate = finalRepaymentDate;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String toString(){
        return "" + this.getAccountId() + "|" + this.getOwner() + "|" + this.getTailNum() + "|" +
                this.getAccount() + "|" + this.getFinalRepaymentDate() + "|" + this.getBillDate() + ";";

    }
}
