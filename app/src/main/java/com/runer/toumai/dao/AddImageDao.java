package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.Logger;
import com.runer.net.IDao;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.Constant;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerParam;
import com.runer.toumai.util.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by szhua on 2017/9/7/007.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AddImageDao
 */

public class AddImageDao extends IDao {
    public AddImageDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

    }


    public void addImagList(String goods_id, File img , String cover ){
        RequestParams requestParams =new RequestParams() ;
        RunnerParam param = new RunnerParam();
        param.put("goods_id",goods_id );
        param.put("cover",cover );
        JSONObject jsonObject = new JSONObject();
        try {
            if(param!=null)
                for (Object key : param.keySet()) {
                    jsonObject.put((String)key,param.get(key));
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
                try {
                    requestParams.put("img",img);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        String timesnamp = String.valueOf(System.currentTimeMillis());
        String sign = getSign(NetInter.ADD_IMG,timesnamp);
        requestParams.put("openid", Constant.OPEN_ID);
        requestParams.put("sign",sign);
        requestParams.put("a",NetInter.ADD_IMG.getA());
        requestParams.put("c",NetInter.ADD_IMG.getC());
        requestParams.put("param",jsonObject.toString());
        requestParams.put("timesnamp",timesnamp);
        //打印请求参数;
        Logger.d(requestParams);
        requestParams.setForceMultipartEntityContentType(true);
        requestParams.setAutoCloseInputStreams(true);
        originalPostRequest(NetConfig.BASE_REQUEST_URL,requestParams, RequestCode.CODE_1);
    }

    public String getSign(NetInter netInter ,String timesnamp){
        String forSign = netInter.getA() + netInter.getC() + timesnamp
                + Constant.TOKEN;
        String sign = AppUtil.md5(AppUtil.md5(forSign));
        return  sign ;
    }

}
