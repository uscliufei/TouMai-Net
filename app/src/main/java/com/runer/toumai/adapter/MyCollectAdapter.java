package com.runer.toumai.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.liabary.swipelayout.SwipeMenuLayout;
import com.runer.toumai.R;
import com.runer.toumai.bean.FavBean;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MyOderAdapter
 */
public class MyCollectAdapter extends BaseQuickAdapter<FavBean,BaseViewHolder> {
    public MyCollectAdapter(@Nullable List<FavBean> data) {
        super(R.layout.item_my_collect,data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final FavBean item) {
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.low_price,item.getPrice())
                .setText(R.id.current_price,item.getNow_price())
                .setText(R.id.price_num,item.getOffer_num())
                .setText(R.id.publish_time,"发布时间"+item.getCreate_time());
        Picasso.with(mContext).load(NetConfig.GOODS_IMG+item.getImg()).placeholder(R.drawable.empty_img).into((ImageView) helper.getView(R.id.img));
        //已结束的情况下
        if("1".equals(item.getIs_end())){
            helper.setText(R.id.left_time,"已结束");
        }else{
            helper.setText(R.id.left_time,"剩余:"+ AppUtil.getTimeStr(item.getRest().getD(),item.getRest().getH(),item.getRest().getM(),item.getRest().getS()));
        }
        TextView tag  =helper.getView(R.id.ming_bt);
        //判断明暗价
        //不是心跳时间;
        if("1".equals(item.getSell_state())){
            tag.setBackgroundResource(R.drawable.light_orange_state);
            tag.setText("暗价");
        }else if("2".equals(item.getSell_state())){
            tag.setBackgroundResource(R.drawable.green_state);
            tag.setText("明价");
        }
        helper.getView(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id",item.getId());
                Intent intent =new Intent(mContext,ProInfoActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        helper.getView(R.id.delete_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteClicklistener!=null){
                    onDeleteClicklistener.onDelete(item,helper.getPosition());
                }
            }
        });
    }
    OnDeleteClicklistener onDeleteClicklistener;
    public void setOnDeleteClicklistener(OnDeleteClicklistener onDeleteClicklistener) {
        this.onDeleteClicklistener = onDeleteClicklistener;
    }

    public interface  OnDeleteClicklistener{
        void onDelete(FavBean favBean,int pos);
    }


}
