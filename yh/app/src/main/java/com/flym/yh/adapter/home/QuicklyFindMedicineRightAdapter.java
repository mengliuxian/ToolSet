package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.QuicklyFindMedicineBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/22
 */

public class QuicklyFindMedicineRightAdapter extends BaseQuickAdapter<QuicklyFindMedicineBean.ListBean.ChildBean, BaseViewHolder> {


    public QuicklyFindMedicineRightAdapter(@Nullable List<QuicklyFindMedicineBean.ListBean.ChildBean> data) {
        super(R.layout.item_quickly_find_medicine, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuicklyFindMedicineBean.ListBean.ChildBean item) {
        helper.setText(R.id.tv, item.getName());
    }
}
