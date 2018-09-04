package com.flym.yh.ui.activity.information;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;

public class InformationDetailsActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {

        } else if (type == 1) {

        }
    }

    @Override
    protected void initData() {

    }
}
