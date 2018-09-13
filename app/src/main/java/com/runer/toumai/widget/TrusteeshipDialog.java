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

/**
 * Created by szhua on 2017/7/26/026.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * TrusteeshipDialog
 托管dialog
 */

public class TrusteeshipDialog extends Dialog {
    public TrusteeshipDialog(@NonNull Context context) {
        super(context);
    }

    public TrusteeshipDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected TrusteeshipDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }



    public static TrusteeshipDialog show(final Context context , final NowPriceBean nowPriceBean , final TrusteeshipClickListener trustelistener) {
        final TrusteeshipDialog dialog = new TrusteeshipDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.trusteeship_layout);
        View close =dialog.findViewById(R.id.delete);
        View faqBt =dialog.findViewById(R.id.faq_bt);
        View nimingFaq =dialog.findViewById(R.id.niming_faq);
        final CheckBox anonymousBox = (CheckBox) dialog.findViewById(R.id.anonymous_box);
        final EditText priceInput = (EditText) dialog.findViewById(R.id.eidt_input);
        View submitBt =dialog.findViewById(R.id.submit);

        final RadioButton allPriceRadio = (RadioButton) dialog.findViewById(R.id.all_price);
        allPriceRadio.setChecked(true);
        final RadioButton overPriceRadio = (RadioButton) dialog.findViewById(R.id.over_price);

         final AppCompatCheckBox ruleRido = (AppCompatCheckBox) dialog.findViewById(R.id.rules_box);
        ColorTextView rules = (ColorTextView) dialog.findViewById(R.id.rules);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, RulesCheckActivity.class);
                Bundle bundle =new Bundle() ;
                bundle.putString("id","5");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        if(ToumaiApplication.isFaq()){
            faqBt.setVisibility(View.VISIBLE);
            faqBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqBean faqBean =new FaqBean() ;
                    faqBean.setQuestion("“全款支付”和“溢价支付”的优缺点各是什么？");
                    faqBean.setAnswer(context.getString(R.string.quankuan_yijia));
                    List<FaqBean> faqBeens =new ArrayList<>();
                    faqBeens.add(faqBean) ;
                    FAQTips.show(context,faqBeens);
                }
            });
            nimingFaq.setVisibility(View.VISIBLE);
            nimingFaq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqBean faqBean =new FaqBean() ;
                    faqBean.setQuestion("“匿名出价”的意义是什么？");
                    faqBean.setAnswer(context.getString(R.string.niming_yiyi));
                    List<FaqBean> faqBeens =new ArrayList<>();
                    faqBeens.add(faqBean) ;
                    FAQTips.show(context,faqBeens);
                }
            });
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(priceInput.getText().toString())){
                    UiUtil.showLongToast(v.getContext(),"请输入价格");
                 //   dialog.dismiss();
                    return  ;
                }
                if(!ruleRido.isChecked()){
                    UiUtil.showLongToast(context,"请同意投买网交易规则");
                    return;
                }
                if(Double.parseDouble(nowPriceBean.getNext_price())>Double.parseDouble(priceInput.getText().toString())){
                    UiUtil.showLongToast(v.getContext(),"输入的价格最低为￥"+nowPriceBean.getNext_price());
                 //   dialog.dismiss();
                    return;
                }
                String all_price;
                if (allPriceRadio.isChecked()) {
                    all_price = "1";
                }else {
                    all_price = "0";
                }
                if (overPriceRadio.isChecked()) {
                    all_price = "0";
                }else{
                    all_price = "1";
                }
                if(trustelistener!=null){
                    trustelistener.onCommit(priceInput.getText().toString(),all_price,anonymousBox.isChecked()?"1":"0");
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




    public interface  TrusteeshipClickListener{
        void onCommit(String price,String all_price ,String anonymous);
    }

}
