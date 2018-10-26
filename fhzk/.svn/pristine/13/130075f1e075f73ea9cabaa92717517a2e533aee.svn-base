package com.flym.fhzk.adapter.mine;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.utils.GlideUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/7 0007
 */

public class OrderGoodsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OrderGoodsAdapter(@Nullable List<String> data) {
        super(R.layout.item_order_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.goods_name, item);
        helper.setText(R.id.goods_classify, "颜色分类：黑色；尺码：S码");
        helper.setText(R.id.goods_money, "180.00");
        helper.setText(R.id.goods_num, "x1");

        GlideUtil.loadImage(mContext, "", (ImageView) helper.getView(R.id.goods_logo));

    }
}
