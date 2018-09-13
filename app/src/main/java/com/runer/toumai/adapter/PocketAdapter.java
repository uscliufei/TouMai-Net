package com.runer.toumai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.AccountFlowBean;

import java.util.List;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * WalletAdapter
 * 我的钱包列表
 */

public class PocketAdapter extends BaseQuickAdapter<AccountFlowBean,BaseViewHolder> {

    public PocketAdapter(@Nullable List<AccountFlowBean> data) {
        super(R.layout.item_wallet_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, AccountFlowBean item) {

    }
}
