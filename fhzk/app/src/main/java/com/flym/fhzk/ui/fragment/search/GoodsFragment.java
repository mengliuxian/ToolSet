package com.flym.fhzk.ui.fragment.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.GoodsListAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.view.LoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class GoodsFragment extends BaseFragment {
    @BindView(R.id.search_edit)
    TextView searchEdit;
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.goods_list)
    RecyclerView goodsList;

    private GoodsListAdapter goodsListAdapter;
    private ArrayList<GoodsBean.GoodsInfoBean> goodsBeanList = new ArrayList<>();
    int page = 1;
    private String keyword;
    private String type = "";
    private int id;

    public static GoodsFragment newInstance(String keyword) {

        Bundle args = new Bundle();
        args.putString("keyword", keyword);
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static GoodsFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("ID", id);
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void initData() {
        search(false);
        initTab();
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
                            search(true);
                        }
                    }, goodsList);
                }
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        keyword = getArguments().getString("keyword");
        id = getArguments().getInt("ID", 0);
        goodsList.setLayoutManager(new GridLayoutManager(context, 2));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    default:
                        break;
                    case 0:
                        //默认
                        type = "";
                        break;
                    case 1:
                        //销量
                        type = "sales";
                        break;
                    case 2:
                        //价格
                        type = "price";
                        break;
                }
                page = 1;
                search(false);
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
            tabLayout.addTab(tabLayout.newTab().setText("默认排序"));
            tabLayout.addTab(tabLayout.newTab().setText("销量最高"));
            tabLayout.addTab(tabLayout.newTab().setText("价格最低"));
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_goods;
    }

    @OnClick(R.id.search_edit)
    public void onViewClicked() {
        ActivityManager.getInstance().currentActivity().finish();
    }


    /**
     * 9.	搜索
     * 销量（sales），价格（price），默认为空
     */
    public void search(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getKeyGoods(
                String.valueOf(id), keyword, type, String.valueOf(page))
                .compose(new SimpleTransFormer<GoodsBean>(GoodsBean.class))
                .subscribeWith(new DisposableWrapper<GoodsBean>(loadingDialog) {
                    @Override
                    public void onNext(GoodsBean goodsBean) {
                        if (page == 1) {
                            goodsBeanList.clear();
                            goodsListAdapter.setEnableLoadMore(false);
                        }
                        if (goodsBean.goodsInfo != null&&goodsBean.goodsInfo.size() > 0) {
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
                }));
    }
}
