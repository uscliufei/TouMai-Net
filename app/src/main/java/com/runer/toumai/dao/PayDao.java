package com.runer.toumai.dao;

import android.content.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.orhanobut.logger.Logger;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.AliPayResultBean;
import com.runer.toumai.bean.WechatPayBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;
import java.io.IOException;
/**
 * Created by szhua on 2017/9/1/001.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * PayDao
 */
public class PayDao extends RunerBaseRequest {
    private WechatPayBean wechatPayBean ;
    public WechatPayBean getWechatPayBean() {
        return wechatPayBean;
    }

    private AliPayResultBean aliPayResultBean ;
    public AliPayResultBean getAliPayResultBean() {
        return aliPayResultBean;
    }
    public PayDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.AliPay){
         aliPayResultBean = JsonUtil.node2pojo(result,AliPayResultBean.class) ;
        }else if(requestCode==RequestCode.WEchatPay){
            wechatPayBean =JsonUtil.node2pojo(result,WechatPayBean.class);
        }
    }
    public void aliPay(String id){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id);
        request(NetInter.aliPay,param, RequestCode.AliPay);
    }


    public void wechatPay(String id){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id) ;
        request(NetInter.wechatPay,param,RequestCode.WEchatPay);
    }

}
