package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.adapter.mine.RebatesAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.ui.activity.mine.WithdrawalActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.itemdecoration.LinearDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class RebatesFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rebates)
    TextView rebates;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.accumulated_rebates)
    TextView accumulatedRebates;
    @BindView(R.id.can_rebates)
    TextView canRebates;
    @BindView(R.id.wait_rebates)
    TextView waitRebates;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RebatesAdapter rebatesAdapter;

    public static RebatesFragment newInstance() {

        Bundle args = new Bundle();

        RebatesFragment fragment = new RebatesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("我的返利");
        return toolbar;
    }

    @Override
    protected void initData() {
        initTab();
        rebatesAdapter = new RebatesAdapter(new ArrayList<String>());
        recyclerView.setAdapter(rebatesAdapter);

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(context, LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));

        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(context,
                R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(8);
    }

    public void initTab() {
        tabLayout.addTab(tabLayout.newTab().setText("收入"));
        tabLayout.addTab(tabLayout.newTab().setText("支出"));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_rebates;
    }

    @OnClick(R.id.rebates)
    public void onViewClicked() {
        ActivityManager.getInstance().startNextActivity(WithdrawalActivity.class);
    }
}
