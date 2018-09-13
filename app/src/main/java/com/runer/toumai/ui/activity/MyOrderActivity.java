package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.runer.liabary.tab.SlidingTabLayout;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.BaseFragmentPagerAdapter;
import com.runer.toumai.ui.fragment.MyEarnedFragment;
import com.runer.toumai.ui.fragment.MyOderFragment;
import com.runer.toumai.ui.fragment.MySoldFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyOrderActivity extends BaseActivity {

    @InjectView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    private String [] titles =new String[]{"我买的","我卖的","我赚的"};

    private List<BaseFragment> fragments ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.inject(this);

        int index =getIntExtras("index",0) ;

        fragments =new ArrayList<>() ;
        fragments.add(MyOderFragment.getInstance());
        fragments.add(new MySoldFragment());
        fragments.add(MyEarnedFragment.getInstance());
        viewPager.setAdapter(new OrderPagerAdapter(getSupportFragmentManager(),fragments));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setCurrentItem(index);


    }

    private class OrderPagerAdapter extends BaseFragmentPagerAdapter{
        private List<BaseFragment> fragments ;
        public OrderPagerAdapter(FragmentManager fm ,List<BaseFragment> fragments) {
            super(fm);
            this.fragments =fragments ;
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("我的行为");
    }
}
