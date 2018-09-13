package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.MyCollectAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.FavBean;
import com.runer.toumai.dao.GoodFavDao;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;

import java.util.List;

/**
 * Created by szhua on 2017/7/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * 收藏列表界面
 * CollectGoodsFragment
 */
public class CollectGoodsFragment extends BaseLoadMoreFragment<MyCollectAdapter> {

    private int currrentPos ;
    private GoodFavDao goodFavDao ;
    private List<FavBean> favBeanList ;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseQuickAdapter.setEnableLoadMore(false);


        baseQuickAdapter.setOnDeleteClicklistener(new MyCollectAdapter.OnDeleteClicklistener() {
            @Override
            public void onDelete(FavBean favBean, int pos) {
                    currrentPos =pos ;
                    goodFavDao.delFav(AppUtil.getUserId(getContext()),favBean.getId());
                showProgress(true);
            }
        });
        goodFavDao =new GoodFavDao(getContext(),this);
        goodFavDao.getGoodFav(AppUtil.getUserId(getContext()));
        showProgress(true);
    }
    @Override
    public MyCollectAdapter getAdater() {
        return new MyCollectAdapter(favBeanList);
    }
    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
    }
    @Override
    public void loadMore() {
        baseQuickAdapter.loadMoreEnd();
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            favBeanList =goodFavDao.getDatas() ;
            baseQuickAdapter.setNewData(favBeanList);

            if(AppUtil.isEmpty(favBeanList)){
                baseQuickAdapter.setEmptyView(getEmptyView("暂无收藏"));
            }


        }else if(requestCode==RequestCode.DEL_FAV){
           try {
               favBeanList.remove(currrentPos);
               baseQuickAdapter.notifyItemRemoved(currrentPos);
           }catch (Exception e){
           }
        }
    }
    @Override
    public void refresh() {
        goodFavDao.refresh();
    }
}
