package com.runer.toumai.dao;

import android.content.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.ExpressBean;
import com.runer.toumai.bean.ExpressInfo;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * OrderDao
 订单相关
 */

public class OrderDao extends RunerBaseRequest {

    private List<ExpressBean> expressBeanList;
    private List<GoodListBean> datas;
    private ExpressInfo expressInfo ;

    public ExpressInfo getExpressInfo() {
        return expressInfo;
    }

    public List<GoodListBean> getDatas() {
        return datas;
    }

    public List<ExpressBean> getExpressBeanList() {
        return expressBeanList;
    }

    public OrderDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
    if(requestCode==RequestCode.CODE_2){
     expressBeanList = JsonUtil.node2pojoList(result.findValue("result"),ExpressBean.class);
    }else if(requestCode==RequestCode.CODE_4){
        datas =JsonUtil.node2pojoList(result.findValue("result"),GoodListBean.class);
    }else if(requestCode==RequestCode.EXPRESS_LIST){
        expressInfo =JsonUtil.node2pojo(result,ExpressInfo.class);
    }
    }


    //获得物流公司的列表
    public void getExpressLists(){
        RunnerParam param =new RunnerParam();
        request(NetInter.express_lists,param, RequestCode.CODE_2);
    }

    /*order_id	是	int	订单id
express_id	是	int	物流公司id
post_remark
post_code	是	int	快递单号*/
    public void addExpress(String order_id ,String express_id ,String post_code ){
        RunnerParam param =new RunnerParam() ;
        param.put("order_id",order_id);
        param.put("express_id",express_id) ;
        param.put("post_code",post_code);
        request(NetInter.express_add,param,RequestCode.CODE_3);
    }
    public void addExpress(String order_id ,String express_id ,String post_code,String  post_remark){
        RunnerParam param =new RunnerParam();
        param.put("order_id",order_id);
        param.put("express_id",express_id);
        param.put("post_code",post_code);
        param.put("post_remark",post_remark);
        request(NetInter.express_add,param,RequestCode.CODE_3);
    }

    /**
     * 用户的ID
     * @param user_id
     * 查询的关键字
     * @param title
     * 查询的状态；
     * @param status
     */
    public void getOrderList(String user_id ,String title ,String status ){
        datas =new ArrayList<>();
     RunnerParam param =new RunnerParam() ;
     param.put("user_id",user_id);
     param.put("title",title) ;
     param.put("status",status) ;
     request(NetInter.order_lists,param,RequestCode.CODE_4);
    }







    public void orderGet(String id ){
        RunnerParam param =new RunnerParam();
        param.put("id",id) ;
        request(NetInter.order_get,param,RequestCode.ORDER_GET);
    }

/*order_id	是	int	订单id
*/
    public void getExpressInfo(String order_id ){
       RunnerParam param =new RunnerParam() ;
        param.put("order_id",order_id) ;
        request(NetInter.order_express,param,RequestCode.EXPRESS_LIST);
    }

    public void  orderPay(String id){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id) ;
        request(NetInter.order_pay,param,RequestCode.ORDER_PAY);
    }


}
