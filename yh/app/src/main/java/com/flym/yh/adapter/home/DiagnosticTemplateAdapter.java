package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.SaveDiagnosticTemplateBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 诊断模板 adapter
 */

public class DiagnosticTemplateAdapter extends BaseQuickAdapter<SaveDiagnosticTemplateBean.ListBean, BaseViewHolder> {

    private int type = 0;

    public DiagnosticTemplateAdapter(@Nullable List<SaveDiagnosticTemplateBean.ListBean> data) {
        super(R.layout.item_diagnostic_template, data);
    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }


    @Override
    protected void convert(BaseViewHolder helper, SaveDiagnosticTemplateBean.ListBean item) {
        helper.setVisible(R.id.r, type == 0 ? false : true);
        helper.setVisible(R.id.iv_select, item.isShow() ? true : false);
        helper.setText(R.id.tv, item.getName());
        helper.addOnClickListener(R.id.r);
    }
}
