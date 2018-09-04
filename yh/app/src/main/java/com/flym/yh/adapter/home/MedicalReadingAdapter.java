package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 医学阅读 adapter
 */

public class MedicalReadingAdapter extends BaseQuickAdapter<GetServicesBean.ListBean, BaseViewHolder> {
    private int type;

    public MedicalReadingAdapter(@Nullable List<GetServicesBean.ListBean> data, int type) {
        super(R.layout.item_medical_reading, data);
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, GetServicesBean.ListBean item) {
        if (type == 0) {
            helper.setVisible(R.id.f_box, false);
        } else {
            helper.setVisible(R.id.f_box, true);
        }
        GlideUtil.loadImage(mContext, ApiConstants.getImageUrl(item.getImage()),
                (ImageView) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_price, item.getPrice());
        helper.setText(R.id.tv_time, item.getCreate_time());
        if (item.selected) {
            helper.setVisible(R.id.iv_select, true);
        } else {
            helper.setVisible(R.id.iv_select, false);
        }
//        if (type == 0) {
//            helper.setVisible(R.id.iv_select, false);
//        } else {
//            helper.setVisible(R.id.iv_select, true);
//        }
        helper.addOnClickListener(R.id.f_box);
    }
}
