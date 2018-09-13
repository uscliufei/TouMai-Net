package com.runer.toumai.bean;

import java.io.Serializable;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AddressBean
 */

public class AddressBean  implements Serializable {

    /**
     * id : 8
     * user_id : 934
     * user_name : 收货人1
     * province : 北京市
     * city : 北京市市辖区
     * area : 东城区
     * address : 详细地址2
     * mobile : 13212312311
     * is_default : 0
     */

    private String id;
    private String user_id;
    private String user_name;
    private String province;
    private String city;
    private String area;
    private String address;
    private String mobile;
    private String is_default;

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }
}
