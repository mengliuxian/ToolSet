package com.flym.yh.ui.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.ShopMallGoodListAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.GoodsGetlistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.home.ShoppingDetailsActivity;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ActivityManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopMallGoodListFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.input)
    TextView input;
    @BindView(R.id.spring)
    SpringView spring;

    private int page = 1;
    private String title;
    private String search;

    private String order = "0";
    private ArrayList<GoodsGetlistBean.ListBean> dataList = new ArrayList<>();
    private ArrayList<GoodsGetlistBean.ListBean> selecterList = new ArrayList<>();
    private ShopMallGoodListAdapter adapter;

    public static ShopMallGoodListFragment newInstance(String title, String search) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("search", search);
        ShopMallGoodListFragment fragment = new ShopMallGoodListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.mall));
        return toolbar;
    }

    @Override
    protected void initData() {
        goodsGetlist();
        setTab();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShopMallGoodListAdapter(dataList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActivityManager.getInstance().startNextActivity(new Intent(context, ShoppingDetailsActivity.class)
                        .putExtra("id", dataList.get(position).id));
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsGetlistBean.ListBean listBean = dataList.get(position);
                if (listBean.selecter == 1) {
                    listBean.selecter = 0;
                    selecterList.remove(listBean);
                } else if (listBean.selecter == 0) {
                    listBean.selecter = 1;
                    selecterList.add(listBean);
                }
                adapter.notifyItemChanged(position);

            }
        });


    }

    private void setTab() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.defaults)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.low_to_high)));
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        title = getArguments().getString("title");
        search = getArguments().getString("search");
        edContent.setText(search);
        if (!TextUtils.isEmpty(title)) {
            toolbarTitle.setText(title);
        }
        initSpring();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    order = "0";
                } else if (position == 1) {
                    order = "1";
                }
                page = 1;
                goodsGetlist();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
                goodsGetlist();

            }
        });
        spring.setFooter(new DefaultFooter(context));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_shop_mall_good_list;
    }


    @OnClick({R.id.iv_clear, R.id.tv_search, R.id.input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                edContent.setText("");
                break;
            case R.id.tv_search:
                page = 1;
                search = edContent.getText().toString();
                goodsGetlist();
                break;
            case R.id.input:
                getActivity().setResult(Activity.RESULT_OK,
                        new Intent().putParcelableArrayListExtra("list", selecterList));
                ActivityManager.getInstance().popActivity(getActivity());
                break;
        }
    }

    /**
     * 获取商品列表
     */
    public void goodsGetlist() {
        compositeDisposable.add(RetrofitUtil.getApiService().goodsGetlist(page, search, order)
                .compose(new SimpleTransFormer<GoodsGetlistBean>(GoodsGetlistBean.class))
                .subscribeWith(new DisposableWrapper<GoodsGetlistBean>() {
                    @Override
                    public void onNext(GoodsGetlistBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
                        dataList.addAll(o.list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                })
        );
    }


}
