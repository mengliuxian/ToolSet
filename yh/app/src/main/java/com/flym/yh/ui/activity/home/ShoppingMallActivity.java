package com.flym.yh.ui.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.ui.fragment.home.ShoppingMallFragment;
import com.flym.yh.utils.FragmentUtil;

public class ShoppingMallActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FragmentUtil.replaceFragmentToActivity(R.id.container, new ShoppingMallFragment(),
                getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
