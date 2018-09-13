package com.runer.toumai.dao;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.UserInfo;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;
import java.io.File;
import java.io.IOException;
/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * UserInfoDao
 */
public class UserInfoDao extends RunerBaseRequest {

    private UserInfo userInfo ;
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public UserInfoDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
           userInfo = JsonUtil.node2pojo(result,UserInfo.class);
//           if(userInfo!=null){
//               AppUtil.setUserInfo(mContext,userInfo);
//           }
    }
    //获得用户详情
    public void getUserInfo(String id){
        ArrayMap<String,String> params =new ArrayMap<>() ;
        params.put("id",id);
        request(NetInter.GET_USER_INFO,params, RequestCode.GET_USER_INFO);
    }
    //上传用户头像
    public void upLoadAvatar(String id ,File header){
        ArrayMap<String ,File> files =new ArrayMap<>() ;
        files.put("img",header) ;
        RunnerParam params =new RunnerParam();
        params.put("user_id",id);
        uploadFile(NetInter.UPLOAD_AVATAR,files,params,RequestCode.UPDATE_HEADER);
    }
    //手机号唯一验证
    public void vaildMobile(String phone){
        RunnerParam param =new RunnerParam() ;
        param.put("mobile",phone);
        request(NetInter.VALID_MOBILE,param,RequestCode.PHONE_AVLID);
    }






}
