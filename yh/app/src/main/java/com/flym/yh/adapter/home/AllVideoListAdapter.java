package com.flym.yh.adapter.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flym.yh.R;
import com.flym.yh.data.model.VideoInfo;


import java.util.List;

/**
 * Created by Morphine on 2017/8/21.
 */

public class AllVideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {
    public AllVideoListAdapter(@Nullable List<VideoInfo> data) {
        super(R.layout.item_video_info_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        helper.setImageBitmap(R.id.logo, item.getBitmap());
        helper.setText(R.id.name, item.getTitle());
        helper.setText(R.id.time, item.getTime());
        helper.setText(R.id.size, item.getSize());
    }
}
