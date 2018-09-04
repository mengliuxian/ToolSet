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
 * @date 2018/3/21
 */

public class MyOfficeAdapter extends BaseQuickAdapter<GetServicesBean.ListBean, BaseViewHolder> {
    public MyOfficeAdapter(@Nullable List<GetServicesBean.ListBean> data) {
        super(R.layout.item_my_office, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetServicesBean.ListBean item) {

        GlideUtil.loadImage(mContext,
                ApiConstants.getImageUrl(item.headimgurl), (ImageView) helper.getView(R.id.iv_head), R.drawable.wd_grmp__touxiang);
        helper.setText(R.id.tv_name, item.nickname);


    }
}
