package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.bean.BaseStateBean;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.AnjiaRulesDialog;

import java.util.List;

/**
 * Created by szhua on 2017/7/17/017.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * HomeCheckGoodsByStateAdapter
 *
 * 首页选择商品状态Adapter
 */

public class HomeCheckGoodsByStateAdapter extends BaseQuickAdapter<BaseStateBean,BaseViewHolder> {

    public HomeCheckGoodsByStateAdapter(@Nullable List<BaseStateBean> data) {
        super(R.layout.item_home_check_by_state,data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BaseStateBean item) {

        helper.setText(R.id.tag_view,item.getTag());
        if(item.isSelected()){
            helper.getView(R.id.tag_view).setSelected(true);
        }else{
            helper.getView(R.id.tag_view).setSelected(false);
        }
        helper.getView(R.id.tag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("暗价".equals(item.getTag())){
                    if(!AppUtil.chckeLogin(mContext,false)){
                        UiUtil.showLongToast(mContext,"请先进行登录");
                        return;
                    }
                    if(!AppUtil.checkIsMakeUp(mContext)||!AppUtil.checkDarkLearn(mContext)){
                        AnjiaRulesDialog.show(mContext,AppUtil.checkIsMakeUp(mContext),AppUtil.checkDarkLearn(mContext));
                        return;
                    }
                }
                if (item.isSelected()){
                        item.setSelected(false);
                        notifyDataSetChanged();
                    }else{
                        item.setSelected(true);
                        notifyDataSetChanged();
                    }
            }
        });
    }
}
