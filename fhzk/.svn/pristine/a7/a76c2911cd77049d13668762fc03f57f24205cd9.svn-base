package com.flym.fhzk.ui.fragment.action;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.GoodsListAdapter;
import com.flym.fhzk.base.BaseFragment;
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

public class LargeVouchersFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    @BindView(R.id.scrollView)
    ScrollViewChosses scrollView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.top)
    ImageView top;

    private ArrayList<GoodsBean.GoodsInfoBean> goodsBeanList = new ArrayList<>();
    private GoodsListAdapter goodsListAdapter;
    public int page = 1;
    private int channelId;

    public static LargeVouchersFragment newInstance(int channelId) {

        Bundle args = new Bundle();
        args.putInt("channelId", channelId);
        LargeVouchersFragment fragment = new LargeVouchersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("超大额券");
        return toolbar;
    }

    @Override
    protected void initData() {
        getChannelGoods(false);
        goodsListAdapter = new GoodsListAdapter(goodsBeanList);
        goodsList.setAdapter(goodsListAdapter);

        goodsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //如果 dx>0 则表示 右滑 ， dx<0 表示 左滑
                //dy <0 表示 上滑， dy>0 表示下滑
                //屏幕显示的区域的高度
                final int b = goodsList.computeVerticalScrollExtent();
                //整个view控件的高度
                final int b2 = goodsList.computeVerticalScrollRange();

                if (b2 > b) {
                    goodsListAdapter.setEnableLoadMore(true);
                    goodsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page += 1;
                            getChannelGoods(true);
                        }
                    }, goodsList);
                }
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        channelId = getArguments().getInt("channelId");

        goodsList.setLayoutManager(new GridLayoutManager(context, 2));
        goodsList.setNestedScrollingEnabled(false);
        scrollView.setScrollViewListener(this);
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_large_vouchers;
    }


    @OnClick(R.id.top)
    public void onViewClicked() {
        scrollView.scrollTo(0, 0);
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
     * 35.	活动位-商品
     *
     * @param is
     */
    public void getChannelGoods(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getChannelGoods(String.valueOf(channelId), null, "0", String.valueOf(page))
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
}
