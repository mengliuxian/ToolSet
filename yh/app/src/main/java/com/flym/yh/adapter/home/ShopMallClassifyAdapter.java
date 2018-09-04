package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GoodsGetcatelistBean;

import java.util.List;

public class ShopMallClassifyAdapter extends BaseQuickAdapter<GoodsGetcatelistBean.ListBean, BaseViewHolder> {
    public ShopMallClassifyAdapter(@Nullable List<GoodsGetcatelistBean.ListBean> data) {
        super(R.layout.item_shop_mall_classify, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsGetcatelistBean.ListBean item) {
        RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        TextAdapter adapter = new TextAdapter(item._child);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsGetcatelistBean.ListBean.ChildBean childBean = item._child.get(position);
                listence.onReult(childBean);
            }
        });

        helper.setText(R.id.title, item.name);

    }

    private ReultListence listence;

    public void setListence(ReultListence listence) {
        this.listence = listence;
    }

    public interface ReultListence {
        void onReult(GoodsGetcatelistBean.ListBean.ChildBean childBean);
    }
}


