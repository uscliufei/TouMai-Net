package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.UserConcernAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.FollowUserBean;
import com.runer.toumai.dao.FollwDao;
import com.runer.toumai.dao.GetFollowUsersDao;
import com.runer.toumai.ui.activity.PersonalMessageList;
import com.runer.toumai.ui.activity.TpyesProListActivity;
import com.runer.toumai.util.AppUtil;

import java.util.List;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * UserConcernFragment
 * 关注用户列表
 */

public class UserConcernFragment extends BaseLoadMoreFragment<UserConcernAdapter> implements UserConcernAdapter.OnItemsClickListener {

    private GetFollowUsersDao getFollowUsersDao ;
    private List<FollowUserBean> datas ;
    private FollwDao follwDao ;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        follwDao =new FollwDao(getContext(),this);
        getFollowUsersDao =new GetFollowUsersDao(getContext(),this) ;
        getFollowUsersDao.getFollowUsers(AppUtil.getUserId(getContext()));
        showProgress(true);

        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        baseQuickAdapter.setOnItemsClickListener(this);
    }
    @Override
    public UserConcernAdapter getAdater() {
        return new UserConcernAdapter(datas) ;
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            datas =getFollowUsersDao.getDatas();
            baseQuickAdapter.setNewData(datas);
            if(AppUtil.isEmpty(datas)){
                baseQuickAdapter.setEmptyView(getEmptyView("您还没有关注的用户"));
            }
        }else if(requestCode==RequestCode.QUIE_CONCERN){
            datas.remove(currentPos);
            baseQuickAdapter.notifyItemRemoved(currentPos);
        }
    }
    private int currentPos;
    @Override
    public void loadMore() {
          baseQuickAdapter.loadMoreEnd();
    }
    @Override
    public void refresh() {
        getFollowUsersDao.refresh();
    }
    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
    }
    //发送私信
    @Override
    public void onMsgToClick(FollowUserBean followUserBean, int pos) {
         Bundle bundle =new Bundle() ;
         bundle.putString("user_id",followUserBean.getId());
         transUi(PersonalMessageList.class,bundle);
    }
    //删除操作
    @Override
    public void onDelClick(FollowUserBean followUserBean, int pos) {
        follwDao.delFollowUser(AppUtil.getUserId(getContext()),followUserBean.getId());
        showProgress(true);
    }
    //查看商品
    @Override
    public void onGoodsClick(FollowUserBean followUserBean, int pos) {
        currentPos = pos;
        Intent intent =new Intent(getContext(), TpyesProListActivity.class);
        intent.putExtra("type", "2");
        intent.putExtra("user", followUserBean.getUser_name());
        intent.putExtra("uid", followUserBean.getId());
        startActivity(intent);
    }
}
