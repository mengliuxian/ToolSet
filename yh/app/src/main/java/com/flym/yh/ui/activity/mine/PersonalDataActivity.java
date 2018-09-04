package com.flym.yh.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.data.model.GetDepartmenglistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CustomDialog;
import com.flym.yh.ui.view.CustomMineItemView;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.ui.view.UploadImagePopupWindow;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.Constants;
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
 * 个人资料
 */
public class PersonalDataActivity extends BaseActivity implements UploadImagePopupWindow.OnImageSelectListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head)
    CustomMineItemView head;
    @BindView(R.id.name)
    CustomMineItemView name;
    @BindView(R.id.gender)
    CustomMineItemView gender;
    @BindView(R.id.department)
    CustomMineItemView department;
    @BindView(R.id.the_title)
    CustomMineItemView theTitle;
    @BindView(R.id.specialty)
    CustomMineItemView specialty;
    @BindView(R.id.practicing_hospital)
    CustomMineItemView practicingHospital;
    @BindView(R.id.detail_address)
    CustomMineItemView detailAddress;


    private UploadImagePopupWindow uploadImagePopupWindow;
    private String headFile;
    private Dialog dialog;
    private String rName;
    private String rGender;
    private GetDepartmenglistBean.ListBean.ChildBean child;
    private String rTheTitle;
    private String rSpecialty;
    private String rHospital;
    private String rAddress;
    private String sex;
    private String id;

    private DoctorGetdetailBean data;

    @Override
    protected String getPagerTitle() {
        return getString(R.string.personal_data);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_data;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        data = UserManager.getUserDataFromNative(this);
    }

    @Override
    protected void initData() {
        if (data != null) {
            if (data.info != null) {
                head.setRightIcon(data.info.image);
                name.setRightText(data.info.name);
                department.setRightText(data.info.department_name);
                gender.setRightText(data.info.sex == 1 ? getString(R.string.man) : getString(R.string.woman));
                theTitle.setRightText(data.info.job);
                specialty.setRightText(data.info.specialty);
                practicingHospital.setRightText(data.info.hospital);
                detailAddress.setRightText(data.info.address);
            }
        } else {
            doctorGetdetail();
        }
    }


    @OnClick({R.id.head, R.id.name, R.id.gender, R.id.department,
            R.id.the_title, R.id.specialty, R.id.practicing_hospital, R.id.detail_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head:
                getPhoto();
                break;
            case R.id.name:
                rName = "";
                dialog = CustomDialog.amendContentHint(this, getString(R.string.name), "", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        doctorUpdateaccount();
                    }
                }, new CustomDialog.EditTextReultListence() {
                    @Override
                    public void onEditReult(String s) {
                        rName = s;
                    }
                });
                break;
            case R.id.gender:
                rGender = "";
                dialog = CustomDialog.genderContentHint(this, getString(R.string.gender), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(rGender)) {
                            doctorUpdateaccount();

                        }

                    }
                }, new CustomDialog.EditTextReultListence() {
                    @Override
                    public void onEditReult(String s) {
                        rGender = s;
                        if (getString(R.string.man).equals(s)) {
                            sex = "1";
                        } else {
                            sex = "2";
                        }
                    }
                });

                break;
            case R.id.department:
                ActivityManager.getInstance().startNextActivityForResult(
                        new Intent(PersonalDataActivity.this, DepartmentActivity.class), Constants.DEPART);
                break;
            case R.id.the_title:
                rTheTitle = "";
                dialog = CustomDialog.theTitleContentHint(this, getString(R.string.the_title), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(rTheTitle)) {
                            doctorUpdateaccount();
                        }
                    }
                }, new CustomDialog.EditTextReultListence() {
                    @Override
                    public void onEditReult(String s) {
                        rTheTitle = s;
                    }
                });
                break;
            case R.id.specialty:
                rSpecialty = "";
                dialog = CustomDialog.amendContentHint(this, getString(R.string.specialty), "", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(rSpecialty)) {

                            doctorUpdateaccount();
                        }
                    }
                }, new CustomDialog.EditTextReultListence() {
                    @Override
                    public void onEditReult(String s) {
                        rSpecialty = s;
                    }
                });
                break;
            case R.id.practicing_hospital:
                rHospital = "";
                dialog = CustomDialog.amendContentHint(this, getString(R.string.practicing_hospital), "", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(rHospital)) {

                            doctorUpdateaccount();
                        }
                    }
                }, new CustomDialog.EditTextReultListence() {
                    @Override
                    public void onEditReult(String s) {
                        rHospital = s;
                    }
                });

                break;
            case R.id.detail_address:
                rAddress = "";
                dialog = CustomDialog.amendContentHint(this, getString(R.string.detail_address), "", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(rAddress)) {

                            doctorUpdateaccount();
                        }
                    }
                }, new CustomDialog.EditTextReultListence() {
                    @Override
                    public void onEditReult(String s) {
                        rAddress = s;
                    }
                });
                break;
        }
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
        MultipartBody body = new MultipartBody.Builder()
                .addPart(part)
                .build();

        RetrofitUtil.getApiService().upload(part)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>(show) {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        headFile = simpleString.string;
                        doctorUpdateaccount();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (uploadImagePopupWindow != null) {
            uploadImagePopupWindow.onResult(requestCode, resultCode, data);
        }
        if (requestCode == Constants.DEPART && resultCode == RESULT_OK) {
            child = data.getParcelableExtra("ChildBean");
            id = child.id;
            doctorUpdateaccount();
        }
    }


    /**
     * 更新医生个人信息
     */
    public void doctorUpdateaccount() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateaccount(
                headFile, rName, sex, rHospital,
                id, rTheTitle, rSpecialty, null, null, null,
                rAddress, null)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (dialog != null) {
                            dialog.dismiss();

                        }
                        Constants.isChange = true;
                        if (!TextUtils.isEmpty(headFile)) {
                            head.setRightIcon(headFile);
                        }
                        if (!TextUtils.isEmpty(rName)) {
                            name.setRightText(rName);
                        }
                        if (!TextUtils.isEmpty(sex)) {
                            switch (sex) {
                                case "1":
                                    gender.setRightText(getString(R.string.man));
                                    break;
                                case "2":
                                    gender.setRightText(getString(R.string.woman));
                                    break;
                            }
                        }
                        if (!TextUtils.isEmpty(rHospital)) {
                            practicingHospital.setRightText(rHospital);
                        }
                        if (!TextUtils.isEmpty(id)) {
                            department.setRightText(child.name);
                        }
                        if (!TextUtils.isEmpty(rTheTitle)) {
                            theTitle.setRightText(rTheTitle);
                        }
                        if (!TextUtils.isEmpty(rSpecialty)) {
                            specialty.setRightText(rSpecialty);
                        }
                        if (!TextUtils.isEmpty(rAddress)) {
                            detailAddress.setRightText(rAddress);
                        }
                    }
                }));
    }

    /**
     * 获取登录医生详情
     */
    public void doctorGetdetail() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorGetdetail("")
                .compose(new SimpleTransFormer<DoctorGetdetailBean>(DoctorGetdetailBean.class))
                .subscribeWith(new DisposableWrapper<DoctorGetdetailBean>() {
                    @Override
                    public void onNext(DoctorGetdetailBean doctorGetdetailBean) {
                        UserManager.saveUserDataToNative(PersonalDataActivity.this, doctorGetdetailBean);
                        data = doctorGetdetailBean;
                        initData();
                    }
                }));
    }
}
