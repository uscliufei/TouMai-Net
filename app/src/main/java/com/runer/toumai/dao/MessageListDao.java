package com.runer.toumai.dao;

import android.content.Context;

import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.MessageBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

/**
 * Created by szhua on 2017/8/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MessageListDao
 */

public class MessageListDao extends RunnerBaseLoadMoreRequest<MessageBean>{
    public MessageListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    /*user_id	是	int	用户ID
type	是	int	类型：0平台通知，1用户私信
num	否	int	每页数据数量
page	否	int	页码*/
    public void getMessages(String user_id ,String type){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        param.put("type",type);
        getData(param, NetInter.GET_MESSAGE);
    }
}
