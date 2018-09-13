package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.OfferInfo;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class OfferDao extends RunerBaseRequest {
    private List<OfferInfo> datas=new ArrayList<>();

    public OfferDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    public List<OfferInfo> getDatas() {
        return datas;
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if (requestCode== RequestCode.CODE_5) {
            datas = JsonUtil.node2pojoList(result.findValue("result"), OfferInfo.class);
        }
    }

    public void getOfferList(String goods_id) {
        RunnerParam param = new RunnerParam();
        param.put("goods_id", goods_id);
        request(NetInter.OFFER_LISTS, param, RequestCode.CODE_5);
    }

    /*出价*/
    /*goods_id	是	int	商品id
user_id	是	int	用户id
price	是	decimal	所出价格
all_price	否	int	支付溢价还是总价：1支付总价
is_auto	否	int	是否为托管出价:0否，1是
anonymous	否	int	匿名出价，默认为0，匿名出价为1*/
    public void addOffer(String goods_id ,String user_id ,String price ,String all_price ,String is_auto ,String anonymous){
        RunnerParam param =new RunnerParam();
        param.put("goods_id",goods_id);
        param.put("user_id",user_id);
        param.put("price",price);
        param.put("all_price",all_price);
        param.put("is_auto",is_auto);
        param.put("anonymous",anonymous);
        request(NetInter.offer_add,param,RequestCode.ADD_OFFER);
    }
    /*user_id	是	int	用户id
goods_id	是	int	商品id
price	是	decimal	出价金额
anonymous	否	int	匿名出价，默认为0，匿名出价为1*/
    public void addAutoOffer(String user_id ,String good_id, String price ,String anonymous,String all_price){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        param.put("goods_id",good_id) ;
        param.put("price",price) ;
        param.put("anonymous",anonymous);
        param.put("all_price",all_price);
        request(NetInter.add_auto_offer,param,RequestCode.ADD_AUTO_OFFER);
    }

    /*goods_id	是	int	商品id
user_id	是	int	用户id
price	是	decimal	出价金额
anonymous	否	int	匿名出价，默认为0，匿名出价为1
all_price	是	int	全款支付，1全款，0溢价*/
    public void addDark(String goods_id ,String user_id ,String price ,String anonymous ,String allPrice){
        RunnerParam param =new RunnerParam() ;
        param.put("goods_id",goods_id );
        param.put("user_id",user_id) ;
        param.put("price",price) ;
        param.put("all_price",allPrice);
        param.put("anonymous",anonymous) ;
        request(NetInter.add_dark,param,RequestCode.ADD_DARK);
    }
    /*goods_id	是	int	商品id
user_id	否	int	用户id：用于判断是否为本人最高出价*/
    public void totalAutoOffer(String goods_id ,String user_id ){
        RunnerParam param =new RunnerParam() ;
        param.put("goods_id",goods_id );
        param.put("user_id",user_id) ;
        request(NetInter.total_auto_offer,param,RequestCode.TOTAL_AUTO_OFFER);
    }

    /**一口价买断；
     * anonymous	否	int	匿名出价，默认为0，匿名出价为1
     * @param user_id
     * @param goods_id
     * @param anonymous
     */
    public  void buyout(String user_id ,String goods_id ,String anonymous ){
        RunnerParam param =new RunnerParam() ;
        param.put("goods_id",goods_id );
        param.put("user_id",user_id) ;
        param.put("anonymous",anonymous);
        request(NetInter.add_buyout,param,RequestCode.ADD_BUYOUT);
    }


}
