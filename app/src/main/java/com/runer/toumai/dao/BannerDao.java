package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.Constant;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;
import com.runer.toumai.widget.adviewpager.BannerBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * BannerDao
 */

public class BannerDao extends RunerBaseRequest {

    List<BannerBean> banners ;


    /**
     *  获得banners '
     * @return
     */
    public List<BannerBean> getBanners() {
        if (banners==null){
            return  new ArrayList<>();
        }
        if (banners.size()> Constant.MAX_AD_COUNT){
            return  banners.subList(0,Constant.MAX_AD_COUNT) ;
        }
        return banners;
    }

    public BannerDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.GET_BANNER)
        banners = JsonUtil.node2pojoList(result.findValue("result"),BannerBean.class) ;
    }

    /*area_id	是	int	1：pc端首页轮播图广告*/
    public void getBanners(String area_id){
        RunnerParam param =new RunnerParam() ;
        param.put("area_id",area_id);
        request(NetInter.GET_BANNERS,param, RequestCode.GET_BANNER);
    }


    public static  void main(String [] args){
        List<String> as =new ArrayList<>() ;
        as.add("111");
        as.add("112");
        as.add("113");
        as.add("114");
        as.add("115");
        as.add("116");
        as.add("117");
        as.add("118");
        as.add("119");
        as.add("1110");
        as.add("1111") ;
        System.err.println(as.subList(0,10));
    }


}
