package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.Constant;
import com.runer.toumai.bean.AlipayBean;
import com.runer.toumai.bean.WechatPayBean;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SelectPayActivity extends BaseActivity {
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.ali_pay)
    RadioButton aliPay;
    @InjectView(R.id.wechat_pay)
    RadioButton wechatPay;
    @InjectView(R.id.types)
    RadioGroup types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);
        ButterKnife.inject(this);

        types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.ali_pay:
                        break;
                    case R.id.wechat_pay:
                        break;
                }
            }
        });
    }
    //支付宝支付

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("支付");
    }
}
