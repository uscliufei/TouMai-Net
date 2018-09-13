package com.runer.toumai.dao;

import android.content.Context;

import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.FollowUserBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

/**
 * Created by szhua on 2017/8/19/019.
 * github:https://github.com/szhua
 * TouMaiNetApp
 */
public class GetFollowUsersDao extends RunnerBaseLoadMoreRequest<FollowUserBean> {
    public GetFollowUsersDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    public  void getFollowUsers(String user_id){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        getData(param, NetInter.GET_FOLLOE_USERS);
    }




}
