package com.flym.yh.ui.activity.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.home.ShoppingDetailsAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.ShopDetailsBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.GlideUtil;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 商品详情
 */
public class ShoppingDetailsActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.tv_medicine_name)
    TextView tvMedicineName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ShoppingDetailsAdapter adapter;
    private ArrayList<String> dataList = new ArrayList<>();
    private int id;

    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.goods));
        return toolbar;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shopping_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("id", 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingDetailsAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        goodsGetdetail();
    }


    /**
     * 获取商品详情
     */
    public void goodsGetdetail() {
        compositeDisposable.add(RetrofitUtil.getApiService().goodsGetdetail(id)
                .compose(new SimpleTransFormer<ShopDetailsBean>(ShopDetailsBean.class))
                .subscribeWith(new DisposableWrapper<ShopDetailsBean>() {
                    @Override
                    public void onNext(ShopDetailsBean o) {
                        setData(o);
                        dataList.clear();
                        if (o.info != null && o.info.image_list != null) {
                            dataList.addAll(o.info.image_list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }));

    }

    private void setData(ShopDetailsBean o) {
        if (o.info != null) {
            tvMedicineName.setText(o.info.name);
            tvPrice.setText(o.info.price);
        }
//        banner.setData(imgList, null);
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                GlideUtil.loadImage(ShoppingDetailsActivity.this, model, itemView, R.mipmap.ic_launcher);
            }
        });
    }

}
