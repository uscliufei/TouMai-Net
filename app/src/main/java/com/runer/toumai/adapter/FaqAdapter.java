package com.runer.toumai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.FaqBean;

import java.util.List;

/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * FaqAdapter
 */

public class FaqAdapter extends BaseQuickAdapter<FaqBean ,BaseViewHolder> {
    public FaqAdapter(@Nullable List<FaqBean> data) {
        super(R.layout.item_faq_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, FaqBean item) {
       helper.setText(R.id.qustion,"【问】\n"+item.getQuestion());
        helper.setText(R.id.answer,"【答】\n"+item.getAnswer());
    }
}
