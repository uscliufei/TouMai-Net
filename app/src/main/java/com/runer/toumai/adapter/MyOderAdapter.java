package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.inter.OrderStateClickListener;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.util.FaqUtil;
import com.runer.toumai.widget.FAQListTipsDialog;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.iwgang.countdownview.CountdownView;
/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MyOderAdapter
 * 订单列表adapter;
 */
public class MyOderAdapter extends BaseQuickAdapter<GoodListBean,BaseViewHolder> {
    public MyOderAdapter(@Nullable List<GoodListBean> data) {
        super(R.layout.item_my_order,data);
    }
    @Override
    protected void convert(final  BaseViewHolder helper, final GoodListBean item) {
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.low_price,"¥"+item.getPrice())
                .setText(R.id.current_price,"¥"+item.getNow_price())
                .setText(R.id.price_num,item.getOffer_num())
                .setText(R.id.publish_time,"发布时间"+item.getCreate_time());
        TextView state1 =helper.getView(R.id.state_bt);
        TextView state2 =helper.getView(R.id.state_bt2);
        TextView state3 =helper.getView(R.id.state_bt3);

        if(ToumaiApplication.isFaq){
            helper.setVisible(R.id.faq_bt,true);
            helper.getView(R.id.faq_bt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQListTipsDialog.show(mContext, FaqUtil.getOrderListFaq(mContext));
                }
            });
        }else{
            helper.setVisible(R.id.faq_bt,false);
        }
        //已结束的情况下
        if("1".equals(item.getIs_end())){
         helper.setText(R.id.left_time,"已结束");
            //隐藏倒计时标签
            helper.setVisible(R.id.left_time,true);
            helper.setVisible(R.id.count_container,false);
            //已结束但是没有order_id的情况下，出价失败
                 if(TextUtils.isEmpty(item.getOrder_id())){
                             state2.setText("已出局");
                             state2.setBackgroundResource(R.drawable.bg_1px_gray_shape);
                             state2.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_gray));
                             state2.setOnClickListener(null);
                             state1.setVisibility(View.GONE);
                             state3.setVisibility(View.GONE);
                             state2.setVisibility(View.VISIBLE);
                 }else{
                     //判断申诉的情况==================================
                     if("0".equals(item.getIs_appeal())){
                         state3.setVisibility(View.VISIBLE);
                         state3.setText("申诉");
                         state3.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
                         state3.setBackgroundResource(R.drawable.bg_1px_theme_shape);
                         state3.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 if(orderStateClickListener!=null){
                                     orderStateClickListener.onStateClick(item.getOrder_id(),OrderStateClickListener.APPEAL_TYPE,helper.getPosition());
                                 }
                             }
                         });
                         //申诉的情况下
                     }else{
                         //申诉未结束的情况下
                         if("0".equals(item.getAppeal_end())){
                             state3.setVisibility(View.VISIBLE);
                             state3.setText("仲裁中");
                             state3.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
                             state3.setBackgroundResource(R.drawable.bg_1px_theme_shape);
                             state3.setOnClickListener(null);
                             //申诉结束的情况下
                         }else if("1".equals(item.getAppeal_end())){
                             state3.setText("仲裁结果");
                             state3.setVisibility(View.VISIBLE);
                             state3.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_gray));
                             state3.setBackgroundResource(R.drawable.bg_1px_gray_shape);
                             state3.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     if(orderStateClickListener!=null){
                                         orderStateClickListener.onStateClick(item.getOrder_id(),OrderStateClickListener.CHECK_APPEAL_INFO,helper.getPosition());
                                     }
                                 }
                             });
                         }
                     }
                     //未发货的情况下========================================================
                             if("0".equals(item.getIs_post())){
                                 state2.setVisibility(View.VISIBLE);
                                 state2.setText("等待发货");
                                 state2.setBackgroundResource(R.drawable.orange_state);
                                 state2.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                                 state2.setOnClickListener(null);
                                            //有尾款的情况下
                                             if("0".equals(item.getIs_pay())){
                                                 state1.setVisibility(View.VISIBLE);
                                                 state1.setText("支付尾款");
                                                 state1.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
                                                 state1.setBackgroundResource(R.drawable.bg_1px_theme_shape);
                                                 state1.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         if(orderStateClickListener!=null){
                                                             orderStateClickListener.onStateClick(item.getOrder_id(),OrderStateClickListener.PAY_LEFT_MONRY,helper.getPosition());
                                                         }
                                                     }
                                                 });
                                             } else{
                                                 state1.setVisibility(View.GONE);
                                                 state1.setOnClickListener(null);
                                             }
                                 //发货的情况下
                             }else{
                                 //未确认收货的情况下
                                 if("0".equals(item.getIs_get())){
                                     state2.setVisibility(View.VISIBLE);
                                     state2.setText("确认收货");
                                     state2.setBackgroundResource(R.drawable.bg_1px_theme_shape);
                                     state2.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
                                     state2.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             if(orderStateClickListener!=null){
                                                 orderStateClickListener.onStateClick(item.getOrder_id(),OrderStateClickListener.GOT_ORDER_TYPE,helper.getPosition());
                                             }
                                         }
                                     });
                                     state1.setVisibility(View.VISIBLE);
                                     state1.setText("查看物流信息");
                                     state1.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_gray));
                                     state1.setBackgroundResource(R.drawable.bg_1px_gray_shape);
                                     state1.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(orderStateClickListener!=null){
                                                 orderStateClickListener.onStateClick(item.getOrder_id(),OrderStateClickListener.LOGISTICS_TYPE,helper.getPosition());
                                             }
                                         }
                                     });
                                     //确认收货的情况下
                                 }else if("1".equals(item.getIs_get())){
                                     state3.setVisibility(View.GONE);
                                     state2.setVisibility(View.GONE);
                                     state1.setBackgroundResource(R.drawable.light_orange_state);
                                     state1.setText("已确认收货");
                                     state1.setVisibility(View.VISIBLE);
                                     state1.setTextColor(ContextCompat.getColor(mContext,R.color.white));
//                                     state1.setVisibility(View.VISIBLE);
//                                     state1.setText("查看物流信息");
//                                     state1.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_gray));
//                                     state1.setBackgroundResource(R.drawable.bg_1px_gray_shape);
//                                     state1.setOnClickListener(new View.OnClickListener() {
//                                         @Override
//                                         public void onClick(View v) {
//                                             if(orderStateClickListener!=null){
//                                                 orderStateClickListener.onStateClick(item.getOrder_id(),OrderStateClickListener.LOGISTICS_TYPE,helper.getPosition());
//                                             }
//                                         }
//                                     });
                                 }
                     }
                 }
            //未结束的情况下；
        }else {
            //显示倒计时标签；
            helper.setVisible(R.id.left_time,false);
            helper.setVisible(R.id.count_container,true);
            //进行倒计时;
            CountdownView countdownView =helper.getView(R.id.count_down_view) ;
            countdownView.start(item.getCountdown()*1000);
            try{
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String endTime = simpleDateFormat.format(new Date(Long.parseLong(item.getEnd_time())*1000)) ;
                helper.setText(R.id.left_time,endTime+"结束");
            }catch (Exception e){e.printStackTrace();}
            state1.setVisibility(View.VISIBLE);
            state2.setVisibility(View.GONE);
            state3.setVisibility(View.GONE);
            state1.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            if ("1".equals(item.getIs_heart())) {
                state1.setBackgroundResource(R.drawable.heart_red_state);
                state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                state1.setText("心跳时间");
            } else {
                if ("1".equals(item.getFall_state())) {
                    state1.setText("降价");
                    state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    state1.setBackgroundResource(R.drawable.green_state);
                } else {
                    //不是心跳时间;
                    if ("1".equals(item.getSell_state())) {
                        state1.setBackgroundResource(R.drawable.light_orange_state);
                        state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        state1.setText("暗价");
                    } else if ("2".equals(item.getSell_state())) {
                        state1.setBackgroundResource(R.drawable.orange_state);
                        state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        state1.setText("明价");
                    }
                }
            }
        }
        Picasso.with(mContext).load(NetConfig.GOODS_IMG+item.getImg()).resize(300,300).centerCrop().placeholder(R.drawable.empty_img).into((ImageView) helper.getView(R.id.img));

    }
    private OrderStateClickListener orderStateClickListener ;
    public void setOrderStateClickListener(OrderStateClickListener orderStateClickListener) {
        this.orderStateClickListener = orderStateClickListener;
    }

}
