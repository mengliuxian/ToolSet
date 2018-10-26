package com.flym.fhzk.adapter.mine;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.utils.GlideUtil;
import com.flym.fhzk.utils.itemdecoration.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/7 0007
 */

public class OrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OrderAdapter(@Nullable List<String> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(mContext,LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));
        OrderGoodsAdapter orderGoodsAdapter = new OrderGoodsAdapter(new ArrayList<String>());
        recyclerView.setAdapter(orderGoodsAdapter);

        helper.setText(R.id.shop_name, item);
        helper.setText(R.id.order_state, item);
        helper.setText(R.id.order_data, "共1件商品 合计：180.00（含运费：￥0.00）");
        GlideUtil.loadImage(mContext, item, (ImageView) helper.getView(R.id.shop_logo));

        helper.addOnClickListener(R.id.more_but);
        helper.addOnClickListener(R.id.mail_but);
        helper.addOnClickListener(R.id.sell_but);
        helper.addOnClickListener(R.id.appraise_but);
    }
}
