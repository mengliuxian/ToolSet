package com.flym.fhzk.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.ui.fragment.home.SingleClassifyFragment;
import com.flym.fhzk.utils.FragmentUtil;

/**
 * 单独分类展示
 *
 * @author Mxm
 * @date 2017/11/30 0030
 */
public class SingleClassifyActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        ClassifyBean.SkuInfoBean keyword = intent.getParcelableExtra("SkuInfoBean");
        FragmentUtil.replaceFragmentToActivity(R.id.container, SingleClassifyFragment.newInstance(keyword), getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
