package com.runer.toumai.bean;

import java.util.List;
import java.util.Objects;

/**
 * Created by szhua on 2017/8/3/003.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ProInfoBean
 */

public class ProInfoBean {
    /**
     * id : 43
     * user_id : 934
     * title : 出价测试
     * label :
     * img : 15016595535076.png
     * intro : xxxxx
     * is_sale : 1
     * price : 1.00
     * now_price : 1.02
     * is_fall : 0
     * dark_time : 0
     * dark_end : 1501659553
     * dark_price : 0
     * bright_time : 2592000
     * bright_end : 1504251553
     * post_way : 包邮
     * is_del : 0
     * create_time : 2017-08-02 15:39:13
     * sell_state : 2
     * fall_state : 0
     * offer_num : 2
     * is_end : 0
     * end_time : 1504251553
     * is_order : 0
     * fav_id : null
     * bright_rest_time : {"is_end":"0","d":29,"h":6,"m":32,"s":31}
     * id_code : 000000000043
     * imgs : [{"id":"77","goods_id":"43","img":"15016595546162.png"}]
     * error : 0
     * msg : 请求成功
     */
    private String id;
    private String user_id;
    private String title;
    private String label;
    private String img;
    private String intro;
    private String is_sale;
    private String price;
    private String now_price;
    private String is_fall;
    private String dark_time;
    private String dark_end;
    private String dark_price;
    private String bright_time;
    private String bright_end;
    private String post_way;
    private String is_del;
    private String create_time;
    private String sell_state;
    private String fall_state;
    private String offer_num;
    private String is_end;
    private String end_time;
    private String is_order;
    private String fav_id;
    private BrightRestTimeBean bright_rest_time;
    private String id_code;
    private String error;
    private String msg;
    private List<ImgsBean> imgs;
    private String follow_id;
    private String is_heart ;
    private long countdown ;
    private String flaw;
    private String label_num;
    private String ratio;
    private String buyout_price ;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIs_sale() {
        return is_sale;
    }

    public void setIs_sale(String is_sale) {
        this.is_sale = is_sale;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNow_price() {
        return now_price;
    }

    public void setNow_price(String now_price) {
        this.now_price = now_price;
    }

    public String getIs_fall() {
        return is_fall;
    }

    public void setIs_fall(String is_fall) {
        this.is_fall = is_fall;
    }

    public String getDark_time() {
        return dark_time;
    }

    public void setDark_time(String dark_time) {
        this.dark_time = dark_time;
    }

    public String getDark_end() {
        return dark_end;
    }

    public void setDark_end(String dark_end) {
        this.dark_end = dark_end;
    }

    public String getDark_price() {
        return dark_price;
    }

    public void setDark_price(String dark_price) {
        this.dark_price = dark_price;
    }

    public String getBright_time() {
        return bright_time;
    }

    public void setBright_time(String bright_time) {
        this.bright_time = bright_time;
    }

    public String getBright_end() {
        return bright_end;
    }

    public void setBright_end(String bright_end) {
        this.bright_end = bright_end;
    }

    public String getPost_way() {
        return post_way;
    }

    public void setPost_way(String post_way) {
        this.post_way = post_way;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSell_state() {
        return sell_state;
    }

    public void setSell_state(String sell_state) {
        this.sell_state = sell_state;
    }

    public String getFall_state() {
        return fall_state;
    }

    public void setFall_state(String fall_state) {
        this.fall_state = fall_state;
    }

    public String getOffer_num() {
        return offer_num;
    }

    public void setOffer_num(String offer_num) {
        this.offer_num = offer_num;
    }

    public String getIs_end() {
        return is_end;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIs_order() {
        return is_order;
    }

    public void setIs_order(String is_order) {
        this.is_order = is_order;
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public BrightRestTimeBean getBright_rest_time() {
        return bright_rest_time;
    }

    public void setBright_rest_time(BrightRestTimeBean bright_rest_time) {
        this.bright_rest_time = bright_rest_time;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }

    public String getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(String follow_id) {
        this.follow_id = follow_id;
    }

    public String getIs_heart() {
        return is_heart;
    }

    public void setIs_heart(String is_heart) {
        this.is_heart = is_heart;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }

    public String getFlaw() {
        return flaw;
    }

    public void setFlaw(String flaw) {
        this.flaw = flaw;
    }

    public String getLabel_num() {
        return label_num;
    }

    public void setLabel_num(String label_num) {
        this.label_num = label_num;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getBuyout_price() {
        return buyout_price;
    }

    public void setBuyout_price(String buyout_price) {
        this.buyout_price = buyout_price;
    }
}
