package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.runer.liabary.util.UiUtil;
import com.runer.liabary.widget.ColorTextView;
import com.runer.toumai.R;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.FaqBean;
import com.runer.toumai.bean.NowPriceBean;
import com.runer.toumai.ui.activity.RulesCheckActivity;

import java.util.ArrayList;
import java.util.List;

import test.jinesh.captchaimageviewlib.CaptchaImageView;

/**
 * Created by szhua on 2017/7/26/026.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * TrusteeshipDialog
 验证码输入dialog ；
 */

public class CodeDialog extends Dialog {
    public CodeDialog(@NonNull Context context) {
        super(context);
    }

    public CodeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected CodeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static CodeDialog show(final Context context , final CodeSuccessCallBack codeSuccessCallBack) {
        final CodeDialog dialog = new CodeDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.code_veriyf_dialog);
        View close =dialog.findViewById(R.id.delete);
        CaptchaImageView captchaImageView = (CaptchaImageView) dialog.findViewById(R.id.image);
        EditText editText = (EditText) dialog.findViewById(R.id.code_input);
        View changeBt =dialog.findViewById(R.id.chage_code_bt);
        View submitBt =dialog.findViewById(R.id.submit);
        captchaImageView.setIsDotNeeded(true);

        changeBt.setOnClickListener(view -> {
            captchaImageView.regenerate();
        });

        submitBt.setOnClickListener(view -> {
            String inputString =editText.getText().toString() ;
            String codeCache =captchaImageView.getCaptchaCode() ;
            if (TextUtils.isEmpty(inputString)){
                UiUtil.showLongToast(context,"请输入验证码！");
                return;
            }
            if (!inputString.equals(codeCache)){
                UiUtil.showLongToast(context,"验证码不正确！");
                return;
            }
            codeSuccessCallBack.success(codeCache);
        });

        close.setOnClickListener(view -> {
            dialog.dismiss();
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


    public interface  CodeSuccessCallBack{
        void success (String codeVerify ) ;
    }

}
