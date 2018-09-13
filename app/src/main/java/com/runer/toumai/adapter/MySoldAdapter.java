package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.inter.OrderStateClickListener;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.FaqUtil;
import com.runer.toumai.widget.FAQListTipsDialog;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MySoldAdapter
 * 我出售的商品adapter
 */
public class MySoldAdapter extends BaseQuickAdapter<GoodListBean,BaseViewHolder>{
    public MySoldAdapter(@Nullable List<GoodListBean> data) {
        super(R.layout.item_my_order,data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final GoodListBean item) {
        helper.setText(R.id.title,item.getTitle())
                .setText(R.id.low_price,mContext.getString(R.string.money)+item.getPrice())
                .setText(R.id.current_price,"¥"+item.getNow_price())
                .setText(R.id.price_num,item.getOffer_num())
                .setText(R.id.publish_time,"发布时间"+item.getCreate_time());
        TextView state1 =helper.getView(R.id.state_bt);
        TextView state2 =helper.getView(R.id.state_bt2);
        TextView state3 =helper.getView(R.id.state_bt3);
        state3.setVisibility(View.GONE);

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
        //结束的情况下；
        if("1".equals(item.getIs_end())){
            //不显示倒计时
            helper.setVisible(R.id.count_container,false);
            helper.setVisible(R.id.left_time,true);
            try{
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String endTime = simpleDateFormat.format(new Date(Long.parseLong(item.getEnd_time())*1000)) ;
                helper.setText(R.id.left_time,endTime+"结束");
            }catch (Exception e){e.printStackTrace();}
            //未成交
            if("0".equals(item.getIs_order())){
                    state1.setBackgroundResource(R.drawable.bg_1px_gray_shape);
                    state1.setText("已结束-未成交");
                    state1.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_gray));
                    state2.setVisibility(View.GONE);
                //已成交
            }else{
                    state1.setBackgroundResource(R.drawable.bg_1px_gray_shape);
                    state1.setText("已结束-成交");
                    state1.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_gray));
                    state2.setVisibility(View.VISIBLE);

                //设置查看收货地址；
                state3.setVisibility(View.VISIBLE);
                state3.setText("查看收货地址");
                state3.setBackgroundResource(R.drawable.light_orange_state);
                state3.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                state3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(orderStateClickListener!=null){
                            orderStateClickListener.onStateClick(item.getBuyer_addr() ,OrderStateClickListener.CHECK_ADDRESS ,helper.getPosition());
                        }
                    }
                });
                //未发货的情况下
                if("0".equals(item.getIs_post())){
                    //没有支付尾款的时候
                    if("0".equals(item.getIs_pay())){
                        state2.setText("待支付尾款");
                        state2.setBackgroundResource(R.drawable.bg_1px_theme_shape);
                        state2.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
                        state2.setOnClickListener(null);
                    }else{
                        state2.setText("上传运单信息");
                        state2.setBackgroundResource(R.drawable.orange_state);
                        state2.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                        state2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(orderStateClickListener!=null){
                                    orderStateClickListener.onStateClick(item.getOrder_id() ,OrderStateClickListener.LOGISTICS_TYPE ,helper.getPosition());
                                }
                            }
                        });
                    }
                }else{
                    //未确认收货
                   if("0".equals(item.getIs_get())){
                       state2.setVisibility(View.VISIBLE);
                       state2.setText("待确认收货");
                       state2.setBackgroundResource(R.drawable.bg_1px_theme_shape);
                       state2.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
                       state2.setOnClickListener(null);
                   }else if("1".equals(item.getIs_get())){
                       state2.setVisibility(View.VISIBLE);
                       state2.setText("已确认收货");
                       state2.setBackgroundResource(R.drawable.bg_1px_theme_shape);
                       state2.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_orange));
                       state2.setOnClickListener(null);
                   }else{
                       //TODO 未确认收货的情况=== 啥状态也没有
                       state2.setVisibility(View.GONE);
                   }
                }
            }
            //未结束的情况下；
        }else {
            //显示倒计时
            helper.setVisible(R.id.count_container,true);
            helper.setVisible(R.id.left_time,false);
            //进行倒计时
            CountdownView countDownView =helper.getView(R.id.count_down_view);
            countDownView.start(item.getCountdown()*1000);
            try{
             helper.setText(R.id.left_time,"剩余:"+item.getBright_rest_time().getLeftTime());
            }catch (Exception e){e.printStackTrace();}
            state2.setVisibility(View.VISIBLE);
            state2.setText("撤下商品");
            state2.setBackgroundResource(R.drawable.bg_1px_gray_shape);
            state2.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_gray));
            state2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if(orderStateClickListener!=null){
                     orderStateClickListener.onStateClick(item.getOrder_id(),OrderStateClickListener.REVOKE_GOODS,helper.getPosition());
                 }
                }
            });
            if ("1".equals(item.getIs_heart())) {
                            state1.setBackgroundResource(R.drawable.heart_red_state);
                            state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                            state1.setText("心跳时间");
            } else {
                   //不是心跳时间;
                if("1".equals(item.getFall_state())){
                    state1.setText("降价");
                    state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    state1.setBackgroundResource(R.drawable.green_state);
                }else{
                    if ("1".equals(item.getSell_state())) {
                        state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        state1.setBackgroundResource(R.drawable.light_orange_state);
                        state1.setText("暗价");
                        state2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UiUtil.showLongToast(mContext,"暗价阶段无法撤下商品。如需撤下，请在明价阶段进行“撤下商品”操作。");
                            }
                        });
                    } else if ("2".equals(item.getSell_state())) {
                        state1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        state1.setBackgroundResource(R.drawable.orange_state);
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
