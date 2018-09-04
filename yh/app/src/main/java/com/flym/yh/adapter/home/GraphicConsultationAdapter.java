package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetServicesBean;

import java.util.List;

public class GraphicConsultationAdapter extends BaseQuickAdapter<GetServicesBean.ListBean, BaseViewHolder> {
    public GraphicConsultationAdapter(@Nullable List<GetServicesBean.ListBean> data) {
        super(R.layout.item_graphic_consultation, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetServicesBean.ListBean item) {
        helper.setText(R.id.time, item.getCreate_time());
        helper.setText(R.id.type, item.getType_id() == 0 ? mContext.getString(R.string.in_the_consultation) :
                mContext.getString(R.string.closed));
        helper.setText(R.id.problem, item.getContent());
    }
}
