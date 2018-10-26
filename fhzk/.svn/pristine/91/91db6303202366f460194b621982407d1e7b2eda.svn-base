package com.flym.fhzk.ui.fragment.action;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.GoodsListAdapter;
import com.flym.fhzk.adapter.home.TabLayoutAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.view.LoadingDialog;
import com.flym.fhzk.view.ScrollViewChosses;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class MailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.scrollView)
    ScrollViewChosses scrollView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.top)
    ImageView top;

    public ArrayList<ClassifyBean.SkuInfoBean> tabBeens = new ArrayList<>();
    private ArrayList<GoodsBean.GoodsInfoBean> goodsBeanList = new ArrayList<>();
    private TabLayoutAdapter tabLayoutAdapter;
    private GoodsListAdapter goodsListAdapter;
    private int page = 1;
    int oldPosition = -1;
    private int channelId;
    private String selected = "1";
    private String skuId = null;

    public static MailFragment newInstance(int channelId) {

        Bundle args = new Bundle();
        args.putInt("channelId", channelId);
        MailFragment fragment = new MailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("9块9包邮");
        return toolbar;
    }

    @Override
    protected void initData() {
        getChannelSku();
        getChannelGoods(false);
        tabLayoutAdapter = new TabLayoutAdapter(tabBeens);
        tabLayout.setAdapter(tabLayoutAdapter);

        tabLayoutAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selected = "0";
                skuId = String.valueOf(tabBeens.get(position).id);
                nowRecommend.setSelected(false);
                tabBeens.get(position).is = true;
                if (oldPosition != -1) {
                    tabBeens.get(oldPosition).is = false;
                }
                oldPosition = position;
                scrollTab(position);
                tabLayoutAdapter.notifyDataSetChanged();
                page = 1;
                getChannelGoods(false);
            }
        });

        goodsListAdapter = new GoodsListAdapter(goodsBeanList);
        goodsList.setAdapter(goodsListAdapter);



        goodsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page += 1;
                getChannelGoods(true);
            }
        }, goodsList);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        channelId = getArguments().getInt("channelId");
        tabLayout.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        goodsList.setLayoutManager(new GridLayoutManager(context, 2));
        goodsList.setNestedScrollingEnabled(false);
        scrollView.setScrollViewListener(this);
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);
        nowRecommend.setSelected(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mail;
    }

    @OnClick({R.id.top, R.id.now_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.top:
                scrollView.scrollTo(0, 0);
                break;
            case R.id.now_recommend:
                selected = "1";
                skuId = null;
                page = 1;
                getChannelGoods(false);
                nowRecommend.setSelected(true);
                tabBeens.get(oldPosition).is = false;
                tabLayoutAdapter.notifyItemChanged(oldPosition);
                oldPosition = -1;
                break;
        }

    }


    /**
     * 根据滑动显示 “返回顶层” 按钮
     *
     * @param show
     */
    @Override
    public void showImageView(boolean show) {
        super.showImageView(show);
        if (show) {
            top.setVisibility(View.VISIBLE);
        } else {
            top.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        getChannelGoods(true);
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


    /**
     * 34.	活动位-分类
     */
    public void getChannelSku() {
        compositeDisposable.add(RetrofitUtil.getApiService().getChannelSku(String.valueOf(channelId))
                .compose(new SimpleTransFormer<ClassifyBean>(ClassifyBean.class))
                .subscribeWith(new DisposableWrapper<ClassifyBean>() {
                    @Override
                    public void onNext(ClassifyBean classifyBean) {
                        tabBeens.clear();
                        tabBeens.addAll(classifyBean.skuInfo);
                        tabLayoutAdapter.notifyDataSetChanged();
                    }
                }));
    }


    /**
     * 35.	活动位-商品
     *
     * @param is
     */
    public void getChannelGoods(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getChannelGoods(String.valueOf(channelId), skuId, selected, String.valueOf(page))
                .compose(new SimpleTransFormer<GoodsBean>(GoodsBean.class))
                .subscribeWith(new DisposableWrapper<GoodsBean>(loadingDialog) {
                    @Override
                    public void onNext(GoodsBean goodsBean) {
                        if (page == 1) {
                            goodsBeanList.clear();
                            goodsListAdapter.setEnableLoadMore(false);
                        }
                        if (goodsBean.goodsInfo != null && goodsBean.goodsInfo.size() > 0) {
                            goodsBeanList.addAll(goodsBean.goodsInfo);
                            goodsListAdapter.loadMoreComplete();
                        } else {
                            goodsListAdapter.loadMoreEnd();
                        }
                        goodsListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        goodsListAdapter.loadMoreFail();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        swipeRefresh.setRefreshing(false);
                    }
                }));
    }

    @Override
    public void loadMore() {
        super.loadMore();
        goodsListAdapter.setEnableLoadMore(true);
    }
}
