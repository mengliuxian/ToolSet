package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.home.ShopMallClassifyAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.GoodsGetcatelistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.FragmentUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class ShopMallClassifyFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String title;

    private ShopMallClassifyAdapter adapter;
    private ArrayList<GoodsGetcatelistBean.ListBean> dataList = new ArrayList<>();

    public static ShopMallClassifyFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        ShopMallClassifyFragment fragment = new ShopMallClassifyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void initData() {
        if (dataList.size() <= 0) {
            goodsGetcatelist();
        }
        adapter = new ShopMallClassifyAdapter(dataList);
        recyclerView.setAdapter(adapter);
        adapter.setListence(new ShopMallClassifyAdapter.ReultListence() {
            @Override
            public void onReult(GoodsGetcatelistBean.ListBean.ChildBean childBean) {

                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        ShopMallGoodListFragment.newInstance(childBean.name, childBean.id),
                        getFragmentManager());
            }
        });

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        title = getArguments().getString("title");
        toolbarTitle.setText(title);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_shop_mall_classify;
    }

    /**
     * 获取商品分类列表
     */
    public void goodsGetcatelist() {
        compositeDisposable.add(RetrofitUtil.getApiService().goodsGetcatelist(null)
                .compose(new SimpleTransFormer<GoodsGetcatelistBean>(GoodsGetcatelistBean.class))
                .subscribeWith(new DisposableWrapper<GoodsGetcatelistBean>() {
                    @Override
                    public void onNext(GoodsGetcatelistBean o) {
                        dataList.addAll(o.list);
                        adapter.notifyDataSetChanged();
                    }
                }));
    }

}
