package com.flym.fhzk.ui.fragment.action;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.TabLayoutAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.ClassifyBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/8 0008
 */

public class RankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.now_recommend)
    TextView nowRecommend;
    @BindView(R.id.tab_layout)
    RecyclerView tabLayout;
    @BindView(R.id.menu_layout)
    LinearLayout menuLayout;
    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private TabLayoutAdapter tabLayoutAdapter;
    public ArrayList<ClassifyBean.SkuInfoBean> tabBeens = new ArrayList<>();
    int oldPosition = -1;

    public static RankFragment newInstance() {

        Bundle args = new Bundle();

        RankFragment fragment = new RankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("每日排行");
        return toolbar;
    }

    @Override
    protected void initData() {
        tabLayoutAdapter = new TabLayoutAdapter(tabBeens);
        tabLayout.setAdapter(tabLayoutAdapter);
        tabLayoutAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                nowRecommend.setSelected(false);
                tabBeens.get(position).is = true;
                if (oldPosition != -1) {
                    tabBeens.get(oldPosition).is = false;
                }
                oldPosition = position;
                scrollTab(position);
                tabLayoutAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabLayout.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        goodsList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_rank;
    }

    /**
     * recyclervire的item的滑动
     *
     * @param position
     */
    public void scrollTab(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) tabLayout.getLayoutManager();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int left = tabLayout.getChildAt(position - firstVisibleItemPosition).getLeft();
        int right = tabLayout.getChildAt(lastVisibleItemPosition - position).getRight();
        tabLayout.scrollBy((left - right) / 2, 0);
    }

    @Override
    public void onRefresh() {

    }

    @OnClick(R.id.now_recommend)
    public void onViewClicked() {
        nowRecommend.setSelected(true);
        tabBeens.get(oldPosition).is = false;
        tabLayoutAdapter.notifyItemChanged(oldPosition);
    }
}
