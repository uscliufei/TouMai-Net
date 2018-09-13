package com.runer.toumai.ui.activity;

import android.os.Bundle;
import com.orhanobut.logger.Logger;
import com.runer.toumai.R;
import com.runer.toumai.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Flowable.timer(2,
                TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.d("跳转"+aLong);
                        transUi(HomeActivity.class,null);
                        finish();
                    }
                });
    }
}
