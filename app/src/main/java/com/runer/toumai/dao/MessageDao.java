package com.runer.toumai.dao;

import android.content.Context;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.liabary.util.UiUtil;
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
 * MessageDao
 */

public class MessageDao extends RunerBaseRequest {
    private String msgNUm ;
/*0	未读平台通知数量
1	未读用户私信数量
2	总未读消息数量*/

String platFormMsgNum ;
    String userMsgNum ;
    private String fistmsgNUm;

    public String getPlatFormMsgNum() {
        return platFormMsgNum;
    }
    public String getUserMsgNum() {
        return userMsgNum;
    }
    public String getMsgNUm() {
        return msgNUm;
    }
    public boolean hasFirstMsgNum() {
        if (TextUtils.isEmpty(fistmsgNUm)){
           return  false ;
        }
        if (Integer.parseInt(fistmsgNUm)!=0){
            return  true ;
        }
        return  false ;
    }

    public MessageDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.Msg_num){
            msgNUm =result.findValue("2").asText();
            platFormMsgNum =result.findValue("0").asText() ;
            userMsgNum =result.findValue("1").asText() ;
        }else if (requestCode==RequestCode.FIRST_GET_MSG_NUM){
            fistmsgNUm =result.findValue("2").asText() ;
        }
    }
    /*user_id	是	int	收信用户id
from_user	是	int	发信用户id
content	是	varchar	私信内容*/
    public void sendMessage(String user_id ,String from_user,String content ){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        param.put("from_user",from_user) ;
        param.put("content",content);
        request(NetInter.mes_send,param, RequestCode.CODE_1);
    }
    /*删除信息
    * 参数名	必选	类型	说明
id	是	int	信息id
del_user	是	int	删除人id
    * */
    public void delMessage(String id ,String del_user){
     RunnerParam param =new RunnerParam();
     param.put("id",id) ;
     param.put("del_user",del_user);
     request(NetInter.mes_del,param,RequestCode.DEL_MSG);
    }
    public void sysMsgRead(String user_id,String type){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        param.put("type",type) ;
        request(NetInter.sysRead,param,RequestCode.sys_read);
    }
    public void getMsgNum(String user_id){
        msgNUm="";
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id);
        request(NetInter.msgNum,param,RequestCode.Msg_num);
    }

    public void firstGetMsgNum(String user_id){
      fistmsgNUm ="" ;
      RunnerParam param =new RunnerParam() ;
      param.put("user_id",user_id) ;
      request(NetInter.msgNum,param,RequestCode.FIRST_GET_MSG_NUM);
    }



}
