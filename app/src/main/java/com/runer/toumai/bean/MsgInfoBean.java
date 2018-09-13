package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MsgInfoBean
 */

public class MsgInfoBean {


    /**
     * from_name : 18753119698
     * from_head : 15003561734011.jpg
     * user_name : 华为微信
     * user_head :
     * from_user : 932
     * user_id : 946
     */

    private String from_name;
    private String from_head;
    private String user_name;
    private String user_head;
    private String from_user;
    private String user_id;

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getFrom_head() {
        return from_head;
    }

    public void setFrom_head(String from_head) {
        this.from_head = from_head;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "MsgInfoBean{" +
                "from_name='" + from_name + '\'' +
                ", from_head='" + from_head + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_head='" + user_head + '\'' +
                ", from_user='" + from_user + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
