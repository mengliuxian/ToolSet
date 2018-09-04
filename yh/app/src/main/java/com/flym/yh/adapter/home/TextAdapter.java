package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GoodsGetcatelistBean;

import java.util.List;

public class TextAdapter extends BaseQuickAdapter<GoodsGetcatelistBean.ListBean.ChildBean, BaseViewHolder> {
    public TextAdapter(@Nullable List<GoodsGetcatelistBean.ListBean.ChildBean> data) {
        super(R.layout.item_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsGetcatelistBean.ListBean.ChildBean item) {
        helper.setText(R.id.text, item.name);
    }
}
