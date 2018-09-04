package com.flym.yh.ui.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.ui.fragment.mine.myaccount.MyAccountFragment;
import com.flym.yh.utils.FragmentUtil;

/**
 * 我的账户
 */
public class MyAccountActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FragmentUtil.replaceFragmentToActivity(R.id.container,new MyAccountFragment(),getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
