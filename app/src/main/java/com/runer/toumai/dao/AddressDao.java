package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.AddressBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AddressDao
 */



public class AddressDao extends RunnerBaseLoadMoreRequest<AddressBean> {

    private String id ;


    public String getId() {
        return id;
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        super.onRequestSuccess(result, requestCode);
        if(requestCode==RequestCode.CODE_0){
            id =result.findValue("id").asText();
        }
    }

    public AddressDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    public void getAddressList(String user_id){
        RunnerParam param =new RunnerParam();
        param.put("user_id",user_id);
        getData(param, NetInter.GET_ADDRESS_LIST);
    }

    /**user_id	是	int	用户ID
     user_name	是	varchar	用户名
     provice	是	varchar	省
     city	是	varchar	市
     area	是	varchar	区
     address	是	varchar	收货地址
     mobile	是	varchar	手机号*/

    //添加收货地址
    public void addAddress(String user_id ,AddressBean addressBean){
        RunnerParam param  =new RunnerParam() ;
        param.put("user_id" ,user_id );
        param.put("user_name",addressBean.getUser_name());
        param.put("province",addressBean.getProvince()) ;
        param.put("city",addressBean.getCity()) ;
        param.put("area",addressBean.getArea());
        param.put("address",addressBean.getAddress()) ;
        param.put("mobile",addressBean.getMobile()) ;
        request(NetInter.User_address_Add,param, RequestCode.CODE_0);
    }

    //设置为默认的收货地址
    public void setAddressDefault(String user_id ,String id ){
        RunnerParam param =new RunnerParam() ;
        param.put("user_id",user_id) ;
        param.put("id",id) ;
        request(NetInter.User_address_default,param, RequestCode.CODE_1);
     }
     //删除收货地址
     public void delAddress(String id){
         RunnerParam param =new RunnerParam();
         param.put("id",id) ;
         request(NetInter.User_address_Del,param,RequestCode.CODE_2);
     }

     //更改收货地址
     public void upDateAddress(String user_id ,AddressBean addressBean){
         RunnerParam param  =new RunnerParam() ;
         param.put("user_id" ,user_id );
         param.put("id",addressBean.getId());
         param.put("user_name",addressBean.getUser_name());
         param.put("provice",addressBean.getProvince()) ;
         param.put("city",addressBean.getCity()) ;
         param.put("area",addressBean.getArea());
         param.put("address",addressBean.getAddress()) ;
         param.put("mobile",addressBean.getMobile()) ;
         request(NetInter.User_address_update,param, RequestCode.CODE_3);
     }

     /*user_id	是	int	用户id
is_default	否	int	1默认收货地址*/
     public void getOrderAddressInfo(String user_id ,String is_default){
         RunnerParam param =new RunnerParam() ;
         param.put("user_id",user_id) ;
         param.put("is_default","1");
         request(NetInter.orderAddressInfo,param,RequestCode.orderAddress);
     }

}
