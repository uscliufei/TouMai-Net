package com.runer.toumai.bean;

/**
 * Created by ruier on 2018/7/16.
 */

public class Label {

    /**
     *  "label": "个人收入",
     "num": "1"
     */
    private String label ;
    private String num;

    public String getLabel(){
        return label;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
