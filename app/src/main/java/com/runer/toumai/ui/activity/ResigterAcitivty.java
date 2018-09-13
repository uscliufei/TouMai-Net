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

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.runer.toumai.base.Constant.EMAL_REGISTER_CODE_GET_TYPE;

public class ResigterAcitivty extends BaseActivity {

    @InjectView(R.id.user_name)
    EditText userName;
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
    @InjectView(R.id.tologin_bt)
    TextView tologinBt;

    private LoginDao loginDao ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter_acitivty);
        ButterKnife.inject(this);

        loginDao =new LoginDao(this,this) ;


        getCodeBt.setOnClickListener(view -> {
            if (getCodeCheck()){
             loginDao.getEmalCode(email,EMAL_REGISTER_CODE_GET_TYPE);
             showProgress(true);
            }
        });

        tologinBt.setOnClickListener(view -> {
            transUi(LoginActivity.class,null);
        });

        registerBt.setOnClickListener(view -> {
            if (checkInput()){
                loginDao.userRegister(name,code,email,pass);
                showProgress(true);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        setTitle("用户注册");
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode== RequestCode.email_code_add){
           UiUtil.showLongToast(this,"已获取验证码！请前往邮箱进行查看！");
           getCodeBt.startNumCode();
        }else if (requestCode==RequestCode.reg){
            UiUtil.showLongToast(this,"用户注册成功，前往登录界面进行登录");
            transUi(LoginActivity.class,null);
        }
    }

    private String name  ;
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
        name =userName.getText().toString() ;
        email =userEmail.getText().toString() ;
        if (TextUtils.isEmpty(name)){
            UiUtil.showLongToast(this,"用户名不能为空");
            return  false ;
        }
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
