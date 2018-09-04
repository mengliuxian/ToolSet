package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

/**
 * Author:mengl
 * Time:2018/4/18
 */
public class ShoppingDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ShoppingDetailsAdapter(@Nullable List<String> data) {
        super(R.layout.item_shop_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtil.loadImage(mContext, ApiConstants.getImageUrl(item), (ImageView) helper.getView(R.id.iv_pic));
    }
}
