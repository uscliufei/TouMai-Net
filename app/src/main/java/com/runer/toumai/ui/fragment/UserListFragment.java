package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.runer.net.RequestCode;
import com.runer.toumai.adapter.UserListAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.UserBean;
import com.runer.toumai.dao.UserListDao;
import com.runer.toumai.ui.activity.PersonalMessageList;
import com.runer.toumai.ui.activity.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruier on 2018/7/14.
 */

public class UserListFragment extends BaseLoadMoreFragment<UserListAdapter> {

    private UserListDao userListDao  ;
    private List<UserBean>  datas =new ArrayList<>() ;
    private String username ;
    private String order ;

    public  static  UserListFragment instance(String userName,String order) {
        UserListFragment userListFragment =new UserListFragment() ;
        userListFragment.username = userName ;
        userListFragment.order =order ;
        return  userListFragment ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userListDao =new UserListDao(getContext(),this);
        //设置监听；
        baseQuickAdapter.setUserListClickListener(new UserListAdapter.UserListClickListener() {
            @Override
            public void onGoodsCheck(UserBean userBean) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id",userBean.getId());
                transUi(SearchResultActivity.class, bundle);
                getActivity().finish();

            }
            @Override
            public void onMessageCheck(UserBean userBean) {
              Bundle bundle =new Bundle() ;
              bundle.putString("user_id",userBean.getId());
              transUi(PersonalMessageList.class,bundle);
              getActivity().finish();
            }
        });


        userListDao.getUserList(username,order);
        showProgress(true);
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            datas =userListDao.getDatas();
            baseQuickAdapter.setNewData(datas);
        }
    }


    @Override
    public LinearLayoutManager getLayoutManager(Context context) {
        return new GridLayoutManager(context,2);
    }

    @Override
    public UserListAdapter getAdater() {
        return new UserListAdapter(datas) ;
    }

    @Override
    public void loadMore() {
        if(userListDao.hasMore()){
            userListDao.loadMore();
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
      userListDao.refresh();
    }
}
