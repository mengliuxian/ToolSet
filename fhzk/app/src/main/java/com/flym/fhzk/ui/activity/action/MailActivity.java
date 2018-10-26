package com.flym.fhzk.ui.activity.action;

import android.os.Bundle;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.ui.fragment.action.MailFragment;
import com.flym.fhzk.utils.FragmentUtil;

public class MailActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        int channelId = getIntent().getIntExtra("channelId", -1);
        FragmentUtil.replaceFragmentToActivity(R.id.container,
                MailFragment.newInstance(channelId),getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
