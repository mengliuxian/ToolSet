package com.flym.yh.adapter.information;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.ArticleGetListBean;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

/**
 * Created by mengl on 2018/3/27.
 */

public class InformationAdapter extends BaseQuickAdapter<ArticleGetListBean.ListBean, BaseViewHolder> {
    public InformationAdapter(@Nullable List<ArticleGetListBean.ListBean> data) {
        super(R.layout.item_information, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleGetListBean.ListBean item) {
        helper.setText(R.id.title, item.title);
        helper.setText(R.id.name_time, item.username + item.create_time);
        GlideUtil.loadImage(mContext, item.image, (ImageView) helper.getView(R.id.logo));
    }
}
