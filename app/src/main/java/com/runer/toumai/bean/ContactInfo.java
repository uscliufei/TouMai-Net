package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/1/001.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ContactInfo
 */

public class ContactInfo {

    /*mobile	电话号码
email	邮箱
img	二维码
weibo	微博
wechat	微信*/
    String mobile;
    String email;
    String img ;
    String weibo ;
    String wechat ;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
}
