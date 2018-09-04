package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.utils.TextWatcherWrapper;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/22
 */

public class SetGohomeServiceAdapter extends BaseQuickAdapter<GetServicesBean.ListBean, BaseViewHolder> {
    private List<GetServicesBean.ListBean> dataList;
    public SetGohomeServiceAdapter(@Nullable List<GetServicesBean.ListBean> data) {
        super(R.layout.item_set_gohome_service, data);
        dataList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetServicesBean.ListBean item) {
        EditText edit = helper.getView(R.id.ed_price);

        TextWatcherWrapper watcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setPrice(s.toString().trim());
            }
        };

        if (edit.getTag() instanceof TextWatcher) {
            edit.removeTextChangedListener((TextWatcher) edit.getTag());
        }

        helper.setText(R.id.tv_name, !TextUtils.isEmpty(item.getContent())?item.getContent():"请选择服务")
                .setText(R.id.ed_price, item.getPrice());

        edit.addTextChangedListener(watcherWrapper);
        edit.setTag(watcherWrapper);
        helper.addOnClickListener(R.id.tv_name);
        helper.setOnClickListener(R.id.tv_left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.remove(item);
                notifyDataSetChanged();
            }
        });
    }
}
