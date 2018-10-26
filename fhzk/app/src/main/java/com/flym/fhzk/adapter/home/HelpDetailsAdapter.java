package com.flym.fhzk.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.fhzk.R;
import com.flym.fhzk.data.model.HelpDetailBean;
import com.flym.fhzk.view.AutoWrapTextView;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class HelpDetailsAdapter extends BaseQuickAdapter<HelpDetailBean.HelpDetailBeans, BaseViewHolder> {
    public HelpDetailsAdapter(@Nullable List<HelpDetailBean.HelpDetailBeans> data) {
        super(R.layout.item_help_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpDetailBean.HelpDetailBeans item) {
        helper.setText(R.id.question, item.question);

        AutoWrapTextView autoWrapTextView = helper.getView(R.id.answer);
        autoWrapTextView.setText(item.anwser);
    }
}
