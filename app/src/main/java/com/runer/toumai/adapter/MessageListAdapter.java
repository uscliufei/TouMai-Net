package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.MessageBean;
import com.zzhoujay.richtext.RichText;

import java.util.List;
/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MessageListAdapter
 * 消息列表adapter;
 */
public class MessageListAdapter extends BaseQuickAdapter<MessageBean,BaseViewHolder> {
    public MessageListAdapter(@Nullable List<MessageBean> data) {
        super(R.layout.item_message_list_layout,data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MessageBean item) {
           helper.setText(R.id.msg_time,item.getCreate_time()) ;
           helper.setText(R.id.msg_content, item.getContent_app());

        TextView msgTx =helper.getView(R.id.msg_content);
        RichText.from(item.getContent_app()).into(msgTx);


        //type	类型：0系统消息，1用户
        if("0".equals(item.getType())){
            helper.setText(R.id.msg_type,"系统消息");
        }else{
            helper.setText(R.id.msg_type,"用户消息");
        }

        if("0".equals(item.getIs_read())){
            helper.setTextColor(R.id.msg_content, ContextCompat.getColor(mContext,R.color.text_color_orange));
        }else{
            helper.setTextColor(R.id.msg_content, ContextCompat.getColor(mContext,R.color.text_color_gray));
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
        void onItemClick(MessageBean messageBean ,int pos);
        void  onItemDeleteClick(MessageBean messageBean ,int pos);
    }
}
