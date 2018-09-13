package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.ui.fragment.ListFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchResultActivity extends BaseActivity {
    @InjectView(R.id.view)
    View view;
    private ListFragment listFragment;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.inject(this);
        title = getIntent().getExtras().getString("key");
        String user_id =getStringExtras("user_id","");
        listFragment = ListFragment.getInstance(title,user_id);
        addFragmentList(R.id.container, listFragment);
    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("搜索结果");
    }
}
