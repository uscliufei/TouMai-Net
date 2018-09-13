package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.util.UiUtil;
import com.runer.net.JsonUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.adapter.LabelsAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.Label;
import com.runer.toumai.dao.LabelListDao;
import com.runer.toumai.ui.activity.TpyesProListActivity;

import java.util.ArrayList;
import java.util.List;

import static com.runer.liabary.vedio.NiceUtil.dp2px;

/**
 * Created by ruier on 2018/7/16.
 * 标签界面 ；
 */

public class LabelsFragment extends BaseLoadMoreFragment<LabelsAdapter> {

    private List<Label>  datas =new ArrayList<>() ;

    private LabelListDao labelListDao ;

    private String title ;

    public static  LabelsFragment getInstance(String title){
        LabelsFragment labelsFragment =new LabelsFragment() ;
        labelsFragment.title =title ;
        return  labelsFragment ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        labelListDao =new LabelListDao(this,this) ;
        labelListDao.getLables(title);
    }

    @Override
    public LabelsAdapter getAdater() {
        LabelsAdapter adapter = new LabelsAdapter(datas);
        try{
            adapter.setOnTagItemClcikListener(new LabelsAdapter.OnTagItemClcikListener() {
                @Override
                public void onItemClcick(Label label) {
                    Intent intent =new Intent(getContext(), TpyesProListActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("lable", label.getLabel());
                    getContext().startActivity(intent);
                    getActivity().finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return adapter;
    }
    @Override
    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
//设置每一个item间距
        recyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(getContext(),10)));
        return  flowLayoutManager;
    }

    @Override
    public void loadMore() {
        if (labelListDao.hasMore()) {
            labelListDao.loadMore();
        } else {
            if (baseQuickAdapter != null) {
                baseQuickAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void refresh() {
      labelListDao.refresh();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            datas =labelListDao.getDatas() ;
            baseQuickAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                baseQuickAdapter.setEmptyView(getEmptyView("暂无搜索标签"));
            }
        }
    }
}
