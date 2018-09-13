package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
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
 * 加一手出价
 */
public class AddOneHandDialog extends Dialog {
    public AddOneHandDialog(@NonNull Context context) {
        super(context);
    }
    public AddOneHandDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected AddOneHandDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static AddOneHandDialog show(final Context context, final NowPriceBean nowPriceBean , final OnOneHandCommitListener oncommitListener , final int type) {
        final AddOneHandDialog dialog = new AddOneHandDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.add_one_hand_dialog);
        TextView  tips = (TextView) dialog.findViewById(R.id.tips);
        View faqBt =dialog.findViewById(R.id.faq_bt);
        View nimingFaq =dialog.findViewById(R.id.niming_faq);
        final  TextView nowProce = (TextView) dialog.findViewById(R.id.price);
        RadioGroup priceTypes = (RadioGroup) dialog.findViewById(R.id.pay_types);
        final CheckBox anonymous_box = (CheckBox) dialog.findViewById(R.id.anonymous_box);
        final RadioButton allPriceRadio = (RadioButton) dialog.findViewById(R.id.all_price);
        allPriceRadio.setChecked(true);
        RadioButton overPriceRadio = (RadioButton) dialog.findViewById(R.id.over_price);
        //若是降价的话；
        if(type== Constant.JIANG_JIA_STATE){
            overPriceRadio.setVisibility(View.GONE);
        }else{
            overPriceRadio.setVisibility(View.VISIBLE);
        }

        if(ToumaiApplication.isFaq()){
            faqBt.setVisibility(View.VISIBLE);
            faqBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type== Constant.JIANG_JIA_STATE) {
                        FaqBean faqBean = new FaqBean();
                        faqBean.setQuestion("“降价”阶段为什么只能“全额支付”而不能“溢价支付”？");
                        faqBean.setAnswer(context.getString(R.string.jiang));
                        List<FaqBean> faqBeens = new ArrayList<>();
                        faqBeens.add(faqBean);
                        FAQTips.show(context, faqBeens);
                    }else{
                        FaqBean faqBean = new FaqBean();
                        faqBean.setQuestion("“全款支付”和“溢价支付”的优缺点各是什么？");
                        faqBean.setAnswer(context.getString(R.string.quankuan_yijia));
                        List<FaqBean> faqBeens = new ArrayList<>();
                        faqBeens.add(faqBean);
                        FAQTips.show(context, faqBeens);
                    }
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
        tips.setText(String.format(context.getString(R.string.price_tips_add_one_hand),nowPriceBean.getMake_price()));
        nowProce.setText("￥"+nowPriceBean.getNext_price());
        final String nextPrice =nowPriceBean.getNext_price() ;
        final String overPrice  = String.valueOf(Arith.sub(Double.parseDouble(nowPriceBean.getNext_price()),Double.parseDouble(nowPriceBean.getPrice())));
        final CheckBox ruleRido = (CheckBox) dialog.findViewById(R.id.rule_box);
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
        priceTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.all_price:
                        nowProce.setText("￥"+nextPrice);
                        break;
                    case R.id.over_price:
                        nowProce.setText("￥"+ overPrice);
                        break;
                }
            }
        });
        View close =dialog.findViewById(R.id.delete);
        View submit =dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ruleRido.isChecked()){
                    UiUtil.showLongToast(context,"请先同意交易规则");
                    return;
                }
                final String  oneHandProce =  allPriceRadio.isChecked()?nextPrice:nextPrice ;
                final String  all_price = allPriceRadio.isChecked()?"1":"0";
                final String  is_auto ="0" ;
                final String anonymous = anonymous_box.isChecked()?"1":"0";
                if(oncommitListener!=null){
                    oncommitListener.onCommit(nextPrice,all_price,is_auto,anonymous);
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
    public interface  OnOneHandCommitListener{
        void onCommit(String pirce ,String all_price ,String is_auto ,String anonymous) ;
    }

}
