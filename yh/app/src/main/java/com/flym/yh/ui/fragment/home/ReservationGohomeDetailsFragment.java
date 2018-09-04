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
 * 回电页面
 */

public class ReservationGohomeDetailsFragment extends BaseFragment {

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
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    private GetServicesBean.ListBean listBean;

    public static ReservationGohomeDetailsFragment newInstance(GetServicesBean.ListBean listBean) {

        Bundle args = new Bundle();
        args.putParcelable("bean", listBean);
        ReservationGohomeDetailsFragment fragment = new ReservationGohomeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (listBean != null) {
            // TODO: 2018/4/17  预约上门详情页面，数据对应不上
            GlideUtil.loadImage(context, ApiConstants.getImageUrl(listBean.getImage()), ivAvatar, R.drawable.wd_grmp__touxiang);
            tvName.setText(listBean.getTitle());
            tvAge.setText(listBean.getTitle());
            tvSex.setText(listBean.getTitle());
            tvTime.setText(listBean.getCreate_time());
            tvType.setText(listBean.getTitle());
            tvAddress.setText(listBean.getTitle());
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
        return R.layout.fragment_reservation_gohome_details;
    }

    @OnClick(R.id.tv_contact)
    public void onViewClicked() {
        PhoneUtils.call("tel");


    }

}
