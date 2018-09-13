package com.runer.toumai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.MineListItemBean;

import java.util.List;

/**
 * Created by szhua on 2017/7/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * UserCenterItemAdapter
 * 个人中心消息
 */
public class UserCenterItemAdapter extends BaseQuickAdapter<MineListItemBean,BaseViewHolder> {

    public UserCenterItemAdapter(@Nullable List<MineListItemBean> data) {
        super(R.layout.item_mine_layout, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MineListItemBean item) {
        helper.setText(R.id.title,item.getTitle())
                .setImageResource(R.id.icon,item.getImg());

        if(item.isHasMsg()){
            helper.setVisible(R.id.msg_icon,true);
        }else{
            helper.setVisible(R.id.msg_icon,false);
        }


    }

}
