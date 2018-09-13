package com.runer.toumai.net;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import com.orhanobut.logger.Logger;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.Constant;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/7/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * RunnerBaseLoadMoreRequest
 *
 * Runer加载更多网络请求工具
 *
 */

public  class RunnerBaseLoadMoreRequest<T> extends RunerBaseRequest {

    private Class<T> tClass;
    private List<T> datas =new ArrayList<>();
    public int currentPage =1;
    private int perPageNum  = Constant.DEFAULT_PERPAGE_COUNT ;
    private int  totalPage;

    public void initAll(){
        currentPage =1 ;
        datas =new ArrayList<>();
    }
    //获得请求数据
    public List<T> getDatas() {
        return datas;
    }
    //**/
    public RunnerBaseLoadMoreRequest(Context context, INetResult iNetResult) {
        super(context, iNetResult);
        //获得泛型的类型class ;
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        tClass = (Class) params[0];
    }
    public RunnerBaseLoadMoreRequest(BaseFragment context, INetResult iNetResult) {
        super(context.getContext(), iNetResult);
        //获得泛型的类型class ;
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        tClass = (Class) params[0];
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        try{
            Logger.json(JsonUtil.node2json(result));
        }catch (Exception e){
           // e.printStackTrace();
        }

        if(requestCode== RequestCode.LOADMORE){
            //获得数据列表
            List<T> resultData =JsonUtil.node2pojoList(result.findValue("result"),tClass);
            //添加数据
            if(resultData!=null){
                //为第一页的情况下，强制其为单数据不再增加；
                if (currentPage==1){
                    datas =resultData ;
                    //数据的增加；
                }else{
                    datas.addAll(resultData) ;
                }
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

    private boolean isMore ;

    public  boolean hasMore(){
        return  isMore ;
    }

    private RunnerParam param ;
    private NetInter netInter ;
    //请求数据
    public void getData(RunnerParam param ,NetInter netInter){
        param.put("num",perPageNum) ;
        param.put("page",currentPage) ;
        this.param =param ;
        this.netInter =netInter ;
        request(netInter,param,RequestCode.LOADMORE);
    }
   //刷新数据
    public void refresh(){
        datas =new ArrayList<>() ;
        currentPage =1 ;
        getData(param,netInter);
    }
    //加载更多
    public void loadMore(){
         if(hasMore()){
             currentPage++ ;
             getData(param,netInter);
         }
    }
}
