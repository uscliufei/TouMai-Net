package com.runer.toumai.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.animation.BaseAnimation;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.adapter.BiddingPredictioAdapter;
import com.runer.toumai.bean.BiddingPredictio;
import com.runer.toumai.util.BiddingPredictionMachine;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_LEFT;

/**
 * Created by ruier on 2018/7/17.
 */

public class BidingView extends LinearLayout {
    Context context;

    RecyclerView recyclerView;


    View truslute_view ;

    private EditText roundView ;

    private TextView submit ;

    private BiddingPredictioAdapter biddingPredictioAdapter ;
    private ArrayList datas =new ArrayList();

    public BidingView(Context context,OnTrusluteClickListener onTrusluteClickListener) {
        this(context, null, 0);
        this.onTrusluteClickListener =onTrusluteClickListener ;
    }

    public BidingView(Context context ) {
        this(context, null, 0);
    }

    public BidingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BidingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.biding_dialog_layout, this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        truslute_view =findViewById(R.id.truslute_view);

        biddingPredictioAdapter =new BiddingPredictioAdapter(datas);
        biddingPredictioAdapter.openLoadAnimation(SLIDEIN_LEFT);
        biddingPredictioAdapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new RunerLinearManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(ItemDecorations.vertical(context)
                .type(0, R.drawable.item_decoration_shape)
                .last(R.drawable.item_decoration_shape)
                .create());
        recyclerView.setAdapter(biddingPredictioAdapter);


        truslute_view.setOnClickListener(view -> {
            if (onTrusluteClickListener!=null){
                onTrusluteClickListener.onClick();
            }
        });

        roundView = (EditText) findViewById(R.id.num);
        submit = (TextView) findViewById(R.id.commit_bt);


        submit.setOnClickListener(view -> {
            try {
             int roundNum = Integer.parseInt(roundView.getText().toString());
                BiddingPredictio data = new BiddingPredictionMachine().getOneBinding(price, ratio, buyoutPrice, roundNum);
                if (data!=null){
                    biddingPredictioAdapter.addData(data);
                    recyclerView.scrollToPosition(biddingPredictioAdapter.getData()==null?0:biddingPredictioAdapter.getData().size()-1);
                }
            }catch (Exception e){
                UiUtil.showLongToast(getContext(),"请输入数字");
            }
        });
    }


    private  double price ;
    private int ratio ;
    private double buyoutPrice ;
    public void setData(Double price ,Integer ratio ,Double buyoutPrice){
        this.price =price ;
        this.ratio =ratio ;
        this.buyoutPrice=buyoutPrice ;
      datas = (ArrayList) new BiddingPredictionMachine().generatePricesByOneOneStep(price,ratio,buyoutPrice);
      biddingPredictioAdapter.setNewData(datas);
      recyclerView.scrollToPosition(biddingPredictioAdapter.getData()==null?0:biddingPredictioAdapter.getData().size()-1);
    }



    public void setOnTrusluteClickListener(OnTrusluteClickListener onTrusluteClickListener) {
        this.onTrusluteClickListener = onTrusluteClickListener;
    }

    private OnTrusluteClickListener onTrusluteClickListener ;

    public interface  OnTrusluteClickListener{
        void onClick() ;
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();



    }
}
