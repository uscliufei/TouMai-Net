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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.net.NetConfig;
import com.squareup.picasso.Picasso;

/**
 * Created by szhua on 2017/7/24/024.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AppealDialog
 *  竞价dialog ；
 */

public class BidingDialog extends Dialog {


    public BidingDialog(@NonNull Context context) {
        super(context);
    }

    public BidingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected BidingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public static BidingDialog show(final Context context , SelectPhotoView.OnItemSelectPicClickListener onItemSelectPicClickListener , GoodListBean goodListBean, final OnCommitClickListener onCommitClickListener) {
        final BidingDialog dialog = new BidingDialog(context, com.runer.liabary.R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.biding_dialog_layout);
        View close =dialog.findViewById(R.id.delete);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView photo = (ImageView) dialog.findViewById(R.id.img);
        TextView title = (TextView) dialog.findViewById(R.id.good_title);
        final EditText appeal_reason_input = (EditText) dialog.findViewById(R.id.appeal_reason_input);
        View commit =dialog.findViewById(R.id.submit);

        Picasso.with(context).load(NetConfig.GOODS_IMG+goodListBean.getImg()).placeholder(R.drawable.empty_img).into(photo);
        title.setText(goodListBean.getTitle());

        SelectPhotoView selectPhotoView = (SelectPhotoView) dialog.findViewById(R.id.select_photos);
        selectPhotoView.setMaxNum(4);
        selectPhotoView.setOnItemSelectPicClickListener(onItemSelectPicClickListener);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(appeal_reason_input.getText().toString())){
                    UiUtil.showLongToast(context,"请输入申诉的内容");
                    return;
                }
               onCommitClickListener.onCommitClick(appeal_reason_input.getText().toString(),dialog);
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


    public interface  OnCommitClickListener{
        void onCommitClick(String content, BidingDialog appealDialog);
    }


}
