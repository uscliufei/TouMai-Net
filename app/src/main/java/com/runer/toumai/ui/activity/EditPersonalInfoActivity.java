package com.runer.toumai.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.TimePickerView;
import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.UpdateUserInfoParam;
import com.runer.toumai.bean.UserInfo;
import com.runer.toumai.dao.UpdateUserInfoDao;
import com.runer.toumai.dao.UserInfoDao;
import com.runer.toumai.net.NetConfig;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.AreaViewShowUtil;
import com.runer.toumai.widget.NormalInputEditText;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class EditPersonalInfoActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.header_icon)
    CircleImageView headerIcon;
    @InjectView(R.id.male_radio)
    RadioButton maleRadio;
    @InjectView(R.id.female_radio)
    RadioButton femaleRadio;
    @InjectView(R.id.sex_radios)
    RadioGroup sexRadios;
    @InjectView(R.id.commit_bt)
    TextView commitBt;
    @InjectView(R.id.birth_bt)
    RelativeLayout birthBt;
    @InjectView(R.id.address_bt)
    RelativeLayout addressBt;
    @InjectView(R.id.nick_name_input)
    NormalInputEditText nickNameInput;
    @InjectView(R.id.birth_text)
    TextView birthText;
    @InjectView(R.id.address_tv)
    TextView addressTv;
    @InjectView(R.id.summary_input)
    EditText summaryInput;
    @InjectView(R.id.authentication_tv)
    TextView authenticationTv;
    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.auth_input)
    EditText authInput;

    private UserInfoDao userInfoDao;
    private UpdateUserInfoDao updateUserInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        ButterKnife.inject(this);
        commitBt.setOnClickListener(this);
        headerIcon.setOnClickListener(this);
        birthBt.setOnClickListener(this);
        addressBt.setOnClickListener(this);
        updateUserInfoDao = new UpdateUserInfoDao(this, this);
        userInfoDao = new UserInfoDao(this, this);
        sexRadios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.male_radio) {
                    sex = "男";
                } else {
                    sex = "女";
                }
            }
        });
        femaleRadio.setChecked(true);
        userInfoDao.getUserInfo(AppUtil.getUserId(this));
        showProgress(true);

    }

    private String nickName;
    private String sex = "女";
    private String birth;
    private String province;
    private String city;
    private String area;
    private String summary;
    private String authentication;

    public void saveUserInfo() {

        nickName = nickNameInput.getInputContent();
        summary = summaryInput.getText().toString();
        authentication =authInput.getText().toString() ;

        UpdateUserInfoParam param = new UpdateUserInfoParam();
        param.setBirth(birth);
        param.setSex(sex);
        param.setId(AppUtil.getUserId(this));
        param.setUser_name(nickName);
        param.setIntro(summary);
        param.setAuth(authentication);
        //上传省市区
        param.setProvince(province);
        param.setCity(city);
        param.setArea(area);
        updateUserInfoDao.upLoadUserInfo(param);
        showProgressWithMsg(true, "正在上传用户信息");

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        //获得个人信息成功
        if (requestCode == RequestCode.GET_USER_INFO) {
            UserInfo userInfo = userInfoDao.getUserInfo();
            if (userInfo != null) {
                Picasso.with(this).load(NetConfig.HEAD_PATH + userInfo.getHead()).into(headerIcon);
            }
            nickNameInput.setRightText(userInfo.getUser_name());
            if ("男".equals(userInfo.getSex())) {
                maleRadio.setChecked(true);
            } else {
                femaleRadio.setChecked(true);
            }
            if (TextUtils.isEmpty(userInfo.getBirth())) {
                birthText.setText("请选择出生年月");
            } else {
                birthText.setText(userInfo.getBirth());
            }
            if (TextUtils.isEmpty(userInfo.getProvince())) {
                addressTv.setText("请选择地址");
            } else {
                addressTv.setText(userInfo.getProvince() + " " + userInfo.getCity() + " " + userInfo.getArea());
            }
            summaryInput.setText(userInfo.getIntro());
            //认证信息
            authInput.setText(userInfo.getAuth());
            //修改个人信息成功
        }else if(requestCode==RequestCode.UPDATE_USER_UINFO){
            UiUtil.showLongToast(this,"修改个人信息成功");
            finish();
        }else if(requestCode==RequestCode.UPDATE_HEADER){
            UiUtil.showLongToast(this,"上传头像成功");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("修改个人信息");
    }

    @Override
    public void onClick(View v) {
        //修改头像；
        if (v == headerIcon) {
            Album.album(this)
                    .toolBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    .statusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    .title("图库")
                    .selectCount(1)
                    .columnCount(3)
                    .camera(true)
                    .requestCode(PHOTO_SELECT_CODE)
                    .start();
        } else if (v == birthBt) {
            TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    if (date.after(new Date())) {
                        UiUtil.showLongToast(EditPersonalInfoActivity.this, "选择时间不对");
                        return;
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    birth = simpleDateFormat.format(date);
                    birthText.setText(birth);
                }
            }).setType(new boolean[]{true, true, true, false, false, false})
                    .setSubmitColor(ContextCompat.getColor(this, R.color.theme_color))//确定按钮文字颜色
                    .setCancelColor(ContextCompat.getColor(this, R.color.text_color_light))//取消按钮文字颜色
                    .setOutSideCancelable(true)
                    .setDividerColor(ContextCompat.getColor(this, R.color.text_color_gray))
                    .setTextColorCenter(ContextCompat.getColor(this, R.color.text_color_normal)) //设置选中项文字颜色
                    .build();
            pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
            pvTime.show();

        } else if (v == addressBt) {
            transUi(AddressActivity.class,null);
            AreaViewShowUtil areaViewShowUtil = new AreaViewShowUtil(this);
            areaViewShowUtil.setOnAreaSelectListener(new AreaViewShowUtil.OnAreaSelectListener() {
                @Override
                public void onAreaSelect(String pro, String city, String area) {
                    province = pro;
                    EditPersonalInfoActivity.this.city = city;
                    EditPersonalInfoActivity.this.area = area;
                    addressTv.setText(pro+" "+city+" "+area);
                }
            });
            areaViewShowUtil.show();
            //保存用户信息；
        } else if (v == commitBt) {
            saveUserInfo();
        }
    }
    public static final int PHOTO_SELECT_CODE = 999;
    public static final int CAMERA_TAKE_CODE = 1000;
    //裁剪头像的缓存地址
    public static final String CROPO_CACHE_PAHT = "imgine_cloud_crop";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_SELECT_CODE) {

            if (resultCode == RESULT_OK) {
                ArrayList<String> pathList = Album.parseResult(data);
                Crop.of(Uri.fromFile(new File(pathList.get(0))), Uri.fromFile(new File(getCacheDir(), CROPO_CACHE_PAHT))).start(EditPersonalInfoActivity.this);
            }
            //裁剪以后的操作
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {

            //进行压缩;
            Flowable.just(new File(getCacheDir(), CROPO_CACHE_PAHT))
                    .observeOn(Schedulers.io())
                    .map(new Function<File, File>() {
                        @Override
                        public File apply(@NonNull File file) throws Exception {
                            return Luban.with(EditPersonalInfoActivity.this).load(file).get();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        //进行上传头像
                        public void accept(File file) throws Exception {
                            userInfoDao.upLoadAvatar(AppUtil.getUserId(getApplicationContext()), file);
                            showProgressWithMsg(true, "正在上传头像");
                            Picasso.with(EditPersonalInfoActivity.this).load(file).into(headerIcon);
                        }
                    });
        } else if (resultCode == Crop.RESULT_ERROR) {
            UiUtil.showLongToast(getApplicationContext(), "裁剪失败!");
        }
    }


}
