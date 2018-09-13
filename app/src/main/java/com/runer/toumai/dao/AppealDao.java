package com.runer.toumai.dao;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szhua on 2017/8/30/030.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AppealDao
 */

public class AppealDao extends RunerBaseRequest {


    private AppealInfo appealInfo ;


    private String appeal_id ;

    public String getAppeal_id() {
        return appeal_id;
    }

    public AppealInfo getAppealInfo() {
        return appealInfo;
    }

    public AppealDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.APPEAL_INFO){
            appealInfo = JsonUtil.node2pojo(result,AppealInfo.class) ;
        }else if(requestCode==RequestCode.APPEAL_CREATE){
            appeal_id =result.findValue("id").asText();
        }
    }
    //创建申述
    /*iorder_id	是	int	订单id
user_id	是	int	用户id
reason	是	varchar	申诉原因*/
    public void createAppeal(String iorder_id ,String user_id ,String reason){
        RunnerParam param =new RunnerParam() ;
        param.put("order_id",iorder_id) ;
        param.put("user_id",user_id) ;
        param.put("reason",reason);
        request(NetInter.appeal_create,param, RequestCode.APPEAL_CREATE);
    }
    /*appeal_id	是	int	申诉id
img	是	file	图片文件*/
    public void apppealUpload(String appeal_id ,String img) {
        RunnerParam param = new RunnerParam();
        param.put("appeal_id", appeal_id);
        ArrayMap<String,File> files =new ArrayMap<>() ;
        files.put("img",new File(img));
        uploadFile(NetInter.appeal_upload,files,param,RequestCode.APPEAL_UPLOAD);
    }
    /*appeal_id	是	int	申诉id，与order_id二选一
order_id	是	int	订单id，与appeal_id二选一*/
    public void getAppealInfo(String appeal_id ,String order_id){
        appealInfo =null ;
        RunnerParam param =new RunnerParam();
        param.put("appeal_id",appeal_id);
        param.put("order_id",order_id) ;
        request(NetInter.appeal_info,param,RequestCode.APPEAL_INFO);
    }
}
