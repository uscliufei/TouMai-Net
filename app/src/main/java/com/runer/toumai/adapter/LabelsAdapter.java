package com.runer.toumai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.Label;

import java.util.List;

/**
 * Created by ruier on 2018/7/16.
 */

public class LabelsAdapter extends BaseQuickAdapter<Label,BaseViewHolder> {
    public LabelsAdapter(@Nullable List<Label> data) {
        super(R.layout.item_goods_tags_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Label item) {
        helper.setText(R.id.tagname,item.getLabel()+"|"+item.getNum());
        helper.getView(R.id.tag_item).setOnClickListener(view -> {
         if (onTagItemClcikListener!=null){
             onTagItemClcikListener.onItemClcick(item);
         }
        });
    }
    public  OnTagItemClcikListener onTagItemClcikListener ;

    public void setOnTagItemClcikListener(OnTagItemClcikListener onTagItemClcikListener) {
        this.onTagItemClcikListener = onTagItemClcikListener;
    }

    public interface  OnTagItemClcikListener{
        void onItemClcick(Label label);
    }
}
