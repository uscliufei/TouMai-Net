package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ExpressInfo
 */

public class ExpressInfo {

    /*is_post	是否发货：0否，1是
post_time	发货时间
express_name	物流公司名称
express_code	物流公司编号
post_code	快递单号*/

    String is_post ;
    String post_time ;
    String express_name ;
    String express_code ;
    String post_code ;
    String remark;


    public String getIs_post() {
        return is_post;
    }

    public void setIs_post(String is_post) {
        this.is_post = is_post;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ExpressInfo{" +
                "is_post='" + is_post + '\'' +
                ", post_time='" + post_time + '\'' +
                ", express_name='" + express_name + '\'' +
                ", express_code='" + express_code + '\'' +
                ", post_code='" + post_code + '\'' +
                '}';
    }
}
