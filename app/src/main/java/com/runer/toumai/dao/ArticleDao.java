package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.RulesDetailBean;
import com.runer.toumai.bean.RulesListBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ArticleDao
 */

public class ArticleDao extends RunerBaseRequest {

    private List<RulesListBean> rulesListBeanList ;
    public List<RulesListBean> getRulesListBeanList() {
        return rulesListBeanList;
    }
    public ArticleDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    private RulesDetailBean rulesDetailBean ;

    public RulesDetailBean getRulesDetailBean() {
        return rulesDetailBean;
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode==RequestCode.CODE_0){
            rulesListBeanList= JsonUtil.node2pojoList(result.findValue("result"),RulesListBean.class);
        }else if(requestCode==RequestCode.CODE_1){
rulesDetailBean =JsonUtil.node2pojo(result,RulesDetailBean.class) ;
        }

    }
    public  void getRulesTypes(){
          RunnerParam param =new RunnerParam() ;
          request(NetInter.rule_types,param, RequestCode.CODE_0);
    }
    public void getRulesDetail(String id){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id);
        request(NetInter.rules_detail,param,RequestCode.CODE_1);
    }


}
