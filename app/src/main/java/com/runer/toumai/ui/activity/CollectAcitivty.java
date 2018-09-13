package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.runer.liabary.tab.CommonTabLayout;
import com.runer.liabary.tab.listener.CustomTabEntity;
import com.runer.liabary.tab.listener.OnTabSelectListener;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.ui.fragment.CollectGoodsFragment;
import com.runer.toumai.ui.fragment.UserConcernFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

//todo init fragment!!!!
public class CollectAcitivty extends BaseActivity {

    @InjectView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @InjectView(R.id.container)
    LinearLayout container;

    private UserConcernFragment userConcernFragment  ;
    private CollectGoodsFragment collectGoodsFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_acitivty);
        ButterKnife.inject(this);

        userConcernFragment =new UserConcernFragment();

        CommonTabEntity tabEntity1 = new CommonTabEntity("关注");
        CommonTabEntity tabEntity2 = new CommonTabEntity("收藏");

        ArrayList<CustomTabEntity> tabs = new ArrayList<>();
        tabs.add(tabEntity1);
        tabs.add(tabEntity2);
        tabLayout.setTabData(tabs);
        addFragmentList(R.id.container,userConcernFragment);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    if (userConcernFragment == null) {
                        userConcernFragment = new UserConcernFragment() ;
                        getSupportFragmentManager().beginTransaction().add(R.id.container, userConcernFragment).commit();
                    }
                    if (collectGoodsFragment != null) {
                        getSupportFragmentManager().beginTransaction().hide(collectGoodsFragment).commit();
                    }
                    getSupportFragmentManager().beginTransaction().show(userConcernFragment).commit();
                } else {
                    if (collectGoodsFragment == null) {
                        collectGoodsFragment = new CollectGoodsFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.container, collectGoodsFragment).commit();
                    }
                    if (userConcernFragment != null) {
                        getSupportFragmentManager().beginTransaction().hide(userConcernFragment).commit();
                    }
                    getSupportFragmentManager().beginTransaction().show(collectGoodsFragment).commit();
                }
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }











    private class CommonTabEntity implements CustomTabEntity {

        private String title;

        private CommonTabEntity(String title) {
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
    protected void onStart() {
        super.onStart();
        setTitle("关注与收藏");
    }
}
