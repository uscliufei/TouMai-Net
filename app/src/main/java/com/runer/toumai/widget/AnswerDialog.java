package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.ui.activity.RulesCheckActivity;
import com.runer.toumai.util.AppUtil;

/**
 * Created by szhua on 2017/9/11/011.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AnswerDialog
 */

public class AnswerDialog extends Dialog {

    public AnswerDialog(@NonNull Context context) {
        super(context);
    }
    public AnswerDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected AnswerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static AnswerDialog show(final Context context , String question, final OnAnwserClickListener onAnwserClickListener ) {
        final AnswerDialog dialog = new AnswerDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.answer_dialog);
        TextView commitBt = (TextView) dialog.findViewById(R.id.submit);
        View delete =dialog.findViewById(R.id.delete);
        TextView  questionTv = (TextView) dialog.findViewById(R.id.question);
        final EditText  contentEt = (EditText) dialog.findViewById(R.id.content);
        questionTv.setText(question);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(contentEt.getText().toString())){
                    UiUtil.showLongToast(context,"请输入您回复的内容");
                    return;
                }
                onAnwserClickListener.onCommitClick(contentEt.getText().toString());
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

    public interface  OnAnwserClickListener{
        void onCommitClick(String content );
    }
}
