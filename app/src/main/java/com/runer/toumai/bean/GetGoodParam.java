package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * GetGoodParam
 获得商品列表所需的参数，在这里进行处理
 */

public class GetGoodParam {
    /*is_del	否	int	默认0显示未删除商品，1显示已删除商品，2全部显示
user_id	否	int	用户id
order	否	int	排序（与下面条件配合使用）：1按底价 从低到高，2按底价 从高到低，3按底价区间 从高到低，4按底价区间 从高到低，5按当前价 从低到高，6按当前价 从高到低，7按当前价区间 从高到低，8按当前价区间 从高到低，9按发布时间 由近到远，10按发布时间 由远到近，11按出价次数 从低到高，12按出价次数 从高到低，13按出价次数 从低到高，14按出价次数 从高到低
start_price	否	decimal	按底价 开始价格
end_price	否	decimal	按底价 结束价格
start_time	否	datetime	按发布时间 开始时间：yyyy-mm-dd
end_time	否	datetime	按发布时间 结束时间：yyyy-mm-dd
sur_time	否	varchar	按距离结束倒计时：5:5分钟以内，15:15分钟以内，60:1小时以内
start_now	否	decimal	按当前价 开始价
end_now	否	decimal	按当前价 结束价
start_offer	否	int	按出价次数 开始次数
end_offer	否	int	按出价次数 结束次数
sell_state	否	int	价格阶段：1暗价，2明价
fall_state	否	int	降价：0未降价，1已降价
heart_time	否	int	心跳时间：1是
is_order	否	int	1已结束-成交，0已结束-未成交
label	否	varchar	标签名称
user	否	int	用户id
title 关键字
tatus ；状态；


*/
    String is_del ;
    String user_id ;
    String order ;
    String start_price ;
    String end_price ;
    String start_time ;
    String end_time ;
    String sur_time ;
    String start_now ;
    String end_now ;
    String start_offer ;
    String end_offer ;
    String sell_state1;
    String sell_state2 ;
    String fall_state ;
    String heart_time ;
    String is_order1 ;
    String is_order0 ;
    String label ;
    String user ;
    String title;
    String status;

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getStart_price() {
        return start_price;
    }

    public void setStart_price(String start_price) {
        this.start_price = start_price;
    }
    public String getEnd_price() {
        return end_price;
    }
    public void setEnd_price(String end_price) {
        this.end_price = end_price;
    }
    public String getStart_time() {
        return start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getSur_time() {
        return sur_time;
    }

    public void setSur_time(String sur_time) {
        this.sur_time = sur_time;
    }

    public String getStart_now() {
        return start_now;
    }

    public void setStart_now(String start_now) {
        this.start_now = start_now;
    }

    public String getEnd_now() {
        return end_now;
    }

    public void setEnd_now(String end_now) {
        this.end_now = end_now;
    }

    public String getStart_offer() {
        return start_offer;
    }

    public void setStart_offer(String start_offer) {
        this.start_offer = start_offer;
    }

    public String getEnd_offer() {
        return end_offer;
    }

    public void setEnd_offer(String end_offer) {
        this.end_offer = end_offer;
    }

    public String getSell_state1() {
        return sell_state1;
    }

    public void setSell_state1(String sell_state1) {
        this.sell_state1 = sell_state1;
    }

    public String getSell_state2() {
        return sell_state2;
    }

    public void setSell_state2(String sell_state2) {
        this.sell_state2 = sell_state2;
    }

    public String getFall_state() {
        return fall_state;
    }

    public void setFall_state(String fall_state) {
        this.fall_state = fall_state;
    }

    public String getHeart_time() {
        return heart_time;
    }

    public void setHeart_time(String heart_time) {
        this.heart_time = heart_time;
    }

    public String getIs_order1() {
        return is_order1;
    }

    public void setIs_order1(String is_order1) {
        this.is_order1 = is_order1;
    }

    public String getIs_order0() {
        return is_order0;
    }

    public void setIs_order0(String is_order0) {
        this.is_order0 = is_order0;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GetGoodParam{" +
                "is_del='" + is_del + '\'' +
                ", user_id='" + user_id + '\'' +
                ", order='" + order + '\'' +
                ", start_price='" + start_price + '\'' +
                ", end_price='" + end_price + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", sur_time='" + sur_time + '\'' +
                ", start_now='" + start_now + '\'' +
                ", end_now='" + end_now + '\'' +
                ", start_offer='" + start_offer + '\'' +
                ", end_offer='" + end_offer + '\'' +
                ", sell_state1='" + sell_state1 + '\'' +
                ", sell_state2='" + sell_state2 + '\'' +
                ", fall_state='" + fall_state + '\'' +
                ", heart_time='" + heart_time + '\'' +
                ", is_order1='" + is_order1 + '\'' +
                ", is_order0='" + is_order0 + '\'' +
                ", label='" + label + '\'' +
                ", user='" + user + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
