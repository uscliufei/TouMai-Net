package com.runer.toumai.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.toumai.R;
import com.runer.toumai.widget.LoamoreView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * BaseLoadMoreFragment
 * <p>
 * 下拉刷新加载更多基类，可自定义实现效果，注意生命周期的运用；
 */
public abstract class BaseLoadMoreFragment<T extends BaseQuickAdapter> extends BaseFragment {

    @InjectView(R.id.id_stickynavlayout_innerscrollview)
    public RecyclerView recyclerView;
    @InjectView(R.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefresh;


    public T baseQuickAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =null ;
        if (getLayOutRes()!=0){
             view = inflater.inflate(getLayOutRes(), container, false);
        }else{
             view = inflater.inflate(R.layout.base_fragment_list_layout, container, false);
        }
        ButterKnife.inject(this, view);
        return view;
    }

    protected  int getLayOutRes(){
        return  0 ;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (baseQuickAdapter == null) {
            baseQuickAdapter = (T) getAdater();
        }
        if (baseQuickAdapter != null) {
            //设置上拉加载更多；
            baseQuickAdapter.setLoadMoreView(getLoadMoreView());
            baseQuickAdapter.setOnLoadMoreListener(this, recyclerView);
        }
        recyclerView.setLayoutManager(getLayoutManager(getContext()));
        recyclerView.addItemDecoration(getDecoration(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(baseQuickAdapter);
        //设置刷新控件
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(getRefreshColor(getContext()));
    }


    //设置刷新和加载更多的完成；
    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        if (swipeRefresh != null) {
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                }
            });
        }
        if (recyclerView != null && baseQuickAdapter != null) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (baseQuickAdapter != null) {
                        baseQuickAdapter.loadMoreComplete();
                    }
                }
            });
            // baseQuickAdapter.loadMoreComplete();
        }
    }
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .type(0, R.drawable.item_decoration_shape).create();
    }
    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new RunerLinearManager(context, LinearLayoutManager.VERTICAL, false);
    }

    public LoadMoreView getLoadMoreView() {
        return new LoamoreView();
    }

    public abstract T getAdater();

    @Override
    public void onRefresh() {
        super.onRefresh();
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        loadMore();
    }

    public abstract void loadMore();

    public abstract void refresh();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
