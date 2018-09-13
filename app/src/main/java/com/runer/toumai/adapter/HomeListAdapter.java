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
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.net.NetConfig;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.List;
import cn.iwgang.countdownview.CountdownView;
/**
 * Created by szhua on 2017/7/14/014.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * HomeListAdapter
 * 首页列表项
 */
public class HomeListAdapter extends BaseQuickAdapter<GoodListBean,BaseViewHolder> {
    DecimalFormat df = new DecimalFormat("##0.00");
    public HomeListAdapter(@Nullable List<GoodListBean> data) {
        super(R.layout.home_list_adapter,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, final GoodListBean item) {
        Picasso.with(mContext).load(NetConfig.GOODS_IMG+item.getImg()).placeholder(R.drawable.empty_img).resize(340,340).centerCrop().into((ImageView) helper.getView(R.id.imageView));
        helper.setText(R.id.title,item.getTitle());
        helper.setText(R.id.dijia, "底价：￥" +df.format(Float.parseFloat(item.getPrice())));
        TextView tag = helper.getView(R.id.tag);
        helper.setText(R.id.times,"出价"+item.getOffer_num()+"次");
        //右下角的button
        TextView bt =helper.getView(R.id.bt);
        CountdownView countDownView =helper.getView(R.id.count_down_view);
        if(item.getCountdown()>0){
            countDownView.setVisibility(View.VISIBLE);
            countDownView.start(item.getCountdown()*1000);
            //倒计时结束的监听
            countDownView.setOnCountdownEndListener(countdownEndListener);
            helper.setText(R.id.left_time,"剩余:");
        }else{
            countDownView.setOnCountdownEndListener(null);
            countDownView.setVisibility(View.GONE);
            //剩余时间
            helper.setText(R.id.left_time,"已结束");
        }
        //判断是否结束
        if("1".equals(item.getIs_end())){
            //结束
            tag.setBackgroundResource(R.drawable.gray_state);
            tag.setText("已结束");
            bt.setBackgroundResource(R.drawable.gray_state);
            //未成交
            if("0".equals(item.getIs_order())){
                helper.setText(R.id.left_time, "已结束-未成交");
                helper.setText(R.id.tag1, "当前价：");
                helper.setTextColor(R.id.tag1, ContextCompat.getColor(mContext,R.color.text_color_light)) ;
                helper.setText(R.id.current_price, "未成交");
                helper.setTextColor(R.id.current_price,ContextCompat.getColor(mContext,R.color.text_color_light)) ;
                //已成交
            }else{
                helper.setText(R.id.left_time,"已结束-已成交");
                helper.setText(R.id.tag1, "当前价：");
                helper.setTextColor(R.id.tag1, ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                helper.setText(R.id.current_price, "￥"+df.format(Float.parseFloat(item.getNow_price())));
                helper.setTextColor(R.id.current_price,ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
            }
        }else{
            //未结束的情况下；
            bt.setBackgroundResource(R.drawable.nor_bt_shape);
            //心跳的时间：
            if("1".equals(item.getIs_heart())){
                tag.setBackgroundResource(R.drawable.heart_red_state);
                tag.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                tag.setText("心跳时间");
                helper.setText(R.id.tag1, "当前价：");
                helper.setTextColor(R.id.tag1, ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                helper.setText(R.id.current_price, "￥"+df.format(Float.parseFloat(item.getNow_price())));
                helper.setTextColor(R.id.current_price,ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                if(TextUtils.isEmpty(item.getBright_rest_time().getLeftTime())){
                    //TODO
                    helper.setText(R.id.left_time,"已结束");
                    bt.setBackgroundResource(R.drawable.gray_state);
                    helper.setText(R.id.tag1, "当前价：");
                    helper.setTextColor(R.id.tag1, ContextCompat.getColor(mContext,R.color.text_color_light)) ;
                    helper.setText(R.id.current_price, "未成交");
                    helper.setTextColor(R.id.current_price,ContextCompat.getColor(mContext,R.color.text_color_light)) ;
                }
            }else{
                //降价的阶段
                if("1".equals(item.getFall_state())){
                    tag.setText("降价");
                    tag.setBackgroundResource(R.drawable.green_state);
                    //未降价的阶段；
                    helper.setText(R.id.tag1, "当前价：");
                    helper.setTextColor(R.id.tag1, ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                    helper.setText(R.id.current_price, "￥"+df.format(Float.parseFloat(item.getNow_price())));
                    helper.setTextColor(R.id.current_price,ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                }else{
                    //不是心跳时间;
                    if("1".equals(item.getSell_state())){
                        tag.setBackgroundResource(R.drawable.light_orange_state);
                        tag.setText("暗价");
                        helper.setText(R.id.tag1, "当前价：");
                        helper.setTextColor(R.id.tag1, ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                        helper.setText(R.id.current_price, "暗价");
                        helper.setTextColor(R.id.current_price,ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                    }else if("2".equals(item.getSell_state())){
                        tag.setBackgroundResource(R.drawable.orange_state);
                        tag.setText("明价");
                        helper.setText(R.id.tag1, "当前价：");
                        helper.setTextColor(R.id.tag1, ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                        helper.setText(R.id.current_price, "￥"+df.format(Float.parseFloat(item.getNow_price())));
                        helper.setTextColor(R.id.current_price,ContextCompat.getColor(mContext,R.color.text_color_orange)) ;
                    }
                }
            }
        }
    }
    private CountdownView.OnCountdownEndListener countdownEndListener ;
    public void setCountdownEndListener(CountdownView.OnCountdownEndListener countdownEndListener) {
        this.countdownEndListener = countdownEndListener;
    }
}
