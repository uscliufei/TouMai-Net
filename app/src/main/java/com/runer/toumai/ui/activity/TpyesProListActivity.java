package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.RunerLinearManager;
import com.runer.liabary.widget.CenterRadioButton;
import com.runer.toumai.R;
import com.runer.toumai.adapter.HomeListAdapter;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.ui.fragment.HomeFragment;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.LoamoreView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class TpyesProListActivity extends BaseActivity {

    private String type;
    private String lable;
    private String uid;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpyes_pro_list);
        type = getIntent().getStringExtra("type");
        lable = getIntent().getStringExtra("lable");
        uid = getIntent().getStringExtra("uid");
        user = getIntent().getStringExtra("user");
        addFragmentList(R.id.container, HomeFragment.getInstance(type,lable,user,uid));
    }
}
