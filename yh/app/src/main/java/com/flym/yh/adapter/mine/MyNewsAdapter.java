package com.flym.yh.adapter.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;

import java.util.List;

/**
 * Created by mengl on 2018/3/26.
 */

public class MyNewsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MyNewsAdapter(@Nullable List<String> data) {
        super(R.layout.item_my_news, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.content, item);
    }
}
