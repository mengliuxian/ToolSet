package com.flym.yh.ui.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.ui.fragment.home.MyServiceFragment;

import butterknife.BindView;

/**
 * @author lsq
 * @date 2018/3/21
 * 我的服务
 */
public class MyServiceActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;

    private BaseFragment currentFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        switchTab();
    }

    @Override
    protected void initData() {

    }

    public void switchTab() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        currentFragment = null;
        currentFragment = MyServiceFragment.newInstance();
        String tag = getString(R.string.my_service);
        if (fm.findFragmentByTag(tag) != null) {
            ft.show(fm.findFragmentByTag(tag));
        } else {
            ft.add(R.id.container, currentFragment, tag);
        }
        ft.commitAllowingStateLoss();
    }

}
