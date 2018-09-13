package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.view.View;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.FaqBean;
import com.runer.toumai.ui.fragment.PocketFragment;
import com.runer.toumai.widget.FAQTips;
import java.util.ArrayList;
import java.util.List;
/*口袋*/
public class PocketActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        addFragmentList(R.id.container,new PocketFragment());
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("口袋");
        if(ToumaiApplication.isFaq){
            setRightImage(R.drawable.faq);
            setRightImageClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqBean faqBean = new FaqBean();
                    faqBean.setQuestion("“口袋”是什么意思？");
                    faqBean.setAnswer(PocketActivity.this.getString(R.string.koudai));
                    List<FaqBean> faqBeens = new ArrayList<>();
                    faqBeens.add(faqBean);
                    FAQTips.show(PocketActivity.this, faqBeens);
                }
            });
        }
    }
}
