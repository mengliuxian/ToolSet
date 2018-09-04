package com.flym.yh.ui.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.ui.fragment.home.DoctorBroadcastFragment;
import com.flym.yh.ui.fragment.home.DoctorLectureFragment;
import com.flym.yh.ui.fragment.home.GraphicConsultationFragment;
import com.flym.yh.ui.fragment.home.MedicalReadingFragment;
import com.flym.yh.ui.fragment.home.ReservationGohomeFragment;
import com.flym.yh.ui.fragment.home.TelephoneCconsultationFragment;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;

/**
 * @author lsq
 * @date 2018/3/21
 * 我的服务
 */
public class DoctorServicesActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;

    private BaseFragment currentFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        switchTab(getIntent().getIntExtra("type", 0));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    public void switchTab(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        currentFragment = null;
        switch (position) {
            case R.string.graphic_consultation:
                currentFragment = GraphicConsultationFragment.newInstance();
                break;
            case R.string.telephone_consultation:
                currentFragment = TelephoneCconsultationFragment.newInstance();
                break;
            case R.string.medical_reading:
                currentFragment = MedicalReadingFragment.newInstance();
                break;
            case R.string.reservation_gohome:
                currentFragment = ReservationGohomeFragment.newInstance();
                break;
            case R.string.doctor_lecture:
                currentFragment = DoctorLectureFragment.newInstance();
                break;
            case R.string.doctor_broadcast:
                currentFragment = DoctorBroadcastFragment.newInstance();
                break;
            default:
                break;
        }
        String tag = getString(position);
        if (fm.findFragmentByTag(tag) != null) {
            ft.show(fm.findFragmentByTag(tag));
        } else {
            ft.add(R.id.container, currentFragment, tag);
        }
        ft.commitAllowingStateLoss();
    }

}
