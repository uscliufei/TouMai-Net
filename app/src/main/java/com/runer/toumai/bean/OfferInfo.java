package com.runer.toumai.bean;

/**
 * Created by Administrator on 2017/8/21.
 */

public class OfferInfo {

    /**
     * id : 1548
     * user_id : 915
     * goods_id : 100
     * total_price : 104.0604
     * price : 1.0303
     * is_pay : 0
     * is_top : 1
     * create_time : 2017/08/18 13:20
     * is_auto : 0
     * is_back : 0
     * is_makeup : 0
     * round : 4
     * order_id : 0
     * anonymous : 0
     * user_name : name
     * income : 0.00
     */
    private String id;
    private String user_id;
    private String goods_id;
    private String total_price;
    private String price;
    private String is_pay;
    private String is_top;
    private String create_time;
    private String is_auto;
    private String is_back;
    private String is_makeup;
    private String round;
    private String order_id;
    private String anonymous;
    private String user_name;
    private String income;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIs_auto() {
        return is_auto;
    }

    public void setIs_auto(String is_auto) {
        this.is_auto = is_auto;
    }

    public String getIs_back() {
        return is_back;
    }

    public void setIs_back(String is_back) {
        this.is_back = is_back;
    }

    public String getIs_makeup() {
        return is_makeup;
    }

    public void setIs_makeup(String is_makeup) {
        this.is_makeup = is_makeup;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "OfferInfo{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", total_price='" + total_price + '\'' +
                ", price='" + price + '\'' +
                ", is_pay='" + is_pay + '\'' +
                ", is_top='" + is_top + '\'' +
                ", create_time='" + create_time + '\'' +
                ", is_auto='" + is_auto + '\'' +
                ", is_back='" + is_back + '\'' +
                ", is_makeup='" + is_makeup + '\'' +
                ", round='" + round + '\'' +
                ", order_id='" + order_id + '\'' +
                ", anonymous='" + anonymous + '\'' +
                ", user_name='" + user_name + '\'' +
                ", income='" + income + '\'' +
                '}';
    }
}
