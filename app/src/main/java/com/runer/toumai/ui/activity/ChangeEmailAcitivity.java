package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.runer.liabary.util.RegexUtils;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.Constant;
import com.runer.toumai.dao.LoginDao;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.CodeButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChangeEmailAcitivity extends BaseActivity {

    @InjectView(R.id.user_email)
    EditText userEmail;
    @InjectView(R.id.eidt_code)
    EditText eidtCode;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.register_bt)
    TextView registerBt;


    private LoginDao loginDao;

    private String email ;
    private String code ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        ButterKnife.inject(this);

        loginDao =new LoginDao(this,this) ;


        getCodeBt.setOnClickListener(v -> {
            if (chekInputCode()){
                loginDao.getEmalCode(email, Constant.EMAIL_EDIT_TYPE);
                showProgress(true);
            }
        });
        registerBt.setOnClickListener(v -> {
            if (checkAll()){
              loginDao.eidtEmail(AppUtil.getUserId(this),email,code);
              showProgress(true);
            }
        });

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode== RequestCode.email_code_add){
           getCodeBt.startNumCode();
        }else if (requestCode==RequestCode.eidtEmail){
            UiUtil.showLongToast(this,"用户邮箱修改成功");
            finish();
        }
    }

    private boolean checkAll(){
        if (!chekInputCode()){
            return  false ;
        }
        code =eidtCode.getText().toString() ;
        if (TextUtils.isEmpty(code)){
            UiUtil.showLongToast(this,"请输入验证码");
            return  false ;
        }
        return  true ;
    }

    private boolean chekInputCode(){

        email =userEmail.getText().toString() ;
        if (TextUtils.isEmpty(email)){
            UiUtil.showLongToast(this,"请输入邮箱");
            return  false ;
        }
        if(!RegexUtils.isEmail(email)){
            UiUtil.showLongToast(this,"邮箱格式不正确");
            return  false ;
        }
        return  true ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("更换邮箱");
    }
}
