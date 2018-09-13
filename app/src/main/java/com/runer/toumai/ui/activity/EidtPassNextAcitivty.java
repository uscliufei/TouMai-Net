package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.dao.LoginDao;
import com.runer.toumai.util.AppUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EidtPassNextAcitivty extends BaseActivity {

    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.comfirm_pass)
    EditText comfirmPass;
    @InjectView(R.id.finish_bt)
    TextView finishBt;


    private String pass ;
    private String confirmPass ;


    private LoginDao loginDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eidt_pass_next_acitivty);
        ButterKnife.inject(this);

        loginDao =new LoginDao(this,this) ;

        finishBt.setOnClickListener(v -> {
            pass =password.getText().toString() ;
            confirmPass = comfirmPass.getText().toString() ;

            if (TextUtils.isEmpty(pass)){
                UiUtil.showLongToast(this,"请输入密码");
                return;
            }
            if (pass.length()>20||pass.length()<6){
                UiUtil.showLongToast(this,getString(R.string.pass_reg));
                return;
            }
            if (!confirmPass.equals(pass)){
                UiUtil.showLongToast(this,"两次输入的密码不同！");
                return;
            }
            loginDao.editPass(pass, AppUtil.getUserId(this));
            showProgressWithMsg(true,"正在更改用户密码！");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("修改密码");
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.editPass) {
            UiUtil.showLongToast(this, "修改密码成功");
            finish();
        }
    }
}
