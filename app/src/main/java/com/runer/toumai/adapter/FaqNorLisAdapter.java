package com.runer.toumai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.FaqListBean;

import java.util.List;

/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * FaqNorLisAdapter
 */

public class FaqNorLisAdapter extends BaseQuickAdapter<FaqListBean,BaseViewHolder> {

    public FaqNorLisAdapter(@Nullable List<FaqListBean> data) {
        super(R.layout.item_faq_list_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, FaqListBean item) {
          if(item.isSpace()){
              helper.setVisible(R.id.divider,true) ;
          }else{
              helper.setVisible(R.id.divider,false) ;
          }
          helper.setText(R.id.content,item.getContent());

    }

}
