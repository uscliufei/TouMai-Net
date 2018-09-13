package com.runer.toumai.dao;

import android.content.Context;

import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.Constant;
import com.runer.toumai.bean.AccountFlowBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AccountListsDao
 */

public class AccountListsDao extends RunnerBaseLoadMoreRequest<AccountFlowBean> {

    public AccountListsDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    public void getAccountList(String user_id,String type ,String is_make ){
        RunnerParam param =new RunnerParam() ;
        param.put("type",type);
        param.put("user_id",user_id) ;
        param.put("is_make",is_make);
        getData(param,NetInter.ACCOUNT_LIST);
    }
}
