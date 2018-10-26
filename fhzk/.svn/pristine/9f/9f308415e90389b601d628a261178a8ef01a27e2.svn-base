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

/**
 * @author Administrator
 * @date 2017/12/4 0004
 */

public class CollectFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collect_list)
    RecyclerView collectList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    int page = 1;
    ArrayList<GoodsBean.GoodsInfoBean> collectBeanList = new ArrayList<>();
    private CollectAdapter collectAdapter;
    private Dialog dialog;

    public static CollectFragment newInstance() {

        Bundle args = new Bundle();

        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("我的收藏");
        return toolbar;
    }

    @Override
    protected void initData() {
        getCollected(false);
        collectAdapter = new CollectAdapter(collectBeanList);
        collectList.setAdapter(collectAdapter);

        collectList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //如果 dx>0 则表示 右滑 ， dx<0 表示 左滑
                //dy <0 表示 上滑， dy>0 表示下滑
                //屏幕显示的区域的高度
                final int b = collectList.computeVerticalScrollExtent();
                //整个view控件的高度
                final int b2 = collectList.computeVerticalScrollRange();

                if (b2 > b) {
                    collectAdapter.setEnableLoadMore(true);
                    collectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page += 1;
                            getCollected(true);
                        }
                    }, collectList);
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
        collectList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        collectList.addItemDecoration(new LinearDividerItemDecoration(context,LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_collect;
    }

    /**
     * 19.	我的收藏
     */
    public void getCollected(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getCollected(String.valueOf(page))
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

    /**
     * 18.	取消收藏
     *
     * @param id
     */
    public void collect(String id, final int position) {
        compositeDisposable.add(RetrofitUtil.getApiService().collect(id, "2")
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        collectBeanList.remove(position);
                        collectAdapter.notifyItemRemoved(position);
                    }
                }));
    }

    /**
     * 25.	删除我的收藏
     *
     * @param id
     */
    public void delBrowsed(String id, final int position) {
        compositeDisposable.add(RetrofitUtil.getApiService().delBrowsed(null,id)
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

    @Override
    public void onRefresh() {
        page = 1;
        getCollected(true);
    }
}
