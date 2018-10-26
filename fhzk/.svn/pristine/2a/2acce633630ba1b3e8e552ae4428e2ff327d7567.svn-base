package com.flym.fhzk.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.ClassifyBean;
import com.flym.fhzk.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class ClassifyAdapter extends BaseQuickAdapter<ClassifyBean.SkuInfoBean.SonBean, BaseViewHolder> {
    public ClassifyAdapter(@Nullable List<ClassifyBean.SkuInfoBean.SonBean> data) {
        super(R.layout.item_classift_pop, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyBean.SkuInfoBean.SonBean item) {
        GlideUtil.loadImage(mContext, item.pic, (ImageView) helper.getView(R.id.logo));
        helper.setText(R.id.name, item.name);
    }
}
