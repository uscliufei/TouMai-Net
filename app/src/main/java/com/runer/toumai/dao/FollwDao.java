package com.runer.toumai.dao;

import android.content.Context;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;
import java.io.IOException;
/**
 * Created by szhua on 2017/8/19/019.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * FollwDao
 */
public class FollwDao extends RunerBaseRequest {
    public FollwDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
    }
    public void delFollowUser(String user_id ,String follow_user){
        RunnerParam param =new RunnerParam();
        param.put("user_id",user_id) ;
        param.put("follow_user",follow_user);
        request(NetInter.del_user_follow,param, RequestCode.QUIE_CONCERN);
     }
     public void addFollowUser(String user_id ,String follow_user){
         RunnerParam param =new RunnerParam();
         param.put("user_id",user_id) ;
         param.put("follow_user",follow_user) ;
         request(NetInter.add_user_follow,param, RequestCode.ADD_CONCERN);
     }
     /*id	否	int	关注id：填写id时，其他参数可不填写
user_id	否	int	用户id：与follow_user同时使用
follow_user	否	int	被关注用户id：与user_id同时使用*/
     public void getFollowInfo(String id ,String user_id ,String follow_user){
         RunnerParam param =new RunnerParam() ;
         param.put("id",id) ;
         if(!TextUtils.isEmpty(id)){
             param.put("user_id",user_id) ;
             param.put("follow_user",follow_user) ;
         }
       request(NetInter.follow_info,param,RequestCode.CODE_0);
     }
}
