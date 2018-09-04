package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/26
 * 开健康处方 adapter
 */

public class OpenHealthyPrescriptionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public OpenHealthyPrescriptionAdapter(@Nullable List<String> data) {
        super(R.layout.item_product_list_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
