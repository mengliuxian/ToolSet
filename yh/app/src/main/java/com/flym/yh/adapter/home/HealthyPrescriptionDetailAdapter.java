package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GoodsGetlistBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/26
 * 商品清单 健康处方详情 adapter
 */

public class HealthyPrescriptionDetailAdapter extends BaseQuickAdapter<GoodsGetlistBean.ListBean, BaseViewHolder> {


    public HealthyPrescriptionDetailAdapter(@Nullable List<GoodsGetlistBean.ListBean> data) {
        super(R.layout.item_product_list_detail, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsGetlistBean.ListBean item) {
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_num, String.valueOf(item.num));
    }

}
