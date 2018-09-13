package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.PerMessageListAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.MessageBean;
import com.runer.toumai.dao.MessageDao;
import com.runer.toumai.dao.MessageListDao;
import com.runer.toumai.ui.activity.AddressActivity;
import com.runer.toumai.ui.activity.HomeActivity;
import com.runer.toumai.ui.activity.MyOrderActivity;
import com.runer.toumai.ui.activity.PersonalMessageList;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.ui.activity.WalletActivity;
import com.runer.toumai.util.AppUtil;
import java.util.List;
/**
 * Created by szhua on 2017/9/9/009.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * PerMessageListFragment
 */

public class PerMessageListFragment extends BaseLoadMoreFragment<PerMessageListAdapter> {

    private MessageListDao messageListDao;
    private List<MessageBean> messageBeanList;
    private MessageDao messageDao;
    private int currentPos;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //进行相应的跳转；
        baseQuickAdapter.setOndeleteClickListener(new PerMessageListAdapter.OndeleteClickListener() {
            @Override
            public void onItemClick(MessageBean messageBean, int pos) {
                Bundle bundle;
                switch (messageBean.getLink_type()) {
                    case "0":
                        bundle =new Bundle() ;
                        bundle.putString("id",messageBean.getId());
                        transUi(PersonalMessageList.class,bundle);
                        break;
                    case "1":
                        bundle = new Bundle();
                        bundle.putString("id", messageBean.getLink_id());
                        transUi(ProInfoActivity.class, bundle);
                        //     getActivity().finish();
                        break;
                    case "2":
                        transUi(MyOrderActivity.class, null);
                        //      getActivity().finish();
                        break;
                    case "3":
                        bundle = new Bundle();
                        bundle.putInt("index", 1);
                        transUi(HomeActivity.class, bundle);
                        //    getActivity().finish();
                        break;
                    case "4":
                        transUi(WalletActivity.class, null);
                        //  getActivity().finish();
                        break;
                    case "5":
                        transUi(AddressActivity.class, null);
                        // getActivity().finish();
                        break;
                }

            }

            @Override
            public void onItemDeleteClick(MessageBean messageBean, int pos) {
                currentPos = pos;
                messageDao.delMessage(messageBean.getId(), AppUtil.getUserId(getContext()));
                showProgress(true);
            }
        });
        messageDao = new MessageDao(getContext(), this);
        messageDao.sysMsgRead(AppUtil.getUserId(getContext()),"1");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(messageListDao==null){
            messageListDao = new MessageListDao(getContext(), this);
            messageListDao.getMessages(AppUtil.getUserId(getContext()), "1");
        }else{
            messageListDao.refresh();
        }
    }
    @Override
    public PerMessageListAdapter getAdater() {
        return new PerMessageListAdapter(messageBeanList);
    }

    @Override
    public void loadMore() {
        if (messageListDao.hasMore()) {
            messageListDao.loadMore();
        } else {
            if (baseQuickAdapter != null) {
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
        if (requestCode == RequestCode.LOADMORE) {
            messageBeanList = messageListDao.getDatas();
            baseQuickAdapter.setNewData(messageBeanList);
            if(baseQuickAdapter.getData()==null||baseQuickAdapter.getData().isEmpty()){
                baseQuickAdapter.setEmptyView(getEmptyView("暂无消息"));
            }
            //删除成功后操作；
        } else if (requestCode == RequestCode.DEL_MSG) {
            messageBeanList.remove(currentPos);
            baseQuickAdapter.notifyItemRemoved(currentPos);
        }
    }
}
