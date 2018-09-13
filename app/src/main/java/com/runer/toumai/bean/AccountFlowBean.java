package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AccountFlowBean
 */

public class AccountFlowBean {


    /**
     * id : 370
     * user_id : 934
     * goods_id : 0
     * title : 余额充值
     * create_time : 2017/07/28 17:48:24
     * amount : 0.01
     * balance : 1000.16
     * type : 0
     * state : 1
     * pay_type : ali
     * pay_order : 201707281891
     * pay_time : 2017-07-28 17:48:24
     * trade_no : 2017072821001004070201452774
     * is_makeup : 0
     * offer_id : 0
     * goods_title : null
     * price : null
     * img : null
     */

    private String id;
    private String user_id;
    private String goods_id;
    private String title;
    private String create_time;
    private String amount;
    private String balance;
    private String type;
    private String state;
    private String pay_type;
    private String pay_order;
    private String pay_time;
    private String trade_no;
    private String is_makeup;
    private String offer_id;
    private String goods_title;
    private String price;
    private String img;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_order() {
        return pay_order;
    }

    public void setPay_order(String pay_order) {
        this.pay_order = pay_order;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getIs_makeup() {
        return is_makeup;
    }

    public void setIs_makeup(String is_makeup) {
        this.is_makeup = is_makeup;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "AccountFlowBean{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", title='" + title + '\'' +
                ", create_time='" + create_time + '\'' +
                ", amount='" + amount + '\'' +
                ", balance='" + balance + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", pay_order='" + pay_order + '\'' +
                ", pay_time='" + pay_time + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", is_makeup='" + is_makeup + '\'' +
                ", offer_id='" + offer_id + '\'' +
                ", goods_title='" + goods_title + '\'' +
                ", price='" + price + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
