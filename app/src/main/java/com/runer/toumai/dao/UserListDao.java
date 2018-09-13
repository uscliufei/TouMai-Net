package com.runer.toumai.dao;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.UserBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;

/**
 * Created by ruier on 2018/7/14.
 *
 *
 */

public class UserListDao extends RunnerBaseLoadMoreRequest<UserBean> {

    public UserListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    /**
     *
     * @param user_name
     * @param order 默认 0 ；
     */
    public void getUserList(String user_name , String order){
        RunnerParam param =new RunnerParam() ;
        param.put("user_name",user_name);
        param.put("order",order);
        getData(param, NetInter.USER_LIST);
    }


}
