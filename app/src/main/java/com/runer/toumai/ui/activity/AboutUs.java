package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.ContactInfo;
import com.runer.toumai.dao.ContactDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.NormalInputEditText;
import com.squareup.picasso.Picasso;
import butterknife.ButterKnife;
import butterknife.InjectView;

/*联系我们界面*/
public class AboutUs extends BaseActivity {

    @InjectView(R.id.kefu_phone)
    TextView kefuPhone;
    @InjectView(R.id.qiye_email)
    TextView qiyeEmail;
    @InjectView(R.id.weobo)
    TextView weobo;
    @InjectView(R.id.weixin)
    TextView weixin;
    @InjectView(R.id.code_img)
    ImageView codeImg;
    @InjectView(R.id.feed_user_name)
    NormalInputEditText feedUserName;
    @InjectView(R.id.feed_user_phone)
    NormalInputEditText feedUserPhone;
    @InjectView(R.id.feed_user_email)
    NormalInputEditText feedUserEmail;
    @InjectView(R.id.feed_content)
    EditText feedContent;
    @InjectView(R.id.commit_bt)
    TextView commitBt;
    private ContactDao contactDao;

    /**
     * 联系客服页面上部的联系方式，仅保留微信公众号一种途径。显示提示文字：“您在使用投买网的过程中遇到任何问题，
     * 欢迎通过“投买网”微信公众号联系小河豚哟~”文字下方显示微信公众号二维码图片，并在图片下方显示“扫描二维码即可关注“投买网”微信公众号”。
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.inject(this);
        contactDao = new ContactDao(this, this);
        contactDao.getContactInfo();
        showProgress(true);
        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    contactDao.add(AppUtil.getUserId(AboutUs.this),phone, eMail, name, content);
                    showProgressWithMsg(true,"正在提交意见");
                }
            }
        });
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_0) {
            ContactInfo contactInfo = contactDao.getInfo();
            if (contactInfo != null) {
                kefuPhone.setText("客服电话：" + contactInfo.getMobile());
                qiyeEmail.setText("企业邮箱:" + contactInfo.getEmail());
                weobo.setText("官方微博:" + contactInfo.getWeibo());
                weixin.setText("官方微信:" + contactInfo.getWechat());
                Logger.d(NetConfig.ABOUT_PATH+contactInfo.getImg());
                Picasso.with(this).load(NetConfig.ABOUT_PATH + contactInfo.getImg()).into(codeImg);
            }
        }else if(requestCode==RequestCode.CODE_1){
            UiUtil.showLongToast(this,"提交意见成功");
            finish();
        }
    }
    private String phone;
    private String name;
    private String eMail;
    private String content;
    //检验输入的内容
    private boolean checkInput() {
        phone = feedUserPhone.getInputContent();
        name = feedUserName.getInputContent();
        eMail = feedUserEmail.getInputContent();
        content = feedContent.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            UiUtil.showLongToast(this, "请填写电话");
            return false;
        }
        if (!UiUtil.isValidMobileNo(phone)) {
            UiUtil.showLongToast(this, "电话格式不正确");
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            UiUtil.showLongToast(this, "请填写名字");
            return false;
        }
        if (TextUtils.isEmpty(eMail)) {
            UiUtil.showLongToast(this, "请填写邮箱");
            return false;
        }
        if (!UiUtil.checkEmail(eMail)) {
            UiUtil.showLongToast(this, "邮箱格式不正确");
            return false;
        }
        if (TextUtils.isEmpty(content)) {
            UiUtil.showLongToast(this, "请填写反馈内容");
            return false;
        }
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("联系我们");
    }
}
