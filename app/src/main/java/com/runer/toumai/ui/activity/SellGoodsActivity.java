package com.runer.toumai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.runer.liabary.util.UiUtil;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.TagBean;
import com.runer.toumai.widget.AnjiaRulesDialog;
import com.runer.toumai.widget.SelectPhotoView;
import com.runer.toumai.widget.TagsView;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/*出售商品界面*/
public class SellGoodsActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.tags_view)
    TagsView tagsView;
    @InjectView(R.id.select_photo)
    SelectPhotoView selectPhoto;
    @InjectView(R.id.sub_mit)
    TextView subMit;
    @InjectView(R.id.select_big_pic)
    TextView selectBigPic;
    @InjectView(R.id.big_pic)
    ImageView bigPic;
    @InjectView(R.id.anchor)
    TextView anchor;
    @InjectView(R.id.anjia_bt)
    RadioButton anjiaBt;
    @InjectView(R.id.jiang_rules_bt)
    TextView jiangRulesBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_goods);
        ButterKnife.inject(this);
        selectBigPic.setOnClickListener(this);
        List<TagBean> tagBeens = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TagBean tag = new TagBean();
            tag.setTagName(String.valueOf(new Random().nextInt(100000)));
            tagBeens.add(tag);
        }
        tagsView.setTagBeanList(tagBeens);
        //选择图片======
        selectPhoto.setOnItemSelectPicClickListener(new SelectPhotoView.OnItemSelectPicClickListener() {
            @Override
            public void onAddPic(int leftNum) {
                Album.album(SellGoodsActivity.this)
                        .toolBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                        .statusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                        .title("图库")
                        .selectCount(leftNum)
                        .columnCount(3)
                        .camera(true)
                        .requestCode(PHOTO_SELECT_CODE)
                        .start();
            }
            @Override
            public void onPictureClick(String path) {
                UiUtil.showLongToast(SellGoodsActivity.this, path);
            }
        });
//        anjiaBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AnjiaRulesDialog dialog = AnjiaRulesDialog.show(SellGoodsActivity.this, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//            }
//        });

        jiangRulesBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transUi(RulesCheckActivity.class,null);
            }
        });
    }

    public static final int PHOTO_SELECT_CODE = 999;
    public static final int CAMERA_TAKE_CODE = 1000;
    public static final int TAKE_BIG_PIC = 1001;
    //裁剪头像的缓存地址
    public static final String CROPO_CACHE_PAHT = "imgine_cloud_crop";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_BIG_PIC) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> pathList = Album.parseResult(data);
                Picasso.with(this).load(new File(pathList.get(0))).resize(100, 100).placeholder(R.drawable.header).into(bigPic);
            }
        } else if (requestCode == PHOTO_SELECT_CODE) {

            if (resultCode == RESULT_OK) {
                ArrayList<String> pathList = Album.parseResult(data);
                selectPhoto.addImgPath(pathList);
                //裁剪
                //Crop.of(Uri.fromFile(new File(pathList.get(0))), Uri.fromFile(new File(getCacheDir(), CROPO_CACHE_PAHT))).start(CompleteUserInfoActivity.this);
            }

            //裁剪以后的操作
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            //进行压缩;
            Flowable.just(new File(getCacheDir(), CROPO_CACHE_PAHT))
                    .observeOn(Schedulers.io())
                    .map(new Function<File, File>() {
                        @Override
                        public File apply(@NonNull File file) throws Exception {
                            return Luban.with(SellGoodsActivity.this).load(file).get();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) throws Exception {
//                            uploadHeaderDao.upLoadUserHeader(AppUtil.getUserId(getApplicationContext()),file);
//                            showProgressWithMsg(true,"正在上传头像");
//                            Picasso.with(CompleteUserInfoActivity.this).load(file).into(headerBt);
                        }
                    });
        } else if (resultCode == Crop.RESULT_ERROR) {
            UiUtil.showLongToast(getApplicationContext(), "裁剪失败!");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == selectBigPic) {
            Album.album(SellGoodsActivity.this)
                    .toolBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                    .statusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                    .title("图库")
                    .selectCount(1)
                    .columnCount(3)
                    .camera(true)
                    .requestCode(TAKE_BIG_PIC)
                    .start();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        setTitle("出售商品");
    }
}
