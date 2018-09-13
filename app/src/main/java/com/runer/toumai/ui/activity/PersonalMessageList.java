package com.runer.toumai.ui.activity;

import android.os.Bundle;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;

import com.runer.toumai.ui.fragment.ConcernMessagelIstFragment;
import com.runer.toumai.util.AppUtil;

import butterknife.ButterKnife;


/*私信列表界面*/
public class PersonalMessageList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_load_more_acitivty_container);
        ButterKnife.inject(this);
        String id =getStringExtras("id","");
        String user_id =getStringExtras("user_id","") ;
        addFragmentList(R.id.container, ConcernMessagelIstFragment.getInstance(id,user_id));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("通知与消息");
    }

}
