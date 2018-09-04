package com.flym.yh.adapter.mine;

import android.support.annotation.Nullable;
import android.support.v4.os.IResultReceiver;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.utils.DateFormatUtil;
import com.flym.yh.utils.DateUtils;

import java.text.ParseException;
import java.util.List;

/**
 * Created by mengl on 2018/3/26.
 */

public class MyEvaluteAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MyEvaluteAdapter(@Nullable List<String> data) {
        super(R.layout.item_my_evalute, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.name, item);
//        try {
//            helper.setText(R.id.time, DateFormatUtil.l(item, "yyyy-MM-dd"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        helper.setText(R.id.content, item);
    }
}
