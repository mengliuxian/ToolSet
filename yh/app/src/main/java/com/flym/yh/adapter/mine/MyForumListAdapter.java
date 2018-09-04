package com.flym.yh.adapter.mine;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.TopicGetlistBean;
import com.flym.yh.utils.GlideUtil;

import java.util.List;

/**
 * Created by mengl on 2018/3/26.
 */

public class MyForumListAdapter extends BaseQuickAdapter<TopicGetlistBean.ListBean, BaseViewHolder> {

    public MyForumListAdapter(@Nullable List<TopicGetlistBean.ListBean> data) {
        super(R.layout.item_my_forum_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicGetlistBean.ListBean item) {
        GlideUtil.loadImage(mContext, item.headimgurl, (ImageView) helper.getView(R.id.head), R.drawable.wd_grmp__touxiang);
        helper.setText(R.id.name, item.nickname);
        helper.setText(R.id.time, item.create_time);
        helper.setVisible(R.id.news_num, false);
        if (!item.vis && !"0".equals(item.comments)) {
            helper.setVisible(R.id.news_num, true);
            helper.setText(R.id.news_num, item.comments);
        }
        helper.setText(R.id.title, item.title);
        helper.setText(R.id.content, item.content);
    }


}
