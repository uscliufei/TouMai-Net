package com.runer.toumai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.runer.toumai.R;
import com.runer.toumai.adapter.BiddingPredictioAdapter;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.BiddingPredictio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruier on 2018/7/16.
 */

public class BiddingPredictioFragment extends BaseLoadMoreFragment<BiddingPredictioAdapter> {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.biding_predictio_header_layout,null);
        baseQuickAdapter.addHeaderView(headerView);


    }

    @Override
    public BiddingPredictioAdapter getAdater() {

        List<BiddingPredictio> datas =new ArrayList<>() ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        datas.add(new BiddingPredictio()) ;
        return new BiddingPredictioAdapter(datas);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void refresh() {

    }
}
