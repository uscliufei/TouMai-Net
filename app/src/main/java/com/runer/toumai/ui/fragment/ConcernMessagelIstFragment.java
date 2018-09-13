package com.runer.toumai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.ConcernMessageAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.ConcernMsgBean;
import com.runer.toumai.bean.MsgInfoBean;
import com.runer.toumai.dao.MessageDao;
import com.runer.toumai.dao.MessageInfoDao;
import com.runer.toumai.util.AppUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ConcernMessagelIstFragment
 * 关注用户的私信列表
 */

public class ConcernMessagelIstFragment extends BaseLoadMoreFragment<ConcernMessageAdapter> {

    private String msg_id ;
    //信息id
    private String user_id ;
    private List<ConcernMsgBean> datas  =new ArrayList<>();
    private MessageDao messageDao;
    private MessageInfoDao messageInfoDao;

    public static ConcernMessagelIstFragment getInstance(String msg_id ,String user_id){
        ConcernMessagelIstFragment concernMessagelIstFragment =new ConcernMessagelIstFragment() ;
        concernMessagelIstFragment.msg_id =msg_id;
        concernMessagelIstFragment.user_id =user_id;
        return  concernMessagelIstFragment ;
    }

    private View headerView ;
    private EditText contentEt ;
    private TextView sendBt ;
    private TextView userName ;
    private TextView rebackBt ;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        headerView =View.inflate(getContext(), R.layout.send_message_header_layout,null);
        contentEt = (EditText) headerView.findViewById(R.id.content);
        sendBt = (TextView) headerView.findViewById(R.id.send);
        userName = (TextView) headerView.findViewById(R.id.user_name);
        rebackBt = (TextView) headerView.findViewById(R.id.reback_bt);
        baseQuickAdapter.addHeaderView(headerView);

        messageDao =new MessageDao(getContext(),this);
        messageInfoDao =new MessageInfoDao(getContext(),this);
        messageInfoDao.getMessageInfo(msg_id,user_id,AppUtil.getUserId(getContext()));
        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(contentEt.getText().toString())){
                    UiUtil.showLongToast(getContext(),"请输入要发送的内容");
                }else{
                      MsgInfoBean msgInfoBean =messageInfoDao.getMsgInfoBean() ;
                  if(msgInfoBean!=null){
                      if(AppUtil.getUserId(getContext()).equals(msgInfoBean.getUser_id())){
                          messageDao.sendMessage(msgInfoBean.getFrom_user(),AppUtil.getUserId(getContext()),contentEt.getText().toString());
                      }else{
                          messageDao.sendMessage(msgInfoBean.getUser_id(),AppUtil.getUserId(getContext()),contentEt.getText().toString());
                      }
                      showProgress(true);
                  }
                }
            }
        });
        rebackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getActivity().finish();
            }
        });
    }
    @Override
    public ConcernMessageAdapter getAdater() {
        return new ConcernMessageAdapter(datas);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_6){
            datas = messageInfoDao.getDatas();
            baseQuickAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                baseQuickAdapter.setHeaderAndEmpty(true);
                baseQuickAdapter.setEmptyView(getEmptyViewFixedHeight("暂无沟通记录"));
            }
            MsgInfoBean msgInfoBean =messageInfoDao.getMsgInfoBean() ;
            Logger.d(msgInfoBean);
            if(msgInfoBean!=null){
                if(AppUtil.getUserId(getContext()).equals(msgInfoBean.getUser_id())){
                    userName.setText(msgInfoBean.getFrom_name());
                }else{
                    userName.setText(msgInfoBean.getUser_name());
                }
            }
        }else if(requestCode==RequestCode.CODE_1){
            UiUtil.showLongToast(getContext(),"发送私信成功");
            contentEt.setText("");
            messageInfoDao.refresh(msg_id,user_id,AppUtil.getUserId(getContext()));
        }
    }
    @Override
    public void loadMore() {
        if(messageInfoDao.hasMore()){
            messageInfoDao.loadMore(msg_id,user_id,AppUtil.getUserId(getContext()));
        }else{
            baseQuickAdapter.loadMoreEnd();
        }
    }
    @Override
    public void refresh() {
        messageInfoDao.refresh(msg_id,user_id,AppUtil.getUserId(getContext()));
    }
}
