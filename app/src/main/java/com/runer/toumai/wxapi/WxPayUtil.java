package com.runer.toumai.wxapi;
import android.app.Activity;
import com.orhanobut.logger.Logger;
import com.runer.toumai.base.Constant;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.WechatPayBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * WxPayUtil
 */
public class WxPayUtil {
    public static  void wxPay(final Activity context , final WechatPayBean wechatPayBean){
        Logger.d(wechatPayBean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayReq req = new PayReq();
                req.appId = Constant.WECHAT_APP_ID;
                req.partnerId = wechatPayBean.getPartnerid();
                req.prepayId = wechatPayBean.getPrepayid();
                req.nonceStr = wechatPayBean.getNoncestr();
                req.timeStamp = wechatPayBean.getTimestamp();
                req.packageValue = "Sign=WXPay";
                req.sign = wechatPayBean.getSign();
                req.extData = "app data";//optional
                ToumaiApplication.iwxapi.sendReq(req);
            }
       }).start();
    }

}
