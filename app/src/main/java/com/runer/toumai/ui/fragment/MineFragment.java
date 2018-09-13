package com.runer.toumai.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.runer.liabary.recyclerviewUtil.HorizontalItemDecoration;
import com.runer.liabary.recyclerviewUtil.ItemDecorations;
import com.runer.liabary.recyclerviewUtil.VerticalItemDecoration;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.adapter.UserCenterItemAdapter;
import com.runer.toumai.base.BaseFragment;
import com.runer.toumai.base.Constant;
import com.runer.toumai.bean.MineListItemBean;
import com.runer.toumai.bean.UserInfo;
import com.runer.toumai.dao.MessageDao;
import com.runer.toumai.dao.UserInfoDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.ui.activity.AboutUs;
import com.runer.toumai.ui.activity.AddressActivity;
import com.runer.toumai.ui.activity.CollectAcitivty;
import com.runer.toumai.ui.activity.LoginActivity;
import com.runer.toumai.ui.activity.MessageActivity;
import com.runer.toumai.ui.activity.MyOrderActivity;
import com.runer.toumai.ui.activity.PersonalMessageListAcitivty;
import com.runer.toumai.ui.activity.PocketActivity;
import com.runer.toumai.ui.activity.RulesListAtvitiy;
import com.runer.toumai.ui.activity.UserConcernActivity;
import com.runer.toumai.ui.activity.UserInfoActivity;
import com.runer.toumai.ui.activity.WalletActivity;
import com.runer.toumai.util.AppUtil;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by szhua on 2017/7/17/017.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * MineFragment
 * 个人中心
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.item_list)
    RecyclerView itemList;
    private UserCenterItemAdapter userCenterItemAdapter;
    private CircleImageView headerImag;
    private TextView userNameTv;
    private View headerContainer;
    private ImageView sexImage;
    private TextView address;
    private TextView timeTv;
    private View footerView;
    private TextView exitTv;
    private MessageDao  messageDao;


    private TextView goods_num ;

    private TextView offer_num ;

    private TextView  income ;

    /**
     * 个人中心十二宫格的按钮调整为九宫格按钮，
     * 第一行为“钱包”、“通知”、“私信”；
     * 第二行为“我买的”、“我卖的”、“我赚的”；
     * 第三行为“关注与收藏”、“规则说明”、“联系客服”。
     * （即“个人信息”取消，“钱包”和“口袋”合并为“钱包”，
     * 行为记录三项不变，
     * “通知与消息”拆开为“通知”和“私信”，
     * “关注用户”和“收藏商品”合并为“关注与收藏”，
     * “收货地址管理”取消，“规则说明”不变，“联系我们”改为“联系客服”）
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.inject(this, view);
        footerView = inflater.inflate(R.layout.exit_account_footer, container, false);
        exitTv = (TextView) footerView.findViewById(R.id.exit_account);
        exitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.exitLogo(getContext());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //动态添加列表项；

        initMineData();

    }

    /**
     * 初始化一些数据；
     */
    private void initMineData() {
        ArrayList<MineListItemBean> mineListItemBeens = new ArrayList<>();
        //mineListItemBeens.add(new MineListItemBean("个人信息","", R.drawable.personal_info));
        mineListItemBeens.add(new MineListItemBean("钱包", "", R.drawable.wallet));
        mineListItemBeens.add(new MineListItemBean("通知", "", R.drawable.noti));
        mineListItemBeens.add(new MineListItemBean("私信", "", R.drawable.noti));
        mineListItemBeens.add(new MineListItemBean("我买的", "", R.drawable.mine_ru));
        mineListItemBeens.add(new MineListItemBean("我卖的", "", R.drawable.sale));
        mineListItemBeens.add(new MineListItemBean("我赚的", "", R.drawable.mine_zhuan));
        mineListItemBeens.add(new MineListItemBean("关注与收藏", "", R.drawable.collect));
        mineListItemBeens.add(new MineListItemBean("规则说明", "", R.drawable.rules));//10
        mineListItemBeens.add(new MineListItemBean("联系客服", "", R.drawable.connect_us));//11
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        userCenterItemAdapter = new UserCenterItemAdapter(mineListItemBeens);
        userCenterItemAdapter.addFooterView(footerView);
        //添加点击事件
        userCenterItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                Bundle bundle;
                switch (position) {
                    //钱包
                    case 0:
                        transUi(WalletActivity.class, null);
                        break;
                    //通知
                    case 1:
                        transUi(MessageActivity.class, null);
                        break;
                    //私信
                    case 2:
                        transUi(PersonalMessageListAcitivty.class, null);
                        break;
                    case 3:
                        bundle =new Bundle() ;
                        bundle.putInt("index",0);
                        transUi(MyOrderActivity.class, bundle);
                        break;
                    case 4:
                        bundle =new Bundle() ;
                        bundle.putInt("index",1);
                        transUi(MyOrderActivity.class, bundle);
                        break;
                    case 5:
                        bundle =new Bundle() ;
                        bundle.putInt("index",2);
                        transUi(MyOrderActivity.class, bundle);
                        break;
                    //关注与收藏
                    case 6:
                        transUi(CollectAcitivty.class, null);
                        break;
                    //规则说明
                    case 7:
                        transUi(RulesListAtvitiy.class, null);
                        break;
                    //联系客服
                    case 8:
                        transUi(AboutUs.class, null);
                        break;
                    case 9:
                        transUi(AddressActivity.class, null);
                        break;
                    case 10:
                        transUi(RulesListAtvitiy.class, null);
                        break;
                    case 11:
                        transUi(AboutUs.class, null);
                        break;
                }
            }
        });
        userCenterItemAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.mine_header_layout, null);
        headerImag = (CircleImageView) headView.findViewById(R.id.header);
        userNameTv = (TextView) headView.findViewById(R.id.username);
        sexImage = (ImageView) headView.findViewById(R.id.sex_icon);
        address = (TextView) headView.findViewById(R.id.address);
        timeTv = (TextView) headView.findViewById(R.id.time);
        headerContainer = headView.findViewById(R.id.header_container);

        offer_num = (TextView) headView.findViewById(R.id.sell_num);
        goods_num = (TextView) headView.findViewById(R.id.price_num);
        income = (TextView) headView.findViewById(R.id.has_num);



        userCenterItemAdapter.addHeaderView(headView);
        VerticalItemDecoration decoration = ItemDecorations.vertical(getContext())
                .last(R.drawable.item_bg_deco)
                .type(0, R.drawable.item_decoration_shape).create();
        HorizontalItemDecoration horizontalItemDecoration =ItemDecorations.horizontal(getContext())
                .type(0, R.drawable.item_decoration_shape_div).create();

        itemList.setLayoutManager(linearLayoutManager);
        itemList.addItemDecoration(decoration);
        itemList.addItemDecoration(horizontalItemDecoration);
        itemList.setHasFixedSize(true);
        itemList.setAdapter(userCenterItemAdapter);
        headerContainer.setOnClickListener(this);

    }

    private UserInfoDao userInfoDao;
    @Override
    public void onResume() {
        super.onResume();
        if (AppUtil.chckeLogin(getContext(), false)) {
            if (userInfoDao == null) {
                userInfoDao = new UserInfoDao(getContext(), this);
            }
            userInfoDao.getUserInfo(AppUtil.getUserId(getContext()));
        }

        if(AppUtil.chckeLogin(getContext(),false)){
            if(messageDao==null){
                messageDao =new MessageDao(getContext(),this);
            }
            messageDao.getMsgNum(AppUtil.getUserId(getContext()));
        }else{
            if(userCenterItemAdapter!=null){
                userCenterItemAdapter.getData().get(6).setHasMsg(false);
                userCenterItemAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    @Override
    public void onClick(View v) {
        if (v == headerContainer) {
            transUi(UserInfoActivity.class, null);
        }
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);

        //设置用户的详情；
        if (requestCode == RequestCode.GET_USER_INFO) {
            UserInfo userInfo = userInfoDao.getUserInfo();
            AppUtil.setUserInfo(getContext(),userInfo);
            if (userInfo != null) {
                Picasso.with(getContext()).load(NetConfig.HEAD_PATH + userInfo.getHead()).placeholder(R.drawable.logo).into(headerImag);
                if ("男".equals(userInfo.getSex())) {
                    sexImage.setImageResource(R.drawable.man);
                } else {
                    sexImage.setImageResource(R.drawable.woman);
                }
                if(TextUtils.isEmpty(userInfo.getProvince())){
                    address.setText("暂无地址");
                }else{
                    address.setText(userInfo.getProvince()+userInfo.getCity()+userInfo.getArea());
                }
                if(TextUtils.isEmpty(userInfo.getId())){
                    timeTv.setText("用户ID:暂无");
                }else{
                    timeTv.setText("用户ID:"+userInfo.getId());
                }
                userNameTv.setText(userInfo.getUser_name());

                goods_num.setText(userInfo.getGoods_num());
                offer_num.setText(userInfo.getOffer_num());
                income.setText(userInfo.getIncome());


            }
        }else if(requestCode== RequestCode.Msg_num){
            //检查是否有信息未读
            if(userCenterItemAdapter!=null) {
                   userCenterItemAdapter.getData().get(1).setHasMsg(checkHasMsg(messageDao.getPlatFormMsgNum()));
                   userCenterItemAdapter.getData().get(2).setHasMsg(checkHasMsg(messageDao.getUserMsgNum()));
                   userCenterItemAdapter.notifyDataSetChanged();
            }

        }
    }

    private boolean checkHasMsg(String msgNum){
        if(TextUtils.isEmpty(msgNum)||"0".equals(msgNum)){
            return false ;
        }
        return  true ;
    }
}
