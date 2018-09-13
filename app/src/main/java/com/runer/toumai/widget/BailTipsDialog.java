package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.ui.activity.RulesCheckActivity;

/**
 * Created by szhua on 2017/7/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * NormalTipsDialog
 */
//保证金tips
public class BailTipsDialog extends Dialog {

    public BailTipsDialog(@NonNull Context context) {
        super(context);
    }
    public BailTipsDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected BailTipsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static BailTipsDialog show(final Context context, String money , View.OnClickListener onCommitListener) {
        final BailTipsDialog dialog = new BailTipsDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.nor_dialog_layout);
        TextView content = (TextView) dialog.findViewById(R.id.content);
        TextView  commitBt = (TextView) dialog.findViewById(R.id.submit);
        View delete =dialog.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        content.setText(new String(context.getString(R.string.baozhengjin,money)));
        Link link = new Link(context.getString(R.string.maijiabaozhengjin))
                .setTextColor(Color.parseColor("#ea633d"))
                .setHighlightAlpha(0.0f)
                //.setTextColorOfHighlightedLink(Color.parseColor("#ea633d")) // optional, defaults to holo blue
                .setUnderlined(false)                                       // optional, defaults to true
                .setBold(false)                                              // optional, defaults to false
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        Intent intent = new Intent(context, RulesCheckActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", "23");
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                      // dialog.dismiss();
                    }
                });
        LinkBuilder.on(content)
                .addLink(link)
                .build();

        commitBt.setOnClickListener(onCommitListener);
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
