package com.runer.toumai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.Constant;
import com.runer.toumai.bean.UserInfo;
import com.runer.toumai.dao.LoginDao;
import com.runer.toumai.dao.UserInfoDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.util.AuthResult;
import com.runer.toumai.widget.NormalTipsDialog;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/*个人信息详情页面*/
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {


    @InjectView(R.id.header)
    CircleImageView header;
    @InjectView(R.id.username)
    TextView username;
    @InjectView(R.id.sex_icon)
    ImageView sexIcon;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.header_container)
    LinearLayout headerContainer;
    @InjectView(R.id.textView3)
    TextView textView3;
    @InjectView(R.id.official_certification)
    TextView officialCertification;
    @InjectView(R.id.des)
    TextView des;
    @InjectView(R.id.auth_state)
    TextView authState;
    @InjectView(R.id.check_reason)
    TextView checkReason;
    @InjectView(R.id.reg_time)
    TextView regTime;
    @InjectView(R.id.wechat_bt)
    TextView wechatBt;
    @InjectView(R.id.sina_bt)
    TextView sinaBt;
    @InjectView(R.id.alipay_bt)
    TextView alipayBt;
    @InjectView(R.id.qq_pay_bt)
    TextView qqPayBt;
    @InjectView(R.id.bang_phone_num)
    TextView bangPhoneNum;
    @InjectView(R.id.bang_bt)
    TextView bangBt;
    @InjectView(R.id.into_bt)
    RelativeLayout intoBt;
    @InjectView(R.id.bang_email)
    TextView bangEmail;
    @InjectView(R.id.change_email)
    TextView changeEmail;
    @InjectView(R.id.change_pass)
    TextView changePass;

    private UserInfoDao userInfoDao;
    private LoginDao loginDao;
    private UserInfo userInfo;
    private NormalTipsDialog tips;
    private NormalTipsDialog delete;
    private NormalTipsDialog deleteTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.inject(this);
        UserInfo userInfo = AppUtil.getUserInfo(this);
        if (userInfo != null) {
            setUserInfo(userInfo);
        }
        bangBt.setOnClickListener(this);
        wechatBt.setOnClickListener(this);
        sinaBt.setOnClickListener(this);
        alipayBt.setOnClickListener(this);
        qqPayBt.setOnClickListener(this);
        loginDao = new LoginDao(this, this);
        checkReason.setOnClickListener(this);
        intoBt.setOnClickListener(this);
        bangEmail.setOnClickListener(this);
        changePass.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userInfoDao == null) {
            userInfoDao = new UserInfoDao(this, this);
        }
        userInfoDao.getUserInfo(AppUtil.getUserId(this));
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.GET_USER_INFO) {
            userInfo = userInfoDao.getUserInfo();
            if (userInfo != null) {
                setUserInfo(userInfo);
            }
        } else if (requestCode == RequestCode.THIRD_LOGIN) {
            if (AppUtil.getUserId(this).equals(loginDao.getUserId())) {
                UiUtil.showLongToast(this, "账户绑定成功");
            } else {
                UiUtil.showLongToast(this, getString(R.string.bind_tips));
            }
        } else if (requestCode == RequestCode.DELETE_OPEN) {
            UiUtil.showLongToast(this, "第三方账户解除成功");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("个人信息");
        //跳转信息编辑界面
        setRightTvClickLister(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(EditPersonalInfoActivity.class, null);
            }
        });
    }

    private void setUserInfo(UserInfo userInfo) {
        username.setText(userInfo.getUser_name());
        Picasso.with(this).load(NetConfig.HEAD_PATH + userInfo.getHead()).placeholder(R.drawable.logo).into(header);
        if (!TextUtils.isEmpty(userInfo.getAuth())) {
            officialCertification.setText(userInfo.getAuth());
        } else {
            officialCertification.setText("暂无官方验证");
        }
        if (!TextUtils.isEmpty(userInfo.getIntro())) {
            des.setText(userInfo.getIntro());
        } else {
            des.setText("暂无描述");
        }
        des.setText(userInfo.getIntro());
        //设置认证状态；
        if ("2".equals(userInfo.getAuth_state())) {
            authState.setText("正在申请中");
            authState.setTextColor(ContextCompat.getColor(this, R.color.text_color_gray));
            checkReason.setVisibility(View.GONE);
        } else if ("0".equals(userInfo.getAuth_state())) {
            authState.setText("未认证");
            authState.setTextColor(ContextCompat.getColor(this, R.color.text_color_gray));
            checkReason.setVisibility(View.GONE);
        } else if ("1".equals(userInfo.getAuth_state())) {
            authState.setText("成功");
            authState.setTextColor(ContextCompat.getColor(this, R.color.text_color_orange));
            checkReason.setVisibility(View.GONE);
        } else if ("3".equals(userInfo.getAuth_state())) {
            authState.setText("认证失败");
            authState.setTextColor(ContextCompat.getColor(this, R.color.text_color_red));
            checkReason.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(userInfo.getBirth())) {
            regTime.setText(userInfo.getBirth());
        } else {
            regTime.setText(Constant.DEFAULT_BIRTHDAY_DATE);
        }
        if ("男".equals(userInfo.getSex())) {
            sexIcon.setImageResource(R.drawable.man);
        } else {
            sexIcon.setImageResource(R.drawable.woman);
        }
        if (TextUtils.isEmpty(userInfo.getMobile())) {
            bangPhoneNum.setText("暂未绑定手机号");
            bangBt.setVisibility(View.VISIBLE);
            bangBt.setText("绑定手机");
        } else {
            bangPhoneNum.setText(userInfo.getMobile());
            bangBt.setVisibility(View.VISIBLE);
            bangBt.setText("更换手机号");
        }
        if (TextUtils.isEmpty(userInfo.getProvince())) {
            address.setText("暂无地址");
        } else {
            address.setText(userInfo.getProvince() + userInfo.getCity() + userInfo.getArea());
        }
        //填写状态;
        if (!TextUtils.isEmpty(userInfo.getQq_login())) {
            UiUtil.setTextTopImage(qqPayBt, R.drawable.account_qq);
            qqPayBt.setText(userInfo.getQq_name());
            qqPayBt.setTag(true);
        } else {
            UiUtil.setTextTopImage(qqPayBt, R.drawable.account_qq_un);
            qqPayBt.setTag(false);
        }
        if (!TextUtils.isEmpty(userInfo.getWechat_login())) {
            UiUtil.setTextTopImage(wechatBt, R.drawable.acc_we);
            wechatBt.setText(userInfo.getWechat_name());
            wechatBt.setTag(true);
        } else {
            wechatBt.setTag(false);
            UiUtil.setTextTopImage(wechatBt, R.drawable.acc_we_un);
        }

        if (!TextUtils.isEmpty(userInfo.getWeibo_login())) {
            UiUtil.setTextTopImage(sinaBt, R.drawable.account_webo);
            sinaBt.setText(userInfo.getWeibo_name());
            sinaBt.setTag(true);
        } else {
            sinaBt.setTag(false);
            UiUtil.setTextTopImage(sinaBt, R.drawable.account_webo_un);
        }
        if (!TextUtils.isEmpty(userInfo.getAli_login())) {
            UiUtil.setTextTopImage(alipayBt, R.drawable.acc_alipay);
            alipayBt.setText(userInfo.getAliname());
            alipayBt.setTag(true);
        } else {
            alipayBt.setTag(false);
            UiUtil.setTextTopImage(alipayBt, R.drawable.acc_alipay_un);
        }

        //邮箱
        if (!TextUtils.isEmpty(userInfo.getEmail())){
            bangEmail.setVisibility(View.GONE);
            changeEmail.setVisibility(View.VISIBLE);
            changePass.setVisibility(View.VISIBLE);
        }else{
            bangEmail.setVisibility(View.VISIBLE);
            changeEmail.setVisibility(View.GONE);
            changePass.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == bangBt) {
            transUi(BindPhoneActivity.class, null);
        } else if (v == wechatBt) {
            //解绑
            if ((boolean) v.getTag()) {
                deleteOpen(Constant.wechat);
            } else {
                wechatLogin();
            }
        } else if (v == sinaBt) {
            //解绑
            if ((boolean) v.getTag()) {
                deleteOpen(Constant.weiBo);
            } else {
                wechatLogin();
            }

        } else if (v == alipayBt) {
            //解绑
            if ((boolean) v.getTag()) {
                deleteOpen(Constant.ali);
            } else {
                aliLogin();
            }

        } else if (v == qqPayBt) {
            //解绑；
            if ((boolean) v.getTag()) {
                deleteOpen(Constant.qq);
            } else {
                qqLogin();
            }
        } else if (v == checkReason) {
            if (userInfo != null)
                tips = NormalTipsDialog.show(this, "信息", "认证消息", userInfo.getAuth_fail_reason(), "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tips.dismiss();
                    }
                });
        } else if (v == intoBt) {
            transUi(EditPersonalInfoActivity.class, null);
        }else  if (v==changeEmail){
            transUi(ChangeEmailAcitivity.class,null);
        }else  if (v==bangEmail){
             transUi(BindEmailActivity.class,null);
        }else if (v==changePass){
             transUi(ForgetPassAcitvity.class,null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    //第三方登录授权回调；
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            UiUtil.showLongToast(UserInfoActivity.this, "正在第三方授权");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {
            switch (platform) {
                case WEIXIN:
                    if (data != null) {
                        String openid = data.get("uid");
                        String nickName = data.get("name");
                        loginDao.openLogin(openid, TextUtils.isEmpty(nickName) ? "微信" : nickName, "wechat", AppUtil.getUserId(UserInfoActivity.this));
                        showProgressWithMsg(true, "授权成功");
                    }
                    break;
                case QQ:
                    if (data != null) {
                        String openid = data.get("unionid");
                        String nickName = data.get("name");
                        String openid2 = data.get("uid");
                        loginDao.openLogin(openid, TextUtils.isEmpty(nickName) ? "QQ" : nickName, "qq", AppUtil.getUserId(UserInfoActivity.this), openid2);
                        showProgressWithMsg(true, "授权成功");
                    }
                    break;
                case SINA:
                    if (data != null) {
                        String openid = data.get("uid");
                        String nickName = data.get("name");
                        loginDao.openLogin(openid, TextUtils.isEmpty(nickName) ? "微博" : nickName, "weibo", AppUtil.getUserId(UserInfoActivity.this));
                        showProgressWithMsg(true, "授权成功");
                    }
                    break;
                case ALIPAY:
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Logger.d(t);
            UiUtil.showLongToast(UserInfoActivity.this, "第三方授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            UiUtil.showLongToast(UserInfoActivity.this, "第三方授权取消");
        }
    };


    private void wechatLogin() {
        if (UMShareAPI.get(this).isAuthorize(
                this, SHARE_MEDIA.WEIXIN)) {
            UMShareAPI.get(getApplicationContext()).deleteOauth(
                    this, SHARE_MEDIA.WEIXIN, umAuthListener);
        }
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(
                this, SHARE_MEDIA.WEIXIN, umAuthListener);
    }


    private void qqLogin() {
        if (UMShareAPI.get(this).isAuthorize(
                this, SHARE_MEDIA.QQ)) {
            UMShareAPI.get(getApplicationContext()).deleteOauth(
                    this, SHARE_MEDIA.QQ, umAuthListener);
        }
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(
                this, SHARE_MEDIA.QQ, umAuthListener);
    }


    private void weiBoLogin() {
        if (UMShareAPI.get(this).isAuthorize(
                this, SHARE_MEDIA.SINA)) {
            UMShareAPI.get(getApplicationContext()).deleteOauth(
                    this, SHARE_MEDIA.SINA, umAuthListener);
        }
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(
                this, SHARE_MEDIA.SINA, umAuthListener);
    }


    /**
     * wechat weibo ali qq
     *
     * @param type
     */
    private void deleteOpen(String type) {
        deleteTip = NormalTipsDialog.show(this, "解除绑定", "确认解除绑定", "你确认解除当前的第三方账号？", "确认", view -> {
            loginDao.deleteOpen(AppUtil.getUserId(this), type);
            deleteTip.dismiss();
            showProgressWithMsg(true, "正在解除绑定..");
        });
    }

    private void aliLogin() {
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(UserInfoActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(Constant.ALI_AUTH_INFO, true);
                final AuthResult authResult = new AuthResult(result, true);
                String resultStatus = authResult.getResultStatus();
                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UiUtil.showLongToast(UserInfoActivity.this, "授权成功");
                            loginDao.openLogin(authResult.getAlipayOpenId(), "支付宝", "ali", AppUtil.getUserId(UserInfoActivity.this));
                            showProgress(true);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 其他状态值则为授权失败
                            UiUtil.showLongToast(UserInfoActivity.this, "授权失败");
                        }
                    });
                }
            }
        };
        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }
}
