package com.runer.toumai.bean;

import android.text.TextUtils;

/**
 * Created by szhua on 2017/8/2/002.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * BrightRestTimeBean
 * bright_rest_time	距结束剩余时间，数组
 */

public class BrightRestTimeBean {
        /**
         * is_end : 1
         * d : null
         * h : null
         * m : null
         * s : null
         */
        private String is_end;
        private String d;
        private String h;
        private String m;
        private String s;

    public String getIs_end() {
        return is_end;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "BrightRestTimeBean{" +
                "is_end='" + is_end + '\'' +
                ", d='" + d + '\'' +
                ", h='" + h + '\'' +
                ", m='" + m + '\'' +
                ", s='" + s + '\'' +
                '}';
    }

    //获得距离结束的时间；
    public String getLeftTime(){
        String result ="" ;
        if(!TextUtils.isEmpty(getD())){
            result+=getD()+"天";
        }
        if(!TextUtils.isEmpty(getH())){
            result+=getH()+"小时";
        }
        if(!TextUtils.isEmpty(getM())){
            result+=getM()+"分钟";
        }
//        if(!TextUtils.isEmpty(getM())){
//            result+=getS()+"秒";
//        }
        return  result ;
    }
}
