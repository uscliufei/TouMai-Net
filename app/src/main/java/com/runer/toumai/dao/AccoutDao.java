package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.orhanobut.logger.Logger;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.Constant;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AccoutDao
 * 账户信息
 */

public class AccoutDao extends RunerBaseRequest {

    private String balance ;


    public String getBalance() {
        return balance;
    }

    private String orderId ;

    public String getOrderId() {
        return orderId;
    }

    public AccoutDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
       if(requestCode==RequestCode.CODE_4){
           balance =result.findValue("result").asText() ;
       }else if(requestCode==RequestCode.CODE_2){
           orderId =result.findValue("result").asText();
       }else if(requestCode==RequestCode.CODE_5){
           balance =result.findValue("result").asText() ;
       }
    }
    public void getAccountInfo(String id){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id) ;
        request(NetInter.ACCOUNT_INFO,param, RequestCode.CODE_0);
     }
     /*user_id	是	int	用户id
amount	是	decimal	金额
offer_id	否	int	出价id，出价余额不足时*/
     public void accountAdd(String user_id ,String account ,String offer_id){
         RunnerParam  param = new RunnerParam() ;
         param.put("user_id",user_id) ;
         param.put("amount",account) ;
         param.put("offer_id",offer_id) ;
         request(NetInter.ACCOUNT_ADD,param,RequestCode.CODE_2);
     }
     /*user_id	是	int	用户id
type	是	int	类型：0钱包，1口袋*
is_make	否	int	我赚的：1是，0不是
*/
     public void getBalance(String user_id ,String type   ){
         balance ="" ;
         RunnerParam param =new RunnerParam() ;
         param.put("user_id",user_id) ;
         param.put("type",type) ;
         request(NetInter.GET_BALANCE,param,RequestCode.CODE_4);
     }

     //获得账户余额
    public void getBalance(String user_id  ){
        balance ="" ;
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        param.put("type","0") ;
        request(NetInter.GET_BALANCE,param,RequestCode.CODE_5);
    }

     /*ser_id	是	int	用户id
amount	是	decimal	提现金额
draw_type	是	varchar	提现方式：ali支付宝，wechat微信
account	是	int	提现账号
nickname	是	string	昵称*/
     public void withDraw(String ser_id ,String amount ,String draw_type ,String account ,String nickname){
         RunnerParam param =new RunnerParam() ;
         param.put("user_id",ser_id) ;
         param.put("amount",amount) ;
         param.put("draw_type",draw_type) ;
         param.put("account",account) ;
         param.put("nickname",nickname) ;
         request(NetInter.with_draw,param,RequestCode.WITH_DRAW);
     }
     /*id	是	int	账户流水id
pay_type	是	varchar	支付方式：ali支付宝，wechat微信*/
     public void accountUpdate(String id ,String pay_type){
         RunnerParam param =new RunnerParam() ;
         param.put("id",id) ;
         param.put("pay_type",pay_type) ;
         request(NetInter.payOrder,param,RequestCode.GET_PAY_INFO);
     }
}
