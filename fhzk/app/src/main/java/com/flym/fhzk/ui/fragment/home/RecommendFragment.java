package com.flym.fhzk.ui.fragment.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.GoodsListAdapter;
import com.flym.fhzk.adapter.home.RankAdapter;
import com.flym.fhzk.base.BaseViewPageFragment;
import com.flym.fhzk.data.model.ActiveBean;
import com.flym.fhzk.data.model.BannerBean;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.action.LargeVouchersActivity;
import com.flym.fhzk.ui.activity.action.MailActivity;
import com.flym.fhzk.ui.activity.action.RankActivity;
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
 * Created by Administrator on 2017/11/27 0027.
 */

public class RecommendFragment extends BaseViewPageFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.mail_image_view)
    ImageView mailImageView;
    @BindView(R.id.voucher_image_view)
    ImageView voucherImageView;
    @BindView(R.id.girl_image_view)
    ImageView girlImageView;
    @BindView(R.id.now_image_view)
    ImageView nowImageView;
    @BindView(R.id.find_all)
    TextView findAll;
    @BindView(R.id.reak_recyvleview)
    RecyclerView reakRecyvleview;
    @BindView(R.id.rank_layout)
    LinearLayout rankLayout;
    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    @BindView(R.id.scrollView)
    ScrollViewChosses scrollView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.top)
    ImageView top;

    int page = 1;


    private ArrayList<GoodsBean.GoodsInfoBean> goodsInfoBeen = new ArrayList<>();
    private ArrayList<GoodsBean.GoodsInfoBean> goodsBeanList = new ArrayList<>();
    private RankAdapter rankAdapter;
    private GoodsListAdapter goodsListAdapter;
    ArrayList<BannerBean.BannerInfoBean> mBannerBeanList = new ArrayList<>();
    private ClassifyBean.SkuInfoBean skuInfoBean;
    private ActiveBean mActiveBean;

    public static RecommendFragment newInstance(ClassifyBean.SkuInfoBean skuInfoBean) {

        Bundle args = new Bundle();
        args.putParcelable("SkuInfoBean", skuInfoBean);
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

        getGoods("", false);
        getGoods("tops", true);
        getActive();
        getBanner();


        rankAdapter = new RankAdapter(goodsInfoBeen);
        reakRecyvleview.setAdapter(rankAdapter);

        goodsListAdapter = new GoodsListAdapter(goodsBeanList);
        goodsList.setAdapter(goodsListAdapter);
        goodsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page += 1;
                getGoods("", true);
            }
        }, goodsList);

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        skuInfoBean = getArguments().getParcelable("SkuInfoBean");
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);
        reakRecyvleview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        goodsList.setLayoutManager(new GridLayoutManager(context, 2));
        goodsList.setNestedScrollingEnabled(false);
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
                ActivityManager.getInstance().toWebActivity(bannerInfoBean.link, "");
            }
        });

        scrollView.setScrollViewListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void onRefresh() {
        page = 1;
        getGoods("", true);

    }

    @OnClick({R.id.mail_image_view, R.id.voucher_image_view, R.id.girl_image_view,
            R.id.now_image_view, R.id.find_all, R.id.top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.mail_image_view:
                ActivityManager.getInstance().startNextActivity(new Intent(context, MailActivity.class)
                        .putExtra("channelId", mActiveBean.channelInfo.get(0).id));
                break;
            case R.id.voucher_image_view:
                ActivityManager.getInstance().startNextActivity(new Intent(context, LargeVouchersActivity.class)
                        .putExtra("channelId", mActiveBean.channelInfo.get(1).id));
                break;
            case R.id.girl_image_view:
                ActivityManager.getInstance().startNextActivity(new Intent(context, GoodsActivity.class)
                        .putExtra("keyword", mActiveBean.channelInfo.get(2).pic)
                );
                break;
            case R.id.now_image_view:
                ActivityManager.getInstance().startNextActivity(new Intent(context, GoodsActivity.class)
                        .putExtra("keyword",mActiveBean.channelInfo.get(3).pic)
                );
                break;
            case R.id.find_all:
                ActivityManager.getInstance().startNextActivity(new Intent(context, RankActivity.class));
                break;
            case R.id.top:
                scrollView.scrollTo(0, 0);
                break;

        }
    }


    /**
     * 5. 轮播图
     */
    public void getBanner() {
        compositeDisposable.add(RetrofitUtil.getApiService().getBanner(String.valueOf(skuInfoBean.id))
                .compose(new SimpleTransFormer<BannerBean>(BannerBean.class))
                .subscribeWith(new DisposableWrapper<BannerBean>() {
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
     * 7.	活动位
     */
    public void getActive() {
        compositeDisposable.add(RetrofitUtil.getApiService().getActive(null)
                .compose(new SimpleTransFormer<ActiveBean>(ActiveBean.class))
                .subscribeWith(new DisposableWrapper<ActiveBean>() {
                    @Override
                    public void onNext(ActiveBean activeBean) {
                        if (activeBean.channelInfo != null && activeBean.channelInfo.size() > 0) {
                            mActiveBean = activeBean;
                            GlideUtil.loadImage(context, activeBean.channelInfo.get(0).pic, mailImageView);
                            GlideUtil.loadImage(context, activeBean.channelInfo.get(1).pic, voucherImageView);
                            GlideUtil.loadImage(context, activeBean.channelInfo.get(2).pic, girlImageView);
                            GlideUtil.loadImage(context, activeBean.channelInfo.get(3).pic, nowImageView);
                        }
                    }
                }));
    }

    /**
     * 8.  商品
     * dataType: 每日排行：tops，为空则获取所有商品
     */
    public void getGoods(final String dataType, boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getGoods(dataType, String.valueOf(page))
                .compose(new SimpleTransFormer<GoodsBean>(GoodsBean.class))
                .subscribeWith(new DisposableWrapper<GoodsBean>(loadingDialog) {
                    @Override
                    public void onNext(GoodsBean goodsBean) {
                        if ("tops".equals(dataType)) {
                            goodsInfoBeen.clear();
                            goodsInfoBeen.addAll(goodsBean.goodsInfo);
                            rankAdapter.notifyDataSetChanged();
                        } else if (TextUtils.isEmpty(dataType)) {
                            if (page == 1) {
                                goodsBeanList.clear();
                                goodsListAdapter.setEnableLoadMore(false);
                            }
                            if (goodsBean.goodsInfo != null && goodsBean.goodsInfo.size() > 0) {
                                goodsListAdapter.loadMoreComplete();
                                goodsBeanList.addAll(goodsBean.goodsInfo);
                            } else {
                                goodsListAdapter.loadMoreEnd();
                            }
                            goodsListAdapter.notifyDataSetChanged();
                        }
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
    public void onFix() {

    }

    @Override
    public void onReset() {

    }

    @Override
    public void loadMore() {
        super.loadMore();
        goodsListAdapter.setEnableLoadMore(true);

    }
}
