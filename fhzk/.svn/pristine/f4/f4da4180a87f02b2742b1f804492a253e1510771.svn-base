package com.flym.fhzk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.flym.fhzk.MyApplication;
import com.flym.fhzk.R;
import com.flym.fhzk.ui.activity.home.HomeActivity;
import com.flym.fhzk.ui.activity.login.LoginActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.Constants;
import com.flym.fhzk.utils.SharePreUtil;
import com.flym.fhzk.utils.UserManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Morphine on 2017/6/13.
 */

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ActivityManager.getInstance().push(this);
        MyApplication.flag = 1;
        startNextPager();

    }

    private void startNextPager() {
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        boolean isGuide = SharePreUtil.getBoolean(SplashActivity.this, Constants.SHARE_PRE_NAME_GUIDE);
//                        if (isGuide) {
////                            if (UserManager.isLogin()) {
////                                ActivityManager.getInstance().startNextActivity(MainActivity.class, true);
////                            } else {
//////                                ActivityManager.getInstance().startNextActivity(LoginActivity.class, true);
////                            }
                        if (UserManager.isLogin()) {
                            ActivityManager.getInstance().startNextActivity(HomeActivity.class, true);
                        } else {
                            ActivityManager.getInstance().startNextActivity(LoginActivity.class, true);
                        }

//                        } else
//                            ActivityManager.getInstance().startNextActivity(GuideActivity.class, true);
                    }
                });
    }

}
