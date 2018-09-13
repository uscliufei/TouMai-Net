package com.runer.toumai.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.BaseStateBean;
import com.runer.toumai.bean.FaqBean;
import com.runer.toumai.bean.FaqListBean;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.util.FaqUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/9/6/006.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * OrderViewState
 */
public class OrderViewState extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    private View container ;
    private CheckBox darkType ;
    private CheckBox brightType ;
    private CheckBox downType ;
    private CheckBox heartType ;
    private CheckBox overType ;
    private CheckBox unOverType ;
    private View commitView ;
    private View  faqBt;
    public OrderViewState(Context context) {
        this(context,null,0);
    }
    public OrderViewState(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public OrderViewState(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.order_view_state_layout,this);
        initView();
    }
    private void initView(){
        faqBt =findViewById(R.id.faq_bt);
        container=findViewById(R.id.container) ;
        darkType = (CheckBox)findViewById(R.id.dark_type);
        brightType = (CheckBox) findViewById(R.id.briht_type);
        downType = (CheckBox) findViewById(R.id.down_type);
        heartType = (CheckBox) findViewById(R.id.heart_type);
        overType = (CheckBox) findViewById(R.id.finished_over);
        unOverType = (CheckBox) findViewById(R.id.finished_un_over);
        darkType.setOnCheckedChangeListener(this);
        brightType.setOnCheckedChangeListener(this);
        downType.setOnCheckedChangeListener(this);
        heartType.setOnCheckedChangeListener(this);
        overType.setOnCheckedChangeListener(this);
        unOverType.setOnCheckedChangeListener(this);
        commitView =findViewById(R.id.commit_bt);
        commitView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
         if(onstateViewClickListener!=null){
             onstateViewClickListener.onCommitClick(stateBeens);
         }
            }
        });
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onstateViewClickListener!=null){
                    onstateViewClickListener.onOutClick();
                }
            }
        });
        darkType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AppUtil.chckeLogin(getContext(),false)){
                    UiUtil.showLongToast(getContext(),"请先进行登录");
                    darkType.setChecked(false);
                    return;
                }
                if(!AppUtil.checkIsMakeUp(getContext())||!AppUtil.checkDarkLearn(getContext())){
                    AnjiaRulesDialog.show(getContext(),AppUtil.checkDarkLearn(getContext()),AppUtil.checkIsMakeUp(getContext()));
                    darkType.setChecked(false);
                    return;
                }
            }
        });

        if(ToumaiApplication.isFaq){
            faqBt.setVisibility(VISIBLE);
            faqBt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQListTipsDialog.show(getContext(), FaqUtil.getOrderStateFaq(getContext()));
                }
            });
        }
    }
    private List<BaseStateBean> stateBeens;
    public void setStates(List<BaseStateBean> states){
        if(states!=null){
            this.stateBeens =states ;
            for (int i = 0; i < states.size(); i++) {
               BaseStateBean stateBean =states.get(i);
               switch (i){
                   case 0:
                           darkType.setChecked(stateBean.isSelected());
                       break;
                   case 1:
                           brightType.setChecked(stateBean.isSelected());
                       break;
                   case 2:
                           downType.setChecked(stateBean.isSelected());
                       break;
                   case 3:
                           heartType.setChecked(stateBean.isSelected());
                       break;
                   case 4:
                           overType.setChecked(stateBean.isSelected());
                       break;
                   case 5:
                           unOverType.setChecked(stateBean.isSelected());
                       break;
               }
            }
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView==darkType){
            stateBeens.get(0).setSelected(isChecked);
        } else  if(buttonView==brightType){
            stateBeens.get(1).setSelected(isChecked);
        }else  if(buttonView==downType){
            stateBeens.get(2).setSelected(isChecked);
        }else  if(buttonView==heartType){
            stateBeens.get(3).setSelected(isChecked);
        }else  if(buttonView==overType){
            stateBeens.get(4).setSelected(isChecked);
        }else  if(buttonView==unOverType){
            stateBeens.get(5).setSelected(isChecked);
        }
        setStates(stateBeens);
    }
    private OnstateViewClickListener onstateViewClickListener ;
    public void setOnstateViewClickListener(OnstateViewClickListener onstateViewClickListener) {
        this.onstateViewClickListener = onstateViewClickListener;
    }
    public  interface  OnstateViewClickListener{

        void onOutClick();
        void onCommitClick(List<BaseStateBean> states) ;

    }
}
