package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.ContactInfo;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;

/**
 * Created by szhua on 2017/8/1/001.
 * github:https://github.com/szhua
 * TouMaiNetApp
 */

public class ContactDao extends RunerBaseRequest{


    private ContactInfo contactInfo ;

   public ContactInfo getInfo(){
        return contactInfo ;
    }

    public ContactDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.CODE_0){
            contactInfo = JsonUtil.node2pojo(result,ContactInfo.class);
        }
    }




    public void getContactInfo(){
        RunnerParam param =new RunnerParam() ;
        request(NetInter.GET_CONTACT_INFO,param, RequestCode.CODE_0);
    }


    /*参数名	必选	类型	说明
mobile	是	varchar	手机号
email	是	varchar	邮箱
name	否	varchar	名字
content	否	tinyint	问题内容*/
    public void add(String user_id ,String mobile ,String email ,String name ,String content){
        RunnerParam param =new RunnerParam() ;
        param.put("mobile",mobile);
        param.put("email",email);
        param.put("name",name);
        param.put("user_id",user_id);
        param.put("content",content);
        request(NetInter.feedback,param,RequestCode.CODE_1);
    }



}
