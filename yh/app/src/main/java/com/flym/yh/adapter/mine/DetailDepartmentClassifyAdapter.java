package com.flym.yh.adapter.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetDepartmenglistBean;

import java.util.List;

/**
 * Created by mengl on 2018/3/22.
 */

public class DetailDepartmentClassifyAdapter extends BaseQuickAdapter<GetDepartmenglistBean.ListBean.ChildBean, BaseViewHolder> {

    public DetailDepartmentClassifyAdapter(@Nullable List<GetDepartmenglistBean.ListBean.ChildBean> data) {
        super(R.layout.item_department_classify, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetDepartmenglistBean.ListBean.ChildBean item) {
        //选中改变背景
        if (item.selecter == 1) {
            helper.setBackgroundColor(R.id.layout, mContext.getResources().getColor(R.color.red_ff));
        } else {
            helper.setBackgroundColor(R.id.layout, mContext.getResources().getColor(R.color.gray_bf));
        }
        helper.setText(R.id.content, item.name);
        helper.addOnClickListener(R.id.layout);

    }
}
