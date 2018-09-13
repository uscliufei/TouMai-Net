package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.ConcernMsgBean;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.util.AppUtil;
import com.squareup.picasso.Picasso;
import java.util.List;
/**
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ConcernMessageAdapter
  私信列表页面adapter;
 */
public class ConcernMessageAdapter extends BaseQuickAdapter<ConcernMsgBean,BaseViewHolder> {
    public ConcernMessageAdapter(@Nullable List<ConcernMsgBean> data) {
        super(R.layout.item_concern_msg_layout,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ConcernMsgBean item) {
        //设置发信人name;
        helper.setText(R.id.name,item.getFrom_name())
               .setText(R.id.msg_time,item.getCreate_time())
               .setText(R.id.msg_content,item.getContent());
        if(item.getFrom_user().equals(AppUtil.getUserId(mContext))){
            helper.setText(R.id.name,"我的回复");
        }else{
            helper.setText(R.id.name,item.getFrom_name());
        }
        Picasso.with(mContext).load(NetConfig.HEAD_PATH+item.getFrom_head()).placeholder(R.drawable.logo).into((ImageView) helper.getView(R.id.header));
    }
}
