package com.flym.yh.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.mine.AuthenticationActivity;
import com.flym.yh.ui.view.ImageTextView;
import com.flym.yh.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationStyleActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.base_information)
    ImageTextView baseInformation;
    @BindView(R.id.license_information)
    ImageTextView licenseInformation;
    @BindView(R.id.submit_audit)
    ImageTextView submitAudit;
    @BindView(R.id.next_bt)
    Button nextBt;
    @BindView(R.id.title)
    TextView title;

    private int vis;

    @Override
    protected String getPagerTitle() {
        return getString(R.string.name_authentication);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_authentication_style;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        vis = intent.getIntExtra("vis", 0);
    }

    @Override
    protected void initData() {
        doctorGetdetail();
        licenseInformation.setTextColor(getResources().getColor(R.color.red_ff));
        baseInformation.setTextColor(getResources().getColor(R.color.red_ff));
        submitAudit.setTextColor(getResources().getColor(R.color.red_ff));
        if (vis == 1) {
            nextBt.setVisibility(View.VISIBLE);
        } else {
            nextBt.setVisibility(View.GONE);
        }

    }


    @OnClick(R.id.next_bt)
    public void onViewClicked() {
        ActivityManager.getInstance().startNextActivity(AuthenticationActivity.class, false);

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
                        AuthenticationActivity.baseInfo = doctorGetdetailBean;
                        if (doctorGetdetailBean.info != null) {
                            title.setText(doctorGetdetailBean.info.refuse_reason);
                        }
                    }
                }));
    }

}
