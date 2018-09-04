package com.flym.yh.adapter.forum;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.TopicGetcommentlistBean;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

/**
 * Created by mengl on 2018/3/27.
 */

public class ReturnCardAdapter extends BaseQuickAdapter<TopicGetcommentlistBean.ListBean, BaseViewHolder> {

    public ReturnCardAdapter(@Nullable List<TopicGetcommentlistBean.ListBean> data) {
        super(R.layout.item_return_card, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicGetcommentlistBean.ListBean item) {

        GlideUtil.loadImage(mContext, item.headimgurl, (ImageView) helper.getView(R.id.head), R.drawable.wd_grmp__touxiang);
        helper.setText(R.id.name, item.nickname);
        helper.setText(R.id.content, item.content);
        helper.setText(R.id.time, item.create_time);

    }
}
