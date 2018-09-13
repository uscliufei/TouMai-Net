package com.runer.toumai.dao;

import android.content.Context;

import com.runer.net.interf.INetResult;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.bean.Label;
import com.runer.toumai.net.NetInter;
import com.runer.toumai.net.RunnerBaseLoadMoreRequest;
import com.runer.toumai.net.RunnerParam;

/**
 * Created by ruier on 2018/7/16.
 */
public class LabelListDao extends RunnerBaseLoadMoreRequest<Label> {


    public LabelListDao(BaseFragment context, INetResult iNetResult) {
        super(context, iNetResult);
    }

    public LabelListDao(Context context, INetResult iNetResult) {
        super(context, iNetResult);
    }
    public  void getLables(String label){
        RunnerParam param =new RunnerParam() ;
        param.put("label",label) ;
        getData(param, NetInter.labelsList);
    }



}
