package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.HelpDetailsAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.HelpDetailBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.utils.itemdecoration.LinearDividerItemDecoration;
import com.flym.fhzk.view.LoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class HelpDetailsFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private HelpDetailsAdapter helpDetailsAdapter;
    private String confId;

    int page = 1;
    ArrayList<HelpDetailBean.HelpDetailBeans> helpDetailList = new ArrayList<>();

    public static HelpDetailsFragment newInstance(String confId) {

        Bundle args = new Bundle();
        args.putString("confId", confId);
        HelpDetailsFragment fragment = new HelpDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("帮助中心");
        return toolbar;
    }

    @Override
    protected void initData() {
        getHelpDetail(false);
        helpDetailsAdapter = new HelpDetailsAdapter(helpDetailList);
        recyclerView.setAdapter(helpDetailsAdapter);

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
                    helpDetailsAdapter.setEnableLoadMore(true);
                    helpDetailsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            page += 1;
                            getHelpDetail(true);
                        }
                    }, recyclerView);
                }
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        confId = getArguments().getString("confId");

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(context,LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_help;
    }

    /**
     * 31.	帮助中心-2
     */
    public void getHelpDetail(boolean is) {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        if (is) {
            loadingDialog.dismiss();
        }
        compositeDisposable.add(RetrofitUtil.getApiService().getHelpDetail(confId, String.valueOf(page))
                .compose(new SimpleTransFormer<HelpDetailBean>(HelpDetailBean.class))
                .subscribeWith(new DisposableWrapper<HelpDetailBean>(loadingDialog) {
                    @Override
                    public void onNext(HelpDetailBean helpDetailBean) {
                        if (page == 1) {
                            helpDetailList.clear();
                            helpDetailsAdapter.setEnableLoadMore(false);
                        }
                        if (helpDetailBean.helpDetail != null && helpDetailBean.helpDetail.size() > 0) {
                            helpDetailList.addAll(helpDetailBean.helpDetail);
                            helpDetailsAdapter.loadMoreComplete();
                        } else {
                            helpDetailsAdapter.loadMoreEnd();
                        }
                        helpDetailsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        helpDetailsAdapter.loadMoreFail();
                    }
                }));
    }

}
