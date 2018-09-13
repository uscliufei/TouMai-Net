package com.runer.toumai.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.runer.liabary.util.UiUtil;
import com.runer.toumai.base.Constant;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * WXPayEntryActivity
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID);
        api.handleIntent(getIntent(),this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
    }
    //处理支付结果
    @Override
    public void onResp(BaseResp respose) {
   if(respose.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
       if(respose.errCode==0){
           UiUtil.showLongToast(this,"支付成功");
           finish();
       }else{
           UiUtil.showLongToast(this,"支付失败："+respose.errCode);
           finish();
       }
       switch (respose.errCode){
           case 0:
                break;
           case -1:
               UiUtil.showLongToast(WXPayEntryActivity.this,"发生未知错误");
               break;
           case -2:
               UiUtil.showLongToast(WXPayEntryActivity.this,"用户取消支付");
               break;
           case -3 :
               UiUtil.showLongToast(WXPayEntryActivity.this,"支付信息发送失败");
               break;
           case -4 :
               UiUtil.showLongToast(WXPayEntryActivity.this,"支付签名错误");
               break;
           case -5:
               UiUtil.showLongToast(WXPayEntryActivity.this,"发生未知错误");
               break;
       }
       finish();
   }
    }
}
