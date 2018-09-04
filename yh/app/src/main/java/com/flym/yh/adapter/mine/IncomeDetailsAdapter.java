package com.flym.yh.adapter.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.DoctorGetAccountBean;

import java.util.List;

/**
 * Author:mengl
 * Time:2018/5/17
 */
public class IncomeDetailsAdapter extends BaseQuickAdapter<DoctorGetAccountBean.ListBean, BaseViewHolder> {
    public IncomeDetailsAdapter(@Nullable List<DoctorGetAccountBean.ListBean> data) {
        super(R.layout.item_income_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctorGetAccountBean.ListBean item) {

        helper.setText(R.id.tv_title, item.remark);
        helper.setText(R.id.tv_time, item.create_time);
        if (item.type == 0) {
            helper.setText(R.id.tv_price, "+0" + item.amount);
        } else if (item.type == 1) {
            helper.setText(R.id.tv_price, "-" + item.amount);
        }

    }
}
