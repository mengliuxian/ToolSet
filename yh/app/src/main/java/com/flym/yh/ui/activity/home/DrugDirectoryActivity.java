package com.flym.yh.ui.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.ui.fragment.home.DrugDirectoryFragment;

import butterknife.BindView;

/**
 * @author lsq
 * @date 2018/3/22
 * 药物目录  药物列表
 */
public class DrugDirectoryActivity extends BaseActivity {


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
        currentFragment = DrugDirectoryFragment.newInstance(0, 0, "");
        String tag = getString(R.string.drug_directory);
        if (fm.findFragmentByTag(tag) != null) {
            ft.show(fm.findFragmentByTag(tag));
        } else {
            ft.add(R.id.container, currentFragment, tag);
        }
        ft.commitAllowingStateLoss();
    }
}
