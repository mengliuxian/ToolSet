package com.flym.yh.ui.fragment.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.ContributorsAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.GetDoctorArtivleListBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.FragmentUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 投稿
 */

public class ContributorsFragment extends BaseFragment {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.spring)
    SpringView spring;
    @BindArray(R.array.contrbutors_tab_text)
    TypedArray bottomTabTitles;

    /**
     * 请求
     */
    private int page;
    private int status;

    private List<String> tabTitles;
    private List<GetDoctorArtivleListBean.ListBean> list = new ArrayList();
    private ContributorsAdapter mAdapter;

    public static ContributorsFragment newInstance() {

        Bundle args = new Bundle();

        ContributorsFragment fragment = new ContributorsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        page = 1;
        status = 0;
        if (list.size() != 0) {
            list.clear();
        }
        setAdapter();
        getList();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new ContributorsAdapter(list, status);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentUtil.replaceFragmentToActivity(R.id.container, WriteArticleFragment.newInstance(list.get(position).getCate_id(), list.get(position).getTitle(),
                        list.get(position).getImage(), list.get(position).getContent(), list.get(position).getId()), getFragmentManager(), getString(R.string.write_article));
                list.clear();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentUtil.replaceFragmentToActivity(R.id.container, ArtivleDetailsFragment.newInstance(list.get(position).getTitle(), list.get(position).getCreate_time(),
                        list.get(position).getImage(), list.get(position).getContent()), getFragmentManager(), getString(R.string.write_article));
                list.clear();
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabTitles = Arrays.asList(getResources().getStringArray(R.array.contrbutors_tab_text));
        for (int i = 0; i < tabTitles.size(); i++) {
            String title = tabTitles.get(i);
            View view = View.inflate(getActivity(), R.layout.bottom_tab_view, null);
            TextView titleText = (TextView) view.findViewById(R.id.title);
            titleText.setText(title);
            tab.addTab(tab.newTab().setCustomView(view));
        }
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setIndicator(tab, 30, 30);
    }

    public void switchTab(int position) {
        switch (position) {
            case 0:
                status = 0;
                break;
            case 1:
                status = 1;
                break;
            case 2:
                status = 2;
                break;
            default:
                break;
        }
        list.clear();
        mAdapter.notifyDataSetChanged();
        page = 1;
        getList();
    }


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contributors;
    }


    @OnClick(R.id.tv_new)
    public void onViewClicked() {
        FragmentUtil.replaceFragmentToActivity(R.id.container, WriteArticleFragment.newInstance(0, "", "", "", 0), getFragmentManager(), getString(R.string.write_article));
    }

    /**
     * 获取医生投稿列表
     */
    public void getList() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .getDoctorArtivleList(page, status)
                .compose(new SimpleTransFormer<GetDoctorArtivleListBean>(Object.class))
                .subscribeWith(new DisposableWrapper<GetDoctorArtivleListBean>() {
                    @Override
                    public void onNext(GetDoctorArtivleListBean o) {
                        if (page == 1)
                        {
                            list.clear();
                        }
                        list.addAll(o.getList());
                        mAdapter.setType(status);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                }));
    }
}
