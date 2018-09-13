package com.runer.toumai.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.bigkoo.pickerview.TimePickerView;
import com.runer.liabary.util.UiUtil;
import com.runer.liabary.util.Utils;
import com.runer.toumai.R;
import com.runer.toumai.bean.GetGoodParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by szhua on 2017/7/28/028.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * OrderView
 */
//排序view
public class OrderView extends LinearLayout implements View.OnClickListener {
    GetGoodParam getGoodParam;
    Context context;
    int currentRadioButton=-1;
    public OrderView(Context context) {
        this(context, null, 0);
    }
    public OrderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public OrderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.order_view_layout, this);
        initView();
        ((RadioButton) leftRadios.getChildAt(0)).setChecked(true);
        resetState(1);
        currentRadioButton=1;
        timeContaienr.setVisibility(VISIBLE);
        leftRadios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.dijia:
                        resetState(1);
                        numsContaienr.setVisibility(VISIBLE);
                        currentRadioButton=1;
                        break;
                    case R.id.current_price:
                        resetState(2);
                        numsContaienr.setVisibility(VISIBLE);
                        currentRadioButton=2;
                        break;
                    case R.id.publish_time:
                        resetState(3);
                        timeContaienr.setVisibility(VISIBLE);
                        currentRadioButton=3;
                        break;
                    case R.id.end_time:
                        resetState(4);
                        daojishiContaienr.setVisibility(VISIBLE);
                        currentRadioButton=4;
                        break;
                    case R.id.publis_num:
                        resetState(3);
                        numsContaienr.setVisibility(VISIBLE);
                        currentRadioButton=5;
                        break;
                }
            }
        });
    }
    //底价选择
    RadioButton dijia;
    //
    RadioButton currentPrice;
    RadioButton publishTime;
    RadioButton endTime;
    RadioButton publisNum;
    RadioGroup leftRadios;
    //从低到高的排序  =底价 当前价 出价次数
    LinearLayout numsContaienr;
    //时间排序 出价的时间
    LinearLayout timeContaienr;
    //倒计时
    LinearLayout daojishiContaienr;
    //由时间进行排序
    TextView startTimeBt;
    TextView endTimeBT;

    //从低到高的排序  =底价 当前价                出价次数==总的 从低到高
    RadioButton numAllLow2highRadio;
    //从低到高的排序  =底价 当前价                 出价次数==总的 从高到低
    RadioButton numAllHigh2lowRadio;
    //从低到高的排序  =底价 当前价                 出价次数==总的    radioGroup
    RadioGroup numAllRadioGroup;
    // =底价 当前价 出价次数             低的input
    EditText numLow;
    //底价 当前价 出价次数               高的input
    EditText numHigh;
    //底价 当前价 出价次数               区间从低到高
    RadioButton numRegionLow2highRadio;
    //底价 当前价 出价次数               区间从高到低
    RadioButton numRegionHigh2lowRadio;
    //底价 当前价 出价次数               区间从radioGroup
    RadioGroup numRegionRadioGroup;
    //由远到近，由近到远
    RadioButton distance2Near,near2Distance;
//结束倒计时
    RadioButton fiveMinitues,fifteenMinitues,oneHour;

    //按发布时间
  RadioButton nearToFar, farToNear;

//按结束的时间
    RadioButton overFar ,overNear ;

    private View contaienr ;
    private void initView() {

        getGoodParam = new GetGoodParam();
        numAllLow2highRadio = (RadioButton) findViewById(R.id.num_all_low2high_radio);
        numAllHigh2lowRadio = (RadioButton) findViewById(R.id.num_all_high2low_radio);
        numAllRadioGroup = (RadioGroup) findViewById(R.id.num_all_radio_group);
        numLow = (EditText) findViewById(R.id.num_low);
        numHigh = (EditText) findViewById(R.id.num_high);
        numRegionHigh2lowRadio = (RadioButton) findViewById(R.id.num_region_high2low_radio);
        numRegionLow2highRadio = (RadioButton) findViewById(R.id.num_region_low2high_radio);
        numRegionRadioGroup = (RadioGroup) findViewById(R.id.num_region_radio_group);

        dijia = (RadioButton) findViewById(R.id.dijia);
        currentPrice = (RadioButton) findViewById(R.id.current_price);
        publishTime = (RadioButton) findViewById(R.id.publish_time);
        endTime = (RadioButton) findViewById(R.id.end_time);
        publisNum = (RadioButton) findViewById(R.id.publis_num);
        leftRadios = (RadioGroup) findViewById(R.id.left_radios);

        numsContaienr = (LinearLayout) findViewById(R.id.nums_contaienr);
        timeContaienr = (LinearLayout) findViewById(R.id.time_contaienr);
        daojishiContaienr = (LinearLayout) findViewById(R.id.daojishi_contaienr);

        startTimeBt = (TextView) findViewById(R.id.start_time);
        endTimeBT = (TextView) findViewById(R.id.end_time_bt);
        distance2Near = (RadioButton) findViewById(R.id.distance2Near);
        near2Distance = (RadioButton) findViewById(R.id.near2Distance);

        fiveMinitues = (RadioButton) findViewById(R.id.fiveMinitues);
        fifteenMinitues = (RadioButton) findViewById(R.id.fifteenMinitues);
        oneHour = (RadioButton) findViewById(R.id.oneHour);

        nearToFar= (RadioButton) findViewById(R.id.time_near_to_far);
        farToNear = (RadioButton) findViewById(R.id.time_far_to_near);

        contaienr =findViewById(R.id.container);


        overFar = (RadioButton) findViewById(R.id.time_far);
        overNear = (RadioButton) findViewById(R.id.time_near);

        overNear.setOnClickListener(this);
        overFar.setOnClickListener(this);

        startTimeBt.setOnClickListener(this);
        endTimeBT.setOnClickListener(this);

        numAllLow2highRadio.setOnClickListener(this);
        numAllHigh2lowRadio.setOnClickListener(this);
        numRegionHigh2lowRadio.setOnClickListener(this);
        numRegionLow2highRadio.setOnClickListener(this);
        distance2Near.setOnClickListener(this);
        near2Distance.setOnClickListener(this);
        fiveMinitues.setOnClickListener(this);
        fifteenMinitues.setOnClickListener(this);
        oneHour.setOnClickListener(this);
        nearToFar.setOnClickListener(this);
        farToNear.setOnClickListener(this);
        contaienr.setOnClickListener(this);


    }
    //1 底价 2当前价 3出价次数 4时间排序 5倒计时
//重置状态
    private void resetState(int type) {
        if(type==1){
            numLow.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            numHigh.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            numLow.setHint("开始底价");
            numHigh.setHint("结束底价");
            numLow.setText("");
            numHigh.setText("");
        }else if(type==2){
            numLow.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            numHigh.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            numLow.setHint("开始当前");
            numHigh.setHint("结束当前");
            numLow.setText("");
            numHigh.setText("");
        }else if(type==3){
            numLow.setInputType(InputType.TYPE_CLASS_NUMBER );
            numHigh.setInputType(InputType.TYPE_CLASS_NUMBER );
            numLow.setHint("开始次数");
            numHigh.setHint("结束次数");
            numLow.setText("");
            numHigh.setText("");
        }
        numRegionHigh2lowRadio = (RadioButton) findViewById(R.id.num_region_high2low_radio);
        numRegionLow2highRadio = (RadioButton) findViewById(R.id.num_region_low2high_radio);
        numRegionRadioGroup = (RadioGroup) findViewById(R.id.num_region_radio_group);

        numsContaienr.setVisibility(GONE);
        timeContaienr.setVisibility(GONE);
        daojishiContaienr.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == startTimeBt) {
            selectTime(0);
        } else if (v == endTimeBT) {
            selectTime(1);
        }else if (v==numAllLow2highRadio) {
            switch (currentRadioButton){
                case 1:
                    getGoodParam.setOrder("1");
                    break;
                case 2:
                    getGoodParam.setOrder("5");
                    break;
                case 5:
                    getGoodParam.setOrder("11");
                    break;
            }
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==numAllHigh2lowRadio) {
            switch (currentRadioButton){
                case 1:
                    getGoodParam.setOrder("2");
                    break;
                case 2:
                    getGoodParam.setOrder("6");
                    break;
                case 5:
                    getGoodParam.setOrder("12");
                    break;
            }
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==numRegionHigh2lowRadio) {
            switch (currentRadioButton){
                case 1:
                    getGoodParam.setOrder("4");
                    if (TextUtils.isEmpty(numLow.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写开始低价");
                        return;
                    }
                    if (TextUtils.isEmpty(numHigh.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写结束低价");
                        return;
                    }
                    getGoodParam.setStart_price(numLow.getText().toString());
                    getGoodParam.setEnd_price(numHigh.getText().toString());
                    break;
                case 2:
                    getGoodParam.setOrder("8");
                    if (TextUtils.isEmpty(numLow.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写开始当前价");
                        return;
                    }
                    if (TextUtils.isEmpty(numHigh.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写结束当前价");
                        return;
                    }
                    getGoodParam.setStart_now(numLow.getText().toString());
                    getGoodParam.setEnd_now(numHigh.getText().toString());
                    break;
                case 5:
                    getGoodParam.setOrder("14");
                    if (TextUtils.isEmpty(numLow.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写开始出价次数");
                        return;
                    }
                    if (TextUtils.isEmpty(numHigh.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写结束出价次数");
                        return;
                    }
                    getGoodParam.setStart_offer(numLow.getText().toString());
                    getGoodParam.setEnd_offer(numHigh.getText().toString());
                    break;
            }
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==numRegionLow2highRadio) {
            switch (currentRadioButton){
                case 1:
                    getGoodParam.setOrder("3");
                    if (TextUtils.isEmpty(numLow.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写开始低价");
                        return;
                    }
                    if (TextUtils.isEmpty(numHigh.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写结束低价");
                        return;
                    }
                    getGoodParam.setStart_price(numLow.getText().toString());
                    getGoodParam.setEnd_price(numHigh.getText().toString());
                    break;
                case 2:
                    getGoodParam.setOrder("7");
                    if (TextUtils.isEmpty(numLow.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写开始当前价");
                        return;
                    }
                    if (TextUtils.isEmpty(numHigh.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写结束当前价");
                        return;
                    }
                    getGoodParam.setStart_now(numLow.getText().toString());
                    getGoodParam.setEnd_now(numHigh.getText().toString());
                    break;
                case 5:
                    getGoodParam.setOrder("13");
                    if (TextUtils.isEmpty(numLow.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写开始出价次数");
                        return;
                    }
                    if (TextUtils.isEmpty(numHigh.getText().toString())) {
                        UiUtil.showLongToastCenter(context,"请填写结束出价次数");
                        return;
                    }
                    getGoodParam.setStart_offer(numLow.getText().toString());
                    getGoodParam.setEnd_offer(numHigh.getText().toString());
                    break;
            }
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==near2Distance) {
            getGoodParam.setOrder("9");
            if (startTimeBt.getText().toString().equals("选择起始时间")) {
                UiUtil.showLongToastCenter(context,"请选择起始时间");
                return;
            }

            if (endTimeBT.getText().toString().equals("选择结束时间")) {
                UiUtil.showLongToastCenter(context,"请选择结束时间");
                return;
            }
            getGoodParam.setStart_offer(startTimeBt.getText().toString());
            getGoodParam.setEnd_time(endTimeBT.getText().toString());

            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==distance2Near) {
            getGoodParam.setOrder("10");
            if (startTimeBt.getText().toString().equals("选择起始时间")) {
                UiUtil.showLongToastCenter(context,"请选择起始时间");
                return;
            }
            if (endTimeBT.getText().toString().equals("选择结束时间")) {
                UiUtil.showLongToastCenter(context,"请选择结束时间");
                return;
            }
            getGoodParam.setStart_offer(startTimeBt.getText().toString());
            getGoodParam.setEnd_time(endTimeBT.getText().toString());
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==fiveMinitues) {
            getGoodParam.setSur_time("5");
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==fifteenMinitues) {
            getGoodParam.setSur_time("15");
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==oneHour) {
            getGoodParam.setSur_time("60");
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==overFar){
            //cong短到长；
            getGoodParam.setOrder("17");
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else  if (v==overNear){
            //由长到短；
            getGoodParam.setOrder("18");
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }
        else if (v==nearToFar) {
            getGoodParam.setOrder("15");
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if (v==farToNear) {
            getGoodParam.setOrder("16");
            if (onClosePopWindowListener != null) {
                onClosePopWindowListener.oinCloseListener(getGoodParam);
            }
        }else if(v==contaienr){
            onClosePopWindowListener.oinCloseListener(null);
        }
    }
    private Date startDate;
    private Date endDate;

    //选择起始时间；
    private void selectTime(final int type) {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (date.after(new Date())) {
                    UiUtil.showLongToast(getContext(), "选择小于当前时间");
                    return;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //起始时间(设置时间的样式)
                    if (type == 0) {
                        startDate = date;
                        //起始时间大于结束时间处理
                        if (endDate != null && startDate.after(endDate)) {
                            UiUtil.showLongToast(getContext(), "起始时间应小于结束时间");
                            return;
                        }
                        startTimeBt.setText(dateFormat.format(date));
                    } else {
                        endDate = date;
                        if (startDate != null && endDate.before(startDate)) {
                            UiUtil.showLongToast(getContext(), "起始时间应小于结束时间");
                            return;
                        }
                        endTimeBT.setText(dateFormat.format(date));
                    }
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .setSubmitColor(ContextCompat.getColor(getContext(), R.color.theme_color))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(getContext(), R.color.text_color_light))//取消按钮文字颜色
                .setOutSideCancelable(true)
                .setDividerColor(ContextCompat.getColor(getContext(), R.color.text_color_gray))
                .setTextColorCenter(ContextCompat.getColor(getContext(), R.color.text_color_normal)) //设置选中项文字颜色
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.showDialog();


    }
private OnClosePopWindowListener onClosePopWindowListener;
    public void setOnClosePopWindowListener(OnClosePopWindowListener onClosePopWindowListener){
        this.onClosePopWindowListener = onClosePopWindowListener;
    }
public  interface OnClosePopWindowListener{
        void oinCloseListener(GetGoodParam getGoodParam);
    }
}
