package com.flym.yh.ui.fragment.information;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.ArticleGetListBean;
import com.flym.yh.utils.GlideUtil;

import butterknife.BindView;

/**
 * Created by mengl on 2018/3/27.
 */

public class InformationDetailsFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name_time)
    TextView nameTime;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.content)
    TextView content;

    private ArticleGetListBean.ListBean bean;
    private String titles;


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.information_details));
        return toolbar;
    }

    public static InformationDetailsFragment newInstance(ArticleGetListBean.ListBean bean) {

        Bundle args = new Bundle();
        args.putParcelable("bean", bean);
        InformationDetailsFragment fragment = new InformationDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static InformationDetailsFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        InformationDetailsFragment fragment = new InformationDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {
        if (bean != null) {

            GlideUtil.loadImage(context, bean.image, logo);
            title.setText(bean.title);
            nameTime.setText(bean.username + bean.create_time);
            content.setText(bean.content);
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bean = getArguments().getParcelable("bean");
        titles = getArguments().getString("title");
        if (!TextUtils.isEmpty(titles)) {
            toolbarTitle.setText(titles);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_information_details;
    }


}
