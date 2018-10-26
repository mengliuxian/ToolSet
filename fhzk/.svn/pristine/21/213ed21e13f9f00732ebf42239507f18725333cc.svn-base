package com.flym.fhzk.adapter.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class RebatesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RebatesAdapter(@Nullable List<String> data) {
        super(R.layout.item_rebates, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.name, item);
        helper.setText(R.id.time, item);
        helper.setText(R.id.money, item + "元");
    }
}
