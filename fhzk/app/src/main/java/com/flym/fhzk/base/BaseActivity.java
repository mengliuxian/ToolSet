package com.flym.fhzk.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flym.fhzk.MyApplication;
import com.flym.fhzk.R;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.FragmentUtil;
import com.flym.fhzk.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


import static com.flym.fhzk.utils.Constants.REQUEST_LOCATION_PERMISSIONS;


/**
 * Created by Morphine on 2017/6/12.
 */

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    protected String TAG = this.getClass().getSimpleName();
    protected Toolbar toolbar;
    protected Unbinder unbinder;
    protected CompositeDisposable compositeDisposable;
    protected TextView toolbarTitle;
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
        if (toolbar != null) {
            toolbar.setNavigationIcon(null);
        }
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

//    @Override
//    public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//        if (level == TRIM_MEMORY_COMPLETE) {
//            ActivityManager.getInstance().exitApp();
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                if (fragments.get(i) != null) {
                    fragments.get(i).onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    LocationListence locationListence;

    public void setLocationListence(LocationListence listence) {
        this.locationListence = listence;
    }

    public interface LocationListence {
        void success();

        void falure();

    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSIONS)
    public void checkLocationPermissions() {
        String[] perms = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {

            locationListence.success();

        } else {
            EasyPermissions.requestPermissions(this, "定位权限", REQUEST_LOCATION_PERMISSIONS, perms);
            locationListence.falure();
        }
    }

}
