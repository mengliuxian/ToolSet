package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/21
 * 我的服务
 */

public class MyServiceFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static MyServiceFragment newInstance() {

        Bundle args = new Bundle();

        MyServiceFragment fragment = new MyServiceFragment();
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
        return R.layout.fragment_my_service;
    }


    @OnClick({R.id.it_graphic_consultation, R.id.it_telephone_consultation, R.id.it_medical_reading,
            R.id.it_reservation_gohome, R.id.it_doctor_lecture, R.id.it_doctor_broadcast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.it_graphic_consultation:
                FragmentUtil.replaceFragmentToActivity(R.id.container, SetMoreServiceFragment.newInstance(),
                        getFragmentManager(),getString(R.string.graphic_consultation));
                break;
            case R.id.it_telephone_consultation:
                FragmentUtil.replaceFragmentToActivity(R.id.container, SetTelephoneConsultationFragment.newInstance(),
                        getFragmentManager(),getString(R.string.telephone_consultation));
                break;
            case R.id.it_medical_reading:
                FragmentUtil.replaceFragmentToActivity(R.id.container, SetMoreServiceFragment.newInstance(),
                        getFragmentManager(),getString(R.string.medical_reading));
                break;
            case R.id.it_reservation_gohome:
                FragmentUtil.replaceFragmentToActivity(R.id.container, SetGohomeServiceFragment.newInstance(),
                        getFragmentManager(),getString(R.string.reservation_gohome));
                break;
            case R.id.it_doctor_lecture:
                FragmentUtil.replaceFragmentToActivity(R.id.container, SetMoreServiceFragment.newInstance(),
                        getFragmentManager(),getString(R.string.doctor_lecture));
                break;
            case R.id.it_doctor_broadcast:
                FragmentUtil.replaceFragmentToActivity(R.id.container, SetMoreServiceFragment.newInstance(),
                        getFragmentManager(),getString(R.string.doctor_broadcast));
                break;

            default:
                break;
        }
    }
}
