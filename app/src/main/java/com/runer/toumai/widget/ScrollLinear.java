package com.runer.toumai.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by szhua on 2017/7/15/015.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * ScrollLinear
 */

public class ScrollLinear extends LinearLayout {


    public  interface OnScrollChangeListener{
        void onScrollChanged( int l, int t,int  oldl, int oldt);
    }

    private OnScrollChangeListener onScrollChangeListener ;

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public ScrollLinear(Context context) {
        super(context);
    }

    public ScrollLinear(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollLinear(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollChangeListener!=null){
            onScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }
    }
}
