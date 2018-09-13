package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.runer.toumai.R;
import com.runer.toumai.bean.LogisticalBean;

import java.util.List;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * LogisticalAdapter
 * 物流adapter;
 */
public class LogisticalAdapter extends BaseQuickAdapter<LogisticalBean,BaseViewHolder> {
    public LogisticalAdapter(@Nullable List<LogisticalBean> data) {
        super(R.layout.item_logistical_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, LogisticalBean item) {
        if(item.isFirst()){
            ((ImageView)helper.getView(R.id.circle)).setImageResource(R.drawable.red_cirle);
        }else{
            ((ImageView)helper.getView(R.id.circle)).setImageResource(R.drawable.gray_circle);
        }
        helper.setText(R.id.content,item.getAcceptStation()) ;
        helper.setText(R.id.time,item.getAcceptTime());
        if(item.isEnd()){
          helper.setVisible(R.id.line,false);
        }else {
            helper.setVisible(R.id.line, true);
        }
    }
}
