package com.flym.yh.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.yh.MyApplication;
import com.flym.yh.R;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.ToastUtil;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Morphine on 2017/6/12.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected String TAG = this.getClass().getSimpleName();
    protected Toolbar toolbar;
    protected Unbinder unbinder;
    protected CompositeDisposable compositeDisposable;
    protected TextView toolbarTitle;
    protected TextView toolbarRight;
    long firstTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();

        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar) findViewById(getToolBarId());
        init(savedInstanceState);
        if (toolbar != null) {
            toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            toolbarRight = (TextView) findViewById(R.id.toolbar_right);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbarTitle != null) {
                toolbarTitle.setText(getPagerTitle());
            }
        }
        ActivityManager.getInstance().push(this);
        initData();

        if (MyApplication.flag == -1) {
            ActivityManager.getInstance().toSplashActivity();
        }
    }

    protected String getPagerTitle() {
        return " ";
    }

    protected void hideBackBtn() {
        if (toolbar != null) toolbar.setNavigationIcon(null);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initData();

    @IdRes
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        updatePagerTitle();
        if (FragmentUtil.popBackStack(getSupportFragmentManager())) {
            return true;
        }
        if (ActivityManager.getInstance().currentActivitySize() == 1) {
            exitApp();
        } else {
            ActivityManager.getInstance().popActivity(this);
        }
        return super.onSupportNavigateUp();
    }


    private void exitApp() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtil.showMessage(this, "再按一次退出程序");
            firstTime = secondTime;
        } else {
            ActivityManager.getInstance().exitApp();
        }
    }

    @Override
    public void onBackPressed() {

        updatePagerTitle();
        if (FragmentUtil.popBackStack(getSupportFragmentManager())) {
            return;
        }
        if (ActivityManager.getInstance().currentActivitySize() == 1) {
            exitApp();
        } else {
            ActivityManager.getInstance().popActivity(this);
        }

    }



    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        compositeDisposable.clear();
        ActivityManager.getInstance().popActivity(this);
        super.onDestroy();
    }


    protected void updatePagerTitle() {
        if (toolbarTitle == null) {
            return;
        }
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(count - 1);
            toolbarTitle.setText(entry.getName());
        } else {
            toolbarTitle.setText(getPagerTitle());
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                if (fragments.get(i) != null) {
                    fragments.get(i).onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }


    /**
     * tabLayout控制下划线
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (tabStrip == null) {
            return;
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        if (llTab == null) {
            return;
        }
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

    protected void setRightBtnVisible(boolean isVisible) {
        toolbarRight.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    protected void setRightBtnClickListener(View.OnClickListener clickListener) {
        toolbarRight.setOnClickListener(clickListener);
    }

    protected void setRightBtnText(String text) {
        toolbarRight.setText(text);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_MODERATE) {

        }
    }
}
