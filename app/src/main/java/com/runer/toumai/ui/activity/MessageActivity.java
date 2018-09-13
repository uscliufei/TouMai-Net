package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.runer.liabary.tab.CommonTabLayout;
import com.runer.liabary.tab.SlidingTabLayout;
import com.runer.liabary.tab.listener.CustomTabEntity;
import com.runer.liabary.tab.listener.OnTabSelectListener;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.PerMessageListAdapter;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.BaseFragmentPagerAdapter;
import com.runer.toumai.dao.MessageDao;
import com.runer.toumai.ui.fragment.ConcernMessagelIstFragment;
import com.runer.toumai.ui.fragment.MessageListFragment;
import com.runer.toumai.ui.fragment.PerMessageListFragment;
import com.runer.toumai.util.AppUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

/*通知与消息*/
public class MessageActivity extends BaseActivity {



    private MessageListFragment platformFragmemt ;
    private PerMessageListFragment userFragment ;
    private MessageDao messageDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        ButterKnife.inject(this);
        messageDao =new MessageDao(MessageActivity.this,this) ;

        platformFragmemt =MessageListFragment.newInstance("0");

        addFragmentList(R.id.container,platformFragmemt);

//        CommonTabEntity tabEntity1=new CommonTabEntity("平台通知");
//        CommonTabEntity tabEntity2=new CommonTabEntity("用户私信");
//
//        ArrayList<CustomTabEntity> tabs =new ArrayList<>() ;
//        tabs.add(tabEntity1);
//        tabs.add(tabEntity2) ;
//        tabLayout.setTabData(tabs);
//        getSupportFragmentManager().beginTransaction().add(R.id.container,platformFragmemt).commit();
//
//
//        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                if(position==0) {
//                    if(platformFragmemt==null){
//                        platformFragmemt =MessageListFragment.newInstance("0");
//                        getSupportFragmentManager().beginTransaction().add(R.id.container,platformFragmemt).commit() ;
//                    }
//                    if(userFragment!=null)
//                      getSupportFragmentManager().beginTransaction().hide(userFragment).commit();
//                      getSupportFragmentManager().beginTransaction().show(platformFragmemt).commit();
//                }else{
//                    if(userFragment==null){
//                        userFragment =new PerMessageListFragment();
//                        getSupportFragmentManager().beginTransaction().add(R.id.container,userFragment).commit();
//                    }
//                    if(platformFragmemt!=null)
//                    getSupportFragmentManager().beginTransaction().hide(platformFragmemt).commit();
//                    getSupportFragmentManager().beginTransaction().show(userFragment).commit();
//                }
//            }
//            @Override
//            public void onTabReselect(int position) {
//            }
//        });
//
//        messageDao.getMsgNum(AppUtil.getUserId(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("通知与消息");
    }

    private class CommonTabEntity implements CustomTabEntity {

        private String title;
        private CommonTabEntity( String title) {
            this.title = title;
        }
        @Override
        public String getTabTitle() {
            return title;
        }
        @Override
        public int getTabSelectedIcon() {
            return R.drawable.icon;
        }
        @Override
        public int getTabUnselectedIcon() {
            return R.drawable.icon;
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
//        if(requestCode== RequestCode.Msg_num){
//            CommonTabEntity tabEntity1=new CommonTabEntity("平台通知("+messageDao.getPlatFormMsgNum()+")");
//            CommonTabEntity tabEntity2=new CommonTabEntity("用户私信("+messageDao.getUserMsgNum()+")");
//            ArrayList<CustomTabEntity> tabs =new ArrayList<>() ;
//            tabs.add(tabEntity1);
//            tabs.add(tabEntity2) ;
//            tabLayout.setTabData(tabs);
//        }
    }
}
