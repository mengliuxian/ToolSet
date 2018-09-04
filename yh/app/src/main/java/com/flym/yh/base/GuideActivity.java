package com.flym.yh.base;

import android.os.Bundle;
import android.view.WindowManager;


import com.flym.yh.R;
import com.flym.yh.ui.activity.home.HomeActivity;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.Constants;
import com.flym.yh.utils.SharePreUtil;

import butterknife.OnClick;

/**
 * Created by Morphine on 2017/8/3.
 */

//public class GuideActivity extends SimpleContainerActivity {
public class GuideActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_guide;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        SharePreUtil.putBoolean(this, Constants.SHARE_PRE_NAME_GUIDE, true);
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.start_btn)
    public void onViewClicked() {
        ActivityManager.getInstance().startNextActivity(HomeActivity.class, true);
    }
}
