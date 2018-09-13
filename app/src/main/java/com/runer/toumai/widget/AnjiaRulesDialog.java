package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.runer.toumai.R;
import com.runer.toumai.ui.activity.RulesCheckActivity;
import com.runer.toumai.util.AppUtil;

/**
 * Created by szhua on 2017/7/25/025.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AnjiaRulesDialog
 * 查看使用暗价功能需要的权限;
 */
public class AnjiaRulesDialog extends Dialog {
    public AnjiaRulesDialog(@NonNull Context context) {
        super(context);
    }
    public AnjiaRulesDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected AnjiaRulesDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static AnjiaRulesDialog show(final Context context , boolean isLearn , boolean isMakeUp) {
        final AnjiaRulesDialog dialog = new AnjiaRulesDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.anjia_dialog);
        TextView  commitBt = (TextView) dialog.findViewById(R.id.submit);
        View delete =dialog.findViewById(R.id.delete);
        TextView  darkState = (TextView) dialog.findViewById(R.id.dark_compelte);
        TextView  makeUpText = (TextView) dialog.findViewById(R.id.make_up);
        TextView rules_bt = (TextView) dialog.findViewById(R.id.rules_bt);
        rules_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, RulesCheckActivity.class);
                Bundle bundle =new Bundle() ;
                bundle.putString("id","12");
                bundle.putBoolean("learn", !AppUtil.checkDarkLearn(context));
                intent.putExtras(bundle);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        if(isLearn){
          darkState.setText("(完成)");
          darkState.setTextColor(ContextCompat.getColor(context,R.color.text_color_green));
        }else{
            darkState.setText("(未完成)");
            darkState.setTextColor(ContextCompat.getColor(context,R.color.text_color_red));
        }
        if(isMakeUp){
            makeUpText.setText("(完成)");
            makeUpText.setTextColor(ContextCompat.getColor(context,R.color.text_color_green));
        }else{
            makeUpText.setText("(未完成)");
            makeUpText.setTextColor(ContextCompat.getColor(context,R.color.text_color_red));
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        commitBt.setOnClickListener(new View.OnClickListener() {
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
}
