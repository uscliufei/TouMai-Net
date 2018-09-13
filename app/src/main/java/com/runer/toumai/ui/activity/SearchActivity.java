package com.runer.toumai.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.adapter.SearchAdapter;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.dao.UserListDao;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.util.SearchRecordManager;
import com.runer.toumai.widget.LoamoreView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class SearchActivity extends BaseActivity {

    @InjectView(R.id.searchEdit)
    EditText searchEdit;
    @InjectView(R.id.clear_img)
    ImageView clearImg;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.search_type)
    TextView searchType;
    private SearchAdapter searchAdapter;
    private List<GoodListBean> searchDatas = new ArrayList<>();

    private String [] searchTypes =new String[]{"商品","用户","标签"};
    private UserListDao  userListDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        searchAdapter = new SearchAdapter(searchDatas);
        searchAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                String key =searchDatas.get(position).getTitle() ;

                switch (searchType.getText().toString()){
                    case "商品":
                        searchGoods(key);
                        break;
                    case "用户":
                        searchPersons(key);
                        break;
                    case "标签":
                        searchLabels(key);
                        break;
                }
            }
        });
        searchAdapter.setLoadMoreView(new LoamoreView());
        searchAdapter.setEnableLoadMore(false);
        VerticalItemDecoration decoration = ItemDecorations.vertical(this)
                .last(R.drawable.item_decoration_shape)
                .type(0, R.drawable.item_decoration_shape).create();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchAdapter);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    editSearch();
                }
                return false;
            }
        });
        getRecordData();
        //清除数据库数据
        clearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInputMethod(searchEdit);//隐藏软件盘
                cleanHistory();//开始搜索逻辑
            }
        });

        searchType.setOnClickListener(view -> {
         showTypeSelector();
        });

          userListDao =new UserListDao(this,this) ;

    }

    public void getRecordData() {
        //请求数据库信息；
        Flowable.just(this)
                .subscribeOn(Schedulers.io())
                .map(new Function<SearchActivity, List<GoodListBean>>() {
                    @Override
                    public List<GoodListBean> apply(SearchActivity searchActivity) throws Exception {
                        return SearchRecordManager.getInstance(searchActivity).queryData("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GoodListBean>>() {
                    @Override
                    public void accept(List<GoodListBean> searchRecordBeen) throws Exception {
                        searchDatas = searchRecordBeen;
                        if (!AppUtil.isEmpty(searchDatas)&&searchDatas.size()>3){
                         searchDatas=  searchDatas.subList(0,3) ;
                        }
                        searchAdapter.setNewData(searchDatas);
                    }
                });
    }

    private void cleanHistory() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("清空记录")
                .setMessage("确定清空搜索记录?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Flowable.just(SearchActivity.this)
                                .map(new Function<Context, Boolean>() {
                                    @Override
                                    public Boolean apply(Context context) {
                                        try {
                                            SearchRecordManager.getInstance(context).deleteData();
                                            return true;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        if (aBoolean) {
                                            UiUtil.showLongToast(getApplicationContext(), "清除成功");
                                            getRecordData();
                                        } else {
                                            UiUtil.showLongToast(getApplicationContext(), "出现错误");
                                        }
                                    }
                                });
                    }
                }).create().show();
    }


    private void editSearch() {
        //进行搜索
        String searhKey = searchEdit.getText().toString();
        if (!searhKey.isEmpty()) {
            final String result = searhKey.replaceAll(" ", "");
            if (!TextUtils.isEmpty(result)) {
                Flowable.just(result)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<String, Boolean>() {
                            @Override
                            public Boolean apply(String s) throws Exception {
                                Logger.d(s);
                                return SearchRecordManager.getInstance(getApplicationContext()).hasData(s);
                            }
                        })
                        .map(new Function<Boolean, String>() {
                            @Override
                            public String apply(Boolean aBoolean) throws Exception {
                                Logger.d(aBoolean);
                                //若是没有的情况下；
                                if (!aBoolean) {
                                    SearchRecordManager.getInstance(getApplicationContext()).insertData(result);
                                }
                                return "ok";
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {

                                switch (searchType.getText().toString()){
                                    case "商品":
                                        searchGoods("");
                                        break;
                                    case "用户":
                                        searchPersons("");
                                        break;
                                    case "标签":
                                        searchLabels("");
                                        break;
                                }



                            }
                        });
            }
        }
    }

    private void searchGoods(String key){
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(key)){
            bundle.putString("key", searchEdit.getText().toString());

        }else{
            bundle.putString("key",key);
        }
        transUi(SearchResultActivity.class, bundle);
        finish();
    }

    private void searchPersons(String key){
//        userListDao.getUserList("侯培宇","2");
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(key)){
            bundle.putString("userName", searchEdit.getText().toString());
        }else{
            bundle.putString("userName",key);
        }
        transUi(UserListAcitivity.class, bundle);
        finish();
    }
    private void searchLabels(String key){
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(key)){
            bundle.putString("label", searchEdit.getText().toString());
        }else{
            bundle.putString("label",key);
        }
        transUi(GoodsLabelsActivity.class, bundle);
        finish();
    }



    private void showTypeSelector(){
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                  String title =  searchTypes[options1];
                  searchType.setText(title);
            }
        })
                .setSubmitColor(ContextCompat.getColor(this, R.color.theme_color))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(this, R.color.text_color_light))//取消按钮文字颜色
                .setTitleText("选择搜索类型")
                .setOutSideCancelable(true)
                .setDividerColor(ContextCompat.getColor(this, R.color.text_color_gray))
                .setTextColorCenter(ContextCompat.getColor(this, R.color.text_color_normal)) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        pvOptions.setPicker(Arrays.asList(searchTypes));//三级选择器
        pvOptions.show();
    }


}
