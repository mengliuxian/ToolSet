package com.flym.fhzk.ui.fragment.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.fhzk.R;
import com.flym.fhzk.adapter.home.TabLayoutAdapter;
import com.flym.fhzk.adapter.home.ViewPageAdapter;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.base.BaseViewPageFragment;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.search.SearchActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.StatusBarUtil;
import com.flym.fhzk.view.ClassifyPop;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, ClassifyPop.DataReturnListene {


    @BindView(R.id.now_recommend)
    TextView nowRecommend;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    RecyclerView tabLayout;
    @BindView(R.id.menu_layout)
    LinearLayout menuLayout;
    @BindView(R.id.ctl)
    LinearLayout ctl;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.search)
    TextView search;


    private ClassifyPop classifyPop;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int oldPosition = -1;
    private TabLayoutAdapter tabLayoutAdapter;
    private ViewPageAdapter viewPageAdapter;
    private ClassifyBean.SkuInfoBean first;
    public ArrayList<ClassifyBean.SkuInfoBean> tabBeens = new ArrayList<>();


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        getSku();
        nowRecommend.setSelected(true);
        viewPageAdapter = new ViewPageAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(viewPageAdapter);
        tabLayoutAdapter = new TabLayoutAdapter(tabBeens);
        tabLayout.setAdapter(tabLayoutAdapter);
        tabLayoutAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                viewPager.setCurrentItem(position + 1, true);
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtil.setStatusBarColor(getActivity(), R.color.colorAccent);
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        StatusBarUtil.changeStatusBarHeight(appBarLayout);
        StatusBarUtil.setStatusBarColor(getActivity(), R.color.colorAccent);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    public void initFragment(int size) {
        for (int i = 0; i < size + 1; i++) {
            BaseViewPageFragment recommend = null;
            if (i == 0) {
                recommend = RecommendFragment.newInstance(tabBeens.get(0));
            } else {
                recommend = ClassifyFragment.newInstance(tabBeens.get(i - 1));
            }
            if (recommend != null) {
                fragments.add(recommend);
            }
        }

        viewPageAdapter.notifyDataSetChanged();

    }


    public void scrollTab(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) tabLayout.getLayoutManager();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当需要跳转的item不在可见范围之内的时候
        if (position > lastVisibleItemPosition || position < firstVisibleItemPosition) {
            //跳转到指定item
            tabLayout.scrollToPosition(position);
            return;
        }
        int left = tabLayout.getChildAt(position - firstVisibleItemPosition).getLeft();
        int right = tabLayout.getChildAt(lastVisibleItemPosition - position).getRight();
        tabLayout.scrollBy((left - right) / 2, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (position != 0) {
            nowRecommend.setSelected(false);
            tabBeens.get(position - 1).is = true;
            if (oldPosition != -1) {
                tabBeens.get(oldPosition).is = false;
            }
            oldPosition = position - 1;
            scrollTab(position - 1);
        } else {
            nowRecommend.setSelected(true);
            tabBeens.get(oldPosition).is = false;
            oldPosition = position - 1;
        }

        tabLayoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.now_recommend, R.id.menu, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.now_recommend:
                nowRecommend.setSelected(true);
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.menu:
                if (classifyPop == null) {
                    classifyPop = new ClassifyPop(context);
                    classifyPop.setDataReturnListene(this);
                    ArrayList<ClassifyBean.SkuInfoBean> list = new ArrayList<>();
                    list.add(first);
                    list.addAll(tabBeens);
                    classifyPop.setList(list);
                }
                classifyPop.showAsDropDown(ctl);
                break;
            case R.id.search:
                ActivityManager.getInstance().startNextActivity(SearchActivity.class);
                break;
        }
    }


    /**
     * 6.	商品分类
     */
    public void getSku() {
        compositeDisposable.add(RetrofitUtil.getApiService().getSku(null)
                .compose(new SimpleTransFormer<ClassifyBean>(ClassifyBean.class))
                .subscribeWith(new DisposableWrapper<ClassifyBean>() {
                    @Override
                    public void onNext(ClassifyBean classifyBean) {
                        tabBeens.clear();
                        tabBeens.addAll(classifyBean.skuInfo);
                        for (ClassifyBean.SkuInfoBean tabBeen : tabBeens) {
                            if (tabBeen.sort == 0 && tabBeen.pid == 0) {
                                first = tabBeen;
                                nowRecommend.setText(tabBeen.name);
                                tabBeens.remove(tabBeen);
                                break;
                            }
                        }
                        tabLayoutAdapter.notifyDataSetChanged();
                        initFragment(tabBeens.size());
                    }
                }));

    }

    /**
     * pop返回的数据，用来改变viewpage的页面
     *
     * @param position
     */
    @Override
    public void dataReturn(int position) {
        viewPager.setCurrentItem(position);

    }

    @Override
    public void onFix() {

    }

    @Override
    public void onReset() {

    }
}
