package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.DrugDirectoryBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class SelectDrugNameAdapter extends BaseQuickAdapter<DrugDirectoryBean.ListBean, BaseViewHolder> {
    //DrugDirectoryBean.ListBean

    public SelectDrugNameAdapter(@Nullable List<DrugDirectoryBean.ListBean> data) {
        super(R.layout.item_select_drug_name, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DrugDirectoryBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getName());
    }

}
