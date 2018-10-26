package com.flym.fhzk.ui.activity.mine;

import android.os.Bundle;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.ui.fragment.mine.MyOrderFragemnt;
import com.flym.fhzk.utils.FragmentUtil;

public class MyOrderActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        int tab = getIntent().getIntExtra("tab", 0);
        FragmentUtil.replaceFragmentToActivity(R.id.container, MyOrderFragemnt.newInstance(tab), getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
