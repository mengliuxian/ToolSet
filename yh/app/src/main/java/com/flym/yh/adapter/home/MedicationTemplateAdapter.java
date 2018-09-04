package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.MedicationTemplateBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 用药模板 adapter
 */

public class MedicationTemplateAdapter extends BaseQuickAdapter<MedicationTemplateBean.ListBean, BaseViewHolder> {
    private int type = 0;

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public MedicationTemplateAdapter(@Nullable List<MedicationTemplateBean.ListBean> data) {
        super(R.layout.item_medication_template, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicationTemplateBean.ListBean item) {
        helper.addOnClickListener(R.id.r);
        helper.setVisible(R.id.r, type == 0 ? false : true);
        helper.setVisible(R.id.iv_select, item.isShow() ? true : false);
        helper.setText(R.id.tv_name, item.getName());
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("药物清单: ");
        for (int i = 0; i < item.getGoods_list().size(); i++) {
            stringBuffer.append(item.getGoods_list().get(i).getName());
            stringBuffer.append("  +");
            stringBuffer.append(item.getGoods_list().get(i).getNum());
            stringBuffer.append("  盒  ");
        }
        helper.setText(R.id.tv_contact, stringBuffer.toString());
    }
}
