package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.RecipeGetlistBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/26
 * 健康处方 adapter
 */

public class HealthyPrescriptionAdapter extends BaseQuickAdapter<RecipeGetlistBean.ListBean, BaseViewHolder> {


    public HealthyPrescriptionAdapter(@Nullable List<RecipeGetlistBean.ListBean> data) {
        super(R.layout.item_healthy_prescription, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecipeGetlistBean.ListBean item) {
        if (item.status == 1) {
            helper.setVisible(R.id.bt_layout, true);
            helper.setText(R.id.tv_modify, "撤回健康处方");
        } else if (item.status == 0) {
            helper.setVisible(R.id.bt_layout, true);
            helper.setText(R.id.tv_modify, "修改");
        } else if (item.status == 2) {
            helper.setVisible(R.id.bt_layout, false);
        }

        helper.setText(R.id.tv_name, item.user_name);
        helper.setText(R.id.tv_sex, item.sex);
        helper.setText(R.id.tv_age, String.valueOf(item.age));
        helper.setText(R.id.tv_disease_name, "疾病名称:" + item.name);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < item.goods_list.size(); i++) {
            builder.append(item.goods_list.get(i).name);
        }
        helper.setText(R.id.tv_good_list, "商品清单:" + builder.toString());

        helper.addOnClickListener(R.id.tv_modify);
        helper.addOnClickListener(R.id.tv_del);
    }
}
