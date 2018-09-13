package com.runer.toumai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.orhanobut.logger.Logger;
import com.runer.liabary.util.RegexUtils;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.Constant;
import com.runer.toumai.dao.LoginDao;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.util.AuthResult;
import com.runer.toumai.widget.CodeButton;
import com.runer.toumai.widget.CodeDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity implements View.OnClickListener, TagAliasCallback {
    @InjectView(R.id.edit_phone_num)
    EditText editPhoneNum;
    @InjectView(R.id.eidt_code)
    EditText eidtCode;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.login_bt)
    TextView loginBt;
    @InjectView(R.id.wechat_login)
    TextView wechatLogin;
    @InjectView(R.id.qq_login)
    TextView qqLogin;
    @InjectView(R.id.sina_login)
    TextView sinaLogin;
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.ali_login)
    TextView aliLogin;
    @InjectView(R.id.rules_check)
    CheckBox rulesCheck;
    @InjectView(R.id.rules)
    TextView rules;
    @InjectView(R.id.phone_code_login_container)
    LinearLayout phoneCodeLoginContainer;
    @InjectView(R.id.email_input)
    EditText emailInput;
    @InjectView(R.id.email_pass_input)
    EditText emailPassInput;
    @InjectView(R.id.email_pass_login_container)
    LinearLayout emailPassLoginContainer;
    @InjectView(R.id.register_bt)
    TextView registerBt;
    @InjectView(R.id.change_login_way)
    TextView changeLoginWay;
    private LoginDao loginDao;
    private CodeDialog codeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginDao = new LoginDao(this, this);
        wechatLogin.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        sinaLogin.setOnClickListener(this);
        aliLogin.setOnClickListener(this);
        registerBt.setOnClickListener(view -> {
            transUi(ResigterAcitivty.class,null);
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "4");
                transUi(RulesCheckActivity.class, bundle);
            }
        });
        changeLoginWay.setOnClickListener(view -> {
            //切换为邮箱密码登录
            if ("0".equals((String) changeLoginWay.getTag())){
              phoneCodeLoginContainer.setVisibility(View.GONE);
              emailPassLoginContainer.setVisibility(View.VISIBLE);
              changeLoginWay.setTag("1");
              changeLoginWay.setText("使用手机验证码登录");
             //切换为验证码的登录
            }else{
              phoneCodeLoginContainer.setVisibility(View.VISIBLE);
              emailPassLoginContainer.setVisibility(View.GONE);
              changeLoginWay.setTag("0");
              changeLoginWay.setText("使用邮箱密码登录");
            }
        });
    }

    String phoneNum;
    String code;

    String email ;
    String pass ;

    private boolean checkInput() {
        //手机验证码登录
        if ("0".equals((String)changeLoginWay.getTag())){
            phoneNum = editPhoneNum.getText().toString();
            code = eidtCode.getText().toString();
            if (TextUtils.isEmpty(phoneNum)) {
                UiUtil.showLongToast(this, "手机号码为空");
                return false;
            }
            if (!UiUtil.isValidMobileNo(phoneNum)) {
                UiUtil.showLongToast(this, "手机号格式不正确");
                return false;
            }
            if (TextUtils.isEmpty(code)) {
                UiUtil.showLongToast(this, "验证码为空");
                return false;
            }
        //邮箱进行登录
        }else{

            email =emailInput.getText().toString() ;
            pass =emailPassInput.getText().toString() ;
            if (TextUtils.isEmpty(email)){
                UiUtil.showLongToast(this,"请输入邮箱");
                return  false ;
            }
            if (!RegexUtils.isEmail(email)){
                UiUtil.showLongToast(this,"请输入正确的邮箱");
                return  false ;
            }
            if (TextUtils.isEmpty(pass)){
                UiUtil.showLongToast(this,"密码不能为空");
                return  false ;
            }
            if (pass.length()>20||pass.length()<6){
                UiUtil.showLongToast(this,getString(R.string.pass_reg));
                return  false ;
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("登录");
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.homeActivity != null) {
                    HomeActivity.homeActivity.mTabHost.setCurrentTab(0);
                }
                finish();
            }
        });
    }

    @OnClick({R.id.get_code_bt, R.id.login_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_bt:
                phoneNum = editPhoneNum.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    UiUtil.showLongToast(this, "手机号码为空");
                    return;
                }
                if (!UiUtil.isValidMobileNo(phoneNum)) {
                    UiUtil.showLongToast(this, "手机号格式不正确");
                    return;
                }
                if (!rulesCheck.isChecked()) {
                    UiUtil.showLongToast(LoginActivity.this, "请阅读《软件许可使用协议（投买网用户使用协议）》");
                    return;
                }
                /**
                 * 图形验证码；
                 */
                codeDialog = CodeDialog.show(this, codeVerify -> {
                    codeDialog.dismiss();
                    loginDao.getCode(phoneNum, "login");
                    showProgress(true);
                });
                break;
            case R.id.login_bt:
                AppUtil.setUserId(this, "0");
                if ("0".equals((String)changeLoginWay.getTag())){
                 if (checkInput()) {
                    loginDao.login(phoneNum, code, "");
                    showProgressWithMsg(true, "正在登录..");
                 }
                }else{
                    if (checkInput()){
                        loginDao.emailLogin(email,pass);
                        showProgressWithMsg(true,"正在登录..");
                    }
                }
                break;
        }
    }

    private String user_id;

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.SEND_CODE) {
            getCodeBt.startNumCode();
            UiUtil.showLongToast(this, "验证码已发送至" + phoneNum);
        } else if (requestCode == RequestCode.LOGIN) {
            user_id = loginDao.getUserId();
            if (!TextUtils.isEmpty(user_id)) {
                AppUtil.setUserId(this, user_id);
                UiUtil.showLongToast(this, "登录成功");
                JPushInterface.setAlias(this, user_id, this);
                finish();
            }
        } else if (requestCode == RequestCode.THIRD_LOGIN) {
            user_id = loginDao.getUserId();
            if (!TextUtils.isEmpty(user_id)) {
                AppUtil.setUserId(this, user_id);
                JPushInterface.setAlias(this, user_id, this);
                UiUtil.showLongToast(this, "登录成功");
                finish();
            }
        }else if (requestCode==RequestCode.user_login1){
            user_id =loginDao.getUserId() ;
            if (!TextUtils.isEmpty(user_id)){
                AppUtil.setUserId(this, user_id);
                JPushInterface.setAlias(this, user_id, this);
                UiUtil.showLongToast(this, "登录成功");
                finish();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v == qqLogin) {
            if (UMShareAPI.get(this).isAuthorize(
                    this, SHARE_MEDIA.QQ)) {
                UMShareAPI.get(getApplicationContext()).deleteOauth(
                        this, SHARE_MEDIA.QQ, umAuthListener);
            }
            UMShareAPI.get(getApplicationContext()).getPlatformInfo(
                    this, SHARE_MEDIA.QQ, umAuthListener);
        } else if (v == wechatLogin) {
            if (UMShareAPI.get(this).isAuthorize(
                    this, SHARE_MEDIA.WEIXIN)) {
                UMShareAPI.get(getApplicationContext()).deleteOauth(
                        this, SHARE_MEDIA.WEIXIN, umAuthListener);
            }
            UMShareAPI.get(getApplicationContext()).getPlatformInfo(
                    this, SHARE_MEDIA.WEIXIN, umAuthListener);
        } else if (v == sinaLogin) {
            if (UMShareAPI.get(this).isAuthorize(
                    this, SHARE_MEDIA.SINA)) {
                UMShareAPI.get(getApplicationContext()).deleteOauth(
                        this, SHARE_MEDIA.SINA, umAuthListener);
            }
            UMShareAPI.get(getApplicationContext()).getPlatformInfo(
                    this, SHARE_MEDIA.SINA, umAuthListener);
        } else if (v == aliLogin) {
            aliLogin();
        }
    }

    //第三方登录授权回调；
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            UiUtil.showLongToast(LoginActivity.this, "正在第三方授权");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Logger.d(data);
            switch (platform) {
                case WEIXIN:
                    if (data != null) {
                        String openid = data.get("uid");
                        String nickName = data.get("name");
                        loginDao.openLogin(openid, TextUtils.isEmpty(nickName) ? "微信" : nickName, "wechat", "");
                        showProgressWithMsg(true, "授权成功，正在登录");
                    }
                    break;
                case QQ:
                    if (data != null) {
                        String openid = data.get("unionid");
                        String nickName = data.get("name");
                        String openid2 = data.get("uid");
                        loginDao.openLogin(openid, TextUtils.isEmpty(nickName) ? "QQ" : nickName, "qq", "", openid2);
                        showProgressWithMsg(true, "授权成功，正在登录");
                    }
                    break;
                case SINA:
                    /*{refreshToken=2.00976uWG0UM78R5102b43d90GTQTiC, accessToken=2.00976uWG0UM78Rb5f69a7b390FGfoT, name=阿花的阿发, uid=5983083852, expiration=2632552}*/
                    if (data != null) {
                        String openid = data.get("uid");
                        String nickName = data.get("name");
                        loginDao.openLogin(openid, TextUtils.isEmpty(nickName) ? "微博" : nickName, "weibo", "");
                        showProgressWithMsg(true, "授权成功，正在登录");
                    }
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Logger.d(t);
            UiUtil.showLongToast(LoginActivity.this, "第三方授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            UiUtil.showLongToast(LoginActivity.this, "第三方授权取消");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void gotResult(int i, String s, Set<String> set) {
        String logs;
        switch (i) {
            case 0:
                logs = "设置别名成功";
                Logger.d(logs);
                break;
            case 6002:
                logs = "设置别名失败，60秒后重新尝试.";
                Logger.d(logs);
                JPushInterface.setAlias(this, user_id, this);
                break;
            default:
                Logger.d("Failed with errorCode = " + code);
        }
    }
    //支付宝登录
    private void aliLogin() {
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(LoginActivity.this);
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
                            UiUtil.showLongToast(LoginActivity.this, "授权成功");
                            loginDao.openLogin(authResult.getAlipayOpenId(), "支付宝", "ali", "");
                            showProgress(true);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 其他状态值则为授权失败
                            UiUtil.showLongToast(LoginActivity.this, "授权失败");
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
