package com.flym.fhzk.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.ClassifyAdapter;
import com.flym.fhzk.adapter.home.GoodsListAdapter;
import com.flym.fhzk.base.BaseViewPageFragment;
import com.flym.fhzk.data.model.BannerBean;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.home.AllClassifyActivity;
import com.flym.fhzk.ui.activity.search.GoodsActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.GlideUtil;
import com.flym.fhzk.view.LoadingDialog;
import com.flym.fhzk.view.ScrollViewChosses;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author Mxm
 * @date 2017/11/30 0030
 */

public class ClassifyFragment extends BaseViewPageFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.all_classify)
    TextView allClassify;
    @BindView(R.id.classify)
    RecyclerView classify;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.good_list)
    RecyclerView goodList;
    @BindView(R.id.scrollView)
    ScrollViewChosses scrollView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.top)
    ImageView top;

    private ArrayList<GoodsBean.GoodsInfoBean> goodsBeanList = new ArrayList<>();
    private ClassifyAdapter classifyadapter;
    private GoodsListAdapter goodsListAdapter;
    private ClassifyBean.SkuInfoBean skuInfoBean;
    ArrayList<BannerBean.BannerInfoBean> mBannerBeanList = new ArrayList<>();
    int page = 1;
    private String type = "";
    private int id;
    private boolean fixedFlag = false, resetFlag = false;

    public static ClassifyFragment newInstance(ClassifyBean.SkuInfoBean skuInfoBean) {

        Bundle args = new Bundle();
        args.putParcelable("SkuInfoBean", skuInfoBean);
        ClassifyFragment fragment = new ClassifyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {
        getBanner();
        initTab();

        classifyadapter = new ClassifyAdapter(skuInfoBean.son);
        goodsListAdapter = new GoodsListAdapter(goodsBeanList);

        classify.setAdapter(classifyadapter);
        goodList.setAdapter(goodsListAdapter);
        goodsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page += 1;
                getSkuGoods(true);
            }
        }, goodList);
        classifyadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifyBean.SkuInfoBean.SonBean sonBean = skuInfoBean.son.get(position);
                ActivityManager.getInstance().startNextActivity(new Intent(context,
                        GoodsActivity.class).putExtra("keyword", sonBean.name));
            }
        });

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        skuInfoBean = getArguments().getParcelable("SkuInfoBean");
        id = skuInfoBean.id;
        classify.setLayoutManager(new GridLayoutManager(context, 4));
        goodList.setLayoutManager(new GridLayoutManager(context, 2));

        classify.setNestedScrollingEnabled(false);
        goodList.setNestedScrollingEnabled(false);

        goodList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int top = tabLayout.getTop();
                        if (top == 0) {
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        top = tabLayout.getTop();
                        if (top == 0) {
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:

                        break;
                }
                return false;
            }
        });
        scrollView.setScrollViewListener(this);

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);

        banner.setAdapter(new BGABanner.Adapter<ImageView, BannerBean.BannerInfoBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, BannerBean.BannerInfoBean model, int position) {
                GlideUtil.loadImage(context, model.path, itemView, R.mipmap.ic_launcher);
            }
        });
        //banner点击事件
        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {

                BannerBean.BannerInfoBean bannerInfoBean = mBannerBeanList.get(position);
                ActivityManager.getInstance().toWebActivity(bannerInfoBean.link,"");
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        //综合
                        type = "all";
                        break;
                    case 1:
                        //销量
                        type = "sales";
                        break;
                    case 2:
                        //最新
                        type = "new";
                        break;
                    default:
                        break;
                }
                page = 1;
                getSkuGoods(false);
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
        return R.layout.fragment_classify;
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        getSkuGoods(true);
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

    @OnClick({R.id.all_classify, R.id.top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_classify:
                ActivityManager.getInstance().startNextActivity(new Intent(context,
                        AllClassifyActivity.class).putExtra("SkuInfoBean", skuInfoBean));
                break;
            case R.id.top:
//                scrollView.scrollTo(0, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 5. 轮播图
     */
    public void getBanner() {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        compositeDisposable.add(RetrofitUtil.getApiService().getBanner(String.valueOf(skuInfoBean.id))
                .compose(new SimpleTransFormer<BannerBean>(BannerBean.class))
                .subscribeWith(new DisposableWrapper<BannerBean>(loadingDialog) {
                    @Override
                    public void onNext(BannerBean bannerBean) {
                        if (bannerBean.bannerInfo != null) {
                            mBannerBeanList.clear();
                            mBannerBeanList.addAll(bannerBean.bannerInfo);
                            banner.setData(R.layout.item_banner, bannerBean.bannerInfo, null);
                        }

                    }
                }));
    }

    /**
     * 23.	首页-分类商品
     * 综合(all)，最新(new)，销量(sales)
     */
    public void getSkuGoods(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getSkuGoods(
                String.valueOf(id), type, String.valueOf(page))
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
    public void onFix() {
        //时事件传递给 RecyclerView
        enableNestedScrolling(goodList);
    }

    @Override
    public void onReset() {
        //时事件传递给 ScrollView
        disableNestedScrolling(goodList);
    }

    //Enable nested scrolling of recyclerView in ScrollView
    private void enableNestedScrolling(RecyclerView recyclerView) {
        if (recyclerView != null) {
            if (!fixedFlag) {
                setFixedFlag();
                recyclerView.setNestedScrollingEnabled(true);
            }
        }
    }

    //Disable nested scrolling of recyclerView in ScrollView
    private void disableNestedScrolling(RecyclerView recyclerView) {
        if (recyclerView != null) {
            if (!resetFlag) {
                setResetFlag();
                recyclerView.setNestedScrollingEnabled(false);
            }
        }
    }

    private void setFixedFlag() {
        setFlag(false);
    }

    private void setResetFlag() {
        setFlag(true);
    }

    //True:reset;false:fix
    private void setFlag(boolean reset) {
        if (reset) {
            resetFlag = true;
            fixedFlag = false;
        } else {
            fixedFlag = true;
            resetFlag = false;
        }
    }

    @Override
    public void loadMore() {
        super.loadMore();
        goodsListAdapter.setEnableLoadMore(true);

    }
}
