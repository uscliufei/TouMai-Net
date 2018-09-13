package com.runer.toumai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.runer.toumai.R;
import com.runer.toumai.bean.BiddingPredictio;

import java.util.List;

/**
 * Created by ruier on 2018/7/16.
 * 竞价预测的adapter；
 */

public class BiddingPredictioAdapter extends BaseQuickAdapter<BiddingPredictio,BaseViewHolder> {
    public BiddingPredictioAdapter(@Nullable List<BiddingPredictio> data) {
        super(R.layout.item_bidding_predictio_layout ,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, BiddingPredictio item) {
      helper.setText(R.id.num,item.getNum())
              .setText(R.id.current_price,item.getCurrentPrice())
              .setText(R.id.one_price,item.getBuyout_price())
              .setText(R.id.biding_get,item.getSellerGotPrice())
              .setText(R.id.one_get,item.getSellerGotBuyOutPrice()) ;
    }
}
