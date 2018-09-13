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
import com.runer.toumai.widget.CodeButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForgetPassAcitvity extends BaseActivity {
    @InjectView(R.id.user_email)
    EditText userEmail;
    @InjectView(R.id.eidt_code)
    EditText eidtCode;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.register_bt)
    TextView registerBt;
    private String code;
    private String  email;

    private LoginDao loginDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_acitvity);
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
                loginDao.emailReg(email,code) ;
                showProgress(true);
            }
        });


    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode== RequestCode.email_code_add){
            UiUtil.showLongToast(this,"验证码已发送");
            getCodeBt.startNumCode();
        }else if (requestCode==RequestCode.emailReg){
            UiUtil.showLongToast(this,"验证成功");
            transUi(EidtPassNextAcitivty.class,null);
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
        setTitle("修改密码");
    }
}
