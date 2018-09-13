package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.OfferInfo;
import com.runer.toumai.util.AppUtil;

import java.util.List;

/**
 * Created by szhua on 2017/7/25/025.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * OfferPriceAdapter
 * 出价列表adapter
 */
public class OfferPriceAdapter extends BaseAdapter {

    private List<OfferInfo> datas ;
    public void setDatas(List<OfferInfo> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OfferInfo item =datas.get(position);
        ViewHolder holder =null;
        if(convertView==null){
            convertView =View.inflate(parent.getContext(),R.layout.item_offter_price_layout,null);
            holder =new ViewHolder() ;
            holder.create_time = (TextView) convertView.findViewById(R.id.create_time);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.income = (TextView) convertView.findViewById(R.id.income);
            holder.offer_id = (TextView) convertView.findViewById(R.id.offer_id);
            holder.name = (TextView) convertView.findViewById(R.id.phone);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.offer_id.setText(item.getRound()) ;
        holder.create_time.setText(item.getCreate_time());
        holder.price.setText(item.getTotal_price());
        holder.income.setText(item.getIncome());

        //rechange on 0713!!!
        if("1".equals(item.getAnonymous())){
            holder.name.setText("匿名出价");
            if (item.getUser_id().equals(AppUtil.getUserId(parent.getContext()))){
                holder.name.setText("匿名出价（我）");
            }
        }else{
            holder.name.setText(item.getUser_name()) ;
        }
        return convertView;
    }
    private class ViewHolder{
        private TextView offer_id ;
        private TextView price ;
        private TextView income ;
        private TextView create_time ;
        private TextView name ;
    }
}
