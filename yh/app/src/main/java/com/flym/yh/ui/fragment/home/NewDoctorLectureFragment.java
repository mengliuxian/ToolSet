package com.flym.yh.ui.fragment.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.ui.view.UploadImagePopupWindow;
import com.flym.yh.utils.ALiYunUtils;
import com.flym.yh.utils.DateUtils;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.PermissionUtil;
import com.flym.yh.utils.SystemUtil;
import com.flym.yh.utils.ToastUtil;



import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lishuqi
 * @date 2018/3/26
 * 添加名医讲堂
 */

public class NewDoctorLectureFragment extends BaseFragment implements UploadImagePopupWindow.OnImageSelectListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_title)
    EditText edTitle;
    @BindView(R.id.ed_details)
    EditText edDetails;
    @BindView(R.id.ed_spoilers)
    EditText edSpoilers;
    @BindView(R.id.l_select_pic)
    LinearLayout lSelectPic;
    @BindView(R.id.ed_price)
    EditText edPrice;
    @BindView(R.id.l_video)
    LinearLayout lVideo;
    @BindView(R.id.iv_select_pic)
    ImageView ivSelectPic;
    @BindView(R.id.r_select_pic)
    RelativeLayout rSelectPic;
    @BindView(R.id.iv_select_video)
    ImageView ivSelectVideo;
    @BindView(R.id.r_select_video)
    RelativeLayout rSelectVideo;
    @BindView(R.id.tv_select_pic)
    TextView tvSelectPic;
    @BindView(R.id.iv_select_pic_del)
    ImageView ivSelectPicDel;
    @BindView(R.id.tv_select_video)
    TextView tvSelectVideo;
    @BindView(R.id.iv_select_video_del)
    ImageView ivSelectVideoDel;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.layout)
    LinearLayout layout;

    private UploadImagePopupWindow uploadImagePopupWindow;
    private String fileString, videoString;
    private String obiectKey;

    public static NewDoctorLectureFragment newInstance() {

        Bundle args = new Bundle();

        NewDoctorLectureFragment fragment = new NewDoctorLectureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_new_doctor_lecture;
    }

    @OnClick({R.id.tv_select_pic, R.id.tv_select_video, R.id.tv_publish, R.id.iv_select_pic_del, R.id.iv_select_video_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_pic:
                if (uploadImagePopupWindow == null) {
                    uploadImagePopupWindow = new UploadImagePopupWindow(getActivity(), false);
                    uploadImagePopupWindow.setImageSelectListener(this);
                }
                if (PermissionUtil.checkSDCardPermission(context, this) &&
                        PermissionUtil.checkCameraPermission(context, this)) {
                    uploadImagePopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                }

                break;
            case R.id.tv_select_video:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent, 701);

                break;
            case R.id.tv_publish:
                if (edTitle.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入标题");
                    return;
                }
                if (edPrice.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入价格");
                    return;
                }
                if (edDetails.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入详情");
                    return;
                }
                if (edSpoilers.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入剧透");
                    return;
                }
                if (fileString.equals("")) {
                    ToastUtil.showMessage(getActivity(), "请上传封面图片");
                    return;
                }
                if (videoString.equals("")) {
                    ToastUtil.showMessage(getActivity(), "请上传视频");
                    return;
                }
                doctoritemsSave();
                break;
            case R.id.iv_select_pic_del:
                fileString = "";
                lSelectPic.setVisibility(View.VISIBLE);
                rSelectPic.setVisibility(View.GONE);
                break;
            case R.id.iv_select_video_del:
                videoString = "";
                lVideo.setVisibility(View.VISIBLE);
                rSelectVideo.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSDCardNotFound() {

    }

    @Override
    public void onSuccess(File file) {
        File compressFile = null;
        try {
            compressFile = new Compressor(context).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        upload(compressFile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 701 && data != null) {
            Uri data1 = data.getData();


            String path = SystemUtil.Uri2Path(context, data1);
            final Bitmap videoThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
            if (TextUtils.isEmpty(path)) {
                return;
            }
            Long s = System.currentTimeMillis() / 1000;
            obiectKey = "Video/" + DateUtils.getDateToString(s) + "/" + s + ".mp3";
            Log.d("tag1", "onActivityResult: " + obiectKey);
            File file = new File(path);

            String formatSize = SystemUtil.getFormatSize(file.length());
            Log.d("tag3", "onActivityResult: " + formatSize);


            final LoadingDialog show = LoadingDialog.show(context, getString(R.string.file_up));
            ALiYunUtils.aLiYunUpLoad(obiectKey, path, new ALiYunUtils.UploadAliayCall() {
                @Override
                public void setView(int f) {

                }

                @Override
                public void succ() {
                    show.dismiss();
                    videoString = ALiYunUtils.url + obiectKey;
                    rSelectVideo.setVisibility(View.VISIBLE);
                    ivSelectVideo.setImageBitmap(videoThumbnail);
                }

                @Override
                public void error(String message) {
                    show.dismiss();
                }
            });

        } else if (uploadImagePopupWindow != null) {
            uploadImagePopupWindow.onResult(requestCode, resultCode, data);
        }
    }


    /**
     * 5.文件上传
     */

    @SuppressLint("CheckResult")
    public void upload(final File file) {
        LoadingDialog show = LoadingDialog.show(context, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        MultipartBody body = new MultipartBody.Builder()
                .addPart(part)
                .build();

        RetrofitUtil.getApiService().upload(part)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>(show) {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        fileString = simpleString.string;
                        GlideUtil.loadImage(getActivity(), file, ivSelectPic);
                        lSelectPic.setVisibility(View.GONE);
                        rSelectPic.setVisibility(View.VISIBLE);
                    }
                });
    }


    /**
     * 新增服务
     */
    public void doctoritemsSave() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .doctoritemsSave(edTitle.getText().toString(), fileString, edSpoilers.getText().toString(),
                        edPrice.getText().toString(), edDetails.getText().toString(),
                        videoString,
                        37)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {
                        FragmentUtil.popBackStack(getFragmentManager());
                    }
                }));
    }


}
