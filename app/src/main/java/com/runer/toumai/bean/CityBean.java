package com.runer.toumai.bean;

import java.util.List;

/**
 * Created by szhua on 2017/7/19/019.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * CityBean
 * City
 */

public class CityBean {
    private String name;
    private List<String> area;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "name='" + name + '\'' +
                ", area=" + area +
                '}';
    }
}
