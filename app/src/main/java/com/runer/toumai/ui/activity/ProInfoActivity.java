package com.runer.toumai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.runer.liabary.flowlayout.TagFlowLayout;
import com.runer.liabary.indicator.CircleIndicator;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.util.Arith;
import com.runer.liabary.util.UiUtil;
import com.runer.liabary.widget.NoScrollListView;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.AnswersListAdapter;
import com.runer.toumai.adapter.GoodsPhotosAdapter;
import com.runer.toumai.adapter.OfferPriceAdapter;
import com.runer.toumai.adapter.ProImageAdapter;
import com.runer.toumai.adapter.ProinfoTagAdapter;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.base.Constant;
import com.runer.toumai.base.ToumaiApplication;
import com.runer.toumai.bean.FaqBean;
import com.runer.toumai.bean.ImgsBean;
import com.runer.toumai.bean.OfferInfo;
import com.runer.toumai.bean.ProInfoBean;
import com.runer.toumai.bean.QuestionBean;
import com.runer.toumai.bean.UserInfo;
import com.runer.toumai.dao.FollwDao;
import com.runer.toumai.dao.GoodFavDao;
import com.runer.toumai.dao.OfferDao;
import com.runer.toumai.dao.ProInfoDao;
import com.runer.toumai.dao.QuestionDao;
import com.runer.toumai.dao.UserInfoDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.util.FaqUtil;
import com.runer.toumai.widget.AddOneHandDialog;
import com.runer.toumai.widget.AnswerDialog;
import com.runer.toumai.widget.BuyOutDialog;
import com.runer.toumai.widget.DarkPriceDiolog;
import com.runer.toumai.widget.FAQListTipsDialog;
import com.runer.toumai.widget.FAQTips;
import com.runer.toumai.widget.NormalTipsDialog;
import com.runer.toumai.widget.TrusteeshipDialog;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.UMShareAPI;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import de.hdodenhof.circleimageview.CircleImageView;


/*商品详情界面*/
public class ProInfoActivity extends BaseActivity {
    @InjectView(R.id.pro_name)
    TextView proName;
    @InjectView(R.id.tags_data)
    TagFlowLayout tagsData;
    @InjectView(R.id.offer_price_list)
    NoScrollListView offerPriceList;
    @InjectView(R.id.answers_list)
    RecyclerView answersList;
    @InjectView(R.id.add_one_hand)
    TextView addOneHand;
    @InjectView(R.id.trusteeship_bt)
    TextView trusteeshipBt;
    @InjectView(R.id.intro)
    TextView intro;
    @InjectView(R.id.create_time)
    TextView createTime;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.user_name)
    TextView userName;
    @InjectView(R.id.new_price)
    TextView newPrice;
    @InjectView(R.id.price_type)
    TextView priceType;
    @InjectView(R.id.offer_num)
    TextView offerNum;
    @InjectView(R.id.post_way)
    TextView postWay;
    @InjectView(R.id.id_code)
    TextView idCode;
    @InjectView(R.id.user_age)
    TextView userAge;
    @InjectView(R.id.user_address)
    TextView userAddress;
    @InjectView(R.id.follow_button)
    TextView followButton;
    @InjectView(R.id.message_button)
    TextView messageButton;
    @InjectView(R.id.identify_info)
    TextView identifyInfo;
    @InjectView(R.id.collect_bt)
    TextView collectBt;
    @InjectView(R.id.question_input)
    EditText questionInput;
    @InjectView(R.id.photo_list)
    RecyclerView photoList;
    @InjectView(R.id.bottom)
    LinearLayout bottom;
    @InjectView(R.id.dark_time)
    TextView darkTime;
    @InjectView(R.id.bright_time)
    TextView brightTime;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.question_bt)
    TextView questionBt;
    @InjectView(R.id.count_down_view)
    CountdownView countDownView;
    @InjectView(R.id.user_header_img)
    CircleImageView userHeaderImg;
    @InjectView(R.id.left_time)
    TextView leftTime;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.circle_indicator)
    CircleIndicator circleIndicator;
    @InjectView(R.id.flaw_text)
    TextView flawText;
    @InjectView(R.id.sex_img)
    ImageView sexImg;
    @InjectView(R.id.faq_bt)
    ImageView faqBt;
    @InjectView(R.id.faq_offer_list_bt)
    ImageView faqOfferListBt;
    @InjectView(R.id.faq_state_bt)
    ImageView faqStateBt;
    @InjectView(R.id.faq_label_bt)
    ImageView faqLabelBt;
    @InjectView(R.id.xiaci_faq_icon)
    ImageView xiaciFaqIcon;
    @InjectView(R.id.buy_out_price)
    TextView buyOutPrice;
    @InjectView(R.id.buyout_bt)
    TextView buyoutBt;
    private TextView moreText;
    private View offerFootView;
    private boolean less;
    private AnswersListAdapter answerListAdapter;
    private String id;
    private ProInfoBean proInfoBean;
    private UserInfo userInfo;
    private List<OfferInfo> offerDatas = new ArrayList<>();
    private List<OfferInfo> lessOfferList = new ArrayList<>();
    private OfferPriceAdapter offerPriceAdapter;
    private List<QuestionBean> questionBeens;
    private ProInfoDao proInfoDao;
    private UserInfoDao userInfoDao;
    private FollwDao follwDao;
    private GoodFavDao goodFavDao;
    private OfferDao offerDao;
    private QuestionDao questionDao;
    private AddOneHandDialog addOneHandDialog;
    private int currentState;
    private TrusteeshipDialog trusteeshipDialog;
    private DarkPriceDiolog dakPriceDialog;
    DecimalFormat df = new DecimalFormat("##0.00");
    private AnswerDialog answerDialog;
    private BuyOutDialog  buyoutDialog;
    private NormalTipsDialog outOKDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_info);
        ButterKnife.inject(this);
        id = getIntent().getExtras().getString("id");
        view.setVisibility(View.GONE);
        setOfferInfo();
        initAnswerList();
         initView();
    }

    private void initView() {
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(R.drawable.share);
        proInfoDao = new ProInfoDao(this, this);
        userInfoDao = new UserInfoDao(this, this);
        offerDao = new OfferDao(this, this);
        follwDao = new FollwDao(this, this);
        goodFavDao = new GoodFavDao(this, this);
        questionDao = new QuestionDao(this, this);
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.chckeLogin(ProInfoActivity.this, true)) {
                    if (proInfoBean != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putString("id", proInfoBean.getId());
                        bundle.putString("img", NetConfig.GOODS_IMG + proInfoBean.getImg());
                        bundle.putString("des", String.format(getString(R.string.price_show_des), df.format(Float.parseFloat(proInfoBean.getNow_price())), proInfoBean.getTitle()));
                        transUi(SuccessfulBidActivity.class, bundle);
                    }
                }
            }
        });

        if (ToumaiApplication.isFaq) {
            xiaciFaqIcon.setVisibility(View.VISIBLE);
            xiaciFaqIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqBean faqBean = new FaqBean();
                    faqBean.setQuestion("“瑕疵说明”的意义是什么？");
                    faqBean.setAnswer(ProInfoActivity.this.getString(R.string.xiaci_des));
                    List<FaqBean> faqBeens = new ArrayList<>();
                    faqBeens.add(faqBean);
                    FAQTips.show(ProInfoActivity.this, faqBeens);
                }
            });


            faqBt.setVisibility(View.VISIBLE);
            faqBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQListTipsDialog.show(ProInfoActivity.this, FaqUtil.getChujiaFaq(ProInfoActivity.this));
                }
            });
            faqOfferListBt.setVisibility(View.VISIBLE);
            faqOfferListBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQListTipsDialog.show(ProInfoActivity.this, FaqUtil.getOfferListFaq(ProInfoActivity.this));
                }
            });
            faqStateBt.setVisibility(View.VISIBLE);
            faqStateBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQListTipsDialog.show(ProInfoActivity.this, FaqUtil.getOrderStateFaq(ProInfoActivity.this));
                }
            });
            faqLabelBt.setVisibility(View.VISIBLE);
            faqLabelBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqBean faqBean = new FaqBean();
                    faqBean.setQuestion("商品标签有什么用？");
                    faqBean.setAnswer(ProInfoActivity.this.getString(R.string.label_des));
                    List<FaqBean> faqBeens = new ArrayList<>();
                    faqBeens.add(faqBean);
                    FAQTips.show(ProInfoActivity.this, faqBeens);
                }
            });

        }
        buyoutBt.setOnClickListener(view1 -> {
            double onePrice = setUpBuyOutPrice();
            buyoutDialog =BuyOutDialog.show(this, String.valueOf(onePrice),anonymous -> {
                if (onePrice==0){
                    UiUtil.showLongToast(this,"最低一口价不符合格式");
                    return;
                }
                offerDao.buyout(AppUtil.getUserId(this),proInfoBean.getId(),anonymous);
                showProgress(true);
                buyoutDialog.dismiss();
            });
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        proInfoDao.getGoodsInfo(id, AppUtil.getUserId(this));
        offerDao.getOfferList(id);
        questionDao.getQuestList(id);
    }


    private int currentPos;
    private String currentContent;

    //初始化回答列表
    private void initAnswerList() {
        answerListAdapter = new AnswersListAdapter(questionBeens);
        answerListAdapter.setOnAnswerClickListener(new AnswersListAdapter.OnAnswerClickListener() {
            @Override
            public void onAnswerClick(final QuestionBean conent, final int pos) {
                answerDialog = AnswerDialog.show(ProInfoActivity.this, conent.getQuestion(), new AnswerDialog.OnAnwserClickListener() {
                    @Override
                    public void onCommitClick(String content) {
                        currentPos = pos;
                        currentContent = content;
                        questionDao.answerQuestion(conent.getId(), content);
                        showProgress(true);
                    }
                });
            }
        });
        answersList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        answersList.addItemDecoration(ItemDecorations.vertical(this).type(0, R.drawable.item_decoration_shape_withpadding).create());
        answersList.setAdapter(answerListAdapter);


    }

    //初始化offer列表
    private void setOfferInfo() {
        //出价列表的查询
        offerPriceAdapter = new OfferPriceAdapter();
        offerPriceAdapter.setDatas(offerDatas);
        offerPriceList.setAdapter(offerPriceAdapter);
        offerFootView = View.inflate(this, R.layout.offer_price_footer_layout, null);
        moreText = (TextView) offerFootView.findViewById(R.id.more_text);
        offerFootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (less) {
                    offerPriceAdapter.setDatas(lessOfferList);
                    moreText.setText("更多");
                } else {
                    offerPriceAdapter.setDatas(offerDatas);
                    moreText.setText("收起");
                }
                less = !less;
            }
        });
        offerPriceList.addFooterView(offerFootView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("商品详情");
    }

    private int chujia_type = 1;

    @OnClick({R.id.add_one_hand, R.id.trusteeship_bt, R.id.follow_button, R.id.collect_bt, R.id.message_button, R.id.question_bt, R.id.user_header_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //加一手出价
            case R.id.add_one_hand:
                if (AppUtil.chckeLogin(this, true)) {
                    proInfoDao.getNowPrice(id);
                    showProgress(true);
                    chujia_type = 1;
                }
                break;
            //托管出价
            case R.id.trusteeship_bt:
                if (AppUtil.chckeLogin(this, true)) {
                    proInfoDao.getNowPrice(id);
                    showProgress(true);
                    chujia_type = 2;
                }
                break;
            case R.id.follow_button:
                if (AppUtil.chckeLogin(this, true)) {
                    if (proInfoBean != null) {
                        if (TextUtils.isEmpty(proInfoBean.getFollow_id())) {
                            follwDao.addFollowUser(AppUtil.getUserId(this), proInfoBean.getUser_id());
                        } else {
                            follwDao.delFollowUser(AppUtil.getUserId(this), proInfoBean.getUser_id());
                        }
                    }
                }
                break;
            case R.id.collect_bt:
                if (AppUtil.chckeLogin(this, true)) {
                    if (proInfoBean != null) {
                        if (TextUtils.isEmpty(proInfoBean.getFav_id())) {
                            goodFavDao.addFav(AppUtil.getUserId(this), proInfoBean.getId());
                        } else {
                            goodFavDao.delFav(AppUtil.getUserId(this), proInfoBean.getId());
                        }
                    }
                }
                break;
            case R.id.message_button:
                if (AppUtil.chckeLogin(this, true)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", proInfoBean.getUser_id());
                    transUi(PersonalMessageList.class, bundle);
                }
                break;
            case R.id.question_bt:
                if (AppUtil.chckeLogin(this, true)) {
                    addQuestion();
                }
                break;
            case R.id.user_header_img:
                if (AppUtil.chckeLogin(this, true)) {
                    Intent intent = new Intent(this, TpyesProListActivity.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("user", userInfo.getUser_name());
                    intent.putExtra("uid", userInfo.getId());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_0) {
            proInfoBean = proInfoDao.getProInfoBean();
            if (proInfoBean != null) {
                setInfo();
            }
        } else if (requestCode == RequestCode.GET_USER_INFO) {
            userInfo = userInfoDao.getUserInfo();
            if (userInfo != null) {
                setUserData();
            }
            //报价列表
        } else if (requestCode == RequestCode.CODE_5) {
            offerDatas = offerDao.getDatas();
            if (offerDatas != null) {
                if (offerDatas.size() <= 5) {
                    offerFootView.setVisibility(View.GONE);
                    lessOfferList = offerDatas;
                } else {
                    offerFootView.setVisibility(View.VISIBLE);
                    lessOfferList = offerDatas.subList(0, 5);
                }
                offerPriceAdapter.setDatas(lessOfferList);
            } else {
                offerFootView.setVisibility(View.GONE);
            }
        } else if (requestCode == RequestCode.ADD_CONCERN) {
            UiUtil.showLongToast(this, "已关注");
            followButton.setText("已关注");
            proInfoBean.setFollow_id("ss");
            followButton.setBackgroundResource(R.drawable.orange_state);
        } else if (requestCode == RequestCode.QUIE_CONCERN) {
            UiUtil.showLongToast(this, "取消成功");
            proInfoBean.setFollow_id("");
            followButton.setText("关注");
            followButton.setBackgroundResource(R.drawable.gray_state);
        } else if (requestCode == RequestCode.ADD_FAV) {
            UiUtil.showLongToast(this, "收藏成功");
            proInfoBean.setFav_id("ss");
            UiUtil.setTextTopImage(collectBt, R.drawable.collect);
            collectBt.setText("已收藏");
        } else if (requestCode == RequestCode.DEL_FAV) {
            UiUtil.showLongToast(this, "取消收藏");
            proInfoBean.setFav_id("");
            UiUtil.setTextTopImage(collectBt, R.drawable.collect_un);
            collectBt.setText("收藏");
        } else if (requestCode == RequestCode.CODE_4) {
            questionBeens = questionDao.getQuestionBeens();
            answerListAdapter.setNewData(questionBeens);
        } else if (requestCode == RequestCode.CODE_3) {
            UiUtil.showLongToast(this, "问题提问成功");
            questionDao.getQuestList(id);
        } else if (requestCode == RequestCode.GOODS_NOW_PRICE) {
            if (proInfoDao.getNowPriceBean() != null) {
                if (currentState == Constant.AN_JIA_STATE) {
                    dakPriceDialog = DarkPriceDiolog.show(this, proInfoDao.getNowPriceBean(), new DarkPriceDiolog.OnDarkCommitListener() {
                        @Override
                        public void onCommit(String pirce, String anonymous, String allPrice) {
                            offerDao.addDark(id, AppUtil.getUserId(ProInfoActivity.this), pirce, anonymous, allPrice);
                            showProgressWithMsg(true, "正在出价");
                        }
                    });
                } else {
                    //加一手出价
                    if (chujia_type == 1) {
                        addOneHandDialog = AddOneHandDialog.show(this, proInfoDao.getNowPriceBean(), new AddOneHandDialog.OnOneHandCommitListener() {
                            @Override
                            public void onCommit(String pirce, String all_price, String is_auto, String anonymous) {
                                offerDao.addOffer(id, AppUtil.getUserId(ProInfoActivity.this), pirce, all_price, is_auto, anonymous);
                                showProgressWithMsg(true, "正在出价");
                            }
                        }, currentState);
                        //托管出价
                    } else {
                        trusteeshipDialog = TrusteeshipDialog.show(this, proInfoDao.getNowPriceBean(), new TrusteeshipDialog.TrusteeshipClickListener() {
                            @Override
                            public void onCommit(String price, String all_price, String anonymous) {
                                offerDao.addAutoOffer(AppUtil.getUserId(ProInfoActivity.this), id, price, anonymous, all_price);
                                showProgressWithMsg(true, "正在托管出价");
                            }
                        });
                    }
                }
            }
        } else if (requestCode == RequestCode.ADD_OFFER) {
            if (addOneHandDialog != null) {
                addOneHandDialog.dismiss();
            }
            UiUtil.showLongToast(this, "出价成功");
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            bundle.putString("img", NetConfig.GOODS_IMG + proInfoBean.getImg());
            bundle.putString("des", String.format(getString(R.string.price_show_des), df.format(Float.parseFloat(proInfoBean.getNow_price())), proInfoBean.getTitle()));
            transUi(SuccessfulBidActivity.class, bundle);
        } else if (requestCode == RequestCode.ADD_AUTO_OFFER) {
            if (trusteeshipDialog != null) {
                trusteeshipDialog.dismiss();
            }
            UiUtil.showLongToast(this, "出价成功");
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            bundle.putString("id", proInfoBean.getId());
            bundle.putString("img", NetConfig.GOODS_IMG + proInfoBean.getImg());
            bundle.putString("des", String.format(getString(R.string.price_show_des), df.format(Float.parseFloat(proInfoBean.getNow_price())), proInfoBean.getTitle()));
            transUi(SuccessfulBidActivity.class, bundle);
        } else if (requestCode == RequestCode.ADD_DARK) {
            if (dakPriceDialog != null) {
                dakPriceDialog.dismiss();
            }
            UiUtil.showLongToast(this, "出价成功");
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            bundle.putString("img", NetConfig.GOODS_IMG + proInfoBean.getImg());
            bundle.putString("des", String.format(getString(R.string.price_show_des), df.format(Float.parseFloat(proInfoBean.getNow_price())), proInfoBean.getTitle()));
            transUi(SuccessfulBidActivity.class, bundle);
        } else if (requestCode == RequestCode.CODE_6) {
            if (answerDialog != null) {
                answerDialog.dismiss();
            }
            questionBeens.get(currentPos).setAnswer(currentContent);
            answerListAdapter.notifyItemChanged(currentPos);
            UiUtil.showLongToast(ProInfoActivity.this, "问题提问成功");
            // questionDao.getQuestList(id);
            //一口价购买成功
        }else if (requestCode==RequestCode.ADD_BUYOUT){
            outOKDialog=  NormalTipsDialog.show(getApplicationContext(),"确认信息","购买成功","一口价买断成功，请确认收货地址和型号款式","确认",view1 -> {
                transUi(bundle -> {
                    bundle.putInt("index",0);
                },MyOrderActivity.class);
                outOKDialog.dismiss();
                finish();
            });
        }
    }

    //设置商品信息;
    private void setInfo() {

        proName.setText(proInfoBean.getTitle());
        List<ImgsBean> paths = new ArrayList<>();
        ImgsBean imgsBean = new ImgsBean();
        imgsBean.setImg(proInfoBean.getImg());
        paths.add(imgsBean);
        if (proInfoBean.getImgs() != null)
            paths.addAll(proInfoBean.getImgs());
        ProImageAdapter proImageAdapter = new ProImageAdapter(paths, this);
        viewPager.setAdapter(proImageAdapter);
        circleIndicator.setViewPager(viewPager);
        //设置商品图片:副图片
        photoList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        photoList.setAdapter(new GoodsPhotosAdapter(paths));
        Logger.d(proInfoBean.getIntro());
        RichText.from(proInfoBean.getIntro())
                .urlClick(url -> {
                    AppUtil.startBrower(getApplicationContext(), url);
                    return true;
                }).type(RichType.html).into(intro);
        RichText.from(proInfoBean.getFlaw()).urlClick(url -> {
            AppUtil.startBrower(getApplicationContext(), url);
            return true;
        }).into(flawText);
        // flawText.setText(proInfoBean.getFlaw());
        createTime.setText("发布时间  " + proInfoBean.getCreate_time());
        price.setText("￥" + proInfoBean.getPrice());
        newPrice.setText(df.format(Float.parseFloat(proInfoBean.getNow_price())));


        setUpBuyOutPrice();


        offerNum.setText("出价" + proInfoBean.getOffer_num() + "次");
        postWay.setText("交割方式  " + proInfoBean.getPost_way());
        if (TextUtils.isEmpty(proInfoBean.getDark_time()) || Long.parseLong(proInfoBean.getDark_time()) == 0) {
            darkTime.setText("暗价时长  无");
        } else {
            darkTime.setText("暗价时长  " + getTime(proInfoBean.getDark_time()));
        }
        brightTime.setText("明价时长  " + getTime(proInfoBean.getBright_time()));
        idCode.setText("货       号  " + proInfoBean.getId_code());
        //未收藏
        if (TextUtils.isEmpty(proInfoBean.getFav_id())) {
            UiUtil.setTextTopImage(collectBt, R.drawable.collect_un);
            collectBt.setText("收藏");
        } else {
            UiUtil.setTextTopImage(collectBt, R.drawable.collect);
            collectBt.setText("已收藏");
        }
        //未关注
        if (TextUtils.isEmpty(proInfoBean.getFollow_id())) {
            followButton.setBackgroundResource(R.drawable.gray_state);
            followButton.setText("关注");
        } else {
            followButton.setBackgroundResource(R.drawable.orange_state);
            followButton.setText("已关注");
        }
        //底部的显示----是自己的时候进行隐藏
        if (proInfoBean.getUser_id().equals(AppUtil.getUserId(this))) {
            bottom.setVisibility(View.GONE);
        } else {
            bottom.setVisibility(View.VISIBLE);
        }
        //已结束的情况
        if ("1".equals(proInfoBean.getIs_end())) {
            countDownView.setVisibility(View.GONE);
            leftTime.setText("已结束");
            currentState = Constant.END_STATE;
            priceType.setText("已结束");
            bottom.setVisibility(View.GONE);
            priceType.setBackgroundResource(R.drawable.gray_state);
            priceType.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            countDownView.start(proInfoBean.getCountdown() * 1000);
            countDownView.setVisibility(View.VISIBLE);
            leftTime.setText("剩余:");
            if ("1".equals(proInfoBean.getIs_heart())) {
                currentState = Constant.XIN_TIAO_STATE;
                priceType.setText("心跳时间");
                priceType.setBackgroundResource(R.drawable.heart_red_state);
                priceType.setTextColor(ContextCompat.getColor(this, R.color.white));
                if (TextUtils.isEmpty(proInfoBean.getBright_rest_time().getLeftTime())) {
                    priceType.setBackgroundResource(R.drawable.gray_state);
                    priceType.setTextColor(ContextCompat.getColor(this, R.color.white));
                    currentState = Constant.END_STATE;
                    bottom.setVisibility(View.GONE);
                    countDownView.setVisibility(View.GONE);
                    leftTime.setText("已结束");
                }
            } else {
                //降价的阶段
                if ("1".equals(proInfoBean.getFall_state())) {
                    currentState = Constant.JIANG_JIA_STATE;
                    priceType.setText("降价");
                    priceType.setBackgroundResource(R.drawable.green_state);
                    trusteeshipBt.setVisibility(View.GONE);
                    //未降价的阶段；
                } else {
                    //不是心跳时间;
                    if (proInfoBean.getSell_state().equals("1")) {
                        priceType.setText("暗价");
                        priceType.setBackgroundResource(R.drawable.light_orange_state);
                        currentState = Constant.AN_JIA_STATE;
                        trusteeshipBt.setVisibility(View.GONE);
                        addOneHand.setText("出价");
                    } else if (proInfoBean.getSell_state().equals("2")) {
                        priceType.setText("明价");
                        priceType.setBackgroundResource(R.drawable.orange_state);
                        currentState = Constant.MING_JIA_STATE;
                        trusteeshipBt.setVisibility(View.VISIBLE);
                        addOneHand.setText("加一手出价");
                    }
                }
            }
        }
        userInfoDao.getUserInfo(proInfoBean.getUser_id());
        if (!TextUtils.isEmpty(proInfoBean.getLabel())) {
            try {
                tagsData.setAdapter(new ProinfoTagAdapter(Arrays.asList(proInfoBean.getLabel_num().split(","))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //标签列表的显示;

    }

    /**
     * 设置最低一口价
     */
    private double setUpBuyOutPrice() {
        try {

            double boyoutPrice = 0.00;
            if (!TextUtils.isEmpty(proInfoBean.getBuyout_price())) {
                boyoutPrice = Double.valueOf(proInfoBean.getBuyout_price());
            }
            double nowPrice = 0.00;
            if (!TextUtils.isEmpty(proInfoBean.getNow_price())) {
                nowPrice = Double.valueOf(proInfoBean.getNow_price());
            }
            int ratio = 0;
            if (!TextUtils.isEmpty(proInfoBean.getRatio())) {
                ratio = Integer.parseInt(proInfoBean.getRatio());
            }
            //当最低的一口价小于当前的拍卖一口价的时候
            if (boyoutPrice < nowPrice * (Arith.div(ratio + 100, 100))) {
                boyoutPrice = nowPrice * (Arith.div(ratio + 100, 100));
            }
            buyOutPrice.setText("¥" + boyoutPrice);
            return  boyoutPrice ;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.00 ;
        }
    }

    //设置卖家信息
    private void setUserData() {
        Picasso.with(this).load(NetConfig.HEAD_PATH + userInfo.getHead()).placeholder(R.drawable.logo).into(userHeaderImg);
        userName.setText(userInfo.getUser_name());
        userAddress.setText(AppUtil.getUserAddress(userInfo));
        userAge.setText(userInfo.getAge() + "岁");
        if ("女".equals(userInfo.getSex())) {
            sexImg.setImageResource(R.drawable.woman);
        } else {
            sexImg.setImageResource(R.drawable.man);
        }
        if ("0".equals(userInfo.getAuth_state())) {
            identifyInfo.setText("未认证");
        } else if ("1".equals(userInfo.getAuth_state())) {
            identifyInfo.setText(userInfo.getAuth());
        } else if ("2".equals(userInfo.getAuth_state())) {
            identifyInfo.setText("认证申请中");
        } else if ("3".equals(userInfo.getAuth_state())) {
            identifyInfo.setText("认证失败");
        }
        //设置关注的显示;
        if (AppUtil.getUserId(ProInfoActivity.this).equals(userInfo.getId())) {
            followButton.setVisibility(View.GONE);
            messageButton.setVisibility(View.GONE);
        } else {
            followButton.setVisibility(View.VISIBLE);
            messageButton.setVisibility(View.VISIBLE);
        }
    }


    /*提出问题*/
    private void addQuestion() {
        hideInputMethod(questionInput);
        String quetion = questionInput.getText().toString();
        if (TextUtils.isEmpty(quetion)) {
            UiUtil.showLongToast(this, "输入的问题为空");
            return;
        }
        questionDao.addQestion(id, AppUtil.getUserId(this), quetion);
        showProgress(true);
        //清空输入的内容;
        questionInput.setText("");
    }

    //暗价时长、明价时长
    public String getTime(String seconds) {
        String time = "";
        time = Long.parseLong(seconds) / (60 * 60 * 24) + "天" + Long.parseLong(seconds) % (60 * 60 * 24) / (60 * 60) + "小时"
                + Long.parseLong(seconds) % (60 * 60 * 24) % (60 * 60) / 60 + "分钟";
        return time;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }

    @Override
    public void onRequestError(int requestCode, String errorInfo, int error_code) {
        super.onRequestError(requestCode, errorInfo, error_code);
        if ("余额不足".equals(errorInfo)) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            transUi(WalletActivity.class, bundle);
            if (addOneHandDialog != null && addOneHandDialog.isShowing()) {
                addOneHandDialog.dismiss();
            }
            if (dakPriceDialog != null && dakPriceDialog.isShowing()) {
                dakPriceDialog.dismiss();
            }
            if (trusteeshipDialog != null && trusteeshipDialog.isShowing()) {
                trusteeshipDialog.dismiss();
            }
        }
    }
}
