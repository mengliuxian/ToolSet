package com.flym.yh.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.flym.yh.R;
import com.flym.yh.utils.SystemUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Morphine on 2017/8/21.
 */

public class UploadVideoPopupWindow extends PopupWindow implements View.OnClickListener {
    private static final int TO_RECORD_VIDEO_ACTIVITY_REQUEST_CODE = 39;
    private static final int TO_SELECT_VIDEO_ACTIVITY_REQUEST_CODE = 40;


    private Activity mActivity;
    TextView fromCamera;
    TextView fromAlbum;
    TextView cancel;

    private OnVideoSelectListener listener;
    String videoFileName;


    public UploadVideoPopupWindow(Activity activity) {
        this.mActivity = activity;
//        View rootView = View.inflate(activity, R.layout.ppw_upload_video, null);
//        setContentView(rootView);
//        fromCamera = (TextView) rootView.findViewById(R.id.from_camera);
//        fromAlbum = (TextView) rootView.findViewById(R.id.from_album);
//        cancel = (TextView) rootView.findViewById(R.id.cancel);
        fromAlbum.setOnClickListener(this);
        cancel.setOnClickListener(this);
        fromCamera.setOnClickListener(this);
        init();
    }

    private void init() {
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        this.setBackgroundDrawable(cd);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1f);
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setWindowAlpha(0.4f);
        super.showAtLocation(parent, gravity, x, y);
    }

    private void setWindowAlpha(float alpha) {
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }

    public void setVideoSelectListener(OnVideoSelectListener listener) {
        this.listener = listener;
    }


    public void onResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TO_RECORD_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                File videoFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        videoFileName);
                Bitmap videoImg = SystemUtil.getVideoThumbnail(videoFile.getAbsolutePath());
                if (listener != null) {
                    listener.onSuccess(videoFile, videoImg);
                    dismiss();
                }
            }
        } else if (requestCode == TO_SELECT_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (listener != null) {
                    File videoFile = (File) data.getSerializableExtra("videoFile");
                    listener.onSuccess(videoFile, SystemUtil.getVideoThumbnail(videoFile.getAbsolutePath()));
                    dismiss();
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
//        if (i == R.id.from_camera) {
//            toRecordVideo();
//        } else if (i == R.id.from_album) {
//            fromAlbum();
//        } else if (i == R.id.cancel) {
//            dismiss();
//        }
    }


    private void fromAlbum() {
//        Intent intent = new Intent(mActivity, VideoListActivity.class);
//        mActivity.startActivityForResult(intent, TO_SELECT_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    private void toRecordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File mediaFile = createMediaFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri videoFileUri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName()
                    + ".fileprovider", mediaFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoFileUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mediaFile));
        }
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024 * 15L);
        mActivity.startActivityForResult(intent, TO_RECORD_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    private File createMediaFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        videoFileName = "VID_" + timeStamp + ".mp4";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + videoFileName);
        return file;
    }

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    public interface OnVideoSelectListener {
        void onSDCardNotFound();

        void onSuccess(File file, Bitmap videoImg);
    }
}
