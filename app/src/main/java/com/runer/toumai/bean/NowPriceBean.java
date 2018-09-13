package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * NowPriceBean
 */

public class NowPriceBean {


    /**
     * price : 23.97
     * now_price : 24.4518
     * next_price : 24.6963
     * make_price : 0.1210
     * top_user : 929
     * error : 0
     * msg : 请求成功
     */
    private String price;
    private String now_price;
    private String next_price;
    private String make_price;
    private String top_user;
    private String error;
    private String msg;




    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNow_price() {
        return now_price;
    }

    public void setNow_price(String now_price) {
        this.now_price = now_price;
    }

    public String getNext_price() {
        return next_price;
    }

    public void setNext_price(String next_price) {
        this.next_price = next_price;
    }

    public String getMake_price() {
        return make_price;
    }

    public void setMake_price(String make_price) {
        this.make_price = make_price;
    }

    public String getTop_user() {
        return top_user;
    }

    public void setTop_user(String top_user) {
        this.top_user = top_user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
