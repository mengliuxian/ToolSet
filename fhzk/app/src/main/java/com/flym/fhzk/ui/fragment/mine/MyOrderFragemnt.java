package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.mine.OrderAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.utils.itemdecoration.LinearDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2017/12/7 0007
 */

public class MyOrderFragemnt extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    int page = 1;
    private int tab;

    public static MyOrderFragemnt newInstance(int tab) {

        Bundle args = new Bundle();
        args.putInt("tab", tab);
        MyOrderFragemnt fragment = new MyOrderFragemnt();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("淘宝订单");
        return toolbar;
    }

    @Override
    protected void initData() {

        initTab();
        tabLayout.getTabAt(tab).select();
        orderAdapter = new OrderAdapter(new ArrayList<String>());
        recyclerView.setAdapter(orderAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //屏幕显示的区域的高度
                int i = recyclerView.computeVerticalScrollExtent();
                //整个view控件的高度
                int i1 = recyclerView.computeVerticalScrollRange();
                if (i1 > i) {
                    orderAdapter.setEnableLoadMore(true);
                    orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page += 1;
//                            getSkuGoods(true);
                        }
                    }, recyclerView);
                }
            }
        });

    }

    public void initTab() {
        tabLayout.addTab(tabLayout.newTab().setText("全部订单"));
        tabLayout.addTab(tabLayout.newTab().setText("即将到账"));
        tabLayout.addTab(tabLayout.newTab().setText("已到账"));
        tabLayout.addTab(tabLayout.newTab().setText("无效订单"));
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tab = getArguments().getInt("tab");
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new  LinearDividerItemDecoration(context, LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    default:
                        break;
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                page = 1;

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tb;
    }


}
