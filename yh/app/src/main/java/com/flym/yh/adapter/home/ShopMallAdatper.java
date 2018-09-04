package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.ShopMallBean;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

public class ShopMallAdatper extends BaseQuickAdapter<ShopMallBean, BaseViewHolder> {
    public ShopMallAdatper(@Nullable List<ShopMallBean> data) {
        super(R.layout.item_shop_mall, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopMallBean item) {
        GlideUtil.loadImage(mContext, item.id, (ImageView) helper.getView(R.id.logo));
        helper.setText(R.id.title, item.title);
    }
}
