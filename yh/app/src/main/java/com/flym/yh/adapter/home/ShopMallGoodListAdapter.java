package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GoodsGetlistBean;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

public class ShopMallGoodListAdapter extends BaseQuickAdapter<GoodsGetlistBean.ListBean, BaseViewHolder> {
    public ShopMallGoodListAdapter(@Nullable List<GoodsGetlistBean.ListBean> data) {
        super(R.layout.item_drug_directory, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsGetlistBean.ListBean item) {
        helper.addOnClickListener(R.id.f_box);
        ImageView img = (ImageView) helper.getView(R.id.iv);
        GlideUtil.loadImage(mContext, ApiConstants.getImageUrl(item.image), img);
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_price, item.price);
        if (item.selecter == 0) {
            helper.setVisible(R.id.iv_select, false);
        } else if (item.selecter == 1) {
            helper.setVisible(R.id.iv_select, true);
        }
    }
}
