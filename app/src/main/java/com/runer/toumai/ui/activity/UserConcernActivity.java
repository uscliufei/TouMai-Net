package com.runer.toumai.ui.activity;

import android.os.Bundle;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.ui.fragment.UserConcernFragment;

public class UserConcernActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        addFragmentList(R.id.container,new UserConcernFragment());
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("关注用户");
    }
}
