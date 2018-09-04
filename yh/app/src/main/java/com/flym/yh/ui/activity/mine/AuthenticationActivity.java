package com.flym.yh.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.fragment.mine.certification.BaseInformationFragment;
import com.flym.yh.ui.fragment.mine.certification.LicenseInformationFragment;
import com.flym.yh.ui.fragment.mine.certification.SubmitAuditFragment;
import com.flym.yh.ui.view.ImageTextView;
import com.flym.yh.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AuthenticationActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.base_information)
    ImageTextView baseInformation;
    @BindView(R.id.license_information)
    ImageTextView licenseInformation;
    @BindView(R.id.submit_audit)
    ImageTextView submitAudit;
    @BindView(R.id.next_text)
    TextView nextText;


    public int page = 1;

    BaseFragment baseInformationFragment = new BaseInformationFragment();
    BaseFragment licenseInformationFragment = new LicenseInformationFragment();
    BaseFragment submitAuditFragment = new SubmitAuditFragment();
    public static DoctorGetdetailBean baseInfo;


    @Override
    protected String getPagerTitle() {
        return getString(R.string.name_authentication);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_authentications;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        baseInformation.setTextColor(getResources().getColor(R.color.red_ff));
        FragmentUtil.replaceFragmentToActivity(
                R.id.container, baseInformationFragment, getSupportFragmentManager());
    }

    @Override
    protected void initData() {
        doctorGetdetail();
    }

    /**
     * 后退的监听
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        if (page > 1) {
            page--;
            changeImageViewBg();
        } else {
            return super.onSupportNavigateUp();
        }
        return true;
    }

    /**
     * 后退的监听
     */
    @Override
    public void onBackPressed() {
        if (page > 1) {
            page--;
            changeImageViewBg();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Frangment切换的回调
     *
     * @param num
     */
    public void changePageFrangment(int num) {
        page = num;
        changeImageViewBg();
    }

    /**
     * Frangment后退时，改变颜色
     */
    public void changeImageViewBg() {
        switch (page) {
            case 1:
                nextText.setText(getString(R.string.next));
                nextText.setVisibility(View.VISIBLE);
                licenseInformation.setBg(R.drawable.wd_smrz_zhizhaoxinxi);
                licenseInformation.setTextColor(getResources().getColor(R.color.black_32));
                submitAudit.setBg(R.drawable.wd_smrz_tijiaoshenhe);
                submitAudit.setTextColor(getResources().getColor(R.color.black_32));
                FragmentUtil.hideFragmentToActivity(R.id.container, baseInformationFragment, licenseInformationFragment, getSupportFragmentManager());
                break;
            case 2:
                nextText.setText(getString(R.string.submit_the_certification));
                nextText.setVisibility(View.VISIBLE);
                licenseInformation.setTextColor(getResources().getColor(R.color.red_ff));
                submitAudit.setTextColor(getResources().getColor(R.color.black_32));
                licenseInformation.setBg(R.drawable.wd_smrz_zhizhaoxinxi_dangqian);
                submitAudit.setBg(R.drawable.wd_smrz_tijiaoshenhe);
                FragmentUtil.hideFragmentToActivity(R.id.container, licenseInformationFragment, submitAuditFragment, getSupportFragmentManager());
                break;
            case 3:
                nextText.setVisibility(View.GONE);
                licenseInformation.setTextColor(getResources().getColor(R.color.red_ff));
                submitAudit.setTextColor(getResources().getColor(R.color.red_ff));
                licenseInformation.setBg(R.drawable.wd_smrz_zhizhaoxinxi_dangqian);
                submitAudit.setBg(R.drawable.wd_smrz_tijiaoshenhe_dangqian);
                FragmentUtil.hideFragmentToActivity(R.id.container, submitAuditFragment, null, getSupportFragmentManager());
                break;
        }
    }

    @OnClick(R.id.next_text)
    public void onViewClicked() {
        switch (page) {
            case 1:
              baseListence.onNext();
                break;
            case 2:
            liscenseInfoListence.onNext();

                break;

        }
    }

    public void BaneNext(){
        FragmentUtil.hideFragmentToActivity(R.id.container, baseInformationFragment, licenseInformationFragment, getSupportFragmentManager(), getString(R.string.base_information));
        page = 2;
        changePageFrangment(2);
    }

    public void LisNext(){
        FragmentUtil.hideFragmentToActivity(R.id.container, licenseInformationFragment, submitAuditFragment, getSupportFragmentManager(), getString(R.string.license_information));
        page = 3;
        changePageFrangment(3);
    }

    /**
     * 获取登录医生详情
     */
    public void doctorGetdetail() {


        Disposable subscribe = RetrofitUtil.getApiService().doctorGetdetail("").
                compose(new SimpleTransFormer<DoctorGetdetailBean>(DoctorGetdetailBean.class))
                .subscribe(new Consumer<DoctorGetdetailBean>() {
            @Override
            public void accept(DoctorGetdetailBean doctorGetdetailBean) throws Exception {

            }
        });
//                        .subscribeWith(new DisposableWrapper<DoctorGetdetailBean>() {
//            @Override
//            public void onNext(DoctorGetdetailBean doctorGetdetailBean) {
//                baseInfo = doctorGetdetailBean;
//            }
//        }

//        )
//        ;


        compositeDisposable.add(subscribe);
    }


    public void setBaseListence(BaseListence baseListence) {
        this.baseListence = baseListence;
    }

    public void setLiscenseInfoListence(LiscenseInfoListence liscenseInfoListence) {
        this.liscenseInfoListence = liscenseInfoListence;
    }

    private BaseListence baseListence;
    private LiscenseInfoListence liscenseInfoListence;


    public static interface BaseListence {
        void onNext();
    }

    public static interface LiscenseInfoListence {
        void onNext();
    }
}
