package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.FavBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * GoodFavDao
 */

public class GoodFavDao extends RunnerBaseLoadMoreRequest<FavBean>{

    public GoodFavDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    public void getGoodFav(String user_id){
        RunnerParam  param =new RunnerParam() ;
        param.put("user_id",user_id);
        getData(param, NetInter.GET_GOODS_FAV);
     }
     /*goods_id	是	int	商品id
user_id	是	int	用户id*/
    public void delFav(String user_id ,String goods_id){
        RunnerParam param =new RunnerParam();
        param.put("user_id",user_id) ;
        param.put("goods_id",goods_id) ;
        request(NetInter.COLLECT_DEL,param,RequestCode.DEL_FAV);
    }
    public void addFav(String user_id ,String goods_id){
        RunnerParam param =new RunnerParam();
        param.put("user_id",user_id) ;
        param.put("goods_id",goods_id) ;
        request(NetInter.GOLLECT_ADD,param,RequestCode.ADD_FAV);
    }
}
