package com.runer.toumai.ui.activity;

import android.os.Bundle;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.dao.AccoutDao;
import com.runer.toumai.ui.fragment.WalletListFragment;

/**
 * 钱包页面点开后仍是顶部显示余额金额和充值、提现按钮，下方仍默认显示钱包明细，但可选择查看口袋明细。即原口袋页面并入钱包页面，
 * 两明细列表切换方式类似原“通知与消息”页面中“平台通知”与“用户私信”的切换方式。“口袋明细”切换按钮旁放置原口袋页面的FAQ图标及其弹窗。
 */
public class WalletActivity extends BaseActivity {
    private AccoutDao accoutDao ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        int type =getIntExtras("type",0);
        addFragmentList(R.id.container, WalletListFragment.getInstance(type));
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("钱包");
    }
}
