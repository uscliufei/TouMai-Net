package com.runer.toumai.dao;

/**
 * Created by szhua on 2017/8/30/030.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AppealInfo
 */

public class AppealInfo {

    /**
     * id : 6
     * order_id : 136
     * user_id : 946
     * reason : 卖家胜
     * state : 2
     * amount : 0.0000
     * punish : 0.0000
     * create_time : 2017-08-30 03:48:41
     * handle_time : 2017-08-30 03:50:04
     * refuse : 卖家胜
     * error : 0
     * msg : 请求成功
     */

    private String id;
    private String order_id;
    private String user_id;
    private String reason;
    private String state;
    private String amount;
    private String punish;
    private String create_time;
    private String handle_time;
    private String refuse;
    private String error;
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPunish() {
        return punish;
    }

    public void setPunish(String punish) {
        this.punish = punish;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getHandle_time() {
        return handle_time;
    }

    public void setHandle_time(String handle_time) {
        this.handle_time = handle_time;
    }

    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse;
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
