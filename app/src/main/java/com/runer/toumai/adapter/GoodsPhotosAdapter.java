package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.ImgsBean;
import com.runer.toumai.net.NetConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szhua on 2017/8/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * GoodsPhotosAdapter
 */

public class GoodsPhotosAdapter extends BaseQuickAdapter<ImgsBean,BaseViewHolder> {
    public GoodsPhotosAdapter(@Nullable List<ImgsBean> data) {
        super(R.layout.item_goods_photos,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ImgsBean item) {
        Picasso.with(mContext).load(NetConfig.GOODS_IMG + item.getImg()).placeholder(R.drawable.empty_img).into((ImageView) helper.getView(R.id.photo));
    }
}
