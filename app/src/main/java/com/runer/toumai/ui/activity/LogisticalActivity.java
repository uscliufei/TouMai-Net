package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.LogisticalAdapter;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.ExpressInfo;
import com.runer.toumai.bean.LogisticalBean;
import com.runer.toumai.dao.OrderDao;
import com.runer.toumai.util.KdniaoTrackQueryAPI;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//查看物流界面
public class LogisticalActivity extends BaseActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.commit_bt)
    TextView commitBt;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.code)
    TextView code;
    @InjectView(R.id.remark)
    TextView remark;
    private String order_id;
    private OrderDao orderDao;
    private List<LogisticalBean> datas;
    private LogisticalAdapter logisticalAdapter;
    private KdniaoTrackQueryAPI kdniaoTrackQueryAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistical);
        ButterKnife.inject(this);
        order_id = getStringExtras("order_id", "");
        orderDao = new OrderDao(this, this);
        orderDao.getExpressInfo(order_id);
        kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI(this, this);
        logisticalAdapter = new LogisticalAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(logisticalAdapter);
        // kdniaoTrackQueryAPI.getExpressInfo("HHTT", "667771430898");
        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_5) {
            datas = kdniaoTrackQueryAPI.getLogisticalResultBean().getTraces();
            if (datas != null && !datas.isEmpty()) {
                datas.get(0).setFirst(true);
                datas.get(datas.size() - 1).setEnd(true);
            }
            Logger.d(datas);
            logisticalAdapter.setNewData(datas);
        } else if (requestCode == RequestCode.EXPRESS_LIST) {
            ExpressInfo expressInfo = orderDao.getExpressInfo();
            if (expressInfo != null) {
                name.setText("物流商家:" + expressInfo.getExpress_name());
                code.setText("订单编号:" + expressInfo.getPost_code());
                if (!TextUtils.isEmpty(expressInfo.getRemark())) {
                    remark.setText("备注：" + expressInfo.getRemark());
                }else{
                    remark.setText("无备注");
                }
                kdniaoTrackQueryAPI.getExpressInfo(expressInfo.getExpress_code(), expressInfo.getPost_code());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("物流信息");
    }
}
