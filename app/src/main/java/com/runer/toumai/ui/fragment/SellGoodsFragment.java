package com.runer.toumai.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.runer.liabary.util.AnimUtil;
import com.runer.liabary.util.Arith;
import com.runer.liabary.util.UiUtil;
import com.runer.liabary.widget.CustomPopWindow;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.BiddingPredictioAdapter;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.Constant;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.BiddingPredictio;
import com.runer.toumai.bean.FaqBean;
import com.runer.toumai.bean.GetGoodParam;
import com.runer.toumai.bean.TagBean;
import com.runer.toumai.dao.AccoutDao;
import com.runer.toumai.dao.SellGoodsDao;
import com.runer.toumai.inter.MyAfterTextChangeListener;
import com.runer.toumai.ui.activity.BiddingPredictionActivity;
import com.runer.toumai.ui.activity.RulesCheckActivity;
import com.runer.toumai.ui.activity.SuccessfulBidActivity;
import com.runer.toumai.ui.activity.WalletActivity;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.util.FaqUtil;
import com.runer.toumai.widget.AnjiaRulesDialog;
import com.runer.toumai.widget.BailTipsDialog;
import com.runer.toumai.widget.BidingView;
import com.runer.toumai.widget.FAQListTipsDialog;
import com.runer.toumai.widget.FAQTips;
import com.runer.toumai.widget.MyEditText;
import com.runer.toumai.widget.NormalTipsDialog;
import com.runer.toumai.widget.OrderView;
import com.runer.toumai.widget.SelectPhotoView;
import com.runer.toumai.widget.TagsView;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/8/22.
 * 出售商品界面；
 */
public class SellGoodsFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.edit_goodsName)
    EditText editGoodsName;
    @InjectView(R.id.tags_view)
    TagsView tagsView;
    @InjectView(R.id.edit_lable)
    EditText editLable;
    @InjectView(R.id.sub_mit)
    TextView subMit;
    @InjectView(R.id.add_lable)
    TextView addLable;
    @InjectView(R.id.big_pic)
    ImageView bigPic;
    @InjectView(R.id.select_big_pic)
    TextView selectBigPic;
    @InjectView(R.id.anchor)
    TextView anchor;
    @InjectView(R.id.select_photo)
    SelectPhotoView selectPhoto;
    @InjectView(R.id.jiang_rules_bt)
    TextView jiangRulesBt;
    @InjectView(R.id.anjia_bt)
    CheckBox anjiaBt;
    @InjectView(R.id.goods_intro)
    EditText goodsIntro;
    @InjectView(R.id.edit_price)
    MyEditText editPrice;
    @InjectView(R.id.time_length)
    TextView timeLength;
    @InjectView(R.id.sure_sell)
    TextView sureSell;
    @InjectView(R.id.linear_submit)
    LinearLayout linearSubmit;
    @InjectView(R.id.reduce_price)
    CheckBox reducePrice;
    @InjectView(R.id.darkPrice_linear)
    LinearLayout darkPriceLinear;
    @InjectView(R.id.min_price)
    MyEditText minPrice;
    @InjectView(R.id.brightTimeH)
    EditText brightTimeH;
    @InjectView(R.id.brightTimeM)
    EditText brightTimeM;
    @InjectView(R.id.darkTimeH)
    EditText darkTimeH;
    @InjectView(R.id.darkTimeM)
    EditText darkTimeM;
    @InjectView(R.id.edit_flaw)
    EditText editFlaw;
    @InjectView(R.id.free_diliver)
    CheckBox freeDiliver;
    @InjectView(R.id.an_rules_check)
    TextView anRulesCheck;
    @InjectView(R.id.ming_rule_checl)
    TextView mingRuleChecl;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.goods_name_text)
    TextView goodsNameText;
    @InjectView(R.id.charge_diliver)
    RadioButton chargeDiliver;
    @InjectView(R.id.agree_rules)
    CheckBox agreeRules;
    @InjectView(R.id.brightTimeD)
    EditText brightTimeD;
    @InjectView(R.id.rules_des)
    TextView rulesDes;
    @InjectView(R.id.darkTimeD)
    EditText darkTimeD;
    @InjectView(R.id.faq_state_bt)
    ImageView faqStateBt;
    @InjectView(R.id.faq_xiaci_bt)
    ImageView faqXiaciBt;
    @InjectView(R.id.faq_label_bt)
    ImageView faqLabelBt;
    @InjectView(R.id.baozhengjin_rules)
    TextView baozhengjinRules;
    @InjectView(R.id.stock_one)
    RadioButton stockOne;
    @InjectView(R.id.stock_nums)
    RadioButton stockNums;
    @InjectView(R.id.socket_num_eidt)
    EditText socketNumEidt;
    @InjectView(R.id.stock_edit_container)
    LinearLayout stockEditContainer;
    @InjectView(R.id.stock_group)
    RadioGroup stockGroup;

    //-------重发相关的控件！！
    @InjectView(R.id.infinited_repeat_radio)
    RadioButton infinitedRepeatRadio;
    @InjectView(R.id.limit_repeated_radio)
    RadioButton limitRepeatedRadio;
    @InjectView(R.id.repeat_radio_group)
    RadioGroup repeatRadioGroup;
    @InjectView(R.id.repeat_num_edit)
    EditText repeatNumEdit;
    @InjectView(R.id.repeat_container)
    LinearLayout repeatContainer;
    @InjectView(R.id.stock_one_repeat_container)
    LinearLayout stockOneRepeatContainer;
    @InjectView(R.id.no_hour_repeated_limit)
    RadioButton noHourRepeatedLimit;
    @InjectView(R.id.hour_repeated_limit)
    RadioButton hourRepeatedLimit;
    @InjectView(R.id.hour_repeated_group)
    RadioGroup hourRepeatedGroup;
    @InjectView(R.id.limit_day)
    EditText limitDay;
    @InjectView(R.id.limit_hour)
    EditText limitHour;
    @InjectView(R.id.limit_minute)
    EditText limitMinute;
    @InjectView(R.id.limit_time_length)
    TextView limitTimeLength;
    @InjectView(R.id.stock_num_limit_repeated_container)
    LinearLayout stockNumLimitRepeatedContainer;
    @InjectView(R.id.repeated_day_hour_container)
    LinearLayout repeated_day_hour_container;
    @InjectView(R.id.repeat_off_check)
    CheckBox repeatOffCheck;
    @InjectView(R.id.limit_repeated_editor_container)
    LinearLayout limit_repeated_editor_container;



    @InjectView(R.id.one_priec_persent)
    MyEditText onePriecPersent;
    @InjectView(R.id.one_price_num)
    MyEditText onePriceNum;
    @InjectView(R.id.check_biding_bt)
    TextView checkBidingBt;
    @InjectView(R.id.header_layout)
    View headerLayout ;
    @InjectView(R.id.replace_view)
    View replace_view ;



    private List<TagBean> tagBeens;
    //默认不接受降价；
    private String is_fall = Constant.AGREE_REDUCE_DEFAULT;
    private File file;
    private SellGoodsDao sellGoodsDao;
    private String post_way = Constant.DEFAULT_POST_WAY;
    private ArrayList<String> pathList;
    DecimalFormat df = new DecimalFormat("###.00");
    int num = 0;
    private String goods_id;
    private AccoutDao accoutDao;
    private BailTipsDialog bailDialog;
    private CustomPopWindow  bidingPop;


    //二期的一些参数；
    String stock;
    String is_repost;
    String repost_type;
    String repost_times;
    String repost_end_time;
    String ratio ;
    String buyout_price ;
    private NormalTipsDialog nortips;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sell_goods, container, false);
        ButterKnife.inject(this, view);
        sellGoodsDao = new SellGoodsDao(getContext(), this);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTotalTime();
        initView();

    }


    @Override
    public void onClick(View v) {
        if (v == selectBigPic) {
            Album.album(getActivity())
                    .toolBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                    .statusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                    .title("图库")
                    .selectCount(1)
                    .columnCount(3)
                    .camera(true)
                    .requestCode(TAKE_BIG_PIC)
                    .start();
        } else if (v == addLable) {
            linearSubmit.setVisibility(View.VISIBLE);
            String lable = editLable.getText().toString();
            if (TextUtils.isEmpty(lable)) {
                return;
            }
            TagBean tagBean = new TagBean();
            tagBean.setTagName(lable);
            tagBeens.add(tagBean);
            editLable.setText("");
            linearSubmit.setVisibility(View.GONE);
            tagsView.setTagBeanList(tagBeens);

        } else if (v == subMit) {
            String lable = editLable.getText().toString();
            if (TextUtils.isEmpty(lable)) {
                UiUtil.showLongToast(getContext(), "请先填写标签");
                return;
            }
            TagBean tagBean = new TagBean();
            tagBean.setTagName(lable);
            tagBeens.add(tagBean);
            editLable.setText("");
            linearSubmit.setVisibility(View.GONE);
            tagsView.setTagBeanList(tagBeens);
        } else if (v == jiangRulesBt) {
            Bundle bundle = new Bundle();
            bundle.putString("id", "14");
            transUi(RulesCheckActivity.class, bundle);
        } else if (v == sureSell) {
            if (canUploadGoods) {
                //查看余额
                if (TextUtils.isEmpty(editPrice.getText().toString())) {
                    UiUtil.showLongToast(getContext(), "请填写底价");
                    return;
                }
                if ("0".equals(editPrice.getText().toString()) || Double.parseDouble(editPrice.getText().toString()) < 1 || Double.parseDouble(editPrice.getText().toString()) > 10000000) {
                    UiUtil.showLongToast(getContext(), "底价填写范围为1~一千万。不能为0");
                    return;
                }
                if (accoutDao == null) {
                    accoutDao = new AccoutDao(getContext(), this);
                }
                accoutDao.getBalance(AppUtil.getUserId(getContext()));
                showProgress(true);
            }
        } else if (v == anRulesCheck) {
            Bundle bundle = new Bundle();
            bundle.putString("id", "12");
            transUi(RulesCheckActivity.class, bundle);
        } else if (v == mingRuleChecl) {
            Bundle bundle = new Bundle();
            bundle.putString("id", "13");
            transUi(RulesCheckActivity.class, bundle);
        } else if (v == bigPic) {
            Album.album(this)
                    .toolBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                    .statusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                    .title("图库")
                    .selectCount(1)
                    .columnCount(3)
                    .camera(true)
                    .requestCode(TAKE_BIG_PIC)
                    .start();
        } else if (v == rulesDes) {
            Intent intent = new Intent(getContext(), RulesCheckActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", "5");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (v == baozhengjinRules) {
            Intent intent = new Intent(getContext(), RulesCheckActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", "23");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    //上传数据；
    private void uploadParam() {
        if (TextUtils.isEmpty(editGoodsName.getText().toString())) {
            UiUtil.showLongToast(getContext(), "填写商品的名称");
            return;
        }
        String lable = "";
        for (int i = 0; i < tagBeens.size(); i++) {
            lable = lable + tagBeens.get(i).getTagName() + ",";
        }
        //label不为空的情况下
        if (!TextUtils.isEmpty(lable))
            lable = lable.substring(0, lable.length() - 1);
        if (TextUtils.isEmpty(goodsIntro.getText().toString())) {
            UiUtil.showLongToast(getContext(), "请填写商品描述");
            return;
        }
        if (TextUtils.isEmpty(editPrice.getText().toString())) {
            UiUtil.showLongToast(getContext(), "请填写底价");
            return;
        }

        if ("0".equals(editPrice.getText().toString()) || Double.parseDouble(editPrice.getText().toString()) < 1 || Double.parseDouble(editPrice.getText().toString()) > 10000000) {
            UiUtil.showLongToast(getContext(), "底价填写范围为1~一千万。不能为0");
            return;
        }
        if (TextUtils.isEmpty(editFlaw.getText().toString())) {
            UiUtil.showLongToast(getContext(), "请描述商品的瑕疵");
            return;
        }
        //判断明价的时常
        if (brightTime == 0) {
            UiUtil.showLongToast(getContext(), "请填写明价的时长(必填)");
            return;
        }
        if (!freeDiliver.isChecked()) {
            UiUtil.showLongToast(getContext(), getString(R.string.touamai_jiaoge_des));
            return;
        }
        if (!agreeRules.isChecked()) {
            UiUtil.showLongToast(getContext(), "请同意投买网交易规则");
            return;
        }

        //库存
        stock =socketNumEidt.getText().toString() ;
        //自动重发；
        //is_repost;
        //repost_type 类型；

        canUploadGoods = false;
        //repost_times 重发的次数；
        repost_times=repeatNumEdit.getText().toString() ;
        if (TextUtils.isEmpty(repost_times)){
            repost_times ="1" ;
        }
        //ratio上浮的比例
        ratio =onePriecPersent.getText().toString() ;
        if (TextUtils.isEmpty(ratio)||"0".equals(ratio)){
            UiUtil.showLongToast(getContext(),"比例应改不小于1！");
            return;
        }
        buyout_price =onePriceNum.getText().toString() ;

        if (TextUtils.isEmpty(buyout_price)){
            UiUtil.showLongToast(getContext(),"请填写一口价！！");
            return;
        }

        //您输入的“最低一口价”相较底价，低于“一口价较当前价上浮比例”，相当于输入的“最低一口价”不产生作用哦~可以尝试把“最低一口价”提高一点，参照竞价预测表制定最适合的商品价格。

        getRepostTime();

        try {
            file = Luban.with(getActivity()).load(file).get();
            sellGoodsDao.sellGoods(editGoodsName.getText().toString(),
                    lable, file, goodsIntro.getText().toString(),
                    editPrice.getText().toString(), is_fall,
                    String.valueOf(darkTime), String.valueOf(brightTime),
                    post_way, editFlaw.getText().toString(),stock,is_repost,repost_type,repost_times,repost_end_time,ratio,buyout_price);
            showProgress(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //是否可以上传商品;
    private boolean canUploadGoods = true;

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_0) {
            goods_id = sellGoodsDao.getGoods_id();
            List<File> compressFiles = new ArrayList<>();
            if (pathList != null) {
                showProgressWithMsg(true, "正在上传图片文件...");
                for (int i = 0; i < pathList.size(); i++) {
                    try {
                        File file = Luban.with(getActivity()).load(new File(pathList.get(i))).get();
                        compressFiles.add(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            sellGoodsDao.addImagList(goods_id, compressFiles, "");
        } else if (requestCode == RequestCode.CODE_1) {
            showProgress(false);
            canUploadGoods = true;
            UiUtil.showLongToast(getContext(), "出售商品成功");
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            bundle.putString("id", goods_id);
            bundle.putString("des", String.format(getString(R.string.price_show_des), df.format(Float.parseFloat(editPrice.getText().toString())), editGoodsName.getText().toString()));
            transUi(SuccessfulBidActivity.class, bundle);
            clearAllData();
        } else if (requestCode == RequestCode.CODE_5) {
            String balance = accoutDao.getBalance();
            //有账户余额的情况下
            if (!TextUtils.isEmpty(balance)) {
                try {
                    //商品底价
                    double lowPrice = Double.parseDouble(editPrice.getText().toString());
                    double nowBanlance = Double.parseDouble(balance);
                    //小于100元的情况下
                    if (nowBanlance < 100) {
                        bailDialog = BailTipsDialog.show(getContext(), "100", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 1);
                                transUi(WalletActivity.class, bundle);
                                if (bailDialog != null) {
                                    bailDialog.dismiss();
                                }
                            }
                        });
                    } else {
                        //余额不足的情况下（如果商品底价高于1000元，则检查卖家钱包余额是否大于底价的10%）
                        if (Arith.mul(lowPrice, 0.1) > nowBanlance) {
                            bailDialog = BailTipsDialog.show(getContext(), String.valueOf(Arith.mul(lowPrice, 0.1)), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("type", 1);
                                    transUi(WalletActivity.class, bundle);
                                    if (bailDialog != null) {
                                        bailDialog.dismiss();
                                    }
                                }
                            });
                            //可以发布的情况下
                        } else {
                            uploadParam();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                UiUtil.showLongToast(getContext(), "请求账户余额出错!");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //设置时间的监听
    private void setAllTime() {

        brightTimeD.addTextChangedListener(new MyAfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                setTotalTime();
            }
        });
        brightTimeH.addTextChangedListener(new MyAfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                setTotalTime();
            }
        });
        brightTimeM.addTextChangedListener(new MyAfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                setTotalTime();
            }
        });
        darkTimeH.addTextChangedListener(new MyAfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                setTotalTime();
            }
        });
        darkTimeM.addTextChangedListener(new MyAfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                setTotalTime();
            }
        });
        darkTimeD.addTextChangedListener(new MyAfterTextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                setTotalTime();
            }
        });

    }



    //获得自动重发的时间；
    private void  getRepostTime(){

        int mingDay = 0;
        int mingHour = 0;
        int mingMi = 0;

        //明价的天数
        if (!TextUtils.isEmpty(limitDay.getText().toString())) {
            mingDay = Integer.parseInt(limitDay.getText().toString());
        }
        //明价的小时
        if (!TextUtils.isEmpty(limitHour.getText().toString())) {
            mingHour = Integer.parseInt(limitHour.getText().toString());
        }
        //明价的分钟
        if (!TextUtils.isEmpty(limitMinute.getText().toString())) {
            mingMi = Integer.parseInt(limitMinute.getText().toString());
        }

        //总分钟
        int totalMin = mingDay * 24 * 60 + mingHour * 60 + mingMi ;
        //天数：
        int totalDay = totalMin / (24 * 60);
        //小时
        int totalHour = totalMin / 60 - totalDay * 24;
        //分钟
        int min = totalMin % 60;

        Logger.d("持续时间总计:" + totalDay + "天" + totalHour + "小时" + min + "分钟");
        //todo时间的设定
        //timeLength.setText("持续时间总计:" + totalDay + "天" + totalHour + "小时" + min + "分钟");
        repost_end_time = String.valueOf(mingHour * 3600 + mingMi * 60 + mingDay * 24 * 3600);

    }


    //明价时间
    private int brightTime;
    //暗价时常
    private int darkTime;

    //设置总时间
    private void setTotalTime() {
        int mingDay = 0;
        int mingHour = 0;
        int mingMi = 0;
        int anDay = 0;
        int anHour = 0;
        int anMi = 0;
        //明价的天数
        if (!TextUtils.isEmpty(brightTimeD.getText().toString())) {
            mingDay = Integer.parseInt(brightTimeD.getText().toString());
        }
        //明价的小时
        if (!TextUtils.isEmpty(brightTimeH.getText().toString())) {
            mingHour = Integer.parseInt(brightTimeH.getText().toString());
        }
        //明价的分钟
        if (!TextUtils.isEmpty(brightTimeM.getText().toString())) {
            mingMi = Integer.parseInt(brightTimeM.getText().toString());
        }
        //暗价的天数
        if (!TextUtils.isEmpty(darkTimeD.getText().toString()) && anjiaBt.isChecked()) {
            anDay = Integer.parseInt(darkTimeD.getText().toString());
        }
        //暗价的小时
        if (!TextUtils.isEmpty(darkTimeH.getText().toString().trim()) && anjiaBt.isChecked()) {
            anHour = Integer.parseInt(darkTimeH.getText().toString());
        }
        //暗价的分钟
        if (!TextUtils.isEmpty(darkTimeM.getText().toString()) && anjiaBt.isChecked()) {
            anMi = Integer.parseInt(darkTimeM.getText().toString());
        }
        //总分钟
        int totalMin = mingDay * 24 * 60 + mingHour * 60 + mingMi + anDay * 24 * 60 + anHour * 60 + anMi;
        //天数：
        int totalDay = totalMin / (24 * 60);
        //小时
        int totalHour = totalMin / 60 - totalDay * 24;
        //分钟
        int min = totalMin % 60;
        timeLength.setText("持续时间总计:" + totalDay + "天" + totalHour + "小时" + min + "分钟");
        brightTime = mingHour * 3600 + mingMi * 60 + mingDay * 24 * 3600;
        darkTime = anHour * 3600 + anMi * 60 + anDay * 24 * 3600;
    }


    //清空数据
    public void clearAllData() {
        try {
            editGoodsName.setText("");
            tagBeens = new ArrayList<>();
            is_fall = Constant.AGREE_REDUCE_DEFAULT;
            file = null;
            post_way = Constant.DEFAULT_POST_WAY;
            pathList = new ArrayList<>();
            num = 0;
            tagsView.setTagBeanList(null);
            bigPic.setImageResource(R.drawable.host_img_add);
            selectPhoto.addImgPath(null);
            selectPhoto.clearData();
            goodsIntro.setText("");
            editFlaw.setText("");
            reducePrice.setChecked(false);
            minPrice.setText("");
            editPrice.setText("");
            anjiaBt.setChecked(false);
            brightTimeH.setText("");
            brightTimeM.setText("");
            darkTimeH.setText("");
            brightTimeD.setText("");
            darkTimeD.setText("");
            darkTimeM.setText("");
            freeDiliver.setChecked(true);
            agreeRules.setChecked(true);
            showStockNum(false);

            //初始化重发相关的东西；
            initRepeated();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_BIG_PIC) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> pathList = Album.parseResult(data);
                file = new File(pathList.get(0));
                Picasso.with(getActivity()).load(new File(pathList.get(0))).resize(100, 100).placeholder(R.drawable.empty_img).into(bigPic);
            }
        } else if (requestCode == PHOTO_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                pathList = Album.parseResult(data);
                selectPhoto.addImgPath(pathList);
            }
            //裁剪以后的操作
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            //进行压缩;
            Flowable.just(new File(getContext().getCacheDir(), CROPO_CACHE_PAHT))
                    .observeOn(Schedulers.io())
                    .map(new Function<File, File>() {
                        @Override
                        public File apply(@NonNull File file) throws Exception {
                            return Luban.with(getActivity()).load(file).get();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) throws Exception {
                        }
                    });
        } else if (resultCode == Crop.RESULT_ERROR) {
            UiUtil.showLongToast(getContext(), "裁剪失败!");
        }
    }

    @Override
    public void onRequestFaild(int requestCode, String errorNo, String errorMessage) {
        super.onRequestFaild(requestCode, errorNo, errorMessage);
        if (requestCode == RequestCode.CODE_1) {
            canUploadGoods = true;
            UiUtil.showLongToast(getContext(), "上传失败");
            clearAllData();
        } else if (requestCode == RequestCode.CODE_0) {
            UiUtil.showLongToast(getContext(), "发布商品失败请重新提交");
            canUploadGoods = true;
        }
    }

    @Override
    public void onRequestError(int requestCode, String errorInfo, int error_code) {
        super.onRequestError(requestCode, errorInfo, error_code);
        if (requestCode == RequestCode.CODE_1) {
            canUploadGoods = true;
            UiUtil.showLongToast(getContext(), "上传失败");
            clearAllData();
        } else if (requestCode == RequestCode.CODE_0) {
            UiUtil.showLongToast(getContext(), "发布商品失败请重新提交");
            canUploadGoods = true;
        }
    }

    @Override
    public void onNoConnect() {
        super.onNoConnect();
        clearAllData();
        canUploadGoods = true;
        UiUtil.showLongToast(getContext(), "发布商品失败");
    }


    /**
     * 显示库存的数量
     *
     * @param show
     */
    private void showStockNum(boolean show) {
        if (show) {
            socketNumEidt.setText("1");
            stockEditContainer.setVisibility(View.VISIBLE);
        } else {
            socketNumEidt.setText("1");
            stockEditContainer.setVisibility(View.GONE);
        }
    }


    private void initRepeated() {
        repeatOffCheck.setChecked(false);
        limitDay.setText("");
        limitHour.setText("");
        limitMinute.setText("");
        infinitedRepeatRadio.setChecked(true);
        repeatNumEdit.setText("1");
    }


    /**
     * 显示重复发的次数
     *
     * @param type          ;
     * @param showContainer
     * @param showNumRadio
     */
    private void showRepeatedContainer(int type, boolean showContainer, boolean showNumRadio) {
        //就一件的时候
        if (type == 1) {

            stockOneRepeatContainer.setVisibility(View.VISIBLE);
            stockNumLimitRepeatedContainer.setVisibility(View.GONE);

            if (showContainer) {
                repeatContainer.setVisibility(View.VISIBLE);
                repeatNumEdit.setText("1");
                if (showNumRadio) {
                    limit_repeated_editor_container.setVisibility(View.VISIBLE);
                } else {
                    limit_repeated_editor_container.setVisibility(View.GONE);
                }

            } else {
                repeatContainer.setVisibility(View.GONE);
                repeatNumEdit.setText("1");
            }

            //多件的情况下；
        } else {
            stockOneRepeatContainer.setVisibility(View.GONE);
            stockNumLimitRepeatedContainer.setVisibility(View.VISIBLE);
            //
            if (showNumRadio) {
                noHourRepeatedLimit.setChecked(false);
                repeated_day_hour_container.setVisibility(View.VISIBLE);
            } else {
                noHourRepeatedLimit.setChecked(true);
                repeated_day_hour_container.setVisibility(View.GONE);
            }


        }


    }


    /**
     * 初始化view；
     */
    private void initView(){
        anjiaBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //在已完成暗价规则学习的情况下，并且有一次出局收益
                    if (AppUtil.checkDarkLearn(getContext()) && AppUtil.checkIsMakeUp(getContext())) {
                        darkPriceLinear.setVisibility(View.VISIBLE);
                        setTotalTime();
                    } else {
                        AnjiaRulesDialog.show(getContext(), AppUtil.checkDarkLearn(getContext()), AppUtil.checkIsMakeUp(getContext()));
                        anjiaBt.setChecked(false);
                    }
                } else {
                    darkPriceLinear.setVisibility(View.GONE);
                    setTotalTime();
                }
            }
        });
        bigPic.setOnClickListener(this);
        selectBigPic.setOnClickListener(this);
        baozhengjinRules.setOnClickListener(this);
        tagBeens = new ArrayList<>();
        tagsView.setTagBeanList(tagBeens);
        tagsView.setOnTagDeleteListener(new TagsView.OnTagDeleteListener() {
            @Override
            public void onDeleteTagListener(int pos) {
                tagBeens.remove(pos);
                tagsView.setTagBeanList(tagBeens);
            }
        });
        //选择图片======
        selectPhoto.setOnItemSelectPicClickListener(new SelectPhotoView.OnItemSelectPicClickListener() {
            @Override
            public void onAddPic(int leftNum) {
                Album.album(SellGoodsFragment.this)
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
        });

        addLable.setOnClickListener(this);
        subMit.setOnClickListener(this);
        sureSell.setOnClickListener(this);
        anjiaBt.setOnClickListener(this);
        anRulesCheck.setOnClickListener(this);
        mingRuleChecl.setOnClickListener(this);
        jiangRulesBt.setOnClickListener(this);
        rulesDes.setOnClickListener(this);
        leftBack.setVisibility(View.INVISIBLE);
        stockOne.setChecked(true);


        freeDiliver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    post_way = "包邮";
                } else {
                    post_way = "不包邮";
                }
            }
        });
        title.setText("出售商品");
        reducePrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    is_fall = "1";
                } else {
                    is_fall = "0";
                }
            }
        });
        //可接受的最低价
        minPrice.setOnImputCompleteListener(new MyEditText.OnImputCompleteListener() {
            @Override
            public void onImputComplete() {
                if (!TextUtils.isEmpty(minPrice.getText().toString())) {
                    editPrice.setText(String.valueOf(df.format(Float.parseFloat(minPrice.getText().toString()) / 0.9594)));
                }
            }
        });

        //底价
        editPrice.setOnImputCompleteListener(new MyEditText.OnImputCompleteListener() {
            @Override
            public void onImputComplete() {
                if (!TextUtils.isEmpty(editPrice.getText().toString())) {
                    minPrice.setText(String.valueOf(df.format(Float.parseFloat(editPrice.getText().toString()) * 0.9594)));
                }
            }
        });

        //一口价
        onePriceNum.setOnImputCompleteListener(new MyEditText.OnImputCompleteListener() {
            @Override
            public void onImputComplete() {
              if (TextUtils.isEmpty(onePriceNum.getText().toString())){
                  UiUtil.showLongToast(getContext(),"请输入一口价");
                  return;
              }
              Double onePrice = Double.valueOf(onePriceNum.getText().toString());
                double lowPrice ;//底价
                if (TextUtils.isEmpty(editPrice.getText().toString())){
                    lowPrice = 0;
                }
                lowPrice =Double.parseDouble(editPrice.getText().toString());
                if (onePrice<lowPrice){
                    nortips = NormalTipsDialog.show(getContext(),"信息","最低一口价","您输入的“最低一口价”相较底价，低于“一口价较当前价上浮比例”，相当于输入的“最低一口价”不产生作用哦~可以尝试把“最低一口价”提高一点，参照竞价预测表制定最适合的商品价格。","知道了",view1->{
                       nortips.dismiss();
                    });
                }



            }
        });

        //一口价较当前价上浮比例
        onePriecPersent.setOnImputCompleteListener(new MyEditText.OnImputCompleteListener() {
            @Override
            public void onImputComplete() {
                ratio =onePriecPersent.getText().toString() ;
                if (TextUtils.isEmpty(ratio)){
                    onePriecPersent.setText("20");
                }
                //
                double lowPrice ;//底价
                if (TextUtils.isEmpty(editPrice.getText().toString())){
                   lowPrice = 0;
                }
                lowPrice =Double.parseDouble(editPrice.getText().toString());
                String onePrice =String.valueOf(Arith.mul(lowPrice,(Arith.div(Integer.parseInt(onePriecPersent.getText().toString()),100)+1)));
                onePriceNum.setText(onePrice);
            }
        });


        //库存相关的内容
        stockGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    //库存一件的时候
                    case R.id.stock_one:
                        showStockNum(false);
                        //设置自动重发的情况
                        showRepeatedContainer(1, false, false);
                        repeatOffCheck.setChecked(false);
                        break;
                    //库存多件的时候
                    case R.id.stock_nums:
                        showStockNum(true);
                        //设置自动重发的情况；
                        showRepeatedContainer(2, false, false);
                        break;
                }
            }
        });

        //一件商品的时候自动重发按钮
        repeatOffCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            //自动重发
            if (b) {
                is_repost = "1" ;
                //设置无限重发的默认模式；
                infinitedRepeatRadio.setChecked(true);
                showRepeatedContainer(1, true, false);
                //不自动重发
            } else {
                is_repost ="" ;
                showRepeatedContainer(1, false, false);
            }
        });

        //一件的时候自动重发两种不同情况
        repeatRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                //无限重发
                case R.id.infinited_repeat_radio:
                    repost_type ="1" ;
                    showRepeatedContainer(1, true, false);
                    break;
                //有限次重发
                case R.id.limit_repeated_radio:
                    repost_type="2" ;
                    showRepeatedContainer(1, true, true);
                    break;
            }
        });

        //多件库存的时候的两种不同情况；
        hourRepeatedGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                //无时间限制的重发
                case R.id.no_hour_repeated_limit:
                    repost_type ="3" ;
                    showRepeatedContainer(2, true, false);
                    //有时间限制的重发
                    break;
                case R.id.hour_repeated_limit:
                    repost_type ="4" ;
                    showRepeatedContainer(2, true, true);
                    break;
            }
        });

        checkBidingBt.setOnClickListener(view1 -> {
            onBidingClick();
        });
        //计算总时长
        setAllTime();
    }

    public static final int PHOTO_SELECT_CODE = 999;
    public static final int TAKE_BIG_PIC = 1001;
    //裁剪头像的缓存地址
    public static final String CROPO_CACHE_PAHT = "imgine_cloud_crop";

    @Override
    public void onResume() {
        super.onResume();
        //开启faq模式的情况下
        if (ToumaiApplication.isFaq) {
            faqStateBt.setVisibility(View.VISIBLE);
            faqStateBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQListTipsDialog.show(getContext(), FaqUtil.getInputPriceFaq(getContext()));
                }
            });
            faqXiaciBt.setVisibility(View.VISIBLE);
            faqXiaciBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqBean faqBean = new FaqBean();
                    faqBean.setQuestion("“瑕疵说明”的意义是什么？");
                    faqBean.setAnswer(getContext().getString(R.string.xiaci_des));
                    List<FaqBean> faqBeens = new ArrayList<>();
                    faqBeens.add(faqBean);
                    FAQTips.show(getContext(), faqBeens);
                }
            });
            faqLabelBt.setVisibility(View.VISIBLE);
            faqLabelBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqBean faqBean = new FaqBean();
                    faqBean.setQuestion("商品标签有什么用？");
                    faqBean.setAnswer(getContext().getString(R.string.label_des));
                    List<FaqBean> faqBeens = new ArrayList<>();
                    faqBeens.add(faqBean);
                    FAQTips.show(getContext(), faqBeens);
                }
            });
        }
    }


    //展示竞价列表的预测
    private void showBidingPop(Double price ,Integer ratio ,Double  buyoutPrice ){
        BidingView bindview = new BidingView(getContext(),()->{
            if (bidingPop!=null){
                bidingPop.dissmiss();
            }
        });
         bidingPop = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(bindview)
                .setOutsideTouchable(true)
                .setFocusable(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .create();
        if (android.os.Build.VERSION.SDK_INT >=24)
        { int[] a = new int[2]; replace_view.getLocationInWindow(a);
            bidingPop.showAtLocation((getActivity()).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+replace_view.getHeight());
        } else{  bidingPop.showAsDropDown(replace_view); }
        //设置数据参数；
        bindview.setData(price ,ratio,buyoutPrice);
    }

    //竞价列表的预测；
    private void onBidingClick(){
        //弹出activity；
        if (TextUtils.isEmpty(editPrice.getText().toString())){
            UiUtil.showLongToast(getContext(),"请输入最低价");
            return;
        }
        if (TextUtils.isEmpty(onePriceNum.getText().toString())){
            UiUtil.showLongToast(getContext(),"请输入一口价的最低价");
            return;
        }
        if (TextUtils.isEmpty(onePriecPersent.getText().toString())){
            UiUtil.showLongToast(getContext(),"请输入一口价的比例");
            return;
        }
        double currentPrice = Double.parseDouble(editPrice.getText().toString());
        int ratio = Integer.parseInt(onePriecPersent.getText().toString());
        double onePrice =Double.parseDouble(onePriceNum.getText().toString());

        if (onePrice<currentPrice){
            nortips = NormalTipsDialog.show(getContext(),"信息","最低一口价","您输入的“最低一口价”相较底价，低于“一口价较当前价上浮比例”，相当于输入的“最低一口价”不产生作用哦~可以尝试把“最低一口价”提高一点，参照竞价预测表制定最适合的商品价格。","知道了",view1->{
                nortips.dismiss();
            });
            return;
        }


        showBidingPop(currentPrice,ratio,onePrice);
    }




}
