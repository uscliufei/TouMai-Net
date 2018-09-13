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

public class BindEmailActivity extends BaseActivity {

    @InjectView(R.id.user_email)
    EditText userEmail;
    @InjectView(R.id.eidt_code)
    EditText eidtCode;
    @InjectView(R.id.get_code_bt)
    CodeButton getCodeBt;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.comfirm_pass)
    EditText comfirmPass;
    @InjectView(R.id.register_bt)
    TextView registerBt;


    private LoginDao loginDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_email);
        ButterKnife.inject(this);
        loginDao =new LoginDao(this,this) ;

        getCodeBt.setOnClickListener(v -> {
         if (getCodeCheck()){
          loginDao.getEmalCode(email, Constant.EMAIL_EDIT_TYPE);
          showProgress(true);
         }
        });
        registerBt.setOnClickListener(v -> {
           if (checkInput()){
               loginDao.bindEmail(AppUtil.getUserId(this),code,email,pass);
               showProgress(true);
           }
        });
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode== RequestCode.email_code_add){
            UiUtil.showLongToast(this,"以获取验证码！请前往邮箱进行查看！");
            getCodeBt.startNumCode();
        }else if (requestCode==RequestCode.bind_email){
            UiUtil.showLongToast(this,"邮箱绑定成功");
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("绑定邮箱");
    }

    private String email ;
    private String code ;
    private String pass ;

    private boolean checkInput(){
        if (!getCodeCheck()){
            return  false ;
        }
        code =eidtCode.getText().toString() ;
        pass =password.getText().toString() ;
        String confirmPass =comfirmPass.getText().toString() ;

        if (TextUtils.isEmpty(code)){
            UiUtil.showLongToast(this,"请输入验证码");
            return  false ;
        }
        if (TextUtils.isEmpty(pass)){
            UiUtil.showLongToast(this,"请输入密码");
            return  false ;
        }
        if (TextUtils.isEmpty(confirmPass)){
            UiUtil.showLongToast(this,"请再次输入密码");
            return false ;
        }
        if (!confirmPass.equals(pass)){
            UiUtil.showLongToast(this,"两次输入的密码不一致");
            return  false ;
        }
        if ((pass.length()<6)||(pass.length()>20)){
            UiUtil.showLongToast(this,"请输入6-20位任意字符密码");
            return false ;
        }
        return  true ;
    }

    private boolean getCodeCheck(){
        email =userEmail.getText().toString() ;
        if (TextUtils.isEmpty(email)){
            UiUtil.showLongToast(this,"请输入邮箱");
            return  false ;
        }
        if (!RegexUtils.isEmail(email)){
            UiUtil.showLongToast(this,"输入的邮箱格式不正确！");
            return  false ;
        }

        return  true ;
    }
}
