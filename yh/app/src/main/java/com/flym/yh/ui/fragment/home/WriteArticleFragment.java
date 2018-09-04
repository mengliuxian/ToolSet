package com.flym.yh.ui.fragment.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.flym.yh.data.model.GetCateListBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CustomDialog;
import com.flym.yh.ui.view.LoadingDialog;
import com.flym.yh.ui.view.UploadImagePopupWindow;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.PermissionUtil;
import com.flym.yh.utils.PopupWindowUtils;
import com.flym.yh.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 写文章  修改文章
 */

public class WriteArticleFragment extends BaseFragment implements UploadImagePopupWindow.OnImageSelectListener {

    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_title)
    EditText edTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
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

    private UploadImagePopupWindow uploadImagePopupWindow;

    /**
     * 类型id  上传的文件
     */
    private int typeId = -1;
    private String fileString = "";
    private int id = 0;

    private List<GetCateListBean.ListBean> list = new ArrayList<>();
    private Dialog dialog;


    public static WriteArticleFragment newInstance(int typeId, String title,
                                                   String fileString, String contant, int id) {

        Bundle args = new Bundle();
        args.putInt("typeId", typeId);
        if (typeId != 0) {
            //大于0就是修改  等于0 就是新增
            args.putString("title", title);
            args.putString("fileString", fileString);
            args.putString("contant", contant);
            args.putInt("id", id);
        }
        WriteArticleFragment fragment = new WriteArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (getArguments().getInt("typeId", 0) != 0) {
            id = getArguments().getInt("id", 0);
            typeId = getArguments().getInt("typeId", 0);
            fileString = getArguments().getString("fileString");
            edTitle.setText(getArguments().getString("title"));
            edContent.setText(getArguments().getString("contant"));

            lSelect.setVisibility(View.GONE);
            rSelect.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(getActivity(), ApiConstants.getImageUrl(fileString), ivImg);
        }
        getCatelist();
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
        return R.layout.fragment_write_article;
    }


    @OnClick({R.id.iv_del, R.id.tv_submit, R.id.tv_type, R.id.tv_select})
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
                if (edContent.getText().toString().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入内容");
                    return;
                }
                if (fileString.equals("")) {
                    ToastUtil.showMessage(getActivity(), "请上传封面图片");
                    return;
                }
                if (typeId == -1) {
                    ToastUtil.showMessage(getActivity(), "请选择分类");
                    return;
                }
                saveCate();
                break;
            case R.id.tv_select:
                getPhoto();
                break;
            case R.id.tv_type:
                PopupWindowUtils.selectCateName(getActivity(), list, tvType, tvType.getWidth(), new SelectCateType() {
                    @Override
                    public void onselect(int potion) {
                        tvType.setText(list.get(potion).name);
                        typeId = list.get(potion).id;
                    }
                });
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
        if (uploadImagePopupWindow != null) {
            uploadImagePopupWindow.onResult(requestCode, resultCode, data);
        }
    }

    /**
     * 获取资讯分类列表
     */
    public void getCatelist() {
        compositeDisposable.add(RetrofitUtil.getApiService().getCatelist("")
                .compose(new SimpleTransFormer<GetCateListBean>(GetCateListBean.class))
                .subscribeWith(new DisposableWrapper<GetCateListBean>() {
                    @Override
                    public void onNext(GetCateListBean o) {
                        if (o.list != null && o.list.size() > 0) {
                            list.addAll(o.list);
                            if (typeId != -1) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).id == typeId) {
                                        list.get(i).isShow = true;
                                        tvType.setText(list.get(i).name);
                                    }
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 5.文件上传
     */
    @SuppressLint("CheckResult")
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
     * 保存文章
     */
    public void saveCate() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .saveDoctorArtivle(edTitle.getText().toString().trim(), fileString,
                        edContent.getText().toString().trim(), typeId, id)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {
                        dialog = CustomDialog.textContentHint(context, "", getString(R.string.submitted_success), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                FragmentUtil.popBackStack(getFragmentManager(), getTag());
                            }
                        }, new CustomDialog.EditTextReultListence() {
                            @Override
                            public void onEditReult(String s) {

                            }
                        });


                    }
                }));
    }


    public interface SelectCateType {
        void onselect(int potion);
    }
}
