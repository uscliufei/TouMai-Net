package com.runer.toumai.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.FollowUserBean;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.ui.activity.TpyesProListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.runer.toumai.R.id.header;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * UserConcernAdapter
 * 个人关注相关
 */
public class UserConcernAdapter extends BaseQuickAdapter<FollowUserBean,BaseViewHolder> {
    public UserConcernAdapter(@Nullable List<FollowUserBean> data) {
        super(R.layout.item_user_concern ,data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final FollowUserBean item) {


        Picasso.with(mContext).load(NetConfig.HEAD_PATH+item.getHead()).placeholder(R.drawable.logo).into((ImageView) helper.getView(header));
        helper.setText(R.id.user_name,item.getUser_name())
                .setText(R.id.age,item.getAge()+"岁")
                .setText(R.id.address,item.getCity()+item.getArea());
        if(TextUtils.isEmpty(item.getAuth())){
            helper.setText(R.id.renzheng,"暂无认证信息");
        }else{
            helper.setText(R.id.renzheng,item.getAuth());
        }
        if(TextUtils.isEmpty(item.getIntro())){
            helper.setText(R.id.des,"暂未简介");
        }else{
            helper.setText(R.id.des,item.getIntro());
        }
        if("女".equals(item.getSex())){
            helper.setImageResource(R.id.sex_icon,R.drawable.woman);
        }else{
            helper.setImageResource(R.id.sex_icon,R.drawable.man);
        }


        helper.getView(R.id.msg_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemsClickListener!=null){
                    onItemsClickListener.onMsgToClick(item,helper.getPosition());
                }
            }
        });

        helper.getView(R.id.check_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemsClickListener!=null){
                    onItemsClickListener.onGoodsClick(item,helper.getPosition());
                }
            }
        });

        helper.getView(R.id.quit_concern).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemsClickListener!=null){
                    onItemsClickListener.onDelClick(item,helper.getPosition());
                }
            }
        });


    }


    public void setOnItemsClickListener(OnItemsClickListener onItemsClickListener) {
        this.onItemsClickListener = onItemsClickListener;
    }

    OnItemsClickListener onItemsClickListener ;

    public interface OnItemsClickListener{
        void onMsgToClick(FollowUserBean followUserBean ,int pos);
        void onDelClick(FollowUserBean followUserBean ,int pos);
        void onGoodsClick(FollowUserBean followUserBean ,int pos);
    }



}
