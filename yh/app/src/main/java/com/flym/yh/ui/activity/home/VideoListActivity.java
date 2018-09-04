package com.flym.yh.ui.activity.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.AllVideoListAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.VideoInfo;
import com.flym.yh.utils.DateFormatUtil;
import com.flym.yh.utils.DensityUtil;
import com.flym.yh.utils.SystemUtil;
import com.flym.yh.utils.ToastUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Morphine on 2017/8/21.
 */

public class VideoListActivity extends BaseActivity {

    @BindView(R.id.video_list)
    RecyclerView videoList;

    private List<VideoInfo> videoInfoList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected String getPagerTitle() {
        return "选择视频";
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        videoList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        videoInfoList = new ArrayList<VideoInfo>();
        String[] mediaColumns = new String[]{MediaStore.MediaColumns.DATA,
                BaseColumns._ID, MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.DURATION, MediaStore.MediaColumns.SIZE};
        Cursor cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
                null, null, null);
        if (cursor == null) {
            return;
        }

        if (cursor.moveToFirst()) {
            do {
                VideoInfo info = new VideoInfo();
                info.setFilePath(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
                info.setMimeType(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)));
                info.setTitle(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE)));
                info.setTime(DateFormatUtil.int2MMss(cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION))));
                info.setSize(SystemUtil.getFormatSize(cursor
                        .getLong(cursor
                                .getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))));
                info.setByteSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)));
                int id = cursor.getInt(cursor
                        .getColumnIndexOrThrow(BaseColumns._ID));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inDither = false; //不进行抖动处理

                options.inPurgeable = true; //便于回收（两个一起用）
                options.inInputShareable = true;

                options.outHeight = DensityUtil.dp2px(this, 100);
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                info.setBitmap(MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id,
                        MediaStore.Images.Thumbnails.MICRO_KIND, options));
                videoInfoList.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        AllVideoListAdapter videoListAdapter = new AllVideoListAdapter(videoInfoList);
        videoList.setAdapter(videoListAdapter);
        videoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (videoInfoList.get(position).getByteSize() > 10485760) {
                    ToastUtil.showMessage(VideoListActivity.this, "视频文件过大，请重新选择");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("videoFile", new File(videoInfoList.get(position).getFilePath()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
