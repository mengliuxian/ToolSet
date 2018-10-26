package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.mine.NewsAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.MessageBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.utils.itemdecoration.LinearDividerItemDecoration;
import com.flym.fhzk.view.LoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class NewsFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private NewsAdapter newsAdapter;

    int page = 1;

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("系统消息");
        return toolbar;
    }

    ArrayList<MessageBean.MsgInfoBean> msgInfoBeen = new ArrayList<>();

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {
        getMessage(false);
        newsAdapter = new NewsAdapter(msgInfoBeen);
        recyclerView.setAdapter(newsAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //如果 dx>0 则表示 右滑 ， dx<0 表示 左滑
                //dy <0 表示 上滑， dy>0 表示下滑
                //屏幕显示的区域的高度
                final int b = recyclerView.computeVerticalScrollExtent();
                //整个view控件的高度
                final int b2 = recyclerView.computeVerticalScrollRange();

                if (b2 > b) {
                    newsAdapter.setEnableLoadMore(true);
                    newsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page += 1;
                            getMessage(true);
                        }
                    }, recyclerView);
                }
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new  LinearDividerItemDecoration(context, LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    /**
     * 27.	消息列表
     */
    public void getMessage(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getMessage(String.valueOf(page))
                .compose(new SimpleTransFormer<MessageBean>(MessageBean.class))
                .subscribeWith(new DisposableWrapper<MessageBean>(loadingDialog) {
                    @Override
                    public void onNext(MessageBean messageBean) {
                        if (page == 1) {
                            msgInfoBeen.clear();
                            newsAdapter.setEnableLoadMore(false);
                        }
                        if (messageBean.msgInfo != null && messageBean.msgInfo.size() > 0) {
                            msgInfoBeen.addAll(messageBean.msgInfo);
                            newsAdapter.loadMoreComplete();
                        } else {
                            newsAdapter.loadMoreEnd();
                        }
                        newsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        newsAdapter.loadMoreFail();
                    }
                }));
    }

}
