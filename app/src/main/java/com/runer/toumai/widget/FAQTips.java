package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.runer.toumai.adapter.FaqAdapter;
import com.runer.toumai.bean.FaqBean;
import com.runer.toumai.bean.NowPriceBean;
import com.runer.toumai.ui.activity.RulesCheckActivity;

import java.util.List;

/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * FAQTips
 */

public class FAQTips extends Dialog {

    public FAQTips(@NonNull Context context) {
        super(context);
    }

    public FAQTips(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected FAQTips(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static FAQTips show(final Context context, List<FaqBean> faqBeanList) {
        final FAQTips dialog = new FAQTips(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.faq_layout);
        View close =dialog.findViewById(R.id.delete);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.faq_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FaqAdapter faqAdapter =new FaqAdapter(faqBeanList);
        recyclerView.setAdapter(faqAdapter);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }
    /*price	是	decimal	所出价格
all_price	否	int	支付溢价还是总价：1支付总价
is_auto	否	int	是否为托管出价:0否，1是
anonymous	否	int	匿名出价，默认为0，匿名出价为1*/
    public interface  OnDarkCommitListener{
        void onCommit(String pirce ,String anonymous,String allPrice) ;
    }

}
