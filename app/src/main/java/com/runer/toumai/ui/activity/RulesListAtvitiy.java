package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.RulesListBean;
import com.runer.toumai.dao.ArticleDao;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class RulesListAtvitiy extends BaseActivity {

    @InjectView(R.id.rules_list)
    RecyclerView rulesList;
    private ArticleDao articleDao;
    private RulesListAdapter rulesListAdapter ;
    private List<RulesListBean> rulesListBeens ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_list_atvitiy);
        ButterKnife.inject(this);
        articleDao = new ArticleDao(this, this);
        articleDao.getRulesTypes();
        rulesList.setLayoutManager(new LinearLayoutManager(this));
        rulesList.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.item_decoration_shape).create());
        rulesListAdapter =new RulesListAdapter(rulesListBeens);
        rulesList.setAdapter(rulesListAdapter);

        rulesListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle =new Bundle() ;
                bundle.putString("id",rulesListBeens.get(position).getId());
                transUi(RulesCheckActivity.class,bundle);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("规则说明");
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_0){
            rulesListBeens =articleDao.getRulesListBeanList();
            rulesListAdapter.setNewData(rulesListBeens);
        }
    }
    private class RulesListAdapter extends BaseQuickAdapter<RulesListBean,BaseViewHolder>{
        public RulesListAdapter(@Nullable List<RulesListBean> data) {
            super(R.layout.item_rule_layout,data);
        }
        @Override
        protected void convert(BaseViewHolder helper, RulesListBean item) {
            helper.setText(R.id.name,item.getTitle());
        }
    }
}
