package com.flym.fhzk.adapter.mine;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.MessageBean;
import com.flym.fhzk.utils.GlideUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class NewsAdapter extends BaseQuickAdapter<MessageBean.MsgInfoBean, BaseViewHolder> {
    public NewsAdapter(@Nullable List<MessageBean.MsgInfoBean> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.MsgInfoBean item) {
        helper.setText(R.id.news_title, item.title);
        helper.setText(R.id.news_content, item.content);
        helper.setText(R.id.news_time, item.addTime);
        GlideUtil.loadImage(mContext, R.drawable.ic_news, (ImageView) helper.getView(R.id.news_logo));
    }
}
