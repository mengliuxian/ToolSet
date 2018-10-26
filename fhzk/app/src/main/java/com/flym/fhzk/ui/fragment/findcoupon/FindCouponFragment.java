package com.flym.fhzk.ui.fragment.findcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.ClassifyPopAdapter;
import com.flym.fhzk.adapter.home.GoodsListAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.home.SingleClassifyActivity;
import com.flym.fhzk.ui.activity.search.SearchActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.StatusBarUtil;
import com.flym.fhzk.utils.TextSpannableUtils;
import com.flym.fhzk.view.DancingNumberView;
import com.flym.fhzk.view.LoadingDialog;
import com.flym.fhzk.view.ScrollViewChosses;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/1 0001
 */

public class FindCouponFragment extends BaseFragment {

    @BindView(R.id.coupon_num)
    DancingNumberView couponNum;
    @BindView(R.id.guide)
    TextView guide;
    @BindView(R.id.search_edit)
    TextView searchEdit;
    @BindView(R.id.top_layout)
    RelativeLayout topLayout;
    @BindView(R.id.all_classify)
    TextView allClassify;
    @BindView(R.id.classify)
    RecyclerView classify;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.good_list)
    RecyclerView goodList;
    @BindView(R.id.scrollView)
    ScrollViewChosses scrollView;
    @BindView(R.id.top)
    ImageView top;

    private ArrayList<GoodsBean.GoodsInfoBean> goodsBeanList = new ArrayList<>();
    private ClassifyPopAdapter classifyadapter;
    private GoodsListAdapter goodsListAdapter;
    private ArrayList<ClassifyBean.SkuInfoBean> skuInfoList = new ArrayList<>();
    int page = 1;
    boolean isError = false;


    public static FindCouponFragment newInstance() {

        Bundle args = new Bundle();

        FindCouponFragment fragment = new FindCouponFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {
        getCouponSku();
        getCoupons();
        getCouponGoods(false);
        classifyadapter = new ClassifyPopAdapter(skuInfoList);
        goodsListAdapter = new GoodsListAdapter(goodsBeanList);

        goodsListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page += 1;
                getCouponGoods(true);
            }
        }, goodList);

        classify.setAdapter(classifyadapter);
        goodList.setAdapter(goodsListAdapter);
        classifyadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifyBean.SkuInfoBean skuInfoBean = skuInfoList.get(position);
                //跳转到SingleClassifyActivity
                ActivityManager.getInstance().startNextActivity(new Intent(context,
                        SingleClassifyActivity.class)
                        .putExtra("SkuInfoBean", skuInfoBean));
            }
        });
        goodsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转到商品详情
            }
        });
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarColor(getActivity(),R.color.transparent);
        classify.setLayoutManager(new GridLayoutManager(context, 4));
        goodList.setLayoutManager(new GridLayoutManager(context, 2));

        classify.setNestedScrollingEnabled(false);
        goodList.setNestedScrollingEnabled(false);

        scrollView.setScrollViewListener(this);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_find_coupon;
    }

    @OnClick({R.id.guide, R.id.search_edit, R.id.top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.guide:
                break;
            case R.id.search_edit:
                ActivityManager.getInstance().startNextActivity(SearchActivity.class);
                break;
            case R.id.top:
                scrollView.scrollTo(0, 0);
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


    /**
     * 6.	商品分类
     */
    public void getCouponSku() {
        compositeDisposable.add(RetrofitUtil.getApiService().getCouponSku(null)
                .compose(new SimpleTransFormer<ClassifyBean>(ClassifyBean.class))
                .subscribeWith(new DisposableWrapper<ClassifyBean>() {
                    @Override
                    public void onNext(ClassifyBean classifyBean) {
                        skuInfoList.clear();
                        if (classifyBean.skuInfo != null) {
                            skuInfoList.addAll(classifyBean.skuInfo);
                        }
                        classifyadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        isError = true;
                    }
                }));

    }


    /**
     * 11.	优惠券
     */
    public void getCoupons() {
        compositeDisposable.add(RetrofitUtil.getApiService().getCoupons(null)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        String format = String.format(getString(R.string.couponNum), simpleString.string);
                        Spannable spannable = TextSpannableUtils.setSpannable(context, format,
                                1, simpleString.string.length() + 1, 16,
                                ContextCompat.getColor(context, R.color.white));
                        couponNum.setText(spannable);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        isError = true;
                    }
                }));
    }

    /**
     * 12.	找券商品
     */
    public void getCouponGoods(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getCouponGoods(null)
                .compose(new SimpleTransFormer<GoodsBean>(GoodsBean.class))
                .subscribeWith(new DisposableWrapper<GoodsBean>(loadingDialog) {
                    @Override
                    public void onNext(GoodsBean goodsBean) {
                        if (page == 1) {
                            goodsBeanList.clear();
                        }
                        if (goodsBean.goodsInfo != null && goodsBean.goodsInfo.size() > 0) {
                            goodsBeanList.addAll(goodsBean.goodsInfo);
                            goodsListAdapter.loadMoreComplete();
                            goodsListAdapter.setEnableLoadMore(false);
                        } else {
                            goodsListAdapter.loadMoreEnd();
                        }
                        goodsListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        goodsListAdapter.loadMoreFail();
                        isError = true;
                    }
                }));
    }

    /**
     * 判断fragment是否隐藏
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isError) {
            getCouponSku();
            getCoupons();
            getCouponGoods(false);
        }else if (!hidden)
        {
            StatusBarUtil.setStatusBarColor(getActivity(),R.color.transparent);
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
