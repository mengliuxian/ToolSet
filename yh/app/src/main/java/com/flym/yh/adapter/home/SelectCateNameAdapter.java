package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetCateListBean;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class SelectCateNameAdapter extends BaseQuickAdapter<GetCateListBean.ListBean, BaseViewHolder> {

    public SelectCateNameAdapter(@Nullable List<GetCateListBean.ListBean> data) {
        super(R.layout.item_select_cate_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetCateListBean.ListBean item) {
        helper.setText(R.id.tv_name, item.name);
        helper.setBackgroundColor(R.id.tv_name, item.isShow ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.gray_d9));
        helper.setTextColor(R.id.tv_name, item.isShow ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.black_32));
    }

}
