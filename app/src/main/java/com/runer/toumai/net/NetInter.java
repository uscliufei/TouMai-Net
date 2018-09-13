package com.runer.toumai.net;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * NetInter
 * 接口配置。
 */
public enum  NetInter {

    ADD_IMG("goods","add_imgs","上传出售商品图片"),
    SELL_GOODS("goods","add","出售商品"),
    OFFER_LISTS("offer","lists","获得出价列表"),
    GET_CODE("mobile_code","add","获取验证码"),
    GET_USER_INFO("user","userInfo","获得用户详情"),
    USER_LOGIN("user","login","用户登录"),
    USER_UPDATE("user","user_update","更改用户信息"),
    USER_LIST("user","lists","获取用户列表"),
    VALID_MOBILE("user","mobile_valid","手机号唯一验证"),
   // upload_avatar
    UPLOAD_AVATAR("user","upload_avatar","上传用户头像"),
    ACCOUNT_LIST("account","lists","账户列表"),
    ACCOUNT_ADD("account","add","充值"),
    GET_CONTACT_INFO("contact","info","联系我们详情"),
    ACCOUNT_INFO("account","info","充值详情"),
    GET_GOODS_FAV("goods_fav","select","获得收藏列表"),
    GET_ADDRESS_LIST("user_address","address_list","地址列表"),
    User_address_Add("user_address","add","添加用户的收货地址"),
    User_address_Del("user_address","del","删除用户的收货地址"),
    User_address_default("user_address","addr_default","设置为默认地址"),
    User_address_update("user_address","edit","修改用户地址"),
    GET_BANNERS("advs","adv_list","广告列表"),
    GET_GOODS_LIST("goods","lists","商品列表"),
    Get_GOODS_INFO("goods","info","获得商品详情"),
    GET_BALANCE("account","balance","获得用户余额"),
    GET_MESSAGE("user_message","lists","获得消息"),
    GET_COLLECT_LIST("goods_fav","select","获得收藏列表"),
    COLLECT_DEL("goods_fav","del","删除收藏"),
    GOLLECT_ADD("goods_fav","add","添加收藏"),
    GET_FOLLOE_USERS("user","user_follow","获得关注用户"),
    del_user_follow("user","del_user_follow","取消关注的用户"),
    add_user_follow("user","add_user_follow","添加用户关注"),
    follow_info("user","follow_info","获得关注用户的详情"),
    mes_send("user_message","mes_send","发送私信"),
    mes_del("user_message","mes_del","删除信息"),
    mes_info("user_message","mes_info","获得消息详情"),
    feedback("contact","add","添加意见反馈"),
    /*订单相关*/
    appeal_create("order","appeal_create","提交申诉"),
    appeal_info("order","appeal_info","申诉详情"),
    with_draw("account","add_draw","提现"),
    appeal_upload("order","appeal_upload","上传申诉图片"),
    express_lists("order","express_lists","物流公司列表"),
    express_info("order","express_info","物流公司详情"),
    express_add("order","express_add","添加物流信息"),
    order_info("order","info","订单详情"),
    order_lists("order","lists","订单列表"),
    order_express("order","order_express","订单物流信息"),
    order_get("order","order_get","确认收货"),
    rule_types("article","rule_types","规则类型"),
    rules_detail("article","rules","规则详情"),
    question_add("goods_question","add","添加商品提问"),
    question_answer("goods_question","answer","回答商品的提问"),
    question_list("goods_question","question","问题列表"),
    offer_add("offer","add","出价"),
    add_auto_offer("offer","add_auto_offer","添加自动出价"),
    add_dark("offer","add_dark","添加暗价"),
    total_auto_offer("offfer","total_auto_offer","当前最高托管出价"),
    add_buyout("offer","add_buyout","一口价买断"),
    goods_now_price("goods","now_price","商品当前价格信息"),
    goods_stop("goods","stop","下架商品"),
    order_pay("order","order_pay","支付尾款"),
    openLogin("user","open_login","第三方授权登录"),
    payOrder("account","pay_order","更新支付信息"),
    bindPhone("user","bind","用户绑定"),
    valid("mobile_code","valid","用户验证"),
    aliPay("pay","ali","支付宝支付"),
    aliOpen("user","ali_open","支付宝登录"),
    sysRead("user_message","sys_read","标记系统消息为已读"),
    msgNum("user_message","nums","未读消息数量"),
    orderAddressInfo("user_address","select","收货地址查看"),
    wechatPay("pay","wechat","微信支付"),
    deleteOpen("user","del_open","解除第三方账号"),
    labelsList("goods","label_list","获得标签的列表"),
    reg("user","reg","用户邮箱注册"),
    email_code_add("email_code","add","用户注册邮箱验证码获取"),
    userLogin1("user","login1","用户邮箱登录"),
    eidtEmail("user","edit_email","用户编辑邮箱"),
 bindEmail("user","bind_email","用户绑定邮箱"),
    emailReg("user","email_yanzheng","邮箱的验证的校验"),
    editPass("user","edit_pwd","编辑用户的密码"),
    ;
    private String a;
    private String c;
    private String des ;

    public String getA() {
        return a;
    }
    public void setA(String a) {
        this.a = a;
    }
    public String getC() {
        return c;
    }
    public void setC(String c) {
        this.c = c;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }

    NetInter(String c , String a , String des){
        this.c =c;
        this.a =a;
        this.des =des;
    }
}
