package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.base.Constant;

/**
 * Created by szhua on 2017/7/26/026.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * WithDrawCashDialog
 */

public class WithDrawCashDialog extends Dialog {

    public WithDrawCashDialog(@NonNull Context context) {
        super(context);
    }
    public WithDrawCashDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected WithDrawCashDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static WithDrawCashDialog show(final Context context , final OnWithDrawCashCommitLisetner onWithDrawCashCommitLisetner) {
        final WithDrawCashDialog dialog = new WithDrawCashDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.with_draw_cash_layout);
        View close =dialog.findViewById(R.id.delete);

        final EditText  priceedit = (EditText) dialog.findViewById(R.id.edit);
        final EditText with_draw_account = (EditText) dialog.findViewById(R.id.with_draw_account);
        final RadioButton radioButtonAli = (RadioButton) dialog.findViewById(R.id.radio_ali);
        final  EditText nickName = (EditText) dialog.findViewById(R.id.nick_name);
        final RadioGroup withdrawGro = (RadioGroup) dialog.findViewById(R.id.radios);
        final TextView receive_account = (TextView) dialog.findViewById(R.id.receive_account);
        final TextView receive_name = (TextView) dialog.findViewById(R.id.receive_name);
        final  TextView wechat_tip= (TextView) dialog.findViewById(R.id.wechat_tip);
        RadioButton radWechat = (RadioButton) dialog.findViewById(R.id.radio_wechat);
        radioButtonAli.setChecked(true);

        final View commitBt =dialog.findViewById(R.id.submit);
        //如果用户选择“支付宝提现”，则下方两行输入框分别为“收款账号”-“请输入支付宝账户/手机号码”和“账号姓名”-“请填写真实姓名以备核对”
        //如果用户选择“微信提现”，则下方不用显示输入框，显示一段提示文字：“提示：微信提现仅支持通过微信充值等额金额的提现，若有通过支付宝充值的金额或竞价收益，建议使用支付宝提现。”
        withdrawGro.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.radio_ali :
                    with_draw_account.setVisibility(View.VISIBLE);
                    with_draw_account.setHint("请输入支付宝账户/手机号码");
                    priceedit.setVisibility(View.VISIBLE);
                    priceedit.setHint("请填写真实姓名以备核");
                    wechat_tip.setVisibility(View.GONE);
                    nickName.setVisibility(View.VISIBLE);
                    //暂定：TODO
                    receive_account.setVisibility(View.VISIBLE);
                    commitBt.setVisibility(View.VISIBLE);
                    receive_name.setVisibility(View.VISIBLE);
                    break;
                case R.id.radio_wechat:
                    with_draw_account.setVisibility(View.GONE);
                    priceedit.setVisibility(View.GONE);
                    receive_account.setVisibility(View.GONE);
                    nickName.setVisibility(View.GONE);
                    receive_name.setVisibility(View.GONE);
                    wechat_tip.setVisibility(View.VISIBLE);
                    commitBt.setVisibility(View.GONE);
                    break;
            }
        });



        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(priceedit.getText().toString())){
                    UiUtil.showLongToast(context,"请输入提现金额");
                    return;
                }
                if(TextUtils.isEmpty(with_draw_account.getText().toString())){
                    UiUtil.showLongToast(context,"请输入提现的账号");
                    return;
                }

                if (Double.parseDouble(priceedit.getText().toString())==0){
                    UiUtil.showLongToast(context,"提现金额为空");
                    return;
                }
                if(TextUtils.isEmpty(nickName.getText().toString())){
                    UiUtil.showLongToast(context,"未防止账号不慎填错造成不必要的麻烦和损失，请填写账户昵称以备核对");
                    return;
                }
                if(radioButtonAli.isChecked()){
                    onWithDrawCashCommitLisetner.onCommit(priceedit.getText().toString(), Constant.ALI_DRAW_TYPE,with_draw_account.getText().toString(),nickName.getText().toString());
                }else{
                    onWithDrawCashCommitLisetner.onCommit(priceedit.getText().toString(), Constant.WECHAT_DRAW_TYPE,with_draw_account.getText().toString(),nickName.getText().toString());
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }

    public interface  OnWithDrawCashCommitLisetner{
        void onCommit(String cash ,String type ,String account,String nick);
    }


}
