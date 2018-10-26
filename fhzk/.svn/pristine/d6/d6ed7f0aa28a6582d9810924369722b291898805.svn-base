package com.flym.fhzk.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.flym.fhzk.R;
import com.flym.fhzk.utils.PermissionUtil;
import com.flym.fhzk.utils.SystemUtil;
import com.flym.fhzk.utils.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Morphine on 2017/7/19.
 */

public class UploadImagePopupWindow extends PopupWindow implements View.OnClickListener {

    public static final int PHOTO_REQUEST_CAMERA = 1100;
    public static final int PHOTO_REQUEST_PHOTO = 1101;
    public static final int PHOTO_REQUEST_CUT = 1102;
    public static final String baseFile = "lpt";


    private Activity mActivity;
    private File tempFile;
    private Bitmap headBitmap;
    TextView fromCamera;
    TextView fromAlbum;
    TextView cancel;
    boolean isCut = true;

    private ArrayList<File> fileArrayList = new ArrayList<>();

    private String photoName = "def_image.jpg";
    private OnImageSelectListener listener;
    static Uri uritempFile;

    public UploadImagePopupWindow(Activity mActivity) {
        this(mActivity, true);
    }

    public UploadImagePopupWindow(Activity activity, boolean isCut) {
        this.isCut = isCut;
        this.mActivity = activity;
        View rootView = View.inflate(activity, R.layout.view_dialog_logo, null);
        setContentView(rootView);
        fromCamera = (TextView) rootView.findViewById(R.id.camera);
        fromAlbum = (TextView) rootView.findViewById(R.id.album);
        cancel = (TextView) rootView.findViewById(R.id.no);
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
        this.photoName = mActivity.getPackageName().replace(".", "_") + System.currentTimeMillis() + ".jpg";
        super.showAtLocation(parent, gravity, x, y);
    }

    private void setWindowAlpha(float alpha) {
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }

    public void setImageSelectListener(OnImageSelectListener listener) {
        this.listener = listener;
    }


    public void onResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_PHOTO) {
            if (data != null) {
                Uri uri = data.getData();
                if (isCut) {
                    // 得到图片的全路径
                    Intent intent = crop(uri);
                    mActivity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
                } else {
                    listener.onSuccess(new File(SystemUtil.Uri2Path(mActivity, uri)));
                    this.dismiss();
                }
            }
        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (data != null && data.getData() == null) {return;}
            tempFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/" + photoName);
            if (isCut) {
                if (tempFile.exists()) {
                    Uri uri = Uri.fromFile(tempFile);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        uri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".fileprovider", tempFile);
                    }
                    Intent intent = crop(uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    mActivity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
                }
            } else {
                if (tempFile.exists()) {
                    listener.onSuccess(tempFile);
                    fileArrayList.add(tempFile);
                }
            }
            this.dismiss();
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
//                headBitmap = data.getParcelableExtra("data");
                headBitmap = BitmapFactory.decodeStream(
                        mActivity.getContentResolver().openInputStream(uritempFile));
                File file = new File(uritempFile.getPath());
                if (file.exists()) {
                    fileArrayList.add(file);
                    listener.onSuccess(bitmap2file(headBitmap));
                }
                this.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.camera) {
            if (PermissionUtil.checkCameraPermission(mActivity)) {
                fromCamera();
            } else {
                ToastUtil.showMessage(mActivity, "请赋予App拍照权限");
            }
        } else if (i == R.id.album) {
            fromPhoto();
        } else if (i == R.id.no) {
            dismiss();
        }
    }

    public File bitmap2file(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(),
                photoName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从相册选择照片
     */
    private void fromPhoto() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_PHOTO);
    }

    /**
     * 拍照作为头像
     */
    private void fromCamera() {
        if (hasSdcard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + photoName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".fileprovider", file);
                //添加权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            mActivity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

        } else {
            if (listener != null) {
                listener.onSDCardNotFound();
            }
        }
    }


    /**
     * 调用系统相机
     *
     * @param photoFileName
     * @return
     */

    public static Intent systemCamera(String photoFileName) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        File file = new File(Environment
                .getExternalStorageDirectory(), photoFileName);
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(file));
        }
        if (file.exists()) {
            file.delete();
        }
        return intent;
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


    /**
     * 裁剪图片
     *
     * @param uri
     */
    public static Intent crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 1200);
        intent.putExtra("outputY", 1200);
        // 图片格式
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }


    public interface OnImageSelectListener {
        void onSDCardNotFound();

        void onSuccess(File file);
    }

    public void clean() {
        for (File file : fileArrayList) {
            file.delete();
        }
    }
}