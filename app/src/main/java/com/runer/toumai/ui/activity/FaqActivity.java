package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.FaqListBean;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class FaqActivity extends BaseActivity {

    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.content)
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.inject(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FaqListBean faqListBean = (FaqListBean) getIntent().getSerializableExtra("data");
        if(faqListBean!=null)
    {
           setTitle("FAQ常见问题");
           content.setText(faqListBean.getValue());
    }
    }

}
