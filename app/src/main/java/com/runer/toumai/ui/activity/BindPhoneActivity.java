package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.dao.LoginDao;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.CodeButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity {

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
    @InjectView(R.id.edit_phone_num)
    EditText editPhoneNum;
    @InjectView(R.id.eidt_code)
    EditText eidtCode;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.login_bt)
    TextView loginBt;

    private LoginDao loginDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.inject(this);
        loginDao =new LoginDao(this,this) ;
    }
    String phoneNum;
    String code;

    private boolean checkInput() {
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
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("更换手机");
    }
    @OnClick({R.id.get_code_bt, R.id.login_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_bt:
                String phone =editPhoneNum.getText().toString() ;
                if(TextUtils.isEmpty(phone)){
                    UiUtil.showLongToast(this,"请输入手机号");
                    return;
                }
                if(!UiUtil.isValidMobileNo(phone)){
                    UiUtil.showLongToast(this,"手机号格式不正确");
                    return;
                }
                loginDao.getCode(phone,"bind");
                showProgress(true);
                break;
            case R.id.login_bt:
               if(checkInput()){
                   loginDao.bindPhone(AppUtil.getUserId(this),phoneNum,code);
                   showProgress(true);
               }
                break;
        }
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==RequestCode.SEND_CODE){
            UiUtil.showLongToast(this,"验证码已发送至"+editPhoneNum.getText().toString());
            getCodeBt.startNumCode();
        }else  if(requestCode==RequestCode.VALID){
        }else if(requestCode==RequestCode.BIND_PHONE){
            UiUtil.showLongToast(this,"绑定成功");
            finish();
        }
    }
}
