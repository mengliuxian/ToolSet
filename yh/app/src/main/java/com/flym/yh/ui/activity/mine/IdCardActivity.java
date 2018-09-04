package com.flym.yh.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CircleImageView;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.ui.view.UploadImagePopupWindow;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.PermissionUtil;
import com.flym.yh.utils.UserManager;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人名片
 */
public class IdCardActivity extends BaseActivity implements UploadImagePopupWindow.OnImageSelectListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_car_heard)
    CircleImageView idCarHeard;
    @BindView(R.id.id_card_name)
    TextView idCardName;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.qr_code)
    ImageView qrCode;

    private DoctorGetdetailBean data;
    private UploadImagePopupWindow uploadImagePopupWindow;
    private String headFile;

    @Override
    protected String getPagerTitle() {
        return getString(R.string.personal_card);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_id_card;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        data = UserManager.getUserDataFromNative(this);

    }

    @Override
    protected void initData() {
        if (data != null && data.info != null) {
            GlideUtil.loadImage(this, ApiConstants.getImageUrl(data.info.image), idCarHeard, R.drawable.wd_grmp__touxiang);
            GlideUtil.loadImage(this, ApiConstants.getImageUrl(data.info.qr_code), qrCode, R.drawable.erweima);
            idCardName.setText(data.info.name);
            department.setText(data.info.department_name + "|" + data.info.job);
            hospital.setText(data.info.hospital);
        }
    }


    @OnClick(R.id.qr_code)
    public void onViewClicked() {
        getPhoto();

    }

    public void getPhoto() {
        if (uploadImagePopupWindow == null) {
            uploadImagePopupWindow = new UploadImagePopupWindow(this, false);
            uploadImagePopupWindow.setImageSelectListener(this);
        }
        if (PermissionUtil.checkSDCardPermission(this) &&
                PermissionUtil.checkCameraPermission(this)) {
            uploadImagePopupWindow.showAtLocation(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (uploadImagePopupWindow != null) {

            uploadImagePopupWindow.onResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSDCardNotFound() {

    }

    @Override
    public void onSuccess(File file) {
        File compressFile = null;
        try {
            compressFile = new Compressor(this).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        upload(compressFile);
    }

    /**
     * 5.文件上传
     */
    public void upload(final File file) {
        LoadingDialog show = LoadingDialog.show(this, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);


        RetrofitUtil.getApiService().upload(part)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>(show) {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        GlideUtil.loadImage(IdCardActivity.this, simpleString.string,
                                idCarHeard, R.drawable.erweima);

                    }
                });
    }

}
