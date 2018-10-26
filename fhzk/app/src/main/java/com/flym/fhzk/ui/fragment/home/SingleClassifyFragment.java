package com.flym.fhzk.ui.fragment.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import com.flym.fhzk.adapter.home.ClassifyAdapter;
import com.flym.fhzk.adapter.home.GoodsListAdapter;
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
 * @auther by Administrator
 * @date by 2017/11/30 0030
 */

public class SingleClassifyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    @BindView(R.id.scrollView)
    ScrollViewChosses scrollView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.top)
    ImageView top;

    private ClassifyAdapter classifyadapter;
    private GoodsListAdapter goodsListAdapter;
    private ClassifyBean.SkuInfoBean skuInfoBean;
    private ArrayList<GoodsBean.GoodsInfoBean> goodsBeanList = new ArrayList<>();
    int page = 1;
    String sort = "all";

    public static SingleClassifyFragment newInstance(ClassifyBean.SkuInfoBean skuInfoBean) {

        Bundle args = new Bundle();
        args.putParcelable("SkuInfoBean", skuInfoBean);
        SingleClassifyFragment fragment = new SingleClassifyFragment();
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
        initTab();
        classifyadapter = new ClassifyAdapter(skuInfoBean.son);
        goodsListAdapter = new GoodsListAdapter(goodsBeanList);

        recyclerView.setAdapter(classifyadapter);
        goodsList.setAdapter(goodsListAdapter);

        goodsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //屏幕显示的区域的高度
                int i = goodsList.computeVerticalScrollExtent();
                //整个view控件的高度
                int i1 = goodsList.computeVerticalScrollRange();
                if (i1 > i) {
                    goodsListAdapter.setEnableLoadMore(true);
                    goodsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page += 1;
                            getCouponSkuGoods(true);
                        }
                    }, goodsList);
                }
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        skuInfoBean = getArguments().getParcelable("SkuInfoBean");

        goodsList.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setLayoutManager(new GridLayoutManager(context,4));

        goodsList.setNestedScrollingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);

        scrollView.setScrollViewListener(this);

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        //综合
                        sort = "all";
                        break;
                    case 1:
                        //销量
                        sort = "sales";
                        break;
                    case 2:
                        //最新
                        sort = "new";
                        break;
                    default:
                        break;
                }
                page = 1;
                getCouponSkuGoods(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initTab() {
        int tabCount = tabLayout.getTabCount();
        if (tabCount != 3) {
            tabLayout.addTab(tabLayout.newTab().setText("综合"));
            tabLayout.addTab(tabLayout.newTab().setText("销量"));
            tabLayout.addTab(tabLayout.newTab().setText("最新"));
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_single_classify;
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
        getCouponSkuGoods(true);
    }


    /**
     * 13.	找券-分类商品
     *
     * @param is
     */
    public void getCouponSkuGoods(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getCouponSkuGoods(String.valueOf(skuInfoBean.id), sort, String.valueOf(page))
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
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        swipeRefresh.setRefreshing(false);
                        goodsListAdapter.loadMoreFail();
                    }
                }));
    }

    @Override
    public void onFix() {

    }

    @Override
    public void onReset() {

    }
}
