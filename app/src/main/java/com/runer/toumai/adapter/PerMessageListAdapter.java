package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.MessageBean;
import com.runer.toumai.net.NetConfig;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MessageListAdapter
 * 消息列表adapter;
 */
public class PerMessageListAdapter extends BaseQuickAdapter<MessageBean,BaseViewHolder> {
    public PerMessageListAdapter(@Nullable List<MessageBean> data) {
        super(R.layout.item_per_msg_layout,data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MessageBean item) {
        helper.setText(R.id.name,item.getFrom_name());
        helper.setText(R.id.msg_time,item.getCreate_time()).setText(R.id.msg_content,item.getContent());
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

        if("0".equals(item.getIs_read())){
            helper.setTextColor(R.id.msg_content, ContextCompat.getColor(mContext,R.color.text_color_orange));
        }else{
            helper.setTextColor(R.id.msg_content, ContextCompat.getColor(mContext,R.color.text_color_gray));
        }

        Picasso.with(mContext).load(NetConfig.HEAD_PATH+item.getFrom_head()).placeholder(R.drawable.logo).into((ImageView) helper.getView(R.id.header));
    }
    OndeleteClickListener ondeleteClickListener ;
    public void setOndeleteClickListener(OndeleteClickListener ondeleteClickListener) {
        this.ondeleteClickListener = ondeleteClickListener;
    }
    public interface  OndeleteClickListener{
        void onItemClick(MessageBean messageBean, int pos);
        void  onItemDeleteClick(MessageBean messageBean, int pos);
    }
}
