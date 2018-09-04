package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetServicelistBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class SelectServiceAdapter extends BaseQuickAdapter<GetServicelistBean.ListBean, BaseViewHolder> {
    //DrugDirectoryBean.ListBean


    public SelectServiceAdapter(@Nullable List<GetServicelistBean.ListBean> data) {
        super(R.layout.item_select_long_time, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, GetServicelistBean.ListBean item) {
        helper.setText(R.id.tv_name, item.name);
    }

}
