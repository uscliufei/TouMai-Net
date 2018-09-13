package com.runer.toumai.net;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.Logger;
import com.runer.net.IDao;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.Constant;
import com.runer.toumai.util.AppUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * BaseRequest
 * 基本请求封装====///
 */

public abstract class RunerBaseRequest extends IDao {

    public RunerBaseRequest(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    //上传图片
    public void uploadFile(NetInter netInter , ArrayMap<String,File> files ,ArrayMap<String,String> params , int requestCode){

        JSONObject jsonObject = new JSONObject();
        try {
            if(params!=null)
                for (String key : params.keySet()) {
                    jsonObject.put(key,params.get(key));
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams requestParams =new RequestParams();
        if(files!=null)
            for (String key : files.keySet()) {
                try {
                    requestParams.put(key,files.get(key));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        String timesnamp = String.valueOf(System.currentTimeMillis());
        String sign = getSign(netInter,timesnamp);
        requestParams.put("openid",Constant.OPEN_ID);
        requestParams.put("sign",sign);
        requestParams.put("a",netInter.getA());
        requestParams.put("c",netInter.getC());
        requestParams.put("param",jsonObject.toString());
        requestParams.put("timesnamp",timesnamp);
        //打印请求参数;
        Logger.d(requestParams);
        requestParams.setForceMultipartEntityContentType(true);
        requestParams.setAutoCloseInputStreams(true);
        originalPostRequest(NetConfig.BASE_REQUEST_URL,requestParams,requestCode);
    }


    //接口请求
    public void request(NetInter netInter , ArrayMap<String,String> params ,int requestCode){

        JSONObject jsonObject = new JSONObject();
        try {
            if(params!=null)
                for (String key : params.keySet()) {
                    jsonObject.put(key,params.get(key));
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestParams requestParams =new RequestParams() ;
        String timesnamp = String.valueOf(System.currentTimeMillis());
        String sign = getSign(netInter,timesnamp);

        requestParams.put("openid",Constant.OPEN_ID);
        requestParams.put("sign",sign);
        requestParams.put("a",netInter.getA());
        requestParams.put("c",netInter.getC());
        requestParams.put("param",jsonObject.toString());
        requestParams.put("timesnamp",timesnamp);
        //打印请求参数;
        Logger.d(requestParams);
        originalPostRequest(NetConfig.BASE_REQUEST_URL,requestParams,requestCode);

    }


    public String getSign(NetInter netInter ,String timesnamp){
        String forSign = netInter.getA() + netInter.getC() + timesnamp
                + Constant.TOKEN;
        String sign = AppUtil.md5(AppUtil.md5(forSign));
        return  sign ;
    }
}
