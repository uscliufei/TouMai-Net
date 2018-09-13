package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.MessageBean;
import com.runer.toumai.util.AppUtil;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessageDetailActivity extends BaseActivity {

    @InjectView(R.id.content)
    TextView contentview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.inject(this);

        final  MessageBean messageBean = (MessageBean) getSerializableExtras("messageBean", null);

        RichText.from(messageBean.getContent())
                .urlClick(url -> {
                    UiUtil.showLongToast(getApplicationContext(),url);
                    //AppUtil.startBrower(MessageDetailActivity.this,url);
                    return true;
                }).into(contentview);
        contentview.setOnClickListener(view -> {
            contentClick(messageBean);
        } );

    }


    @Override
    protected void onStart() {
        super.onStart();
        setTitle("消息详情");
    }

    private void contentClick(MessageBean messageBean){
        if (messageBean != null) {
            Bundle bundle = null;
            switch (messageBean.getLink_type()) {
                case "0":
                    break;
                case "1":
                    bundle = new Bundle();
                    bundle.putString("id", messageBean.getLink_id());
                    transUi(ProInfoActivity.class, bundle);
                    finish();
                    break;
                case "2":
                    transUi(MyOrderActivity.class, null);
                    finish();
                    break;
                case "3":
                    bundle = new Bundle();
                    bundle.putInt("index", 1);
                    transUi(HomeActivity.class, bundle);
                    finish();
                    break;
                case "4":
                    transUi(WalletActivity.class, null);
                    finish();
                    break;
                case "5":
                    transUi(AddressActivity.class, null);
                    finish();
                    break;
            }
        }else{
            UiUtil.showLongToast(MessageDetailActivity.this,"解析错误！");
        }
    }
}
