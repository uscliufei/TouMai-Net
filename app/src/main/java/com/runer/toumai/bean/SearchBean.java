package com.runer.toumai.bean;

/**
 * Created by ruier on 2018/7/14.
 */

public class SearchBean {
    String type ;
    String name ;


    public SearchBean() {
    }

    public SearchBean(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name ;
    }
}
