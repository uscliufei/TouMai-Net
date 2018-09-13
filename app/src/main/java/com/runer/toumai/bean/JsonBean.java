package com.runer.toumai.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Province
 */

public class JsonBean  implements IPickerViewData{


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }


    @Override
    public String toString() {
        return "JsonBean{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
