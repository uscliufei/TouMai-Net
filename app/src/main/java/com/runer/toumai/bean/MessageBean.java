package com.runer.toumai.bean;

import java.io.Serializable;

/**
 * Created by szhua on 2017/8/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MessageBean
 */

public class MessageBean implements Serializable {


    /**
     * id : 979
     * user_id : 946
     * from_user : 0
     * type : 0
     * content : <a href="goods_info.php?id=100">有买家就您的商品[出价规则计算测试即撤下商品付款金额测试]有提问，请及时解答，以便卖出更好的价格哦！——点击此处进行回答</a>
     * is_read : 1
     * is_del : 0
     * is_del_from : 0
     * create_time : 2017/08/18 13:27
     "link_type":"1","link_id":"319"
     from_head
     */

    private String id;
    private String user_id;
    private String from_user;
    private String type;
    private String content;
    private String is_read;
    private String is_del;
    private String is_del_from;
    private String create_time;
    private String link_type ;
    private String link_id ;
    private String content_app ;
    private String from_head ;
    private String from_name ;


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

    public String getContent_app() {
        return content_app;
    }

    public void setContent_app(String content_app) {
        this.content_app = content_app;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

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

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getIs_del_from() {
        return is_del_from;
    }

    public void setIs_del_from(String is_del_from) {
        this.is_del_from = is_del_from;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
