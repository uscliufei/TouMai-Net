package com.runer.toumai.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.util.Arith;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.MySoldAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.base.Constant;
import com.runer.toumai.bean.GetGoodParam;
import com.runer.toumai.bean.GoodListBean;
import com.runer.toumai.bean.SearchBean;
import com.runer.toumai.dao.AddressDao;
import com.runer.toumai.dao.GoodsListDao;
import com.runer.toumai.dao.OrderDao;
import com.runer.toumai.dao.SellGoodsDao;
import com.runer.toumai.inter.OrderStateClickListener;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.NormalTipsDialog;
import com.runer.toumai.widget.UploadWaybillInformationDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;

/**
 *
 * <option value="">全部</option>
 <option value="1" <{if $smarty.get.status=='1'}>selected<{/if}>>竞价中-我领先</option>
 <option value="2" <{if $smarty.get.status=='2'}>selected<{/if}>>竞价中-已出局</option>
 <option value="3" <{if $smarty.get.status=='3'}>selected<{/if}>>已结束-已出局</option>
 <option value="4" <{if $smarty.get.status=='4'}>selected<{/if}>>已成交-待付尾款</option>
 <option value="5" <{if $smarty.get.status=='5'}>selected<{/if}>>已成交-待确认收货地址和型号款式</option>
 <option value="6" <{if $smarty.get.status=='6'}>selected<{/if}>>已成交-未发货</option>
 <option value="7" <{if $smarty.get.status=='7'}>selected<{/if}>>已成交-已发货</option>
 <option value="8" <{if $smarty.get.status=='8'}>selected<{/if}>>已成交-已收货</option>
 <option value="9" <{if $smarty.get.status=='9'}>selected<{/if}>>申诉处理中</option>
 </select>
 * Created by szhua on 2017/8/21/021.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MySoldFragment
 我卖的商品列表 我卖的
 */
public class MySoldFragment extends BaseLoadMoreFragment<MySoldAdapter> {

    private GoodsListDao goodsListDao ;
    private List<GoodListBean> datas ;
    private UploadWaybillInformationDialog uploadExpressDialog ;
    private OrderDao orderDao;
    private SellGoodsDao sellGoodsDao ;
    private NormalTipsDialog  norTipsDialog;

    @InjectView(R.id.type)
    public TextView type;
    @InjectView(R.id.searchEdit)
    public EditText searchEdit ;

    private String title ;
    private String status ;


    private List<SearchBean> searchTypes = AppUtil.getSoldSearchBean() ;



    @Override
    public MySoldAdapter getAdater() {
        return new MySoldAdapter(datas);
    }

    @Override
    protected int getLayOutRes() {
        return R.layout.fragment_my_sell_layout ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setItemCickListener();
        orderDao =new OrderDao(getContext(),this) ;
        sellGoodsDao =new SellGoodsDao(getContext(),this);
        goodsListDao =new GoodsListDao(getContext(),this) ;

        goodsListDao.getGoodsList(setParam());

        //进行刷行
        searchEdit.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                //进行刷新；
                ordersSearch();
            }
            return false;
        });



        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter,View view, int position) {
          // UiUtil.showLongToast(getContext(), String.valueOf(position));
               Bundle bundle =new Bundle();
               bundle.putString("id",baseQuickAdapter.getItem(position).getId());
               transUi(ProInfoActivity.class,bundle);
           }
       });
        type.setOnClickListener(view1->{
            showTypeSelect();
        });
    }



    //当前点击事件position
    private int currentPos;
    //设置不同订单状态下的点击事件；
    private void setItemCickListener() {
        baseQuickAdapter.setOrderStateClickListener(new OrderStateClickListener() {
            @Override
            public void onStateClick(final String order_id , int type ,int pos) {
                 currentPos =pos ;
                //上传物流信息;
                 if(type==OrderStateClickListener.LOGISTICS_TYPE){
                     if(uploadExpressDialog==null){
                         uploadExpressDialog=new UploadWaybillInformationDialog(getContext()) ;
                     }
                     uploadExpressDialog.setOnCommitClickListener(new UploadWaybillInformationDialog.OnCommitClickListener() {
                         @Override
                         public void onCommitClick(String express_id, String post_code ,String remark) {
                             orderDao.addExpress(order_id,express_id,post_code,remark);
                             showProgress(true);
                         }
                     });
                     uploadExpressDialog.showDialog();
                     //撤下商品
                 }else if(type==OrderStateClickListener.REVOKE_GOODS){
                                     final GoodListBean item = baseQuickAdapter.getData().get(pos);
                                     //有出价的情况下
                                     if(!"0".equals(item.getOffer_num())) {
                                         String nowPrice =item.getNow_price() ;
                                         if(!TextUtils.isEmpty(nowPrice)){
                                             //精确4位 ，
                                             double  overPayPrice = Arith.round(Arith.div(Double.parseDouble(nowPrice), Constant.over_rate),4);
                                        norTipsDialog =  NormalTipsDialog.show(getContext(), "信息", "确认撤销商品", String.format(getString(R.string.over_pay_tips),String.valueOf(overPayPrice)), "确定", new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     sellGoodsDao.stopGood(item.getId(),"2");
                                                     showProgress(true);
                                                 }
                                             });
                                         }
                                     }else{
                                         norTipsDialog = NormalTipsDialog.show(getContext(), "信息", "确认撤销商品", getString(R.string.over_pay_no_tips), "确定", new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 sellGoodsDao.stopGood(item.getId(),"1");
                                                 showProgress(true);
                                             }
                                         });
                                     }
                                     //用户地址；；；；；；
                 }else if(type==OrderStateClickListener.CHECK_ADDRESS){
                     norTipsDialog =       NormalTipsDialog.show(getContext(), "信息", "收货地址信息",TextUtils.isEmpty(order_id)?"买家未设置收货地址，平台已发通知至买家，提醒其设置收货地址。":order_id, "确定", new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                           if(norTipsDialog!=null){
                               norTipsDialog.dismiss();
                           }
                         }
                     });
                 }
            }
        });
    }
    @Override
    public void loadMore() {
        if(goodsListDao.hasMore()){
            goodsListDao.loadMore();
        }else{
            if(baseQuickAdapter!=null){
                baseQuickAdapter.loadMoreEnd();
            }
        }
    }
    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .first(R.drawable.decoration_divider_6dp)
                .type(0, R.drawable.decoration_divider_6dp).create();
    }
    @Override
    public void refresh() {
         goodsListDao.initAll();
         goodsListDao.getGoodsList(setParam());
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            datas =goodsListDao.getDatas() ;
            baseQuickAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                baseQuickAdapter.setEmptyView(getEmptyView("还没有出售商品"));
            }
        }else if(requestCode==RequestCode.CODE_3){
            UiUtil.showLongToast(getContext(),"上传物流信息成功");
            if(uploadExpressDialog!=null){
                uploadExpressDialog.dismiss();
            }
            //更新item显示
            datas.get(currentPos).setIs_post("1");
            baseQuickAdapter.notifyItemChanged(currentPos);
        }else if(requestCode==RequestCode.STOP_GOOD){
            if(norTipsDialog!=null){
                norTipsDialog.dismiss();
            }
            //更新状态
            datas.get(currentPos).setIs_order("0");
            datas.get(currentPos).setIs_end("1");
            baseQuickAdapter.notifyItemChanged(currentPos);
        }else if(requestCode==RequestCode.orderAddress){

        }
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

    private GetGoodParam setParam(){
        GetGoodParam getGoodParam =new GetGoodParam() ;
        getGoodParam.setIs_del("2");
        getGoodParam.setUser(AppUtil.getUserId(getContext()));
        getGoodParam.setStatus(status);
        getGoodParam.setTitle(title);
        return  getGoodParam ;
    }



}
