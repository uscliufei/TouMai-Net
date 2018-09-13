package com.runer.toumai.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.runer.liabary.util.UiUtil;
import com.runer.liabary.widget.TabFragmentHost;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.HomeListRefreshEventBean;
import com.runer.toumai.bean.HomeMessageBean;
import com.runer.toumai.bean.MessageBean;
import com.runer.toumai.dao.AccoutDao;
import com.runer.toumai.dao.MessageDao;
import com.runer.toumai.ui.fragment.HomeFragment;
import com.runer.toumai.ui.fragment.MineFragment;
import com.runer.toumai.ui.fragment.SellGoodsFragment;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.BailTipsDialog;
import com.runer.toumai.widget.NormalTipsDialog;
import com.runer.toumai.widget.NormalTipsWith2ButtonDialog;
import com.tencent.mm.opensdk.utils.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;




public class HomeActivity extends BaseActivity implements  TabHost.OnTabChangeListener{

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;

    @InjectView(android.R.id.tabhost)
    TabFragmentHost mTabHost;
    public static HomeActivity homeActivity;
    private MessageDao messageDao ;
    private View  msgIcon;
    private AccoutDao accoutDao;
    private BailTipsDialog  bailDialog;
    private NormalTipsWith2ButtonDialog  messgeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ToumaiApplication.isFaq =true;
        homeActivity = this;
        ButterKnife.inject(this);
        /**
         * 权限的请求；
         */
        requestPermission();
        AppUtil.setMsgAlerted(this ,false);

        //todo
        AppUtil.setUserId(this,"1107");


        Logger.d(AppUtil.getUserId(this));


        View homeTabView = getTabItemView(R.drawable.home_home_selector, "查看最新");
        View saleProductView = getTabItemView(R.drawable.home_sale_selector, "出售商品");
        View mineView = getTabItemView(R.drawable.home_mine_selector, "个人中心");
        //-------------------------------蒲公英自动更新
        /**测试版本先将更新去掉
         *
         */
        //registerUpdatePyer();

        //设置底部的tab和相应的其他模块;
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setBackgroundResource(R.color.tab_color);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(homeTabView),
                HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("sale_products").setIndicator(saleProductView),
                SellGoodsFragment.class, null);
       msgIcon = mineView.findViewById(R.id.msg_icon);
        mTabHost.addTab(mTabHost.newTabSpec("mine").setIndicator(mineView),
                MineFragment.class, null);
        mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = (int) getResources().getDimension(R.dimen.tab_height);
        homeTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabHost.setCurrentTab(0);
                EventBus.getDefault().post(new HomeListRefreshEventBean());
            }
        });
        saleProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(AppUtil.chckeLogin(HomeActivity.this,false));
                if (!AppUtil.chckeLogin(HomeActivity.this, true)) {
                    UiUtil.showLongToast(HomeActivity.this, "尚未登录，请先进行登录");
                } else {
                    mTabHost.setCurrentTab(1);
                }
            }
        });
        mineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtil.chckeLogin(HomeActivity.this, true)) {
                    UiUtil.showLongToast(HomeActivity.this, "尚未登录，请先进行登录");
                } else {
                    mTabHost.setCurrentTab(2);
                }
            }
        });
        //切换tab
        if(getIntExtras("index",0)!=0){
            setCurrentTab(1);
        }
        EventBus.getDefault().register(this);
        mTabHost.setOnTabChangedListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HomeMessageBean event) {
       if(msgIcon!=null){
           msgIcon.setVisibility(View.VISIBLE);
       }
    };
    //切换tab
    public void setCurrentTab(int index){
        if(mTabHost!=null){
         mTabHost.setCurrentTab(index);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //todo
        AppUtil.setUserId(this,"1107");
        Logger.d(AppUtil.chckeLogin(this,false));

        if(AppUtil.chckeLogin(this,false)){
            if(messageDao==null){
                messageDao =new MessageDao(this,this);
            }
            messageDao.getMsgNum(AppUtil.getUserId(this));
            if (AppUtil.needMsgAlert(this)) {
                messageDao.firstGetMsgNum(AppUtil.getUserId(this));
            }
        }else{
            if(msgIcon!=null){
                msgIcon.setVisibility(View.GONE);
            }
        }

    }
    /*create tabLayout from drawable and title*/
    private View getTabItemView(int id, String title){
        View view = getLayoutInflater().inflate(R.layout.tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(id);
        TextView textView = (TextView) view.findViewById(R.id.tab_title);
        textView.setText(title);
        return view;
    }
    //关于自动更新；
    private static final long waitTime = 2000;
    private long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 两次返回键，退出程序
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                UiUtil.showLongToast(getApplicationContext(), "再按一次退出程序");
                touchTime = currentTime;
            } else {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.Msg_num){
           if(TextUtils.isEmpty(messageDao.getMsgNUm())||"0".equals(messageDao.getMsgNUm())){
               if(msgIcon!=null){
                   msgIcon.setVisibility(View.GONE);
               }
           }else{
               if(msgIcon!=null){
                   msgIcon.setVisibility(View.VISIBLE);
               }
           }
        }else if(requestCode==RequestCode.CODE_5){
            String balance = accoutDao.getBalance();
            //有账户余额的情况下
            if(!TextUtils.isEmpty(balance)){
               try{
                   if(Double.parseDouble(balance)<100){
                       setCurrentTab(0);
                       bailDialog = BailTipsDialog.show(this, "100", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Bundle bundle = new Bundle();
                               bundle.putInt("type", 1);
                               transUi(WalletActivity.class, bundle);
                               bailDialog.dismiss();
                           }
                       });
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
            }else{
                UiUtil.showLongToast(this,"请求账户余额出错!");
            }
        }else if (requestCode==RequestCode.FIRST_GET_MSG_NUM){
            //弹窗
            if (messageDao.hasFirstMsgNum()){
                AppUtil.setMsgAlerted(this,true);
                messgeDialog= NormalTipsWith2ButtonDialog.show(this, "消息", "新消息", "您有未读消息。", "前往查看", "回头再说", new NormalTipsWith2ButtonDialog.Nor2BT_Listener() {
                    @Override
                    public void commit(View view) {
                        messgeDialog.dismiss();
                        transUi(MessageActivity.class,null);
                    }
                    @Override
                    public void cancle(View view) {
                        messgeDialog.dismiss();
                    }
                });
            }
        }
    }
    @Override
    public void onTabChanged(String tabId) {
        if("sale_products".equals(tabId)){
//            if(accoutDao==null){
//                accoutDao =new AccoutDao(this,this);
//            }
//            accoutDao.getBalance(AppUtil.getUserId(this));
//            showProgress(true);
        }
    }

    private void requestPermission(){
        //动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS);
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
        }
    }


}
