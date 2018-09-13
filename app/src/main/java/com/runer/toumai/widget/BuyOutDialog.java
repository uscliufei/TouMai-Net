package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.runer.liabary.util.Arith;
import com.runer.liabary.util.UiUtil;
import com.runer.liabary.widget.ColorTextView;
import com.runer.toumai.R;
import com.runer.toumai.base.Constant;
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
 * AddOneHandDialog
 * 一口价买断
 */
public class BuyOutDialog extends Dialog {
    public BuyOutDialog(@NonNull Context context) {
        super(context);
    }
    public BuyOutDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected BuyOutDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    public static BuyOutDialog show(final Context context, String buyOutPrice , final OnBuyOutListener oncommitListener ) {
        final BuyOutDialog dialog = new BuyOutDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.buyout_dialog_layout);
        View nimingFaq =dialog.findViewById(R.id.niming_faq);
        final  TextView buyOutPriceTextView = (TextView) dialog.findViewById(R.id.price);
        final CheckBox anonymous_box = (CheckBox) dialog.findViewById(R.id.anonymous_box);
        if(ToumaiApplication.isFaq()){
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
        buyOutPriceTextView.setText("￥"+buyOutPrice);
        final String nextPrice =buyOutPrice;
        final CheckBox ruleRido = (CheckBox) dialog.findViewById(R.id.rule_box);
        ColorTextView rules = (ColorTextView) dialog.findViewById(R.id.rules);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, RulesCheckActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("id","5");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        View close =dialog.findViewById(R.id.delete);
        View submit =dialog.findViewById(R.id.submit);

        final String anonymous = anonymous_box.isChecked()?"1":"0";

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ruleRido.isChecked()){
                    UiUtil.showLongToast(context,"请先同意交易规则");
                    return;
                }
                if(oncommitListener!=null){
                    oncommitListener.onCommit(anonymous);
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
    /*price	是	decimal	所出价格
all_price	否	int	支付溢价还是总价：1支付总价
is_auto	否	int	是否为托管出价:0否，1是
anonymous	否	int	匿名出价，默认为0，匿名出价为1*/
    public interface OnBuyOutListener{
        void onCommit(String anonymous) ;
    }

}
