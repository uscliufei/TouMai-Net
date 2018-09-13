package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.CollectBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by szhua on 2017/8/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * CollecListDao
 */

public class CollecListDao extends RunerBaseRequest{

    private List<CollectBean> datas;

    public List<CollectBean> getDatas() {
        return datas;
    }

    public CollecListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode== RequestCode.CODE_0){
        datas = JsonUtil.node2pojoList(result,CollectBean.class);
        }
    }
    public void getCollectList(String user_id){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id);
        request(NetInter.GET_COLLECT_LIST,param,RequestCode.CODE_4);
     }
}
