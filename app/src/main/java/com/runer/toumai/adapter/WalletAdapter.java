package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.AccountFlowBean;
import com.runer.toumai.net.NetConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * WalletAdapter
 * 我的钱包列表
 *
 */
public class WalletAdapter extends BaseQuickAdapter<AccountFlowBean,BaseViewHolder> {
    /*http://localhost/toumai/upload/goods/large/15030285964775.jpg*/
    private String type="";
    public void setType(String type) {
        this.type = type;
    }
    public WalletAdapter(@Nullable List<AccountFlowBean> data) {
        super(R.layout.item_wallet_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, AccountFlowBean item) {
        helper.setVisible(R.id.typ3,false);
        //不是商品的时候
        if("0".equals(item.getGoods_id())){
            helper.setText(R.id.title,item.getTitle());
            helper.setText(R.id.end_time,item.getCreate_time());
            helper.setVisible(R.id.img,false).setVisible(R.id.type1,false).setVisible(R.id.type2,false).setVisible(R.id.typ3,false) ;
            //收益的时候
            float amout = Float.parseFloat(item.getAmount());
            if(amout>0){
                helper.setText(R.id.shouyi,"+￥"+Math.abs(amout));
                ((TextView)helper.getView(R.id.shouyi)).setTextColor(ContextCompat.getColor(mContext,R.color.text_color_green));
                //亏损的时候
            }else{
                helper.setText(R.id.shouyi,"-￥"+Math.abs(amout));
                ((TextView)helper.getView(R.id.shouyi)).setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
            }
            //是商品的时候
        }else{
            Picasso.with(mContext).load(NetConfig.WALLET_IMG+item.getImg()).placeholder(R.drawable.empty_img).into((ImageView) helper.getView(R.id.img));
            //设置商品标题
            helper.setText(R.id.title,item.getGoods_title()) ;
            helper.setText(R.id.type1,"底价￥"+item.getPrice());
            //获得出局补偿；
           if(Float.parseFloat(item.getAmount())>0&&"1".equals(item.getIs_makeup())){
               helper.setText(R.id.type2,"出局收益￥"+item.getAmount());
               helper.setText(R.id.end_time,item.getCreate_time());
               helper.setVisible(R.id.img,true).setVisible(R.id.type1,true).setVisible(R.id.type2,true) ;
           }else if(Float.parseFloat(item.getAmount())<=0&&!"1".equals(item.getIs_makeup())){
               helper.setText(R.id.type2,item.getTitle());
               helper.setText(R.id.end_time,item.getCreate_time());
               helper.setVisible(R.id.img,true).setVisible(R.id.type1,true).setVisible(R.id.type2,true);
               //不是出局收益下情况
           }else{
               helper.setText(R.id.type2,item.getTitle());
               helper.setText(R.id.end_time,item.getCreate_time());
               helper.setVisible(R.id.img,true).setVisible(R.id.type1,true).setVisible(R.id.type2,true) ;
           }
           helper.setVisible(R.id.typ3,false);
           //设置成交时间
           if(Float.parseFloat(item.getAmount())!=0){
               if (type.equals("earned")) {
                   helper.setVisible(R.id.typ3,false);
               }else if (type.equals("wallet")) {
                   helper.setVisible(R.id.typ3,false);
               }else{
                   helper.setVisible(R.id.typ3,false);
               }
               helper.setText(R.id.typ3,"成交时间"+item.getCreate_time());
           }else{
               helper.setVisible(R.id.typ3,false);
           }
            if(Float.parseFloat(item.getAmount())>0){
                 helper.setText(R.id.shouyi,"+￥"+item.getAmount());
                 ((TextView)helper.getView(R.id.shouyi)).setTextColor(ContextCompat.getColor(mContext,R.color.text_color_green));
                //亏损的时候
            }else{
                helper.setText(R.id.shouyi,"-￥"+item.getAmount().substring(1,item.getAmount().length()));
                ((TextView)helper.getView(R.id.shouyi)).setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
            }
        }
    }
}
