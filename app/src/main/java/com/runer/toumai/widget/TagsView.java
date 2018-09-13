package com.runer.toumai.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.runer.liabary.flowlayout.FlowLayout;
import com.runer.liabary.flowlayout.TagAdapter;
import com.runer.liabary.flowlayout.TagFlowLayout;
import com.runer.toumai.R;
import com.runer.toumai.bean.TagBean;

import java.util.List;
import java.util.Set;

/**
 * Created by szhua on 2017/7/19/019.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * TagsView
 */

public class TagsView extends LinearLayout{

    private List<TagBean> tagBeanList ;
    private TagFlowLayout tagFlowLayout ;
    private TagsAdapter  tagsAdapter;

    public TagsView(Context context) {
        this(context,null,0);
    }
    public TagsView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    private int currentPose ;
    public TagsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.tags_layout,this);
        tagFlowLayout = (TagFlowLayout) findViewById(R.id.tag_flow_layout);
        tagFlowLayout.setMaxSelectCount(1);

        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                //前面的设置为未选中
                tagsAdapter.getItem(currentPose).setSelected(false);
                //当前的设置为选中
                tagsAdapter.getItem((int) selectPosSet.toArray()[0]).setSelected(true);
                tagsAdapter.notifyDataChanged();

                currentPose = (int) selectPosSet.toArray()[0];
            }
        });
    }

    public void setTagBeanList(List<TagBean> tagBeanList) {
        this.tagBeanList = tagBeanList;
        tagsAdapter =new TagsAdapter(tagBeanList) ;
        tagFlowLayout.setAdapter(tagsAdapter);
    }


    private class TagsAdapter extends TagAdapter<TagBean>{
        public TagsAdapter(List<TagBean> datas) {
            super(datas);
        }
        @Override
        public View getView(FlowLayout parent, final int position, TagBean tagBean) {
            View view =View.inflate(getContext(),R.layout.item_tags_layout,null);
            View delete =view.findViewById(R.id.delete) ;
            TextView tagName = (TextView) view.findViewById(R.id.tagname);
            tagName.setText(tagBean.getTagName());
            if(tagBean.isSelected()){
                delete.setVisibility(View.VISIBLE);
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.tag_bg_shape);
            }else{
                delete.setVisibility(View.GONE);
                view.setSelected(false);
                view.setBackgroundResource(R.drawable.tag_bg_shape_un);
            }
            delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTagDeleteListener != null) {
                        onTagDeleteListener.onDeleteTagListener(position);
                    }
                }
            });
            return view;
        }
    }
    public  OnTagDeleteListener onTagDeleteListener;

    public void setOnTagDeleteListener(OnTagDeleteListener onTagDeleteListener) {
        this.onTagDeleteListener = onTagDeleteListener;
    }

    public interface OnTagDeleteListener{
    void onDeleteTagListener(int pos);
}
}
