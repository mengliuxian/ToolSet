package com.flym.fhzk.adapter.home;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.ClassifyBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class TabLayoutAdapter extends BaseQuickAdapter<ClassifyBean.SkuInfoBean, BaseViewHolder> {
    public TabLayoutAdapter(@Nullable List<ClassifyBean.SkuInfoBean> data) {
        super(R.layout.item_tab, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyBean.SkuInfoBean item) {
        helper.setText(R.id.tab, item.name);
        TextView view = (TextView) helper.getView(R.id.tab);
        if (item.is) {
            view.setSelected(true);
        } else {
            view.setSelected(false);
        }
    }
}
