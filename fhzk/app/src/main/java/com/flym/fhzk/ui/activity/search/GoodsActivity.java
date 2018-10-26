package com.flym.fhzk.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.ui.fragment.search.GoodsFragment;
import com.flym.fhzk.utils.FragmentUtil;

public class GoodsActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");
        FragmentUtil.replaceFragmentToActivity(R.id.container, GoodsFragment.newInstance(keyword),getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
