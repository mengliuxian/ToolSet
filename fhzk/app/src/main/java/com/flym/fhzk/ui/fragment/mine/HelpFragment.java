package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.mine.HelpAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.HelpConfBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.utils.FragmentUtil;
import com.flym.fhzk.utils.itemdecoration.LinearDividerItemDecoration;
import com.flym.fhzk.view.LoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class HelpFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private HelpAdapter helpAdapter;

    ArrayList<HelpConfBean.HelpConfBeans> helpList = new ArrayList<>();

    public static HelpFragment newInstance() {

        Bundle args = new Bundle();

        HelpFragment fragment = new HelpFragment();
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
        getHelpConf();
        helpAdapter = new HelpAdapter(helpList);
        recyclerView.setAdapter(helpAdapter);

        helpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HelpConfBean.HelpConfBeans helpConfBeans = helpList.get(position);

                FragmentUtil.replaceFragmentToActivity(
                        R.id.container, HelpDetailsFragment.newInstance(String.valueOf(helpConfBeans.id)), getFragmentManager(), "帮助中心");
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(context,LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_help;
    }


    /**
     * 30.	帮助中心-1
     */
    public void getHelpConf() {
        LoadingDialog loadingDialog = LoadingDialog.show(context);
        compositeDisposable.add(RetrofitUtil.getApiService().getHelpConf(null)
                .compose(new SimpleTransFormer<HelpConfBean>(HelpConfBean.class))
                .subscribeWith(new DisposableWrapper<HelpConfBean>(loadingDialog) {
                    @Override
                    public void onNext(HelpConfBean helpConfBean) {
                        helpList.clear();
                        helpList.addAll(helpConfBean.helpConf);
                        helpAdapter.notifyDataSetChanged();
                    }
                }));
    }
}