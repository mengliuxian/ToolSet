package com.flym.yh.adapter.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetDoctorArtivleListBean;

import java.util.List;

/**
 * Created by mengl on 2018/3/23.
 */

public class PublishedArticlesAdapter extends BaseQuickAdapter<GetDoctorArtivleListBean.ListBean, BaseViewHolder> {
    public PublishedArticlesAdapter(@Nullable List<GetDoctorArtivleListBean.ListBean> data) {
        super(R.layout.item_published_articles, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetDoctorArtivleListBean.ListBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.content, item.getContent());
    }
}
