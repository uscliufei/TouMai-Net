package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.MyOderAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.bean.SearchBean;
import com.runer.toumai.dao.AppealDao;
import com.runer.toumai.dao.AppealInfo;
import com.runer.toumai.dao.OrderDao;
import com.runer.toumai.inter.OrderStateClickListener;
import com.runer.toumai.ui.activity.LogisticalActivity;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.AppealDialog;
import com.runer.toumai.widget.NormalTipsDialog;
import com.runer.toumai.widget.SelectPhotoView;
import com.yanzhenjie.album.Album;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MyOderFragment
 * 订单列表项
 */
public class MyOderFragment extends BaseLoadMoreFragment<MyOderAdapter>  implements  OrderStateClickListener{

    private OrderDao orderDao ;
    private List<GoodListBean> datas ;
    private SelectPhotoView  selectPhotoView;
    private ArrayList<String>  pathList;
    private AppealDao appealDao ;
    private NormalTipsDialog  normalTipsDialog;
    private int currentPos ;
    private NormalTipsDialog  commitDialog;
    private NormalTipsDialog  appealTipDialog;
    public static final int PHOTO_SELECT_CODE = 999;
    private AppealDialog appealDialog ;
    @InjectView(R.id.type)
    public TextView type;
    @InjectView(R.id.searchEdit)
    public EditText searchEdit ;

    private String title ;
    private String status  ;


    private List<SearchBean> searchTypes = AppUtil.getBuySearchBean() ;

    public static MyOderFragment getInstance(){
        MyOderFragment myOderFragment =new MyOderFragment() ;
        return  myOderFragment ;
    }

    @Override
    protected int getLayOutRes() {
        return  R.layout.fragment_my_sell_layout ;
    }

    @Override
    public MyOderAdapter getAdater() {
        return new MyOderAdapter(datas);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        type.setOnClickListener(view1 -> {
            showTypeSelect();
        });
        //进行刷行
        searchEdit.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
              //进行刷新；
              ordersSearch();
            }
            return false;
        });

        baseQuickAdapter.setOrderStateClickListener(this);
        orderDao =new OrderDao(getContext(),this);
        orderDao.getOrderList(AppUtil.getUserId(getContext()),"","");
        appealDao =new AppealDao(getContext(),this);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle =new Bundle() ;
                bundle.putString("id",baseQuickAdapter.getItem(position).getId());
                transUi(ProInfoActivity.class,bundle);
            }
        });
    }
    @Override
    public void loadMore() {
        baseQuickAdapter.loadMoreEnd();
    }
    @Override
    public void refresh() {
        orderDao.getOrderList(AppUtil.getUserId(getContext()),title,status);
    }
    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
    }


    //item上面的各个button的点击
    @Override
    public void onStateClick(String order_id, int type, final int pos) {
        currentPos =pos;
        //支付尾款
        if(type==OrderStateClickListener.PAY_LEFT_MONRY){
            /*确定要为商品：fdsfffsdfuuuu4444sf支付尾款，￥23.97吗？*/
            final GoodListBean item = datas.get(pos);
            normalTipsDialog =NormalTipsDialog.show(getContext(), "信息", "支付尾款", String.format(getString(R.string.pay_left_des), item.getTitle(), item.getPrice()), "确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            orderDao.orderPay(item.getOrder_id());
                    showProgress(true);
                }
            });
        //确认收货
        }else if(type==OrderStateClickListener.GOT_ORDER_TYPE){
           commitDialog =  NormalTipsDialog.show(getContext(),"信息","确认收货","您确认收到此件物品了？","确定",view -> {
                commitDialog.dismiss();
                orderDao.orderGet(datas.get(pos).getOrder_id());
                showProgress(true);
            });
            //查看物流
        }else if(type==OrderStateClickListener.LOGISTICS_TYPE){
            Bundle budle =new Bundle() ;
            budle.putString("order_id",datas.get(pos).getOrder_id());
            transUi(LogisticalActivity.class,budle);
            //申述
        }else if(type==OrderStateClickListener.APPEAL_TYPE){
           //显示申诉的提示；
           appealTipDialog =  NormalTipsDialog.show(getContext(),"信息","提示",
                    getString(R.string.appeal_tips_dialog_text),"确定", view -> {
                    appealTipDialog.dismiss();
                    appealSelect(pos);
            });
            //查看申诉结果
        }else if(type==OrderStateClickListener.CHECK_APPEAL_INFO){
             appealDao.getAppealInfo(datas.get(pos).getAppeal_id(),datas.get(pos).getOrder_id());
             showProgress(true);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                pathList = Album.parseResult(data);
                selectPhotoView.addImgPath(pathList);
            }
        }
    }




    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        //订单列表
        if(requestCode== RequestCode.CODE_4){
            datas =orderDao.getDatas();
            baseQuickAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                baseQuickAdapter.setEmptyView(getEmptyView("暂无订单"));
            }
            //申诉详情
        }if(requestCode==RequestCode.APPEAL_INFO){
            AppealInfo appealInfo =appealDao.getAppealInfo() ;
            normalTipsDialog = NormalTipsDialog.show(getContext(), "信息", "仲裁结果", appealInfo.getReason(), "确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normalTipsDialog.dismiss();
                }
            });
            //上传申述
        }else if(requestCode==RequestCode.APPEAL_CREATE){
            Logger.d("上传文字成功");
            String appeal_id = appealDao.getAppeal_id() ;
            if(selectPhotoView.getSelectedPaht()!=null){
                for (String s : selectPhotoView.getSelectedPaht()) {
                    appealDao.apppealUpload(appeal_id,s);
                }
            }
            UiUtil.showLongToast(getContext(),"申诉成功");
            if(appealDialog!=null){
                appealDialog.dismiss();
            }
            datas.get(currentPos).setIs_appeal("1");
            datas.get(currentPos).setAppeal_end("0");
            baseQuickAdapter.notifyItemChanged(currentPos);
        }else if(requestCode==RequestCode.APPEAL_UPLOAD){
            Logger.d("上传图片成功");
        }else if(requestCode==RequestCode.ORDER_GET){
            UiUtil.showLongToast(getContext(),"确认收货成功");
            datas.get(currentPos).setIs_get("1");
            baseQuickAdapter.notifyItemChanged(currentPos);
        }else if(requestCode==RequestCode.ORDER_PAY){
            UiUtil.showLongToast(getContext(),"支付尾款成功");
            datas.get(currentPos).setIs_pay("1");
            baseQuickAdapter.notifyItemChanged(currentPos);
        }
    }


    /**
     * 申诉的具体流程；
     * @param pos
     */
    private void appealSelect(int pos){
        selectPhotoView= AppealDialog.show(getContext(), new SelectPhotoView.OnItemSelectPicClickListener() {
                    @Override
                    public void onAddPic(int leftNum) {
                        Album.album(MyOderFragment.this)
                                .toolBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                                .statusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                                .title("图库")
                                .selectCount(leftNum)
                                .columnCount(3)
                                .camera(true)
                                .requestCode(PHOTO_SELECT_CODE)
                                .start();
                    }
                    @Override
                    public void onPictureClick(String path) {

                    }
                }
                , datas.get(pos), new AppealDialog.OnCommitClickListener() {
                    @Override
                    public void onCommitClick(String content ,AppealDialog appealDialog) {
                        MyOderFragment.this.appealDialog =appealDialog ;
                        appealDao.createAppeal(datas.get(pos).getOrder_id(),AppUtil.getUserId(getContext()),content);
                        showProgress(true);
                    }
                }
        );
    }


    private void showTypeSelect(){
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SearchBean title = searchTypes.get(options1);
                type.setText(title.getName());
                type.setTag(title.getType());
                ordersSearch();
            }
        })
                .setSubmitColor(ContextCompat.getColor(getContext(), R.color.theme_color))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(getContext(), R.color.text_color_light))//取消按钮文字颜色
                .setTitleText("选择搜索类型")
                .setOutSideCancelable(true)
                .setDividerColor(ContextCompat.getColor(getContext(), R.color.text_color_gray))
                .setTextColorCenter(ContextCompat.getColor(getContext(), R.color.text_color_normal)) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        pvOptions.setPicker(searchTypes);//三级选择器
        pvOptions.show();
    }

    private void ordersSearch(){
        title =searchEdit.getText().toString() ;
        status = (String) type.getTag();
        refresh();
        showProgress(true);
    }

}
