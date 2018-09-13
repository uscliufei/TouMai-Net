package com.runer.toumai.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runer.liabary.util.UiUtil;
import com.runer.net.RequestCode;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;
import com.runer.toumai.bean.AddressBean;
import com.runer.toumai.dao.AddressDao;
import com.runer.toumai.util.AppUtil;
import com.runer.toumai.widget.AreaViewShowUtil;
import com.runer.toumai.widget.NormalInputEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddressEditActivity extends BaseActivity {


    @InjectView(R.id.left_back)
    ImageView leftBack;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.right_img)
    ImageView rightImg;
    @InjectView(R.id.name)
    NormalInputEditText nameInput;
    @InjectView(R.id.phone)
    NormalInputEditText phoneInput;
    @InjectView(R.id.address_tv)
    TextView addressTv;
    @InjectView(R.id.address_bt)
    RelativeLayout addressBt;
    @InjectView(R.id.address_detail_input)
    NormalInputEditText addressDetailInput;
    @InjectView(R.id.default_address)
    CheckBox defaultAddress;
    @InjectView(R.id.commit_bt)
    TextView commitBt;
    private AddressBean addressBean;
    private AddressDao addressDao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.inject(this);
        addressDao =new AddressDao(this,this) ;
        if(getIntent().getExtras()!=null)
        addressBean = (AddressBean) getIntent().getExtras().getSerializable("data");
        if(addressBean!=null){
            commitBt.setText("删除地址");
            commitBt.setVisibility(View.GONE);
            commitBt.setBackgroundResource(R.color.text_color_red);
            commitBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  addressDao.delAddress(addressBean.getId());
                    showProgress(true);
                }
            });
            nameInput.setRightText(addressBean.getUser_name());
            phoneInput.setRightText(addressBean.getMobile());
            province =addressBean.getProvince() ;
            city =addressBean.getCity() ;
            area =addressBean.getArea() ;
            addressTv.setText(addressBean.getProvince()+" "+addressBean.getCity()+" "+addressBean.getArea());
            addressDetailInput.setRightText(addressBean.getAddress());

            if("1".equals(addressBean.getIs_default())){
                defaultAddress.setChecked(true);
            }else{
                defaultAddress.setChecked(false);
            }

        }else{
           commitBt.setVisibility(View.GONE);
        }


        addressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            AreaViewShowUtil areaViewShowUtil = new AreaViewShowUtil(AddressEditActivity.this);
            areaViewShowUtil.setOnAreaSelectListener(new AreaViewShowUtil.OnAreaSelectListener() {
                @Override
                public void onAreaSelect(String pro, String city, String area) {
                    AddressEditActivity.this.province = pro;
                    AddressEditActivity.this.city = city;
                    AddressEditActivity.this.area = area;
                    addressTv.setText(pro+" "+city+" "+area);
                }
            });
                areaViewShowUtil.show();
                hideInput();
            }
        });

    }

    String province ;
    String city ;
    String area ;

 public AddressBean getData(){
     AddressBean addressBean =new AddressBean() ;
     String name =nameInput.getInputContent() ;
     String phone =phoneInput.getInputContent();
     String detailAddress =addressDetailInput.getInputContent() ;
     if(TextUtils.isEmpty(name)){
         UiUtil.showLongToast(this,"收货人为空");
         return  null ;
     }
     if(TextUtils.isEmpty(phone)){
         UiUtil.showLongToast(this,"输入收货人的电话");
         return  null ;
     }
     if(!UiUtil.isValidMobileNo(phone)){
         UiUtil.showLongToast(this,"输入的电话格式不正确");
         return  null ;
     }
     if(TextUtils.isEmpty(detailAddress)){
         UiUtil.showLongToast(this,"请输入详细的收货地址");
         return  null ;
     }
     if(TextUtils.isEmpty(province)){
         UiUtil.showLongToast(this,"请选择地区");
         return  null ;
      }

     addressBean.setAddress(detailAddress);
     addressBean.setArea(area);
     addressBean.setProvince(province);
     addressBean.setCity(city);
     addressBean.setMobile(phone);
     addressBean.setUser_id(AppUtil.getUserId(this));
     addressBean.setUser_name(name);

     return  addressBean ;
 }


    @Override
    protected void onStart() {
        super.onStart();
        if (addressBean != null) {
           setTitle("修改收货地址");
           setRightText("保存");
            setRightTvClickLister(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddressBean addressBean =getData();
                    if(addressBean!=null) {
                        addressDao.upDateAddress(AppUtil.getUserId(AddressEditActivity.this), addressBean);
                        showProgress(true);
                    }
                }
            });
        }else{
            setTitle("添加收货地址");
            setRightText("添加");
            setRightTvClickLister(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        AddressBean addressBean = getData();
                        if(addressBean!=null) {
                            addressDao.addAddress(AppUtil.getUserId(AddressEditActivity.this), addressBean);
                            showProgress(true);
                    }
                }
            });
        }
    }
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode== RequestCode.CODE_0){
            if(defaultAddress.isChecked()){
                addressDao.setAddressDefault(AppUtil.getUserId(this),addressDao.getId());
                showProgress(true);
            }else{
                UiUtil.showLongToast(this,"添加成功");
                finish();
            }
        }else if(requestCode==RequestCode.CODE_1){
            UiUtil.showLongToast(this,"添加成功");
            finish();
        }else if(requestCode==RequestCode.CODE_2){
            UiUtil.showLongToast(this,"删除成功");
            finish();
        }else if(requestCode==RequestCode.CODE_3){
           if(defaultAddress.isChecked()){
               addressDao.setAddressDefault(AppUtil.getUserId(this),addressBean.getId());
               showProgress(true);
           }else{
               UiUtil.showLongToast(this,"更新成功");
               finish();
           }
        }
    }
}
