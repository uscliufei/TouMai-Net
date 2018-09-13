package com.runer.toumai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.UiUtil;
import com.runer.liabary.widget.CenterRadioButton;
import com.runer.liabary.widget.CustomPopWindow;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.HomeCheckGoodsByStateAdapter;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.bean.BaseStateBean;
import com.runer.toumai.bean.GetGoodParam;
import com.runer.toumai.bean.HomeListRefreshEventBean;
import com.runer.toumai.bean.UserInfo;
import com.runer.toumai.dao.BannerDao;
import com.runer.toumai.dao.UserInfoDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.ui.activity.HomeActivity;
import com.runer.toumai.ui.activity.LoginActivity;
import com.runer.toumai.ui.activity.SearchActivity;
import com.runer.toumai.ui.activity.UserInfoActivity;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.AnjiaRulesDialog;
import com.runer.toumai.widget.DarkPriceDiolog;
import com.runer.toumai.widget.OrderView;
import com.runer.toumai.widget.OrderViewState;
import com.runer.toumai.widget.StickyNavLayout;
import com.runer.toumai.widget.adviewpager.AdViewPager;
import com.runer.toumai.widget.adviewpager.BannerBean;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by szhua on 2017/7/14/014.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * HomeFragment
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.banner)
    AdViewPager banner;
    @InjectView(R.id.logo)
    ImageView logo;
    @InjectView(R.id.reg_log_bt)
    TextView regLogBt;
    @InjectView(R.id.user_liner)
    RelativeLayout userLiner;
    @InjectView(R.id.id_stickynavlayout_viewpager)
    ViewPager viewPager;
    @InjectView(R.id.id_stickynavlayout_topview)
    LinearLayout idStickynavlayoutTopview;
    @InjectView(R.id.nav_layout)
    StickyNavLayout navLayout;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @InjectView(R.id.good_state)
    CenterRadioButton goodState;
    @InjectView(R.id.order_orders)
    CenterRadioButton orderOrders;
    @InjectView(R.id.radios)
    RadioGroup radios;
    @InjectView(R.id.header_icon)
    CircleImageView headerIcon;
    @InjectView(R.id.id_stickynavlayout_indicator)
    LinearLayout idStickynavlayoutIndicator;
    @InjectView(R.id.lable)
    TextView lableText;
    @InjectView(R.id.lable_linear)
    LinearLayout lableLinear;
    @InjectView(R.id.user_name)
    TextView userName;
    @InjectView(R.id.user_linear)
    LinearLayout userLinear;
    @InjectView(R.id.search_tv)
    TextView searchTv;

    private List<BaseStateBean> stateBeans = new ArrayList<>();
    private UserInfoDao userInfoDao;
    private BannerDao bannerDao;
    private HomeListFragment homeListFragment;

    private static GetGoodParam getGoodParam;
    private View sureFootView;
    private String type = "";
    private String lable = "";
    private String uid = "";
    private String user = "";
    private static HomeFragment homeFragment;
    private View  containerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
       // AppUtil.setUserId(getContext(),"946");
        ButterKnife.inject(this, view);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return view;
    }
    public static HomeFragment getInstance(String type, String lable, String user, String uid) {
        homeFragment = new HomeFragment();
        homeFragment.type = type;
        homeFragment.lable = lable;
        homeFragment.user = user;
        homeFragment.uid = uid;
        return homeFragment;
    }
    //检测是否有登录
    @Override
    public void onResume() {
        super.onResume();
        if (type.equals("1")) {
            lableLinear.setVisibility(View.VISIBLE);
            lableText.setText(lable);
            banner.setVisibility(View.GONE);
        } else if (type.equals("2")) {
            userLinear.setVisibility(View.VISIBLE);
            banner.setVisibility(View.GONE);
            userName.setText(user);
        }
        userInfoDao = new UserInfoDao(getContext(), this);
        if (AppUtil.chckeLogin(getContext(), false)) {
            regLogBt.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            //设置用户信息
            Picasso.with(getContext()).load(NetConfig.HEAD_PATH + AppUtil.getUserInfo(getContext()).getHead()).resize(100, 100).placeholder(R.drawable.logo).into(headerIcon);
            userInfoDao.getUserInfo(AppUtil.getUserId(getContext()));
        } else {
            regLogBt.setVisibility(View.VISIBLE);
            headerIcon.setVisibility(View.GONE);
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getGoodParam = new GetGoodParam();
        getGoodParam.setLabel(lable);
        getGoodParam.setUser(uid);

        if (TextUtils.isEmpty(lable) || TextUtils.isEmpty(uid)) {
            //明价
            getGoodParam.setSell_state2("2");
            //暗价
            getGoodParam.setSell_state1("1");
            //降价
            getGoodParam.setFall_state("1");
            //是否心跳时间
            getGoodParam.setHeart_time("1");
        }
        userInfoDao = new UserInfoDao(getContext(), this);
        BaseStateBean stateBean2 = new BaseStateBean();
        stateBean2.setTag("暗价");
        //查看暗价规则
         stateBean2.setSelected(false);
        stateBeans.add(stateBean2);
        BaseStateBean stateBean1 = new BaseStateBean();
        stateBean1.setTag("明价");
        stateBean1.setSelected(true);
        stateBeans.add(stateBean1);
        BaseStateBean stateBean3 = new BaseStateBean();
        stateBean3.setTag("降价");
        stateBean3.setSelected(true);
        stateBeans.add(stateBean3);
        BaseStateBean stateBean4 = new BaseStateBean();
        stateBean4.setTag("心跳时间");
        stateBean4.setSelected(true);
        stateBeans.add(stateBean4);
        BaseStateBean stateBean5 = new BaseStateBean();
        stateBean5.setTag("已结束-成交");
        stateBeans.add(stateBean5);
        BaseStateBean stateBean6 = new BaseStateBean();
        stateBean6.setTag("已结束-未成交");
        stateBeans.add(stateBean6);


        searchTv.setOnClickListener(this);
        homeListFragment = HomeListFragment.getInstance(lable, uid);
        viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), homeListFragment));
        swipeRefresh.setEnabled(true);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(getRefreshColor(getContext()));
        navLayout.setOnStickStateChangeListener(onStickStateChangeListener);
        regLogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(LoginActivity.class, null);
            }
        });
        headerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).setCurrentTab(2);
            }
        });
        goodState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoodsByState();
            }
        });
        orderOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrdersSelect();
            }
        });
        bannerDao = new BannerDao(getContext(), this);
        bannerDao.getBanners("1");
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HomeListRefreshEventBean event) {
        homeListFragment.getNewdata();
    };
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onClick(View v) {
      if (v==searchTv) {
            transUi(SearchActivity.class);
        }else if(v==containerList){
            if(mListPopWindow!=null){
                mListPopWindow.dissmiss();
            }
        }
    }

    class HomePagerAdapter extends FragmentPagerAdapter {
        HomeListFragment homeListFragment;
        public HomePagerAdapter(FragmentManager fm, HomeListFragment homeListFragment) {
            super(fm);
            this.homeListFragment = homeListFragment;
        }
        @Override
        public Fragment getItem(int position) {
            return homeListFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            return super.instantiateItem(container, position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private CustomPopWindow sortPopWindow;

    private void showOrdersSelect() {
        if (sortPopWindow == null) {
            OrderView orderView = new OrderView(getContext());
            sortPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                    .setView(orderView)
                    .setOutsideTouchable(true)
                    .setFocusable(true)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                    .create();
                    orderView.setOnClosePopWindowListener(new OrderView.OnClosePopWindowListener() {
                @Override
                public void oinCloseListener(GetGoodParam getGoodParam) {
                    if (sortPopWindow != null) {
                        if(getGoodParam==null){
                            sortPopWindow.dissmiss();
                        }else{
                            sortPopWindow.dissmiss();
                            if (homeFragment != null) {
                                getGoodParam.setSell_state2(homeFragment.getGoodParam.getSell_state2());
                                getGoodParam.setSell_state1(homeFragment.getGoodParam.getSell_state1());
                                getGoodParam.setFall_state(homeFragment.getGoodParam.getFall_state());
                                getGoodParam.setHeart_time(homeFragment.getGoodParam.getHeart_time());
                                getGoodParam.setIs_order1(homeFragment.getGoodParam.getIs_order1());
                                getGoodParam.setIs_order0(homeFragment.getGoodParam.getIs_order0());
                            }
                            getGoodParam.setLabel(lable);
                            getGoodParam.setUser(uid);
                            homeListFragment.setParam(getGoodParam);
                        }
                    }
                }
            });
        }
         //   sortPopWindow.showAsDropDown(orderOrders);
            if (android.os.Build.VERSION.SDK_INT >=24)
            { int[] a = new int[2]; orderOrders.getLocationInWindow(a);
                sortPopWindow.showAtLocation((getActivity()).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+orderOrders.getHeight());
            } else{  sortPopWindow.showAsDropDown(orderOrders); }

    }

    //商品状态的筛选
    private CustomPopWindow mListPopWindow;
    private void showGoodsByState() {
        if (mListPopWindow == null) {
            OrderViewState orderViewState =new OrderViewState(getContext()) ;
            orderViewState.setStates(stateBeans);
            orderViewState.setOnstateViewClickListener(new OrderViewState.OnstateViewClickListener() {
                @Override
                public void onOutClick() {
                    if(mListPopWindow!=null){
                        mListPopWindow.dissmiss();
                    }
                }
                @Override
                public void onCommitClick(List<BaseStateBean> states) {
                    handleStatesChanged();
                }
            });
            mListPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                    .setView(orderViewState)
                    .setFocusable(true)
                    .setOutsideTouchable(true)
                    .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                    .create();
        }
            if (android.os.Build.VERSION.SDK_INT >=24)
            { int[] a = new int[2]; goodState.getLocationInWindow(a);
                mListPopWindow.showAtLocation((getActivity()).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+goodState.getHeight());
            } else{  mListPopWindow.showAsDropDown(goodState); }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.GET_USER_INFO) {
            UserInfo userInfo =userInfoDao.getUserInfo();
            Picasso.with(getContext()).load(NetConfig.HEAD_PATH + userInfo.getHead()).placeholder(R.drawable.logo).into(headerIcon);
            AppUtil.setUserInfo(getContext(),userInfo);
        } else if (requestCode == RequestCode.GET_BANNER) {
            banner.setBannerEntities(bannerDao.getBanners());
        }
    }
    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        if (swipeRefresh != null) {
            swipeRefresh.setRefreshing(false);
        }
    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        if (homeListFragment != null) {
            homeListFragment.refresh();
        }
    }
    //控制swipeRefresh与navLayout的冲突；
    private StickyNavLayout.onStickStateChangeListener onStickStateChangeListener = new StickyNavLayout.onStickStateChangeListener() {
        @Override
        public void isStick(boolean isStick) {

        }
        @Override
        public void scrollPercent(float percent) {
            if (percent == 0) {
                swipeRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        swipeRefresh.setEnabled(true);
                        swipeRefresh.setOnRefreshListener(HomeFragment.this);
                    }
                });

            } else {
                swipeRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        swipeRefresh.setEnabled(false);
                        swipeRefresh.setOnRefreshListener(null);
                    }
                });
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void handleStatesChanged(){
        for (int i = 0; i < stateBeans.size(); i++) {
            if (stateBeans.get(i).isSelected()) {
                switch (i) {
                    case 0:
                        //暗价
                        getGoodParam.setSell_state1("1");
                        break;
                    case 1:
                        //明价
                        getGoodParam.setSell_state2("2");
                        break;
                    case 2:
                        //降价
                        getGoodParam.setFall_state("1");
                        break;
                    case 3:
                        //是否心跳时间
                        getGoodParam.setHeart_time("1");
                        break;
                    case 4:
                        //已成交
                        getGoodParam.setIs_order1("1");
                        break;
                    case 5:
                        //未成交
                        getGoodParam.setIs_order0("0");
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        //暗价
                        getGoodParam.setSell_state1("");
                        break;
                    case 1:
                        //明价
                        getGoodParam.setSell_state2("");
                        break;
                    case 2:
                        //降价
                        getGoodParam.setFall_state("");
                        break;
                    case 3:
                        //是否心跳时间
                        getGoodParam.setHeart_time("");
                        break;
                    case 4:
                        //已成交
                        getGoodParam.setIs_order1("");
                        break;
                    case 5:
                        //未成交
                        getGoodParam.setIs_order0("");
                        break;

                }
            }
        }
        if (mListPopWindow != null) {
            mListPopWindow.dissmiss();
            homeListFragment.setParam(getGoodParam);
        }
    }
}
