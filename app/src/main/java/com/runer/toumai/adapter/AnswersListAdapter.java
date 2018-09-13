package com.runer.toumai.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.toumai.R;
import com.runer.toumai.bean.QuestionBean;
import com.runer.toumai.util.AppUtil;

import java.util.List;

/**
 * Created by szhua on 2017/7/25/025.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * AnswersListAdapter
 */

public class AnswersListAdapter extends BaseQuickAdapter<QuestionBean,BaseViewHolder> {
    public AnswersListAdapter(@Nullable List<QuestionBean> data) {
        super(R.layout.item_pro_answer_layout,data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final QuestionBean item) {
        helper.setText(R.id.question,item.getQuestion())
                .setText(R.id.name,item.getUser_name())
                .setText(R.id.time,item.getCreate_time());
                if(TextUtils.isEmpty(item.getAnswer())){
                    helper.setText(R.id.answer,"暂无回答");
                    //显示回答界面
                    if(AppUtil.getUserId(mContext).equals(item.getSeller_id())){
                      helper.setVisible(R.id.answer_bt,true);
                      helper.getView(R.id.answer_bt).setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
 if(onAnswerClickListener!=null){
     onAnswerClickListener.onAnswerClick(item ,helper.getPosition());
 }
                          }
                      });
                    }else{
                        helper.setVisible(R.id.answer_bt,false);
                    }


                }else{
                    helper.setText(R.id.answer,item.getAnswer());
                    helper.setVisible(R.id.answer_bt,false);
           }
    }

    private OnAnswerClickListener onAnswerClickListener ;

    public void setOnAnswerClickListener(OnAnswerClickListener onAnswerClickListener) {
        this.onAnswerClickListener = onAnswerClickListener;
    }

    public  interface  OnAnswerClickListener{
        void onAnswerClick(QuestionBean conent ,int pos);
    }
}
