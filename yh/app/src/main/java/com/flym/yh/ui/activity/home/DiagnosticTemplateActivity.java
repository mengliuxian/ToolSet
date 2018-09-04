package com.flym.yh.ui.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.ui.fragment.home.DiagnosticTemplateFragment;

import butterknife.BindView;

/**
 * @author lsq
 * @date 2018/3/22
 * 诊断模板
 */
public class DiagnosticTemplateActivity extends BaseActivity {


    @BindView(R.id.container)
    FrameLayout container;

    private BaseFragment currentFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra("type", 0);

        switchTab(type);
    }

    @Override
    protected void initData() {

    }

    public void switchTab(int type) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        currentFragment = null;
        currentFragment = DiagnosticTemplateFragment.newInstance(type);
        String tag = getString(R.string.diagnostic_template);
        if (fm.findFragmentByTag(tag) != null) {
            ft.show(fm.findFragmentByTag(tag));
        } else {
            ft.add(R.id.container, currentFragment, tag);
        }
        ft.commitAllowingStateLoss();
    }

}
