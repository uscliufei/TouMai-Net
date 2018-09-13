package com.runer.toumai.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.toumai.R;
import com.runer.toumai.adapter.FaqAdapter;
import com.runer.toumai.adapter.FaqNorLisAdapter;
import com.runer.toumai.base.BaseWebAcitivity;
import com.runer.toumai.bean.FaqBean;
import com.runer.toumai.bean.FaqListBean;
import com.runer.toumai.ui.activity.FaqActivity;

import java.util.List;

/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * FAQListTipsDialog
 */

public class FAQListTipsDialog extends Dialog {

    public FAQListTipsDialog(@NonNull Context context) {
        super(context);
    }

    public FAQListTipsDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected FAQListTipsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public static FAQTips show(final Context context, final List<FaqListBean> faqBeanList) {
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
        FaqNorLisAdapter faqAdapter =new FaqNorLisAdapter(faqBeanList);
        faqAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent =new Intent(context,BaseWebAcitivity.class) ;
                intent.putExtra("data",faqBeanList.get(position));
                context.startActivity(intent);
            }
        });
//        recyclerView.addItemDecoration(ItemDecorations.vertical(context)
//                .type(0, R.drawable.item_decoration_shape).create());
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


}
