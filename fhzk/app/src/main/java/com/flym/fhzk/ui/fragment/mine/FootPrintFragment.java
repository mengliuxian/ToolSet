package com.flym.fhzk.ui.fragment.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.mine.CollectAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.GoodsBean;
import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.utils.ToastUtil;
import com.flym.fhzk.utils.itemdecoration.LinearDividerItemDecoration;
import com.flym.fhzk.view.CustomDialog;
import com.flym.fhzk.view.LoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/4 0004
 */

public class FootPrintFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.all_remove)
    TextView allRemove;

    int page = 1;
    ArrayList<GoodsBean.GoodsInfoBean> collectBeanList = new ArrayList<>();

    private CollectAdapter collectAdapter;
    private Dialog dialog;

    public static FootPrintFragment newInstance() {

        Bundle args = new Bundle();

        FootPrintFragment fragment = new FootPrintFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("我的足迹");
        return toolbar;
    }

    @Override
    protected void initData() {
        getBrowsed(false);

        collectAdapter = new CollectAdapter(collectBeanList);
        goodsList.setAdapter(collectAdapter);

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
                    collectAdapter.setEnableLoadMore(true);
                    collectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page += 1;
                            getBrowsed(true);
                        }
                    }, goodsList);
                }
            }
        });

        collectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                int id = view.getId();
                switch (id) {
                    case R.id.remove:
                        dialog = CustomDialog.HintNoTitleDialog(context, "是否删除足迹", null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                GoodsBean.GoodsInfoBean goodsInfoBean = collectBeanList.get(position);
                                delBrowsed(String.valueOf(goodsInfoBean.id), position);
                            }
                        });
                        ToastUtil.showMessage(context, "删除");
                        break;
                    case R.id.relative_layout:
                        ToastUtil.showMessage(context, "跳转");
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);

        goodsList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        goodsList.addItemDecoration(new  LinearDividerItemDecoration(context, LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_foot_print;
    }

    @Override
    public void onRefresh() {
        page = 1;
        getBrowsed(true);
    }

    /**
     * 21.	我的足迹
     *
     * @param is
     */
    public void getBrowsed(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getBrowsed(String.valueOf(page))
                .compose(new SimpleTransFormer<GoodsBean>(GoodsBean.class))
                .subscribeWith(new DisposableWrapper<GoodsBean>(loadingDialog) {
                    @Override
                    public void onNext(GoodsBean goodsBean) {
                        if (page == 1) {
                            collectBeanList.clear();
                            collectAdapter.setEnableLoadMore(false);
                        }
                        if (goodsBean.goodsInfo != null && goodsBean.goodsInfo.size() > 0) {
                            collectBeanList.addAll(goodsBean.goodsInfo);
                            collectAdapter.loadMoreComplete();
                        } else {
                            collectAdapter.loadMoreEnd();
                        }

                        collectAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        swipeRefresh.setRefreshing(false);
                        collectAdapter.loadMoreFail();
                    }
                }));
    }

    @OnClick(R.id.all_remove)
    public void onViewClicked() {
        dialog = CustomDialog.HintNoTitleDialog(context, "删除全部历史记录", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                delBrowsed("", -1);
            }
        });
    }

    /**
     * 22.	删除我的足迹
     *
     * @param id
     */
    public void delBrowsed(String id, final int position) {
        compositeDisposable.add(RetrofitUtil.getApiService().delBrowsed(id,null)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        if (position == -1) {
                            collectBeanList.clear();
                            collectAdapter.notifyDataSetChanged();
                        } else {
                            collectBeanList.remove(position);
                            collectAdapter.notifyItemRemoved(position);
                        }
                    }
                }));

    }
}
