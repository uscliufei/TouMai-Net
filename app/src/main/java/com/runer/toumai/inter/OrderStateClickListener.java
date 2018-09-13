package com.runer.toumai.inter;

/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * OrderStateClickListener
 */

public interface OrderStateClickListener {


    /***
     * 上传物流信息
     */
     int LOGISTICS_TYPE =1 ;

    /*确认收货
    * */
    int GOT_ORDER_TYPE =2 ;

    /*申诉*/
    int APPEAL_TYPE =3 ;

    /*查看物流信息*/
    int CHECK_EXPRESS_INFO =4 ;


    /*查看申诉结果*/
    int CHECK_APPEAL_INFO =5 ;


    /*撤下商品*/
    int REVOKE_GOODS =6 ;


    int PAY_LEFT_MONRY =7 ;


    int CHECK_ADDRESS =8 ;

     void onStateClick( String order_id ,int type ,int pos);



}
