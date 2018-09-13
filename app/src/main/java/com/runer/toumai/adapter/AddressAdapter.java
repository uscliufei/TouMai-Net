package com.runer.toumai.adapter;


import android.support.annotation.Nullable;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.AddressBean;
import com.runer.toumai.bean.MessageBean;

import java.util.List;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AddressAdapter
 */
public class AddressAdapter extends BaseQuickAdapter<AddressBean,BaseViewHolder> {
    public AddressAdapter(@Nullable List<AddressBean> data) {
        super(R.layout.item_address_layout,data);
    }
    @Override
    protected void convert(final  BaseViewHolder helper, final AddressBean item) {
        helper.setText(R.id.name,item.getUser_name())
                .setText(R.id.tel,item.getMobile())
                .setText(R.id.address_detail,item.getProvince()+item.getCity()+item.getArea()+item.getAddress());
        if("0".equals(item.getIs_default())){
            helper.setVisible(R.id.moren_tag,false);
        }else{
            helper.setVisible(R.id.moren_tag,true);
        }
        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ondeleteClickListener!=null){
                    ondeleteClickListener.onItemClick(item,helper.getPosition());
                }
            }
        });

        helper.getView(R.id.delete_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ondeleteClickListener!=null){
                    ondeleteClickListener.onItemDeleteClick(item,helper.getPosition());
                }
            }
        });
    }


    OndeleteClickListener ondeleteClickListener ;
    public void setOndeleteClickListener(OndeleteClickListener ondeleteClickListener) {
        this.ondeleteClickListener = ondeleteClickListener;
    }
    public interface  OndeleteClickListener{
        void onItemClick(AddressBean messageBean , int pos);
        void  onItemDeleteClick(AddressBean messageBean ,int pos);
    }
}
