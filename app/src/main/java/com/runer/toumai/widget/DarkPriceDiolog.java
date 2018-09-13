package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
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
import com.runer.toumai.bean.NowPriceBean;
import com.runer.toumai.ui.activity.RulesCheckActivity;

/**
 * Created by szhua on 2017/8/26/026.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * DarkPriceDiolog
 */

public class DarkPriceDiolog extends Dialog {
    public DarkPriceDiolog(@NonNull Context context) {
        super(context);
    }

    public DarkPriceDiolog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected DarkPriceDiolog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static DarkPriceDiolog show(final Context context, final NowPriceBean nowPriceBean , final OnDarkCommitListener oncommitListener ) {
        final DarkPriceDiolog dialog = new DarkPriceDiolog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dark_price_dialog_layout);

        /*goods_id	是	int	商品id
user_id	是	int	用户id
price	是	decimal	出价金额
anonymous	否	int	匿名出价，默认为0，匿名出价为1
all_price	是	int	全款支付，1全款，0溢价*/
        final CheckBox anonymous_box = (CheckBox) dialog.findViewById(R.id.anonymous_box);
          final EditText priceINput = (EditText) dialog.findViewById(R.id.price_input);
        View close =dialog.findViewById(R.id.delete);
        View submit =dialog.findViewById(R.id.submit);


        final RadioButton allPriceRadio = (RadioButton) dialog.findViewById(R.id.all_price);
        allPriceRadio.setChecked(true);
        RadioButton overPriceRadio = (RadioButton) dialog.findViewById(R.id.over_price);


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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(priceINput.getText().toString())){
                    UiUtil.showLongToast(v.getContext(),"请输入商品的价格");
                    return;
                }

                if(!ruleRido.isChecked()){
                    UiUtil.showLongToast(context,"请同意投买网规则");
                    return;
                }
                final String  all_price = allPriceRadio.isChecked()?"1":"0";
                if(oncommitListener!=null){
                    oncommitListener.onCommit(priceINput.getText().toString(),anonymous_box.isChecked()?"1":"0",all_price);
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
    public interface  OnDarkCommitListener{
        void onCommit(String pirce ,String anonymous,String allPrice) ;
    }

}
