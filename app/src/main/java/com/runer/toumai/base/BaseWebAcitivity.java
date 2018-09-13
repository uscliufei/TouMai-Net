package com.runer.toumai.base;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.bean.FaqListBean;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BaseWebAcitivity extends BaseActivity {


    public static final String WEB_TITLE ="title" ;
    public static final String WEB_URL="url" ;

    @InjectView(R.id.container)
    LinearLayout container;
    private String title  ;


    private FaqListBean faqListBean ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web_acitivity);
        ButterKnife.inject(this);

        String url ;
        faqListBean = (FaqListBean) getIntent().getSerializableExtra("data");
        if(faqListBean!=null){
            title="FAQ常见问题解答";
             url =getString(R.string.faq_url)+faqListBean.getValue();
        }else{
            title = getStringExtras(WEB_TITLE,"网页标题");
            url = getStringExtras(WEB_URL,"") ;
        }

        AgentWeb.with(this)//传入Activity
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(new ChromeClientCallbackManager.ReceivedTitleCallback() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                     //UiUtil.showLongToast(getApplicationContext(),title);
                    }
                }) //设置 Web 页面的 title 回调
                .closeWebViewClientHelper()
                .createAgentWeb()//
                .ready()
                .go(url);
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle(title);
    }
}
