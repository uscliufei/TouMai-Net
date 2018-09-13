package com.runer.toumai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.HomeListAdapter;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.GetGoodParam;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.dao.GoodsListDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.ui.activity.LoginActivity;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.ui.activity.SellGoodsActivity;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.LoamoreView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.iwgang.countdownview.CountdownView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by szhua on 2017/7/14/014.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * HomeListFragment
 * 首页列表界面
 */
public class HomeListFragment extends BaseLoadMoreFragment<HomeListAdapter> {

    private GoodsListDao goodsListDao;
    private String lable = "";
    private String uid = "";
    private List<GoodListBean> datas=new ArrayList<>();
    public static HomeListFragment getInstance(String labe, String uid) {
        HomeListFragment homeListFragment = new HomeListFragment();
        homeListFragment.lable = labe;
        homeListFragment.uid = uid;
        return homeListFragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseQuickAdapter.setCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if (goodsListDao != null) {
                    goodsListDao.refresh();
                }
            }
        });
        swipeRefresh.setEnabled(false);
        goodsListDao = new GoodsListDao(getContext(), this);
        GetGoodParam getGoodParam = new GetGoodParam();
        getGoodParam.setLabel(lable);
        getGoodParam.setUser(uid);
        //明价
        getGoodParam.setSell_state2("2");
//     //暗价
//    getGoodParam.setSell_state1("1");
        //降价
        getGoodParam.setFall_state("1");
        //是否心跳时间
        getGoodParam.setHeart_time("1");
        //两者不为空的时候==============(标签或者tag进来的时候)
        if (!TextUtils.isEmpty(lable) || !TextUtils.isEmpty(uid)) {
            //明价
            getGoodParam.setSell_state2("");
            //暗价
            getGoodParam.setSell_state1("");
            //降价
            getGoodParam.setFall_state("");
            //是否心跳时间
            getGoodParam.setHeart_time("");
        }
        goodsListDao.getGoodsList(getGoodParam);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", baseQuickAdapter.getItem(position).getId());
                transUi(ProInfoActivity.class, bundle);
            }
        });
    }
    @Override
    public HomeListAdapter getAdater() {
        return new HomeListAdapter(null);
    }
    @Override
    public void loadMore() {
        if (goodsListDao.hasMore()) {
            goodsListDao.loadMore();
        } else {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    baseQuickAdapter.loadMoreEnd();
                }
            }, 1000);
        }
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.LOADMORE) {
            showProgress(false);
            datas = goodsListDao.getDatas();
            baseQuickAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                baseQuickAdapter.setEmptyView(getEmptyViewFixedHeight("该条件下无此商品，请重新设置筛选条件"));
            }
        }
    }
    @Override
    public void refresh() {
        if (goodsListDao != null) {
            goodsListDao.refresh();
        }
    }
    public void getNewdata() {
        if (goodsListDao != null) {
            datas.clear();
            baseQuickAdapter.notifyDataSetChanged();
            goodsListDao.refresh();
            showProgress(true);
        }
    }
    public void setParam(GetGoodParam getGoodParam) {
        if (goodsListDao.getDatas() != null)
            goodsListDao.getDatas().clear();
        goodsListDao.getGoodsList(getGoodParam);
        showProgress(true);
    }
    @Override
    public void onCompeleteRefresh() {
        super.onCompeleteRefresh();
        ((BaseFragment) getParentFragment()).onCompeleteRefresh();
    }
}
