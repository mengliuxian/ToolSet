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
import com.flym.yh.adapter.home.HealthyPrescriptionAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.RecipeGetlistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.FragmentUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * @author lishuqi
 * @date 2018/3/26
 * 健康处方
 */

public class HealthyPrescriptionFragment extends BaseFragment {

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

    private int page = 1;
    private int status = 1; //状态（0撤回，1已开，2已购买）
    private List<String> tabTitles;
    private List<RecipeGetlistBean.ListBean> dataList = new ArrayList();
    private HealthyPrescriptionAdapter mAdapter;
    private int tabPosition;

    public static HealthyPrescriptionFragment newInstance() {

        Bundle args = new Bundle();

        HealthyPrescriptionFragment fragment = new HealthyPrescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {


        recipeGetlist();
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {

        mAdapter = new HealthyPrescriptionAdapter(dataList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentUtil.replaceFragmentToActivity(R.id.container, HealthyPrescriptionDetailsFragment
                        .newInstance(), getFragmentManager(), getString(R.string.health_prescription_details));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RecipeGetlistBean.ListBean listBean = dataList.get(position);
                switch (view.getId()) {
                    case R.id.tv_modify:
                        if (listBean.status == 1) {
                            //撤回操作
                            recipeRecall(listBean.id, listBean);

                        } else if (listBean.status == 0) {
                            //修改操作
                            FragmentUtil.replaceFragmentToActivity(R.id.container,
                                    OpenHealthyPrescriptionFragment.newInstance(listBean), getFragmentManager(),
                                    "健康处方");
                        }
                        break;
                    case R.id.tv_del:
                        //删除操作
                        recipeDel(listBean.id, listBean);
                        break;
                }
            }
        });

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabTitles = Arrays.asList(getResources().getStringArray(R.array.healthy_prescription_tab_text));
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
                if (tabPosition != tab.getPosition()) {
                    switchTab(tabPosition);
                } else {
                    tabPosition = tab.getPosition();
                    switchTab(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setIndicator(tab, 30, 30);
        initSpring();
    }

    private void initSpring() {

        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadmore() {
                page++;
                recipeGetlist();

            }
        });

        spring.setFooter(new DefaultFooter(context));

    }

    public void switchTab(int position) {
        switch (position) {
            case 0:
                status = 1;
                break;
            case 1:
                status = 0;
                break;
            case 2:
                break;
            default:
                break;
        }
        page = 1;
        recipeGetlist();

    }


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_healthy_prescription;
    }


    /**
     * 获取健康处方列表
     */
    public void recipeGetlist() {
        compositeDisposable.add(RetrofitUtil.getApiService().recipeGetlist(page, "", status)
                .compose(new SimpleTransFormer<RecipeGetlistBean>(RecipeGetlistBean.class))
                .subscribeWith(new DisposableWrapper<RecipeGetlistBean>() {
                    @Override
                    public void onNext(RecipeGetlistBean recipeGetlistBean) {
                        dataList.clear();
                        dataList.addAll(recipeGetlistBean.list);
                        mAdapter.notifyDataSetChanged();

                    }
                }));

    }


    /**
     * 撤回健康处方
     */
    public void recipeRecall(int id, final RecipeGetlistBean.ListBean listBean) {
        compositeDisposable.add(RetrofitUtil.getApiService().recipeRecall(id)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        dataList.remove(listBean);
                        mAdapter.notifyDataSetChanged();
                    }
                }));
    }

    /**
     * 删除健康处方
     *
     * @param id
     */
    public void recipeDel(int id, final RecipeGetlistBean.ListBean listBean) {
        compositeDisposable.add(RetrofitUtil.getApiService().recipeDel(id)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        dataList.remove(listBean);
                        mAdapter.notifyDataSetChanged();
                    }
                }));
    }

}
