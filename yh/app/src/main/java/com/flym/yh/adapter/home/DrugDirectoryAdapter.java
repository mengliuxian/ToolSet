package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.DrugDirectoryBean;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 药物目录 adapter
 */

public class DrugDirectoryAdapter extends BaseQuickAdapter<DrugDirectoryBean.ListBean, BaseViewHolder> {


    public DrugDirectoryAdapter(@Nullable List<DrugDirectoryBean.ListBean> data) {
        super(R.layout.item_drug_directory, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, DrugDirectoryBean.ListBean item) {

        helper.setVisible(R.id.iv_select, item.isShow());
        helper.addOnClickListener(R.id.f_box);
        ImageView img = (ImageView) helper.getView(R.id.iv);
        GlideUtil.loadImage(mContext, ApiConstants.getImageUrl(item.getImage()),img);

        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_price, item.getPrice());
    }
}
