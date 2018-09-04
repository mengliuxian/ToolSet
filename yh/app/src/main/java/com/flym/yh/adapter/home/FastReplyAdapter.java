package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.FastReplyBean;

import java.util.List;

/**
 * Author:mengl
 * Time:2018/4/12 ${TIEM}
 */
public class FastReplyAdapter extends BaseQuickAdapter<FastReplyBean.ListBean, BaseViewHolder> {
    private int type = 0;

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public FastReplyAdapter(@Nullable List<FastReplyBean.ListBean> data) {
        super(R.layout.item_fast, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FastReplyBean.ListBean item) {
        helper.setVisible(R.id.r, type != 0);
        helper.setVisible(R.id.iv_select, item.show);
        helper.addOnClickListener(R.id.r);
        helper.setText(R.id.tv_content, item.content);
    }
}
