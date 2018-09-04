package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.UserManager;

import butterknife.BindView;

/**
 * @author lishuqi
 * @date 2018/3/28
 * 文章详情
 */

public class ArtivleDetailsFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_contant)
    TextView tvContant;

    public static ArtivleDetailsFragment newInstance(String title, String time, String imgString, String contant) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("time", time);
        args.putString("imgString", imgString);
        args.putString("contant", contant);

        ArtivleDetailsFragment fragment = new ArtivleDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getArguments().getString("title"));
        tvName.setText(UserManager.getUserDataFromNative(getActivity()).info.name + "    " + getArguments().getString("time"));
        GlideUtil.loadImage(getActivity(), ApiConstants.getImageUrl(getArguments().getString("imgString")), ivImg);
        tvContant.setText("        " + getArguments().getString("contant"));
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
        return R.layout.fragment_article_details;
    }

}
