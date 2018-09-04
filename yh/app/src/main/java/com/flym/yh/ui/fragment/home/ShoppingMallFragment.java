package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.ShopMallAdatper;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.ShopMallBean;
import com.flym.yh.utils.FragmentUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城
 */
public class ShoppingMallFragment extends BaseFragment {
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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ShopMallAdatper adatper;
    private ArrayList<ShopMallBean> dataList = new ArrayList<>();

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.mall));
        return toolbar;
    }

    @Override
    protected void initData() {
        if (dataList.size() <= 0) {
            serData();
        }
        adatper = new ShopMallAdatper(dataList);
        recyclerView.setAdapter(adatper);

        adatper.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopMallBean shopMallBean = dataList.get(position);
                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        ShopMallClassifyFragment.newInstance(shopMallBean.title),
                        getFragmentManager(),"分类");
            }
        });

    }

    private void serData() {
        dataList.add(new ShopMallBean(getString(R.string.care_products),
                R.drawable.sy_wdzs_zxym_scfb_hufupin));
        dataList.add(new ShopMallBean(getString(R.string.diabetes_zone),
                R.drawable.sy_wdzs_zxym_scfb_tangniaobing));
        dataList.add(new ShopMallBean(getString(R.string.high_blood),
                R.drawable.sy_wdzs_zxym_scfb_gaoxueya));
        dataList.add(new ShopMallBean(getString(R.string.strong_kidney),
                R.drawable.sy_wdzs_zxym_scfb_zhongyiqiangshen));
        dataList.add(new ShopMallBean(getString(R.string.health_products),
                R.drawable.sy_wdzs_zxym_scfb_yingyangbaojian));
        dataList.add(new ShopMallBean(getString(R.string.care_equipment),
                R.drawable.sy_wdzs_zxym_scfb_baojianqicai));
        dataList.add(new ShopMallBean(getString(R.string.sexual_health),
                R.drawable.sy_wdzs_zxym_scfb_xingjianlkang));
        dataList.add(new ShopMallBean(getString(R.string.medicine_nursing),
                R.drawable.sy_wdzs_zxym_scfb_zhongyitiaoyang));
        dataList.add(new ShopMallBean(getString(R.string.child_supplies),
                R.drawable.sy_wdzs_zxym_scfb_muying));
        dataList.add(new ShopMallBean(getString(R.string.book_city),
                R.drawable.sy_wdzs_zxym_scfb_yixueshucheng));

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_shop_mall;
    }


    @OnClick({R.id.iv_clear, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                edContent.setText("");
                break;
            case R.id.tv_search:
                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        ShopMallGoodListFragment.newInstance("", edContent.getText().toString()),
                        getFragmentManager());
                break;
        }
    }


}
