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
import com.runer.toumai.adapter.WalletAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.AccountFlowBean;
import com.runer.toumai.dao.AccountListsDao;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;

import java.util.List;
/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MyEarnedFragment
 * 我赚的列表
 */
public class MyEarnedFragment extends BaseLoadMoreFragment<WalletAdapter> {
    private List<AccountFlowBean> datas ;
    private AccountListsDao accountListsDao ;
    public static MyEarnedFragment getInstance(){
        MyEarnedFragment myEarnedFragment =new MyEarnedFragment() ;
        return  myEarnedFragment ;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountListsDao =new AccountListsDao(getContext(),this);
        accountListsDao.getAccountList(AppUtil.getUserId(getContext()),"0","1");
        showProgress(true);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle =new Bundle() ;
                bundle.putString("id",baseQuickAdapter.getItem(position).getGoods_id());
                transUi(ProInfoActivity.class,bundle);
            }
        });
        baseQuickAdapter.setType("earned");
    }

    @Override
    public WalletAdapter getAdater(){
        return new WalletAdapter(datas);
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            datas =accountListsDao.getDatas() ;
            baseQuickAdapter.setNewData(datas);
        }
    }
    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
    }
    @Override
    public void loadMore() {
        if(accountListsDao.hasMore()){
            accountListsDao.loadMore();
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
    public void refresh() {
        accountListsDao.refresh();

    }
}
