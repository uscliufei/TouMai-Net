package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.Constant;
import com.runer.toumai.bean.ConcernMsgBean;
import com.runer.toumai.bean.MsgInfoBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MessageInfoDao
 */

public class MessageInfoDao extends RunerBaseRequest{

    private MsgInfoBean msgInfoBean ;
    private List<ConcernMsgBean> datas =new ArrayList<>();
    private int  totalPage;
    private boolean isMore ;
    public  boolean hasMore(){
        return  isMore ;
    }
    public MsgInfoBean getMsgInfoBean() {
        return msgInfoBean;
    }
    public List<ConcernMsgBean> getDatas() {
        return datas;
    }
    public MessageInfoDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
          if(requestCode==RequestCode.CODE_6){
              msgInfoBean = JsonUtil.node2pojo(result.get("info"),MsgInfoBean.class);
              List<ConcernMsgBean> resultData =JsonUtil.node2pojoList(result.findValue("lists"),ConcernMsgBean.class);
              //添加数据
              if(resultData!=null){
                  datas.addAll(resultData) ;
              }
//获得总页数
              if(result.findValue("total_page")!=null)
                  totalPage =result.findValue("total_page").asInt();
              //判断是否含有更多
              if(currentPage>=totalPage){
                  isMore =false ;
              }else{
                  isMore =true ;
              }
          }
    }
    private int currentPage =1;
    private int perPageNum  = Constant.DEFAULT_PERPAGE_COUNT ;
    public void getMessageInfo(String id ,String user_id ,String from_user ){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id) ;
        param.put("num",perPageNum) ;
        param.put("page",currentPage) ;
        param.put("user_id",user_id) ;
        param.put("from_user",from_user);
        request(NetInter.mes_info,param, RequestCode.CODE_6);
    }

    //刷新数据
    public void refresh(String id ,String user_id ,String from_user){
        datas=new ArrayList<>();
        currentPage =1 ;
        getMessageInfo(id,user_id,from_user);
    }
    //加载更多
    public void loadMore(String id ,String user_id ,String from_user){
        if(hasMore()){
            currentPage++ ;
            getMessageInfo(id,user_id,from_user);
        }
    }
}
