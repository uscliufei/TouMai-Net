package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.PocketAdapter;
import com.runer.toumai.adapter.WalletAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.AccountFlowBean;
import com.runer.toumai.dao.AccountListsDao;
import com.runer.toumai.dao.AccoutDao;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;

import java.util.List;

/**
 * Created by szhua on 2017/7/19/019.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * PocketFragment
 * 我的口袋
 */
public class PocketFragment extends BaseLoadMoreFragment<WalletAdapter>{

    private View headerView ;
    private List<AccountFlowBean> datas ;
    private AccountListsDao accountListsDao ;
    private AccoutDao accoutDao ;
    private TextView balanceTv ;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        headerView =View.inflate(getContext(), R.layout.header_pocket_layout,null);
        balanceTv = (TextView) headerView.findViewById(R.id.balance);
        super.onViewCreated(view, savedInstanceState);
        baseQuickAdapter.addHeaderView(headerView);
        accountListsDao =new AccountListsDao(getContext(),this);
        accountListsDao.getAccountList(AppUtil.getUserId(getContext()),"1","0");
        accoutDao =new AccoutDao(getContext(),this);
        accoutDao.getBalance(AppUtil.getUserId(getContext()),"1");
        showProgress(true);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if("0".equals(baseQuickAdapter.getData().get(position).getGoods_id())|| TextUtils.isEmpty(baseQuickAdapter.getData().get(position).getGoods_id())){
                }else{
                    Bundle bundle =new Bundle() ;
                    bundle.putString("id",baseQuickAdapter.getItem(position).getGoods_id());
                    transUi(ProInfoActivity.class,bundle);
                }
            }
        });
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

                if(datas==null||datas.isEmpty()){
                    baseQuickAdapter.setHeaderAndEmpty(true);
                    baseQuickAdapter.setEmptyView(getEmptyViewFixedHeight("暂无钱包明细"));
                }

        }else if(requestCode==RequestCode.CODE_4){
           balanceTv.setText("￥"+accoutDao.getBalance());
        }
    }
    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
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
    accoutDao.getBalance(AppUtil.getUserId(getContext()),"1");
    }
}
