package com.flym.fhzk.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.ClassifyAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.ui.activity.search.GoodsActivity;
import com.flym.fhzk.utils.ActivityManager;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class AllClassifyFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ClassifyAdapter classifyadapter;
    private ClassifyBean.SkuInfoBean skuInfoBean;

    public static AllClassifyFragment newInstance(ClassifyBean.SkuInfoBean skuInfoBean) {

        Bundle args = new Bundle();
        args.putParcelable("SkuInfoBean", skuInfoBean);
        AllClassifyFragment fragment = new AllClassifyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(skuInfoBean.name);
        return toolbar;
    }

    @Override
    protected void initData() {
        classifyadapter = new ClassifyAdapter(skuInfoBean.son);
        recyclerView.setAdapter(classifyadapter);
        classifyadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifyBean.SkuInfoBean.SonBean sonBean = skuInfoBean.son.get(position);
                ActivityManager.getInstance().startNextActivity(new Intent(context, GoodsActivity.class)
                        .putExtra("keyword", sonBean.name));
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        skuInfoBean = getArguments().getParcelable("SkuInfoBean");
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_all_classify;
    }


    @Override
    public void onFix() {

    }

    @Override
    public void onReset() {

    }
}
