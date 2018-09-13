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
import android.widget.TextView;

import com.runer.toumai.R;

/**
 * Created by szhua on 2017/7/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * NormalTipsDialog
 */

public class NormalTipsWith2ButtonDialog extends Dialog {

    public NormalTipsWith2ButtonDialog(@NonNull Context context) {
        super(context);
    }
    public NormalTipsWith2ButtonDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected NormalTipsWith2ButtonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static NormalTipsWith2ButtonDialog show(Context context, String titleStr, String subtitleStr, String contentStr, String commitStr , String cancleStr ,Nor2BT_Listener nor2BT_listener) {
        final NormalTipsWith2ButtonDialog dialog = new NormalTipsWith2ButtonDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.nor_dialog_2_layout);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView subTitle = (TextView) dialog.findViewById(R.id.subtitle);
        TextView content = (TextView) dialog.findViewById(R.id.content);
        TextView  commitBt = (TextView) dialog.findViewById(R.id.submit);
        TextView   quitBt = (TextView) dialog.findViewById(R.id.quit);
        View delete =dialog.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(!TextUtils.isEmpty(titleStr)){
            title.setText(titleStr);
        }
        if(!TextUtils.isEmpty(subtitleStr)){
            subTitle.setText(subtitleStr);
        }
        if(!TextUtils.isEmpty(contentStr)){
            content.setText(contentStr);
        }
        if(!TextUtils.isEmpty(commitStr)){
            commitBt.setText(commitStr);
        }
        if (!TextUtils.isEmpty(cancleStr)){
            quitBt.setText(cancleStr);
        }

        commitBt.setOnClickListener(view -> {
            nor2BT_listener.commit(view);
        });
        quitBt.setOnClickListener(view -> {
            nor2BT_listener.cancle(view);
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

   public  interface Nor2BT_Listener{
        void  commit(View view) ;
        void cancle(View view) ;
    }



}
