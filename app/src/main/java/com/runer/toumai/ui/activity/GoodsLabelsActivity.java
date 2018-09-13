package com.runer.toumai.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.ui.fragment.ConcernMessagelIstFragment;
import com.runer.toumai.ui.fragment.LabelsFragment;

import butterknife.ButterKnife;

public class GoodsLabelsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        ButterKnife.inject(this);
        String label =getStringExtras("label","");
        addFragmentList(R.id.container, LabelsFragment.getInstance(label));
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("标签列表");
    }
}
