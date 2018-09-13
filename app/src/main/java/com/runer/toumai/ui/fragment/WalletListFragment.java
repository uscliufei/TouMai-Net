package com.runer.toumai.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.WalletAdapter;
import com.runer.toumai.base.BaseLoadMoreFragment;
import com.runer.toumai.base.Constant;
import com.runer.toumai.bean.AccountFlowBean;
import com.runer.toumai.bean.AliPayResultBean;
import com.runer.toumai.dao.AccountListsDao;
import com.runer.toumai.dao.AccoutDao;
import com.runer.toumai.dao.PayDao;
import com.runer.toumai.ui.activity.ProInfoActivity;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.util.OrderInfoUtil2_0;
import com.runer.toumai.util.PayResult;
import com.runer.toumai.widget.NormalTipsDialog;
import com.runer.toumai.widget.RechargeDialog;
import com.runer.toumai.widget.WithDrawCashDialog;
import com.runer.toumai.wxapi.WxPayUtil;

import org.apache.commons.codec.binary.StringUtils;

import java.util.List;
import java.util.Map;




/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * WalletListFragment
 * 我的钱包列表;
 */
public class WalletListFragment extends BaseLoadMoreFragment<WalletAdapter> {
    private View headerView;
    private View reChargeBt;
    private  View withDrawbt;
    private AccoutDao  accoutDao;
    private AccountListsDao accountListsDao ;
    private List<AccountFlowBean> datas;
    private TextView banlanceTv ;
    private WithDrawCashDialog  withDrawdilog;
    private String payType ;
    private PayDao payDao ;
    private int showType ;
    private NormalTipsDialog  withDrawdilogOk;

    public static WalletListFragment getInstance(int type){
        WalletListFragment walletListFragment =new WalletListFragment() ;
        walletListFragment.showType =type ;
        return  walletListFragment ;
    }
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")){
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        };
    };
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        headerView =View.inflate(getContext(), R.layout.header_wallet_layout,null);
        reChargeBt =headerView.findViewById(R.id.re_charge);
        withDrawbt =headerView.findViewById(R.id.with_draw_cash);
        banlanceTv = (TextView) headerView.findViewById(R.id.balance);
        super.onViewCreated(view, savedInstanceState);
        baseQuickAdapter.addHeaderView(headerView) ;
        //充值
        reChargeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeDialog.show(getContext(), new RechargeDialog.OnCommitClickListener() {
                    @Override
                    public void onCommitClick(String editStr, String paytype) {
                     if(!TextUtils.isEmpty(editStr)){
                         payType =paytype;
                         accoutDao.accountAdd(AppUtil.getUserId(getContext()),editStr,"");
                         showProgress(true);
                     }
                    }
                });
            }
         });
        //提现 ；
        withDrawbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             withDrawdilog =   WithDrawCashDialog.show(getContext(), new WithDrawCashDialog.OnWithDrawCashCommitLisetner() {
                    @Override
                    public void onCommit(String cash, String type, String account,String nickName) {
                        //金额未加载出来的情况下
                        if (TextUtils.isEmpty(accoutDao.getBalance())){
                            UiUtil.showLongToast(getContext(),"用户余额信息正在加载中，请等待！");
                            return;
                        }
                        try{
                            Double balance = Double.valueOf(accoutDao.getBalance());
                            Double toRechargeAmount =Double.valueOf(cash) ;
                            if (toRechargeAmount>balance){
                                UiUtil.showLongToast(getContext(),"提现金额不能高于账户余额");
                                return;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            UiUtil.showLongToast(getContext(),"解析失误！请重新进行尝试！");
                            return;
                        }

                        accoutDao.withDraw(AppUtil.getUserId(getContext()),cash,type,account,nickName);
                        showProgress(true);
                    }
                });
            }
        });
        showProgress(true);
        payDao =new PayDao(getContext(),this) ;
        //跳转
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               if("0".equals(baseQuickAdapter.getData().get(position).getGoods_id())||TextUtils.isEmpty(baseQuickAdapter.getData().get(position).getGoods_id())){
               }else{
                   Bundle bundle =new Bundle() ;
                   bundle.putString("id",baseQuickAdapter.getItem(position).getGoods_id());
                   transUi(ProInfoActivity.class,bundle);
               }
            }
        });
        baseQuickAdapter.setType("wallet");
        //显示充值弹窗
        if(showType==1){
            RechargeDialog.show(getContext(), new RechargeDialog.OnCommitClickListener() {
                @Override
                public void onCommitClick(String editStr, String paytype) {
                    if(!TextUtils.isEmpty(editStr)){
                        payType =paytype;
                        accoutDao.accountAdd(AppUtil.getUserId(getContext()),editStr,"");
                        showProgress(true);
                    }
                }
            });
        }
    }
    @Override
    public WalletAdapter getAdater() {
        return new WalletAdapter(datas);
    }

    @Override
    public RecyclerView.ItemDecoration getDecoration(Context context) {
        return ItemDecorations.vertical(context)
                .type(0, R.drawable.decoration_divider_6dp).create();
    }
    @Override
    public void loadMore() {
        if(accountListsDao.hasMore()){
            accountListsDao.loadMore();
        }else{
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    baseQuickAdapter.loadMoreEnd();
                }
            }, 1000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(accoutDao==null&&accountListsDao==null){
            accoutDao =new AccoutDao(getContext(),this) ;
            accoutDao.getBalance(AppUtil.getUserId(getContext()),"0");
            accountListsDao =new AccountListsDao(getContext(),this);
            accountListsDao.getAccountList(AppUtil.getUserId(getContext()),"0","0");
        }else{
            accountListsDao.refresh();
            accoutDao.getBalance(AppUtil.getUserId(getContext()),"0");
        }
    }

    @Override
    public void refresh() {
        accountListsDao.refresh();
        accoutDao.getBalance(AppUtil.getUserId(getContext()),"0");
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.LOADMORE){
            datas =accountListsDao.getDatas() ;
            baseQuickAdapter.setNewData(datas);
            if(datas==null||datas.isEmpty()){
                baseQuickAdapter.setHeaderAndEmpty(true);
             baseQuickAdapter.setEmptyView(getEmptyViewFixedHeight("暂无余额明细"));
            }
        }else if(requestCode==RequestCode.CODE_4){
            if(!TextUtils.isEmpty(accoutDao.getBalance())){
                banlanceTv.setText("￥"+accoutDao.getBalance());
            }
        }else if(requestCode==RequestCode.WITH_DRAW){
            UiUtil.showLongToast(getContext(),"提交成功");
            if(withDrawdilog!=null){
                withDrawdilog.dismiss();
            }
            accountListsDao.refresh();
            accoutDao.getBalance(AppUtil.getUserId(getContext()),"0");

            //show TIPS
            showWithDrawOkTips();


        }else if(requestCode==RequestCode.CODE_2){
            String orderId =accoutDao.getOrderId();
            accoutDao.accountUpdate(orderId,payType);
            showProgress(true);
        }else if(requestCode==RequestCode.GET_PAY_INFO){
          //ceo@qkarma.com
          if("ali".equals(payType)){
                 payDao.aliPay(accoutDao.getOrderId());
          }else {
                payDao.wechatPay(accoutDao.getOrderId());
          }
        }else if(requestCode==RequestCode.AliPay){
            aliPay(payDao.getAliPayResultBean());
        }else if(requestCode==RequestCode.WEchatPay){
            WxPayUtil.wxPay(getActivity(),payDao.getWechatPayBean());
        }
    }

    private void aliPay(final AliPayResultBean aliPayResultBean ){
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(aliPayResultBean.getApp_id(),aliPayResultBean.getBiz_content().getTotal_amount(),aliPayResultBean.getBiz_content().getSubject(),aliPayResultBean.getBiz_content().getBody(),aliPayResultBean.getBiz_content().getOut_trade_no(),true);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//        String sign = OrderInfoUtil2_0.getSign(params, Constant.RSA2_PRIVATE, true);
//        final String orderInfo = orderParam + "&" + sign;
//        Logger.d(orderInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(aliPayResultBean.getStr(),true);
            // Map<String, String> result = alipay.payV2("alipay_sdk=alipay-sdk-php-20161101&app_id=2017072707919444&biz_content=%7B%22body%22%3A%22%5Cu6295%5Cu4e70%5Cu7f51%5Cu5145%5Cu503c%22%2C%22subject%22%3A%22%5Cu6295%5Cu4e70%5Cu7f51%5Cu5145%5Cu503c%22%2C%22out_trade_no%22%3A%22201709025651%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Flocalhost%2Ftoumai%2Fapi%2Fnotify_alipay.php&sign_type=RSA2&timestamp=2017-09-02+13%3A27%3A49&version=1.0&sign=ftrFX5c3PKW%2B1JZVv60XEs%2BCOr14IrBDfg3waAT11G8YggTMqcgXQv85OiSrYVAMS9wUfOW1QgqmiNVDEHriq7NNz8j0hgaRkcDgmlLLvyDjtKPQOu1UE8C3b9%2BqvX%2F%2FwycpkSYwHXIvCiWPwEUoOBPRo3MJzLabOTOYH3OcjqPW5niZ6d5x63sb6eC5DfFXVpyUwm7ceURfG%2B6%2FIoJ%2BDynjQEKU1iq3STrckGZxNqVjwNHoFPVgRXAHHRZr5fkG2YowWk%2Byycv39eSpbckx03hl%2FixLdwUVQyBzL1XdfxBNh%2FTtOfhL4Bn6vzybc7%2Fbviu3XvSzSkITVzYY692nkg%3D%3D",true);
                Logger.d(result);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 用户提交提现申请之后，弹出一个弹窗，文字内容为：
     * "小河豚已收到您的提现要求！投买网没有 “三个工作日”、“七个工作日”等提现周期限制，小河豚人工验证收款账户的准确性后会立即打款哒。投买网事儿多河豚少，有时不能立即到账，但是小河豚保证24小时内会完成提现哒。您在提现过程中遇到的任何问题，欢迎通过“投买网”微信公众号联系小河豚哟~"，下面有个按钮为“我知道了”，点击按钮关闭弹窗。
     */
   private void showWithDrawOkTips(){
      withDrawdilogOk = NormalTipsDialog.show(getContext(),"成功",
               "提现申请成功",
               getString(R.string.with_draw_ok_tips),
               "我知道了",
                view -> {
                withDrawdilogOk.dismiss();
                }
       );
   }



}
