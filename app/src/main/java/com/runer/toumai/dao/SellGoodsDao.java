package com.runer.toumai.dao;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.Logger;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.base.Constant;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;
import com.runer.toumai.util.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class SellGoodsDao extends RunerBaseRequest {
    private String goods_id;
    public String getGoods_id() {
        return goods_id;
    }
    public SellGoodsDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if (requestCode== RequestCode.CODE_0) {
            goods_id = result.findValue("result").asText();
        }else if (requestCode== RequestCode.CODE_1) {

        }
    }

    /**
     *
     * @param title
     * @param label
     * @param img
     * @param intro
     * @param price
     * @param is_fall
     * @param dark_time
     * @param bright_time
     * @param post_way
     * @param flaw
     *
     * 'user_id'=>$_user['id'],
    'title'=>$_POST['title'],
    'label'=>$label,
    'intro'=>$_POST['intro'],
    'flaw'=>$_POST['flaw'],
    'price'=>$_POST['price'],
    'is_fall'=>$_POST['is_fall'],
    'dark_time'=>$dark_time,
    'bright_time'=>$bright_time,
    'post_way'=>$_POST['post_way'],


    'stock'=>$stock,
    'is_repost'=>$_POST['is_repost'], ===1 为不成交自动重发
    'repost_type'=>$repost_type, ===1 无限次重发（直至成交为止） 2有限次重发  3.多库存有限 4。多库存的限时；
    'repost_times'=>$_POST['repost_times'], 重发的次数
    'repost_end_time'=>$repost_end_time,   重发的时间 为秒
    'ratio'=>$_POST['ratio'], //一口价较当前价上浮比例
    'buyout_price'=>$_POST['buyout_price'], //最低一口价

     */


    public void sellGoods(String title, String label, File img,String intro,String price,String is_fall,
                          String dark_time,String bright_time,String post_way,String flaw,String stock ,String is_repost
    ,String repost_type ,String repost_times ,String repost_end_time,String ratio ,String buyout_price){

        ArrayMap<String ,File> files =new ArrayMap<>() ;
        files.put("img",img) ;
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("user_id", AppUtil.getUserId(mContext));
        param.put("title", title);
        param.put("flaw", flaw);
        param.put("label",label );
        param.put("intro",intro );
        param.put("price", price);
        param.put("is_fall",is_fall );
        param.put("dark_time",dark_time);
        param.put("bright_time", bright_time);
        param.put("post_way", post_way);

        //二期添加的参数；
        if (TextUtils.isEmpty(stock)){
            stock ="1" ;
        }
        param.put("stock",stock);
        param.put("is_repost",is_repost);
        param.put("repost_type",repost_type) ;
        param.put("repost_times",repost_times);
        param.put("repost_end_time",repost_end_time) ;
        param.put("ratio",ratio) ;
        param.put("buyout_price",buyout_price) ;

        uploadFile(NetInter.SELL_GOODS,files,param, RequestCode.CODE_0);
    }
    public void addImgs(String goods_id,File img,String cover){
        ArrayMap<String ,File> files =new ArrayMap<>();
        files.put("img", img);
        RunnerParam param = new RunnerParam();
        param.put("goods_id",goods_id );
        param.put("cover",cover );
        uploadFile(NetInter.ADD_IMG, files, param, RequestCode.CODE_1);
    }
    public void addImagList(String goods_id,List<File> imgs  ,String cover){
        RequestParams requestParams =new RequestParams() ;
        RunnerParam param = new RunnerParam();
        param.put("goods_id",goods_id );
        param.put("cover",cover );
        JSONObject jsonObject = new JSONObject();
        try {
            if(param!=null)
                for (Object key : param.keySet()) {
                    jsonObject.put((String)key,param.get(key));
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(imgs!=null){
            for (int i = 0; i < imgs.size(); i++) {
                try {
                    requestParams.put("img"+i,imgs.get(i));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        String timesnamp = String.valueOf(System.currentTimeMillis());
        String sign = getSign(NetInter.ADD_IMG,timesnamp);
        requestParams.put("openid", Constant.OPEN_ID);
        requestParams.put("sign",sign);
        requestParams.put("a",NetInter.ADD_IMG.getA());
        requestParams.put("c",NetInter.ADD_IMG.getC());
        requestParams.put("param",jsonObject.toString());
        requestParams.put("timesnamp",timesnamp);
        //打印请求参数;
        Logger.d(requestParams);
        requestParams.setForceMultipartEntityContentType(true);
        requestParams.setAutoCloseInputStreams(true);
        originalPostRequest(NetConfig.BASE_REQUEST_URL,requestParams,RequestCode.CODE_1);
    }
    /*goods_id	是	int	商品id
type	是	string	类型：1直接下架，2下架前先补偿买家收益*/
    public void stopGood(String goods_id ,String type){
        RunnerParam param =new RunnerParam() ;
        param.put("goods_id",goods_id) ;
        param.put("type",type);
        request(NetInter.goods_stop,param,RequestCode.STOP_GOOD);
    }





}
