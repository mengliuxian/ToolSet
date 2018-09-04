package com.flym.yh.ui.fragment.information;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.information.InformationAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.ArticleGetListBean;
import com.flym.yh.data.model.GetCateListBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.FragmentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 资讯
 * <p>
 * Created by mengl on 2018/3/27.
 */

public class InformationFragment extends BaseFragment {

    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.l_top)
    LinearLayout lTop;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.spring)
    SpringView spring;

    private InformationAdapter adapter;
    private int page = 1;
    private int cateId = 0;
    private List<GetCateListBean.ListBean> tabTitles;
    private ArrayList<ArticleGetListBean.ListBean> dataList = new ArrayList<>();

    public static InformationFragment newInstance() {

        Bundle args = new Bundle();

        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {
        getCatelist();


        adapter = new InformationAdapter(dataList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleGetListBean.ListBean listBean = dataList.get(position);
                //跳转到详情页面
                FragmentUtil.replaceFragmentToActivity(R.id.container, InformationDetailsFragment.newInstance(listBean),
                        getFragmentManager(), getString(R.string.information));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
//                cateId = tabTitles.get(position).id;
                getList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initSpring();
        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));


    }

    /**
     * 设置刷新控件
     */
    private void initSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                getList();
            }

            @Override
            public void onLoadmore() {
                page++;
                getList();

            }
        });
        spring.setFooter(new DefaultFooter(getActivity()));

    }

    /**
     * 初始化tab
     */
    private void ininTab(GetCateListBean o) {
        for (int i = 0; i < o.list.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(o.list.get(i).name));
        }

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_information;
    }


    @OnClick({R.id.iv_clear, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                edContent.setText("");
                break;
            case R.id.tv_search:

                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        InformationSearchFragment.newInstance(edContent.getText().toString()),
                        getFragmentManager(), getString(R.string.information));

                break;
        }
    }


    /**
     * 获取资讯列表
     */
    public void getList() {
        compositeDisposable.add(RetrofitUtil.getApiService().getList(edContent.getText().toString(),
                page, cateId)
                .compose(new SimpleTransFormer<ArticleGetListBean>(ArticleGetListBean.class))
                .subscribeWith(new DisposableWrapper<ArticleGetListBean>() {
                    @Override
                    public void onNext(ArticleGetListBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
                        dataList.addAll(o.list);
                        adapter.notifyDataSetChanged();
                        spring.onFinishFreshAndLoad();


                    }
                }));
    }

    /**
     * 获取资讯分类列表
     */
    public void getCatelist() {
        compositeDisposable.add(RetrofitUtil.getApiService().getCatelist("")
                .compose(new SimpleTransFormer<GetCateListBean>(GetCateListBean.class))
                .subscribeWith(new DisposableWrapper<GetCateListBean>() {
                    @Override
                    public void onNext(GetCateListBean o) {
                        if (o.list != null && o.list.size() > 0) {
                            tabTitles = o.list;
                            ininTab(o);
                        }

                    }
                }));
    }


}
