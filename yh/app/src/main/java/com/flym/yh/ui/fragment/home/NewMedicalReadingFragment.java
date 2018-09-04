package com.flym.yh.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.ui.view.UploadImagePopupWindow;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.PermissionUtil;
import com.flym.yh.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 写文章  添加名医讲堂
 */

public class NewMedicalReadingFragment extends BaseFragment implements UploadImagePopupWindow.OnImageSelectListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_title)
    EditText edTitle;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.l_select)
    LinearLayout lSelect;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.r_select)
    RelativeLayout rSelect;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.ed_price)
    EditText edPrice;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.iv_del)
    ImageView ivDel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.layout)
    LinearLayout layout;

    private UploadImagePopupWindow uploadImagePopupWindow;
    private String fileString;


    public static NewMedicalReadingFragment newInstance() {

        Bundle args = new Bundle();

        NewMedicalReadingFragment fragment = new NewMedicalReadingFragment();
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
        return R.layout.fragment_new_medical_reading;
    }


    @OnClick({R.id.iv_del, R.id.tv_submit, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_del:
                fileString = "";
                lSelect.setVisibility(View.VISIBLE);
                rSelect.setVisibility(View.GONE);
                break;
            case R.id.tv_submit:
                if (edTitle.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入标题");
                    return;
                }
                if (edPrice.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入价格");
                    return;
                }
                if (edContent.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入内容");
                    return;
                }
                if (fileString.equals("")) {
                    ToastUtil.showMessage(getActivity(), "请上传封面图片");
                    return;
                }

                doctoritemsSave();
                break;
            case R.id.tv_select:
                getPhoto();
                break;
            default:
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
            compressFile = new Compressor(context).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        upload(compressFile);
    }

    /**
     * 5.文件上传
     */
    public void upload(final File file) {
        LoadingDialog show = LoadingDialog.show(context, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        RetrofitUtil.getApiService().upload(part)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>(show) {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        fileString = simpleString.string;
                        GlideUtil.loadImage(getActivity(), file, ivImg);
                        lSelect.setVisibility(View.GONE);
                        rSelect.setVisibility(View.VISIBLE);
                    }
                });
    }


    /**
     * 新增服务
     */
    public void doctoritemsSave() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .doctoritemsSave(edTitle.getText().toString(), fileString, "",
                        edPrice.getText().toString(), edContent.getText().toString(), "",
                        39)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {
                        FragmentUtil.popBackStack(getFragmentManager());
                    }
                }));
    }


}
