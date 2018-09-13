package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * UpdateUserInfoParam
 */

public class UpdateUserInfoParam {
    /*id	是	int	用户Id
user_name	否	string	昵称
intro	否	varchar	介绍
auth	否	varchar	认证信息
sex	否	varchar	性别：男，女
birth	否	varchar	生日，yyyy-mm-dd
province	否	varchar	常住地，省，如‘山东省’
city	否	varchar	常住地，市，如‘济南市’
area	否	varchar	常住地，区，如‘槐荫区’*/

    String id ;
    String user_name ;
    String intro ;
    String auth ;
    String sex ;
    String birth ;
    String province ;
    String city ;
    String area ;
    String is_dark_learn ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getIs_dark_learn() {
        return is_dark_learn;
    }

    public void setIs_dark_learn(String is_dark_learn) {
        this.is_dark_learn = is_dark_learn;
    }

    @Override
    public String toString() {
        return "UpdateUserInfoParam{" +
                "id='" + id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", intro='" + intro + '\'' +
                ", auth='" + auth + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
