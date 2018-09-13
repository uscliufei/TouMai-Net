package com.runer.toumai.base;


import android.app.Notification;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.runer.net.JsonUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by szhua on 2017/7/5/005.
 * github:https://github.com/szhua
 */
public class ToumaiApplication extends MultiDexApplication {

    public static  boolean isFaq =false;
    public static  boolean isFaq(){
        return  isFaq ;
    }
    public static IWXAPI iwxapi ;
    public static  boolean allowMucsicCustom = true ;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
        Logger.t(Constant.TAG);
        //集成极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());





        UMShareAPI.get(this);
        Config.DEBUG = true;
        UMShareAPI.init(this, "59a7c19a75ca351ea5000f3a");
        iwxapi = WXAPIFactory.createWXAPI(this,Constant.WECHAT_APP_ID) ;
        iwxapi.registerApp(Constant.WECHAT_APP_ID);
    }
    {
        PlatformConfig.setWeixin("wx26addcee7bcbbb02", "4a3bfba2c6097faaedf7fcef837c5581");
        PlatformConfig.setSinaWeibo("257733286", "871a1ed332466744516f0e4d61ff98e3","https://sns.whalecloud.com/sina2/callback");
        PlatformConfig.setQQZone("1106385498", "dgF1HAhAEsUs1nwf");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
