package com.flym.fhzk.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class RecommendAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RecommendAdapter(@Nullable List<String> data) {
        super(R.layout.item_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtil.loadImage(mContext, item, (ImageView) helper.getView(R.id.logo));
    }
}
