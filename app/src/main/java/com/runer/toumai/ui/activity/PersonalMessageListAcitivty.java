package com.runer.toumai.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.ui.fragment.ConcernMessagelIstFragment;
import com.runer.toumai.ui.fragment.PerMessageListFragment;

public class PersonalMessageListAcitivty extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        //userFragment =new PerMessageListFragment();
        addFragmentList(R.id.container, new PerMessageListFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("私信列表");
    }
}
