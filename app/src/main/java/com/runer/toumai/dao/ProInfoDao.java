package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.RequestParams;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.NowPriceBean;
import com.runer.toumai.bean.ProInfoBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;

/**
 * Created   by   szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ProInfoDao
 * 获得商品详情
 */
public class ProInfoDao extends RunerBaseRequest {

    ProInfoBean proInfoBean ;

    private NowPriceBean nowPriceBean ;
    public ProInfoBean getProInfoBean() {
        return proInfoBean;
    }


    public NowPriceBean getNowPriceBean() {
        return nowPriceBean;
    }

    public ProInfoDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
       if(requestCode==RequestCode.CODE_0){
           proInfoBean = JsonUtil.node2pojo(result,ProInfoBean.class);
       }else if(requestCode==RequestCode.GOODS_NOW_PRICE){
           nowPriceBean =JsonUtil.node2pojo(result,NowPriceBean.class);
       }
    }
    public  void getGoodsInfo(String id,String user_id){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id) ;
        param.put("user_id",user_id);
        request(NetInter.Get_GOODS_INFO,param, RequestCode.CODE_0);
    }
    public void getNowPrice(String id){
        proInfoBean =null ;
        RunnerParam param =new RunnerParam() ;
        param.put("id",id) ;
        request(NetInter.goods_now_price,param,RequestCode.GOODS_NOW_PRICE);
    }

}

