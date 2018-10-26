package com.flym.fhzk.adapter.mine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.HelpConfBean;
import com.flym.fhzk.view.CustomMineItemView;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class HelpAdapter extends BaseQuickAdapter<HelpConfBean.HelpConfBeans, BaseViewHolder> {
    public HelpAdapter(@Nullable List<HelpConfBean.HelpConfBeans> data) {
        super(R.layout.item_help, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpConfBean.HelpConfBeans item) {

        CustomMineItemView view = helper.getView(R.id.custom_view);
        view.setTitleText(item.title);
    }
}
