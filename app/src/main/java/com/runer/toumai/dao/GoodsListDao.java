package com.runer.toumai.dao;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.GetGoodParam;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * GoodsListDao
 */

public class GoodsListDao extends RunnerBaseLoadMoreRequest<GoodListBean> {
    public GoodsListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    /*is_del	否	int	默认0显示未删除商品，1显示已删除商品，2全部显示
user_id	否	int	用户id
order	否	int	排序（与下面条件配合使用）：1按底价 从低到高，2按底价 从高到低，3按底价区间 从高到低，4按底价区间 从高到低，5按当前价 从低到高，6按当前价 从高到低，7按当前价区间 从高到低，8按当前价区间 从高到低，9按发布时间 由近到远，10按发布时间 由远到近，11按出价次数 从低到高，12按出价次数 从高到低，13按出价次数 从低到高，14按出价次数 从高到低
start_price	否	decimal	按底价 开始价格
end_price	否	decimal	按底价 结束价格
start_time	否	datetime	按发布时间 开始时间：yyyy-mm-dd
end_time	否	datetime	按发布时间 结束时间：yyyy-mm-dd
sur_time	否	varchar	按距离结束倒计时：5:5分钟以内，15:15分钟以内，60:1小时以内
start_now	否	decimal	按当前价 开始价
end_now	否	decimal	按当前价 结束价
start_offer	否	int	按出价次数 开始次数
end_offer	否	int	按出价次数 结束次数
sell_state	否	int	价格阶段：1暗价，2明价
fall_state	否	int	降价：0未降价，1已降价
heart_time	否	int	心跳时间：1是
is_order	否	int	1已结束-成交，0已结束-未成交
label	否	varchar	标签名称
user	否	int	用户id*/
    public void getGoodsList(GetGoodParam getGoodParam ){
        currentPage =1;
        RunnerParam  param =new RunnerParam() ;
        param.put("is_del",getGoodParam.getIs_del());
        param.put("user_id",getGoodParam.getUser_id());
        param.put("order",getGoodParam.getOrder());
        param.put("start_price",getGoodParam.getStart_price());
        param.put("end_price",getGoodParam.getEnd_price());
        param.put("start_time",getGoodParam.getStart_time());
        param.put("end_time",getGoodParam.getEnd_time());
        param.put("sur_time",getGoodParam.getSur_time());
        param.put("start_now",getGoodParam.getStart_now());
        param.put("end_now",getGoodParam.getEnd_time());
        param.put("start_offer",getGoodParam.getStart_offer());
        param.put("end_offer",getGoodParam.getEnd_offer());
        param.put("sell_state1",getGoodParam.getSell_state1());
        param.put("sell_state2",getGoodParam.getSell_state2());
        param.put("fall_state",getGoodParam.getFall_state());
        param.put("heart_time",getGoodParam.getHeart_time());
        param.put("is_order1",getGoodParam.getIs_order1());
        param.put("is_order0",getGoodParam.getIs_order0());
        param.put("label",getGoodParam.getLabel());
        param.put("title",getGoodParam.getTitle()) ;
        param.put("user",getGoodParam.getUser());
        param.put("status",getGoodParam.getStatus());
        getData(param,NetInter.GET_GOODS_LIST);
    }
}
