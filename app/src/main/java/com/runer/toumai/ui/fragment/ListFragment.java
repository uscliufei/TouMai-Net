package com.runer.toumai.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.HomeListAdapter;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.GetGoodParam;
import com.runer.toumai.dao.GoodsListDao;
import com.runer.toumai.ui.activity.LoginActivity;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseLoadMoreFragment<HomeListAdapter> {

    private GoodsListDao goodsListDao;
    private String title = "";
    private String user_id ="" ;

    public static ListFragment getInstance(String title ,String user_id){
        ListFragment listFragment = new ListFragment();
        listFragment.title = title;
        listFragment.user_id =user_id;
        return listFragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setEnabled(false);
        goodsListDao =new GoodsListDao(getContext(),this);
        GetGoodParam getGoodParam =new GetGoodParam() ;
        getGoodParam.setTitle(title);
        if (!TextUtils.isEmpty(user_id)) {
            getGoodParam.setUser(user_id);
            getGoodParam.setSell_state1("2");
        }
        goodsListDao.getGoodsList(getGoodParam);

        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Bundle bundle =new Bundle() ;
                    bundle.putString("id",baseQuickAdapter.getItem(position).getId());
                    transUi(ProInfoActivity.class,bundle);
            }
        });
    }
    @Override
    public HomeListAdapter getAdater() {
        return new HomeListAdapter(null);
    }
    @Override
    public void loadMore() {
        if(goodsListDao.hasMore()){
            goodsListDao.loadMore();
        }else{
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    baseQuickAdapter.loadMoreEnd();
                }
            }, 1000);
        }
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            Logger.d(goodsListDao.getDatas());
            baseQuickAdapter.setNewData(goodsListDao.getDatas());
            baseQuickAdapter.setEmptyView(R.layout.empty_view);
        }
    }
    @Override
    public void refresh() {
        if (goodsListDao != null) {
            goodsListDao.refresh();
        }
    }
    public void setParam(GetGoodParam getGoodParam){
        if(goodsListDao.getDatas()!=null)
            goodsListDao.getDatas().clear();
        goodsListDao.getGoodsList(getGoodParam);
        showProgress(true);
    }
    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        if (getParentFragment() != null) {
            ((BaseFragment)getParentFragment()).onCompeleteRefresh();
        }
    }

}
