package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/9/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * BizContent
 */

public class BizContent {

    /**
     * body : 投买网充值
     * subject : 投买网充值
     * out_trade_no : 201709028939
     * timeout_express : 30m
     * total_amount : 1.00
     * product_code : QUICK_MSECURITY_PAY
     */
    private String body;
    private String subject;
    private String out_trade_no;
    private String timeout_express;
    private String total_amount;
    private String product_code;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTimeout_express() {
        return timeout_express;
    }
    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }
    public String getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
    public String getProduct_code() {
        return product_code;
    }
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
    @Override
    public String toString() {
        return "BizContent{" +
                "body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", timeout_express='" + timeout_express + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", product_code='" + product_code + '\'' +
                '}';
    }
}
