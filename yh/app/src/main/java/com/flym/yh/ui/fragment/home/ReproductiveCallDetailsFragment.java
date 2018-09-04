package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.PhoneUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 回电页面，联系患者
 */

public class ReproductiveCallDetailsFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_type)
    TextView tvType;
    private GetServicesBean.ListBean listBean;

    public static ReproductiveCallDetailsFragment newInstance(GetServicesBean.ListBean listBean) {

        Bundle args = new Bundle();
        args.putParcelable("bean", listBean);
        ReproductiveCallDetailsFragment fragment = new ReproductiveCallDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (listBean != null) {
            // TODO: 2018/4/17 回电页面，数据不对
            GlideUtil.loadImage(context, ApiConstants.getImageUrl(listBean.getImage()), ivAvatar);
            tvSex.setText(listBean.getTitle());
            tvAge.setText(listBean.getTitle());
            tvName.setText(listBean.getTitle());
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        listBean = getArguments().getParcelable("bean");
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_reproductive_call_details;
    }

    @OnClick(R.id.tv_contact)
    public void onViewClicked() {
        if (listBean != null) {
            PhoneUtils.call(listBean.getTitle());

        }
    }



}
