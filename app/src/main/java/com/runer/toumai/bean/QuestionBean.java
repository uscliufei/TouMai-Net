package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * QuestionBean
 */

public class QuestionBean {

    /**
     * id : 71
     * goods_id : 180
     * user_id : 929
     * question : 吃点饭胜多负少反反复复
     * create_time : 2017-08-24 14:59:32
     * answer :
     * answer_time : 0000-00-00 00:00:00
     * mobile : 15153109902
     * seller_id : 946
     */

    private String id;
    private String goods_id;
    private String user_id;
    private String question;
    private String create_time;
    private String answer;
    private String answer_time;
    private String mobile;
    private String seller_id;
    private String user_name ;


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getCreate_time() {
        return create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAnswer_time() {
        return answer_time;
    }
    public void setAnswer_time(String answer_time) {
        this.answer_time = answer_time;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getSeller_id() {
        return seller_id;
    }
    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
