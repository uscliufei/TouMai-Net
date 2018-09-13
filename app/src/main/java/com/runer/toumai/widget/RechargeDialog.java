package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
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

import com.runer.liabary.dialog.ProgressHUD;
import com.runer.toumai.R;

/**
 * Created by szhua on 2017/7/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * RechargeDialog
 */

public class RechargeDialog extends Dialog {
    public RechargeDialog(@NonNull Context context) {
        super(context);
    }

    public RechargeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected RechargeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public  interface  OnCommitClickListener{
        void onCommitClick(String editStr ,String payType );
    }

    private OnCommitClickListener onCommitClickListener ;

    public void setOnCommitClickListener(OnCommitClickListener onCommitClickListener) {
        this.onCommitClickListener = onCommitClickListener;
    }

    static  String payType ="ali" ;
    public static RechargeDialog show(Context context , final OnCommitClickListener onCommitListener) {
        final RechargeDialog dialog = new RechargeDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.recharge_dialog_layout);
        final EditText editText = (EditText) dialog.findViewById(R.id.edit);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radios);
        RadioButton aliRadio = (RadioButton) dialog.findViewById(R.id.radio_ali);
        aliRadio.setChecked(true);
        View close =dialog.findViewById(R.id.delete);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.radio_ali){
                    payType = "ali" ;
                }else{
                    payType ="wechat";
                }
            }
        });
        View commitBt =dialog.findViewById(R.id.submit);

        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCommitListener!=null){
                    onCommitListener.onCommitClick(editText.getText().toString(),payType);
                }
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
}
