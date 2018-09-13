package com.runer.toumai.dao;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.net.interf.INetResult;
import com.runer.toumai.bean.QuestionBean;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunerBaseRequest;
import com.runer.toumai.net.RunnerParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by szhua on 2017/8/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * QuestionDao
 */

public class QuestionDao extends RunerBaseRequest {

    private List<QuestionBean> questionBeens ;

    public List<QuestionBean> getQuestionBeens() {
        return questionBeens;
    }

    public QuestionDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode==RequestCode.CODE_4){
            questionBeens = JsonUtil.node2pojoList(result.findValue("result"),QuestionBean.class);
        }
    }

    public void getQuestList(String id){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id);
        request(NetInter.question_list,param, RequestCode.CODE_4);
    }

    /*goods_id	是	int	商品id
user_id	是	int	提问用户的id
question	是	string	问题内容*/
    public void addQestion(String goods_id,String user_id ,String question){
        RunnerParam param =new RunnerParam() ;
        param.put("goods_id",goods_id) ;
        param.put("user_id",user_id) ;
        param.put("question",question) ;
        request(NetInter.question_add,param,RequestCode.CODE_3);
    }

    /*id	是	int	问题id
answer	是	varchar	回答内容*/
    public void answerQuestion(String id ,String answer){
        RunnerParam param =new RunnerParam() ;
        param.put("id",id) ;
        param.put("answer",answer) ;
        request(NetInter.question_answer,param,RequestCode.CODE_6);
    }




}


