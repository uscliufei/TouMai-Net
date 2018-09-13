package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/3/003.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ImgsBean
 */

public class ImgsBean {
    /**
     * id : 77
     * goods_id : 43
     * img : 15016595546162.png
     */

    private String id;
    private String goods_id;
    private String img;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    @Override
    public String toString() {
        return "ImgsBean{" +
                "id='" + id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
