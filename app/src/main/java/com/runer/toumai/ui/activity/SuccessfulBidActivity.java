package com.runer.toumai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.ProInfoBean;
import com.runer.toumai.dao.ProInfoDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.util.ShareBean;
import com.runer.toumai.util.ShareUtil;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/*出价成功Activity*/
public class SuccessfulBidActivity extends BaseActivity {
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.wechat_share)
    TextView wechatShare;
    @InjectView(R.id.qq_share)
    TextView qqShare;
    @InjectView(R.id.sina_share)
    TextView sinaShare;
    @InjectView(R.id.tips)
    TextView tips;
    @InjectView(R.id.publis_success_tips)
    TextView publisSuccessTips;

    private int type;
    private String des;
    private String img;
    private String goods_id;
    private ShareBean shareBean;
    private ProInfoDao proInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_bid);
        ButterKnife.inject(this);
        type = getIntExtras("type", 0);
        des = getStringExtras("des", "");
        img = getStringExtras("img", "");
        goods_id = getStringExtras("id", "");
        shareBean = new ShareBean();
        if (type == 0) {
            title.setText("出价成功");
            tips.setText("出价成功");
            shareBean.setTitle(des);
            shareBean.setUrl(NetConfig.SHARE_URL + goods_id);
            shareBean.setImgUrl(img);
            shareBean.setDes(getString(R.string.tou_ad_des));
            publisSuccessTips.setVisibility(View.VISIBLE);
            publisSuccessTips.setText(getString(R.string.publish_price_tips));
        } else  if(type==1){
            title.setText("发布商品成功");
            tips.setText("恭喜，发布商品成功");
            proInfoDao = new ProInfoDao(this, this);
            proInfoDao.getGoodsInfo(goods_id, "");
            publisSuccessTips.setVisibility(View.VISIBLE);
            publisSuccessTips.setText(getString(R.string.publis_success_tips));
        }else if(type==2){
            title.setText("分享");
            tips.setText("分享商品详情");
            proInfoDao = new ProInfoDao(this, this);
            proInfoDao.getGoodsInfo(goods_id, "");
            publisSuccessTips.setVisibility(View.VISIBLE);
            publisSuccessTips.setText(getString(R.string.publish_price_tips));
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @OnClick({R.id.wechat_share, R.id.qq_share, R.id.sina_share, R.id.qq_zone_share,R.id.wechat_circle_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechat_share:
                ShareUtil.getInstance(this).share(shareBean, this, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.qq_share:
                ShareUtil.getInstance(this).share(shareBean, this, SHARE_MEDIA.QQ);
                break;
            case R.id.sina_share:
                ShareUtil.getInstance(this).share(shareBean, this, SHARE_MEDIA.SINA);
                break;
            //分享到qq空间
            case R.id.qq_zone_share:
                ShareUtil.getInstance(this).share(shareBean, this, SHARE_MEDIA.QZONE);
                break;
            case R.id.wechat_circle_share :
                ShareUtil.getInstance(this).share(shareBean, this, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == RequestCode.CODE_0) {
            ProInfoBean proInfoBean = proInfoDao.getProInfoBean();
            shareBean.setTitle(des);
            shareBean.setUrl(NetConfig.SHARE_URL + goods_id);
            shareBean.setImgUrl(NetConfig.GOODS_IMG + proInfoBean.getImg());
            shareBean.setDes(getString(R.string.tou_ad_des));
        }
    }
}
