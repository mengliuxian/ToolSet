package com.flym.fhzk.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.ui.fragment.home.AllClassifyFragment;
import com.flym.fhzk.utils.FragmentUtil;


/**
 * @author Mxm
 * @date 2017/11/30 0030
 */
public class AllClassifyActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_container;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        ClassifyBean.SkuInfoBean skuInfoBean = intent.getParcelableExtra("SkuInfoBean");
        FragmentUtil.replaceFragmentToActivity(R.id.container, AllClassifyFragment.newInstance(skuInfoBean), getSupportFragmentManager());
    }

    @Override
    protected void initData() {

    }
}
