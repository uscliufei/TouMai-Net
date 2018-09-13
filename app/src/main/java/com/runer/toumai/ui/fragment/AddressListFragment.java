package com.runer.toumai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.AddressAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.AddressBean;
import com.runer.toumai.dao.AddressDao;
import com.runer.toumai.ui.activity.AddressEditActivity;
import com.runer.toumai.util.AppUtil;
import java.util.List;
/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AddressListFragment
 *收货地址列表界面
 */
public class AddressListFragment extends BaseLoadMoreFragment<AddressAdapter> {
    private List<AddressBean> data ;
    private AddressDao addressDao ;
    private int currentPos;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View addView =View.inflate(getContext(), R.layout.add_address_header_layout,null) ;
        baseQuickAdapter.addHeaderView(addView);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(AddressEditActivity.class,null);
            }
        });
        baseQuickAdapter.setOndeleteClickListener(new AddressAdapter.OndeleteClickListener() {
            @Override
            public void onItemClick(AddressBean messageBean, int pos) {
                Bundle bundle =new Bundle() ;
                bundle.putSerializable("data",messageBean);
                transUi(AddressEditActivity.class,bundle);
            }
            @Override
            public void onItemDeleteClick(AddressBean messageBean, int pos) {
                currentPos =pos-1;
                addressDao.delAddress(messageBean.getId());
                showProgress(true);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if(addressDao==null){
            addressDao =new AddressDao(getContext(),this) ;
            addressDao.getAddressList(AppUtil.getUserId(getContext()));
        }else{
            addressDao.refresh();
        }
       showProgress(true);

    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            data =addressDao.getDatas();
            baseQuickAdapter.setNewData(data);
            if(data==null||data.isEmpty()){
                baseQuickAdapter.setEmptyView(getEmptyViewFixedHeight("暂无收货地址"));
                baseQuickAdapter.setHeaderAndEmpty(true);
            }
        }else if(requestCode==RequestCode.CODE_2){
           addressDao.refresh();
        }
    }

    @Override
    public AddressAdapter getAdater() {
        return new AddressAdapter(data);
    }
    @Override
    public void loadMore() {
        if(addressDao.hasMore()){
            addressDao.loadMore();
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
        addressDao.refresh();
    }
}
