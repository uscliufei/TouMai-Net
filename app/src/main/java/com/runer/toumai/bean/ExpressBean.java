package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ExpressBean
 */

public class ExpressBean {

    /**
     * id : 1
     * name : 安捷快递
     * code : AJ
     */

    private String id;
    private String name;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name ;
    }
}
