package com.flym.fhzk.ui.activity.login;

import android.os.Bundle;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.ui.fragment.login.LoginFragment;
import com.flym.fhzk.utils.FragmentUtil;


public class LoginActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

            FragmentUtil.replaceFragmentToActivity(R.id.container, LoginFragment.newInstance(), getSupportFragmentManager());

    }

    @Override
    protected void initData() {

    }
}
