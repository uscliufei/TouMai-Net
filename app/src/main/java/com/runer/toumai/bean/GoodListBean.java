package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * GoodListBean
 */

public class GoodListBean  {


    /*id	商品id
user_id	发售商品用户id
title	商品标题
label	商品标签
img	商品图片
intro	商品描述
is_sale	是否上架：0未上架，1上架
price	商品底价
now_price	当前价格
if_fall	是否接受降价：0否，1是
dark_time	暗价时长，单位秒
dark_end	暗价结束时间
dark_price	暗价结果是否已产生：0否，1是
bright_time	明价时长，单位秒
bright_end	明价结束时间
post_way	包邮，线上交割
is_del	删除标签：0为未删除，1为已删除
create_time	创建时间
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
sell_state	价格阶段，1暗价，2明价
fall_state	降价。0未降价，1以降价
>>>>>>>>>>>>>>
offer_num	出价次数
>>>>>>>>>>>>>>>>>>>>.
is_end	是否结束：0否，1是
end_time	结束时间，时间戳
>>>>>>>>>>>>>>>>>
is_order	是否成交：0未成交，1已成交
>>>>>>>>>>>>>>>>>>>
is_heart	是否进入心跳时间：0否，1是
bright_rest_time	距结束剩余时间，数组
d	天数
h	小时
m	分钟
s	秒
user_name	用户名称
is_post	是否发货：0否，1是
order_id	订单id
is_get	是否收货：0否，1是*/

    /*is_appeal	是否申诉：0否，1是
appeal_end	申诉是否结束：0否，1是
appeal_id	申诉id，申诉时有值*/


    private String is_pay ;



    private String is_appeal ;
    private String appeal_end ;
    private String appeal_id ;

    public String getIs_appeal() {
        return is_appeal;
    }

    public void setIs_appeal(String is_appeal) {
        this.is_appeal = is_appeal;
    }

    public String getAppeal_end() {
        return appeal_end;
    }

    public void setAppeal_end(String appeal_end) {
        this.appeal_end = appeal_end;
    }

    public String getAppeal_id() {
        return appeal_id;
    }

    public void setAppeal_id(String appeal_id) {
        this.appeal_id = appeal_id;
    }

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
    private String is_heart;
    private BrightRestTimeBean bright_rest_time;
    private String is_post ;
    private String is_get ;
    private String order_id ;
    private long countdown ;
    private String buyer_id ;
    private String buyer_addr;


    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getBuyer_addr() {
        return buyer_addr;
    }

    public void setBuyer_addr(String buyer_addr) {
        this.buyer_addr = buyer_addr;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }

    public String getIs_post() {
        return is_post;
    }

    public void setIs_post(String is_post) {
        this.is_post = is_post;
    }

    public String getIs_get() {
        return is_get;
    }

    public void setIs_get(String is_get) {
        this.is_get = is_get;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getIs_heart() {
        return is_heart;
    }

    public void setIs_heart(String is_heart) {
        this.is_heart = is_heart;
    }

    public BrightRestTimeBean getBright_rest_time() {
        return bright_rest_time;
    }

    public void setBright_rest_time(BrightRestTimeBean bright_rest_time) {
        this.bright_rest_time = bright_rest_time;
    }


    @Override
    public String toString() {
        return "GoodListBean{" +
                "is_appeal='" + is_appeal + '\'' +
                ", appeal_end='" + appeal_end + '\'' +
                ", appeal_id='" + appeal_id + '\'' +
                ", id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", label='" + label + '\'' +
                ", img='" + img + '\'' +
                ", intro='" + intro + '\'' +
                ", is_sale='" + is_sale + '\'' +
                ", price='" + price + '\'' +
                ", now_price='" + now_price + '\'' +
                ", is_fall='" + is_fall + '\'' +
                ", dark_time='" + dark_time + '\'' +
                ", dark_end='" + dark_end + '\'' +
                ", dark_price='" + dark_price + '\'' +
                ", bright_time='" + bright_time + '\'' +
                ", bright_end='" + bright_end + '\'' +
                ", post_way='" + post_way + '\'' +
                ", is_del='" + is_del + '\'' +
                ", create_time='" + create_time + '\'' +
                ", sell_state='" + sell_state + '\'' +
                ", fall_state='" + fall_state + '\'' +
                ", offer_num='" + offer_num + '\'' +
                ", is_end='" + is_end + '\'' +
                ", end_time='" + end_time + '\'' +
                ", is_order='" + is_order + '\'' +
                ", is_heart='" + is_heart + '\'' +
                ", bright_rest_time=" + bright_rest_time +
                ", is_post='" + is_post + '\'' +
                ", is_get='" + is_get + '\'' +
                ", order_id='" + order_id + '\'' +
                '}';
    }


    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }
}
