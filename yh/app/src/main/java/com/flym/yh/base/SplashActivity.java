package com.flym.yh.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;


import com.flym.yh.MyApplication;
import com.flym.yh.R;
import com.flym.yh.net.SimpleString;
import com.flym.yh.ui.activity.home.HomeActivity;
import com.flym.yh.ui.activity.login.LoginActivity;
import com.flym.yh.ui.activity.mine.AuthenticationActivity;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.UserManager;

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
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ActivityManager.getInstance().push(this);
        MyApplication.flag = 1;
        startNextPager();

    }


    @SuppressLint("CheckResult")
    private void startNextPager() {
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        SimpleString user = UserManager.getUser(SplashActivity.this);
//                        if (user != null && user.auth_status == 2) {
                            ActivityManager.getInstance().startNextActivity(HomeActivity.class, true);
//                        } else {
//                            ActivityManager.getInstance().startNextActivity(LoginActivity.class, true);
//                        }
                    }
                });
    }

}
