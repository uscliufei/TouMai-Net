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
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.runer.liabary.util.UiUtil;
import com.runer.net.interf.INetResult;
import com.runer.toumai.R;
import com.runer.toumai.bean.ExpressBean;
import com.runer.toumai.dao.OrderDao;

import java.util.List;

/**
 * Created by szhua on 2017/7/26/026.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * UploadWaybillInformationDialog
 上传物流信息dialog
 */
public  class UploadWaybillInformationDialog extends Dialog implements INetResult {
    private  OrderDao  orderDao;

    private Context context ;
    public UploadWaybillInformationDialog(@NonNull Context context) {
        super(context,R.style.ProgressHUD);
        orderDao =new OrderDao(context, this) ;
        orderDao.getExpressLists();
        this.context =context ;
    }
    public UploadWaybillInformationDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        orderDao =new OrderDao(context, this) ;
        orderDao.getExpressLists();
        this.context =context ;
    }
    protected UploadWaybillInformationDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    private  List<ExpressBean> expressBeanList;

    public void showDialog(){
        setTitle("");
        setContentView(R.layout.upload_waybill_information_dialog);
        View close =findViewById(R.id.delete);
        View commitBt =findViewById(R.id.submit);
        final EditText expressCodeEt = (EditText) findViewById(R.id.express_code);
        final TextView wuliuBt = (TextView)findViewById(R.id.select_wuliu);
        final EditText remarkEt = (EditText) findViewById(R.id.remark);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wuliuBt.getTag()==null|| TextUtils.isEmpty((CharSequence) wuliuBt.getTag())){
                    UiUtil.showLongToast(context,"请选择物流公司");
                    return;
                }
                if(TextUtils.isEmpty(expressCodeEt.getText().toString())){
                    UiUtil.showLongToast(context,"请输入物流单号");
                    return;
                }

                if(onCommitClickListener!=null){
                    onCommitClickListener.onCommitClick((String) wuliuBt.getTag(),expressCodeEt.getText().toString(),remarkEt.getText().toString());
                }
            }
        });
        wuliuBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expressBeanList!=null){
                    OptionsPickerView optionsPickerView =new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            wuliuBt.setText(expressBeanList.get(options1).getName());
                            wuliuBt.setTag(expressBeanList.get(options1).getId());
                        }
                    })
                            .isDialog(true)
                            .setTitleText("选择物流").build();
                    optionsPickerView.setPicker(expressBeanList);
                    optionsPickerView.showDialog();
                }
            }
        });
        setCancelable(false);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
        show();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
         expressBeanList =orderDao.getExpressBeanList();

    }

    @Override
    public void onRequestError(int requestCode, String errorInfo, int error_code) {
        UiUtil.showLongToast(context,errorInfo);
    }

    @Override
    public void onRequestFaild(int requestCode, String errorNo, String errorMessage) {
       UiUtil.showLongToast(context,errorMessage);
    }
    @Override
    public void onNoConnect() {
UiUtil.showLongToast(context,"无网络连接");
    }

    private OnCommitClickListener onCommitClickListener ;
    public void setOnCommitClickListener(OnCommitClickListener onCommitClickListener) {
        this.onCommitClickListener = onCommitClickListener;
    }
    public interface  OnCommitClickListener{
        void onCommitClick(String express_id ,String post_code ,String remark);
    }
}
