package com.runer.toumai.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.runer.liabary.util.Prefs;
import com.runer.toumai.bean.SearchBean;
import com.runer.toumai.bean.UserInfo;
import com.runer.toumai.ui.activity.LoginActivity;
import com.tencent.mm.opensdk.utils.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by szhua on 2017/7/4/004.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * AppUtil
 */

public class AppUtil {


    /**
     * <option value="">全部</option>
     <option value="1" <{if $smarty.get.status=='1'}>selected<{/if}>>竞价中-我领先</option>
     <option value="2" <{if $smarty.get.status=='2'}>selected<{/if}>>竞价中-已出局</option>
     <option value="3" <{if $smarty.get.status=='3'}>selected<{/if}>>已结束-已出局</option>
     <option value="4" <{if $smarty.get.status=='4'}>selected<{/if}>>已成交-待付尾款</option>
     <option value="5" <{if $smarty.get.status=='5'}>selected<{/if}>>已成交-待确认收货地址和型号款式</option>
     <option value="6" <{if $smarty.get.status=='6'}>selected<{/if}>>已成交-未发货</option>
     <option value="7" <{if $smarty.get.status=='7'}>selected<{/if}>>已成交-已发货</option>
     <option value="8" <{if $smarty.get.status=='8'}>selected<{/if}>>已成交-已收货</option>
     <option value="9" <{if $smarty.get.status=='9'}>selected<{/if}>>申诉处理中</option>
     * @return
     */
    public static List<SearchBean> getBuySearchBean(){
        List<SearchBean> shareBeans =new ArrayList<>() ;
        shareBeans.add(new SearchBean("","全部"));
        shareBeans.add(new SearchBean("1","竞价中-我领先"));
        shareBeans.add(new SearchBean("2","竞价中-已出局"));
        shareBeans.add(new SearchBean("3","已结束-已出局"));
        shareBeans.add(new SearchBean("4","已成交-待付尾款"));
        shareBeans.add(new SearchBean("5","已成交-待确认收货地址和型号款式"));
        shareBeans.add(new SearchBean("6","已成交-未发货"));
        shareBeans.add(new SearchBean("7","已成交-已发货"));
        shareBeans.add(new SearchBean("8","已成交-已收货"));
        shareBeans.add(new SearchBean("9","申诉处理中"));

         return  shareBeans ;
    }


    /**
     * <option value="">全部</option>
     <option value="1" <{if $smarty.get.status=='1'}>selected<{/if}>>竞价中</option>
     <option value="2" <{if $smarty.get.status=='2'}>selected<{/if}>>未成交</option>
     <option value="3" <{if $smarty.get.status=='3'}>selected<{/if}>>已成交-待付尾款</option>
     <option value="4" <{if $smarty.get.status=='4'}>selected<{/if}>>已成交-待确认收货地址和型号款式</option>
     <option value="5" <{if $smarty.get.status=='5'}>selected<{/if}>>已成交-未发货</option>
     <option value="6" <{if $smarty.get.status=='6'}>selected<{/if}>>已成交-已发货</option>
     <option value="7" <{if $smarty.get.status=='7'}>selected<{/if}>>已成交-已收货</option>
     <option value="8" <{if $smarty.get.status=='8'}>selected<{/if}>>申诉处理中</option>
     * @return
     */
    public static  List<SearchBean> getSoldSearchBean(){
        List<SearchBean> shareBeans =new ArrayList<>() ;
        shareBeans.add(new SearchBean("","全部"));
        shareBeans.add(new SearchBean("1","竞价中"));
        shareBeans.add(new SearchBean("2","未成交"));
        shareBeans.add(new SearchBean("3","已成交-待付尾款"));
        shareBeans.add(new SearchBean("4","已成交-未发货"));
        shareBeans.add(new SearchBean("5","已成交-已发货"));
        shareBeans.add(new SearchBean("6","已成交-未发货"));
        shareBeans.add(new SearchBean("7","已成交-已收货"));
        shareBeans.add(new SearchBean("9","申诉处理中"));
        return  shareBeans ;
    }



    public static  void startBrower(Context context,String url){
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent); //启动浏览器
    }


    public static void registerUpdatePyer(){
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                        Log.d("pgyer", "there is no new version");
                    }

                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        //没有更新是回调此方法
                        Log.d("pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.getVersionCode());

                        //调用以下方法，DownloadFileListener 才有效；如果完全使用自己的下载方法，不需要设置DownloadFileListener
                        PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        Logger.d("check update failed ", e);

                    }
                })
                //注意 ：下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                .setDownloadFileListener(new DownloadFileListener() {   // 使用蒲公英提供的下载方法，这个接口才有效。
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        Logger.e("pgyer", "download apk failed");
                    }

                    @Override
                    public void downloadSuccessful(Uri uri) {
                        Logger.e("pgyer", "download apk failed");
                        PgyUpdateManager.installApk(uri);  // 使用蒲公英提供的安装方法提示用户 安装apk

                    }
                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        Logger.e("pgyer", "update download apk progress" + integers);
                    }
                })
                .register();
    }


    /**
     * 是否需要弹出未读消息
     * @param context
     * @return
     */
    public static  boolean needMsgAlert(Context context){
        if ("1".equals(Prefs.with(context).read("alertMsg"))){
            return  false ;
        }
        return  true ;
    }
    /**
     * 设置已经弹出了弹窗；
     * @param context
     */
    public static  void setMsgAlerted(Context context ,boolean isAlerted){
        if (isAlerted) {
            Prefs.with(context).write("alertMsg", "1");
        }else{
            Prefs.with(context).write("alertMsg","0");
        }
    }


    public  static  String getVersionName(Context context){
        return  getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    //获得本app的而一些信息
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static UserInfo getUserInfo(Context context){
        UserInfo userInfo =new UserInfo() ;
        userInfo.setId(Prefs.with(context).read("user_id"));
        userInfo.setHead(Prefs.with(context).read("user_head"));
        userInfo.setUser_name(Prefs.with(context).read("user_name"));
        userInfo.setSex(Prefs.with(context).read("sex"));
        userInfo.setReg_date(Prefs.with(context).read("reg_time"));
        userInfo.setProvince(Prefs.with(context).read("pro"));
        userInfo.setMobile(Prefs.with(context).read("phone"));
        userInfo.setAuth(Prefs.with(context).read("auth"));
        userInfo.setAuth_state(Prefs.with(context).read("auth_state"));
        userInfo.setIntro(Prefs.with(context).read("intro"));
        userInfo.setAuth_fail(Prefs.with(context).read("auth_fail"));
        return  userInfo ;
    }
    public static void setUserHeader(Context context ,String userHeader){
        Prefs.with(context).write("user_head",userHeader);
    }

    public static void setUserInfo(Context context ,UserInfo userInfo){
        Prefs.with(context).write("user_id","1107");
        Prefs.with(context).write("user_name",userInfo.getUser_name());
        Prefs.with(context).write("user_head",userInfo.getHead());
        Prefs.with(context).write("sex",userInfo.getSex());
        Prefs.with(context).write("reg_time",userInfo.getReg_date());
        Prefs.with(context).write("pro",userInfo.getProvince());
        Prefs.with(context).write("phone",userInfo.getMobile());
        Prefs.with(context).write("auth",userInfo.getAuth());
        Prefs.with(context).write("user_head",userInfo.getHead());
        /*auth_state	认证状态。0未认证，1成功，2申请中，3失败
auth_apply_time	认证申请日期*/
       Prefs.with(context).write("auth_state",userInfo.getAuth_state());
       Prefs.with(context).write("intro",userInfo.getIntro());
        Prefs.with(context).write("auth_fail", String.valueOf(userInfo.getAuth_fail()));
        Prefs.with(context).write("is_dark_learn",userInfo.getIs_dark_learn());
        Prefs.with(context).write("is_makeup",userInfo.getIs_makeup());

    }

    public static void setDarkLearn(Context context){
        Prefs.with(context).write("is_dark_learn","1");
    }

    public static  boolean checkDarkLearn(Context context){
        if("0".equals(Prefs.with(context).read("is_dark_learn"))){
            return  false ;
        }
        return true ;
    }
    public static  boolean checkIsMakeUp(Context context){
        if("0".equals(Prefs.with(context).read("is_makeup"))){
            return  false ;
        }
        return true ;
    }



    //获取用户id
    public static  String getUserId(Context context){
        return Prefs.with(context).read("user_id");
    }

    //设置用户id
    public static  void setUserId(Context context,String user_id){
        Prefs.with(context).write("user_id",user_id);
    }

    //退出登陆
    public static  void exitLogo(Context context){
        UserInfo userInfo =new UserInfo();
        setUserInfo(context,userInfo);
    }
    /*是否登录*/
    public static boolean chckeLogin(Context context ,boolean isToLog){
        if(TextUtils.isEmpty(getUserId(context))){
            //去登陆
            if(isToLog){
                Intent intent =new Intent(context, LoginActivity.class) ;
                context.startActivity(intent);
            }
            return  false ;
        }else{
            return  true ;
        }
    }

    public static List<String> getTestData(){
        ArrayList<String> data =new ArrayList();
        for (int i = 0; i <20; i++) {
            if(i==1) {
                data.add("itemUnit" + i);
            }else{
                data.add("item"+i);
        }
            }
        return  data ;
    }


    public static String timeParse(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }

    // for md5 ;
    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    public  static  String getTimeStr(int d ,int h ,int m ,int s){

        String result ="" ;
        if(d!=0){
            result+=d+"天";
        }
        if(h!=0){
            result+=h+"小时";
        }
        if(m!=0){
            result+=m+"分";
        }
        if(s!=0){
            result+=s+"秒";
        }
        return result ;
    }



    public static String getUserAddress(UserInfo userInfo){
        StringBuilder adressInfo = new StringBuilder();
        adressInfo.append(TextUtils.isEmpty(userInfo.getProvince())?"":userInfo.getProvince())
                .append(TextUtils.isEmpty(userInfo.getCity())?"":userInfo.getCity())
                .append(TextUtils.isEmpty(userInfo.getArea())?"":userInfo.getArea())
                .append((TextUtils.isEmpty(userInfo.getProvince())&&TextUtils.isEmpty(userInfo.getCity())&&TextUtils.isEmpty(userInfo.getArea())?"暂无地址":""));
        return  adressInfo.toString() ;
    }

    public static  boolean isEmpty(List list){
        if (list==null||list.isEmpty()){
            return  true ;
        }
       return  false ;
    }



}
