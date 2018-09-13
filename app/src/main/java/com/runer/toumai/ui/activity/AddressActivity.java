package com.runer.toumai.ui.activity;

import android.os.Bundle;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.ui.fragment.AddressListFragment;

/*收货地址管理界面*/
public class AddressActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        addFragmentList(R.id.container,new AddressListFragment());
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("收货地址管理");
    }
}
