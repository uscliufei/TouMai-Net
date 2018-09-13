package com.runer.toumai.dao;


import android.content.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.UpdateUserInfoParam;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * UpdateUserInfoDao'
 * 更改用户信息；
 */
public class UpdateUserInfoDao extends RunerBaseRequest {
    public UpdateUserInfoDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
    }
    /*id	是	int	用户Id
user_name	否	string	昵称
intro	否	varchar	介绍
auth	否	varchar	认证信息
sex	否	varchar	性别：男，女
birth	否	varchar	生日，yyyy-mm-dd
province	否	varchar	常住地，省，如‘山东省’
city	否	varchar	常住地，市，如‘济南市’
area	否	varchar	常住地，区，如‘槐荫区’*/
    public void upLoadUserInfo(UpdateUserInfoParam updateUserInfoParam){
        RunnerParam param =new RunnerParam() ;
        param.put("id",updateUserInfoParam.getId()) ;
        param.put("user_name",updateUserInfoParam.getUser_name());
        param.put("intro",updateUserInfoParam.getIntro()) ;
        param.put("auth",updateUserInfoParam.getAuth());
        param.put("sex",updateUserInfoParam.getSex()) ;
        param.put("birth",updateUserInfoParam.getBirth()) ;
        param.put("province",updateUserInfoParam.getProvince()) ;
        param.put("city",updateUserInfoParam.getCity()) ;
        param.put("area",updateUserInfoParam.getArea()) ;
        param.put("is_dark_learn",updateUserInfoParam.getIs_dark_learn());
        request(NetInter.USER_UPDATE,param, RequestCode.UPDATE_USER_UINFO);
    }
}
