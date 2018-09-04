package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetDoctorArtivleListBean;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 投稿 adapter
 */

public class ContributorsAdapter extends BaseQuickAdapter<GetDoctorArtivleListBean.ListBean, BaseViewHolder> {

    private int type;

    public ContributorsAdapter(@Nullable List<GetDoctorArtivleListBean.ListBean> data, int type) {
        super(R.layout.item_contributors, data);
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }


    @Override
    protected void convert(BaseViewHolder helper, GetDoctorArtivleListBean.ListBean item) {
        ImageView img = helper.getView(R.id.iv);
        GlideUtil.loadImage(mContext, ApiConstants.getImageUrl(item.getImage()), img);
        helper.setText(R.id.tv_name, item.getTitle()).setText(R.id.tv_time, item.getCreate_time())
                .setVisible(R.id.tv_edit, type == 2 ? true : false);
        helper.addOnClickListener(R.id.tv_edit);
    }
}
