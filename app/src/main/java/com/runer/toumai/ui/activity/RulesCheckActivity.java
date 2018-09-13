package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.RulesDetailBean;
import com.runer.toumai.bean.UpdateUserInfoParam;
import com.runer.toumai.dao.ArticleDao;
import com.runer.toumai.dao.UpdateUserInfoDao;
import com.runer.toumai.util.AppUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
/*规则说明界面*/
public class RulesCheckActivity extends BaseActivity {
    @InjectView(R.id.content)
    TextView content;
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
    @InjectView(R.id.got_bt)
    TextView gotBt;
    private ArticleDao articleDao;
    private String id;
    private RulesDetailBean rulesDetailBean;
    private boolean isLearn;
    private UpdateUserInfoDao userInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_check);
        ButterKnife.inject(this);
        id = getStringExtras("id","");
        articleDao = new ArticleDao(this, this);
        articleDao.getRulesDetail(id);
        showProgress(true);
        userInfoDao =new UpdateUserInfoDao(this,this) ;
        isLearn = getIntent().getExtras().getBoolean("learn");
        if (isLearn) {
            gotBt.setVisibility(View.VISIBLE);
        }else{
            gotBt.setVisibility(View.GONE);
        }
        gotBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserInfoParam updateUserInfoParam =new UpdateUserInfoParam() ;
                updateUserInfoParam.setIs_dark_learn("1");
                updateUserInfoParam.setId(AppUtil.getUserId(RulesCheckActivity.this));
                userInfoDao.upLoadUserInfo(updateUserInfoParam);
                showProgress(true);
            }
        });
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_1) {
            rulesDetailBean = articleDao.getRulesDetailBean();
            if (rulesDetailBean != null) {
                setTitle(rulesDetailBean.getTitle());
                content.setText(Html.fromHtml(rulesDetailBean.getContent()));
            }
        }else if(requestCode==RequestCode.UPDATE_USER_UINFO){
            UiUtil.showLongToast(this,"已学会");
            AppUtil.setDarkLearn(this);
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("规则说明");
    }
}
