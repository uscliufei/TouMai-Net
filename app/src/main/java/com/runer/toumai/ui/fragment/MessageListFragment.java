package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.MessageListAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.MessageBean;
import com.runer.toumai.dao.MessageDao;
import com.runer.toumai.dao.MessageListDao;
import com.runer.toumai.ui.activity.AddressActivity;
import com.runer.toumai.ui.activity.AddressEditActivity;
import com.runer.toumai.ui.activity.HomeActivity;
import com.runer.toumai.ui.activity.MessageDetailActivity;
import com.runer.toumai.ui.activity.MyOrderActivity;
import com.runer.toumai.ui.activity.PersonalMessageList;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.ui.activity.WalletActivity;
import com.runer.toumai.util.AppUtil;

import java.util.List;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MessageListFragment
 * 消息列表
 */

public class MessageListFragment extends BaseLoadMoreFragment<MessageListAdapter> {

    private MessageListDao messageListDao ;
    private List<MessageBean> messageBeanList;
    private MessageDao messageDao ;
    String type ;
    public static MessageListFragment newInstance(String type){
        MessageListFragment messageListFragment =new MessageListFragment() ;
        messageListFragment.type =type ;
        return  messageListFragment ;
    }
    private int currentPos ;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageDao =new MessageDao(getContext(),this);
        //进行相应的跳转；
        baseQuickAdapter.setOndeleteClickListener(new MessageListAdapter.OndeleteClickListener() {
            @Override
            public void onItemClick(MessageBean messageBean, int pos) {


                         //跳转消息详情；
                         Bundle bundle=new Bundle() ;
                         bundle.putSerializable("messageBean",messageBean);
                         transUi(MessageDetailActivity.class,bundle);






            }
            @Override
            public void onItemDeleteClick(MessageBean messageBean, int pos) {
                currentPos =pos ;
                messageDao.delMessage(messageBean.getId(),AppUtil.getUserId(getContext()));
                showProgress(true);
            }
        });
        messageDao.sysMsgRead(AppUtil.getUserId(getContext()),"0");
//        messageDao =new MessageDao(getContext(),this);
//        messageDao.sysMsgRead(AppUtil.getUserId(getContext()));
    }
    @Override
    public void onResume() {
        super.onResume();
        if(messageListDao==null){
            messageListDao =new MessageListDao(getContext(),this) ;
            messageListDao.getMessages(AppUtil.getUserId(getContext()),type);
        }else{
            messageListDao.refresh();
        }
    }
    @Override
    public MessageListAdapter getAdater() {
        return new MessageListAdapter(messageBeanList);
    }
    @Override
    public void loadMore() {
        if(messageListDao.hasMore()){
            messageListDao.loadMore();
        }else{
            if(baseQuickAdapter!=null){
             baseQuickAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
    }

    @Override
    public void refresh() {
        messageListDao.refresh();
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            messageBeanList =messageListDao.getDatas() ;
            baseQuickAdapter.setNewData(messageBeanList);
            if(baseQuickAdapter.getData()==null||baseQuickAdapter.getData().isEmpty()){
                baseQuickAdapter.setEmptyView(getEmptyView("暂无消息"));
            }
            //删除成功后操作；
        }else if(requestCode==RequestCode.DEL_MSG){
            messageBeanList.remove(currentPos) ;
            baseQuickAdapter.notifyItemRemoved(currentPos);
        }
    }
}
