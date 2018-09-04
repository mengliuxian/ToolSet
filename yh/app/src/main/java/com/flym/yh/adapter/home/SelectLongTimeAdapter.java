package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetPhoneLongTimeBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class SelectLongTimeAdapter extends BaseQuickAdapter<GetPhoneLongTimeBean.ListBean, BaseViewHolder> {
    //DrugDirectoryBean.ListBean

    public SelectLongTimeAdapter(@Nullable List<GetPhoneLongTimeBean.ListBean> data) {
        super(R.layout.item_select_long_time, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GetPhoneLongTimeBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getName());
    }


}
