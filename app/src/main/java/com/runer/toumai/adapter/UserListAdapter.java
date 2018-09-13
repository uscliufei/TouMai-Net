package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.UserBean;
import com.runer.toumai.net.NetConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ruier on 2018/7/14.
 */

public class UserListAdapter extends BaseQuickAdapter<UserBean,BaseViewHolder> {

    private UserListClickListener userListClickListener ;


    public UserListAdapter(@Nullable List data) {
        super(R.layout.item_user_list_layout ,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.user_name,item.getUser_name());
        Picasso.with(mContext).load(NetConfig.HEAD_PATH+item.getHead())
                .resize(300,300).centerCrop().placeholder(R.drawable.empty_img).into((ImageView) helper.getView(R.id.header));
        helper.getView(R.id.check_goods).setOnClickListener(view -> {
            userListClickListener.onGoodsCheck(item);
        });
        helper.getView(R.id.send_message).setOnClickListener(view -> {
            userListClickListener.onMessageCheck(item);
        });
    }

    public void setUserListClickListener(UserListClickListener userListClickListener) {
        this.userListClickListener = userListClickListener;
    }

    public   interface   UserListClickListener{
        void onGoodsCheck(UserBean userBean);
        void onMessageCheck(UserBean userBean);
   }

}
