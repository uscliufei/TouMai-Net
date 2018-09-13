package com.runer.toumai.bean;

import java.io.Serializable;

/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * FaqListBean
 */
public class FaqListBean  implements Serializable {
    String content ;
    String value;
    boolean space ;
   private  String id ;

    public FaqListBean(String content ,String value ,boolean space ){
        this.content =content ;
        this.value =value ;
        this.space =space ;
    }
    public FaqListBean(){

    }


    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
