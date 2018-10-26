package com.flym.fhzk.ui.activity.mine;

import android.os.Bundle;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.ui.fragment.mine.FootPrintFragment;
import com.flym.fhzk.utils.FragmentUtil;

public class FootPrintActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FragmentUtil.replaceFragmentToActivity(R.id.container, FootPrintFragment.newInstance(), getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
