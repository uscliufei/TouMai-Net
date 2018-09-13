package com.runer.toumai.util;

import android.os.Bundle;

/**
 * Created by ruier on 2018/7/18.
 */
public class BundleMapUtil {


    public  interface  BundleInputCall{
        void inputInBundle(Bundle bundle) ;
    }

    public  static Bundle getWithDataBundle(Bundle bundle ,  BundleInputCall bundleInputCall){
        if ( bundle ==null){
            bundle =new Bundle() ;
        }
        if (bundleInputCall!=null){
            bundleInputCall.inputInBundle(bundle);
        }
        return  bundle ;
    }

    public static Bundle getWithDataBundle(BundleInputCall bundleInputCall){
     return  getWithDataBundle(null,bundleInputCall);
    }

}
