package com.flym.yh.ui.fragment.mine.certification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.mine.AuthenticationActivity;
import com.flym.yh.ui.view.ImageTextView;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.ui.view.UploadImagePopupWindow;
import com.flym.yh.utils.PermissionUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by mengl on 2018/3/21.
 */

public class LicenseInformationFragment extends BaseFragment implements UploadImagePopupWindow.OnImageSelectListener, AuthenticationActivity.LiscenseInfoListence {


    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.id_positive)
    ImageTextView idPositive;
    @BindView(R.id.id_reverse)
    ImageTextView idReverse;
    @BindView(R.id.medical_licence)
    ImageTextView medicalLicence;
    @BindView(R.id.physicians_practicing_certificate)
    ImageTextView physiciansPracticingCertificate;
    @BindView(R.id.employee_card)
    ImageTextView employeeCard;
    @BindView(R.id.next_text)
    TextView nextText;
    private UploadImagePopupWindow uploadImagePopupWindow;
    private int imageViewId;
    private String idPositiveFile;
    private String idReverseFile;
    private String medicalLicenceFile;
    private String physiciansPracticingCertificateFile;
    private String employeeCardFile;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AuthenticationActivity) getActivity()).setLiscenseInfoListence(this);

    }

    @Override
    protected void initData() {
        if (AuthenticationActivity.baseInfo != null && AuthenticationActivity.baseInfo.certs != null) {
            DoctorGetdetailBean.CertsBean certs = AuthenticationActivity.baseInfo.certs;
            idPositiveFile = certs.id_card_front;
            idPositive.loadImage(certs.id_card_front);
            idReverseFile = certs.id_card_back;
            idReverse.loadImage(certs.id_card_back);
            medicalLicenceFile = certs.license;
            medicalLicence.loadImage(certs.license);
            physiciansPracticingCertificate.loadImage(certs.practising);
            physiciansPracticingCertificateFile = certs.practising;
            employeeCard.loadImage(certs.employee_card);
            employeeCardFile = certs.employee_card;

        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_license_information;
    }


    @OnClick({R.id.id_positive, R.id.id_reverse, R.id.medical_licence, R.id.physicians_practicing_certificate, R.id.employee_card, R.id.next_text})
    public void onViewClicked(View view) {
        imageViewId = view.getId();
        switch (view.getId()) {
            case R.id.id_positive:
            case R.id.id_reverse:
            case R.id.medical_licence:
            case R.id.physicians_practicing_certificate:
            case R.id.employee_card:
                getPhoto();
                break;
            case R.id.next_text:

                break;
        }
    }

    public void getPhoto() {
        if (uploadImagePopupWindow == null) {
            uploadImagePopupWindow = new UploadImagePopupWindow(getActivity(), false);
            uploadImagePopupWindow.setImageSelectListener(this);
        }
        if (PermissionUtil.checkSDCardPermission(context, this) &&
                PermissionUtil.checkCameraPermission(context, this)) {
            uploadImagePopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onSDCardNotFound() {

    }

    /**
     * 照片的回调
     *
     * @param file
     */
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
        if (uploadImagePopupWindow != null) {
            uploadImagePopupWindow.onResult(requestCode, resultCode, data);
        }
    }


    /**
     * 5.文件上传
     */

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
                        switch (imageViewId) {
                            case R.id.id_positive:
                                idPositiveFile = simpleString.string;
                                idPositive.loadImage(file);
                                break;
                            case R.id.id_reverse:
                                idReverseFile = simpleString.string;
                                idReverse.loadImage(file);
                                break;
                            case R.id.medical_licence:
                                medicalLicenceFile = simpleString.string;
                                medicalLicence.loadImage(file);
                                break;
                            case R.id.physicians_practicing_certificate:
                                physiciansPracticingCertificateFile = simpleString.string;
                                physiciansPracticingCertificate.loadImage(file);
                                break;
                            case R.id.employee_card:
                                employeeCardFile = simpleString.string;
                                employeeCard.loadImage(file);
                                break;

                        }
                    }
                });
    }

    /**
     * 更新医生职业信息
     */
    public void doctorUpdateinfo() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateinfo(
                idPositiveFile, idReverseFile, medicalLicenceFile, physiciansPracticingCertificateFile,
                employeeCardFile, "1"
        )
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {

                        ((AuthenticationActivity) getActivity()).LisNext();
                    }
                }));
    }


    @Override
    public void onNext() {
        doctorUpdateinfo();
    }
}
