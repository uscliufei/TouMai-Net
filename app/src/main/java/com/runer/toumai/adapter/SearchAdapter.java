package com.runer.toumai.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.GoodListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class SearchAdapter extends BaseQuickAdapter<GoodListBean,BaseViewHolder> {


    public SearchAdapter(@Nullable List<GoodListBean> data) {
        super(R.layout.item_title,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodListBean item) {
        helper.setText(R.id.title, item.getTitle());
    }
}
