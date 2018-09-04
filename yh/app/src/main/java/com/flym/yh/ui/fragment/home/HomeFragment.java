package com.flym.yh.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.AdlistBean;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.home.ContributorsActivity;
import com.flym.yh.ui.activity.home.DiagnosticTemplateActivity;
import com.flym.yh.ui.activity.home.DoctorServicesActivity;
import com.flym.yh.ui.activity.home.DrugDirectoryActivity;
import com.flym.yh.ui.activity.home.HealthyPrescriptionActivity;
import com.flym.yh.ui.activity.home.MedicationTemplateActivity;
import com.flym.yh.ui.activity.home.MyOfficeActivity;
import com.flym.yh.ui.activity.home.MyServiceActivity;
import com.flym.yh.ui.view.AutoScrollTextView;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author lishuqi
 * @date 2018/3/21
 * 主页
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.au)
    AutoScrollTextView au;
    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_good_comment)
    TextView tvGoodComment;
    private DoctorGetdetailBean data;
    private List<String> imgList = new ArrayList<>();

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {

        data = UserManager.getUserDataFromNative(context);

        if (data != null && data.stats != null)
        {
            tvFans.setText(data.stats.fans);
            tvService.setText(data.stats.orders);
            tvGoodComment.setText(data.stats.favor_rate);
        }
        setAu("医学健康咨询在线");
        setBanner();
    }

    /**
     * 设置跑马灯
     */
    public void setAu(String s) {
        //启动公告滚动条
        au.setText(s);
        au.init(getActivity().getWindowManager(), getActivity());
        au.startScroll();
    }

    /**
     * 设置轮播图
     */
    public void setBanner() {
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                GlideUtil.loadImage(getContext(), model, itemView, R.mipmap.ic_launcher);
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getList();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @OnClick({R.id.it_my_office, R.id.it_my_service, R.id.it_healthy_prescription, R.id.it_drug_directory,
            R.id.it_diagnostic_template, R.id.it_medication_template, R.id.it_contributors,
            R.id.it_graphic_consultation, R.id.it_telephone_consultation, R.id.it_medical_reading,
            R.id.it_reservation_gohome, R.id.it_doctor_lecture, R.id.it_doctor_broadcast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.it_my_office:
                ActivityManager.getInstance().startNextActivity(MyOfficeActivity.class);
                break;
            case R.id.it_my_service:
                ActivityManager.getInstance().startNextActivity(MyServiceActivity.class);
                break;
            case R.id.it_healthy_prescription:
                ActivityManager.getInstance().startNextActivity(HealthyPrescriptionActivity.class);
                break;
            case R.id.it_drug_directory:
                ActivityManager.getInstance().startNextActivity(DrugDirectoryActivity.class);
                break;
            case R.id.it_diagnostic_template:
                ActivityManager.getInstance().startNextActivity(DiagnosticTemplateActivity.class);
                break;
            case R.id.it_medication_template:
                ActivityManager.getInstance().startNextActivity(MedicationTemplateActivity.class);
                break;
            case R.id.it_contributors:
                ActivityManager.getInstance().startNextActivity(ContributorsActivity.class);
                break;
            case R.id.it_graphic_consultation:
                ActivityManager.getInstance().startNextActivity(new Intent(getActivity(), DoctorServicesActivity.class).putExtra("type", R.string.graphic_consultation));
                break;
            case R.id.it_telephone_consultation:
                ActivityManager.getInstance().startNextActivity(new Intent(getActivity(), DoctorServicesActivity.class).putExtra("type", R.string.telephone_consultation));
                break;
            case R.id.it_medical_reading:
                ActivityManager.getInstance().startNextActivity(new Intent(getActivity(), DoctorServicesActivity.class).putExtra("type", R.string.medical_reading));
                break;
            case R.id.it_reservation_gohome:
                ActivityManager.getInstance().startNextActivity(new Intent(getActivity(), DoctorServicesActivity.class).putExtra("type", R.string.reservation_gohome));
                break;
            case R.id.it_doctor_lecture:
                ActivityManager.getInstance().startNextActivity(new Intent(getActivity(), DoctorServicesActivity.class).putExtra("type", R.string.doctor_lecture));
                break;
            case R.id.it_doctor_broadcast:
                ActivityManager.getInstance().startNextActivity(new Intent(getActivity(), DoctorServicesActivity.class).putExtra("type", R.string.doctor_broadcast));
                break;
            default:
                break;
        }
    }

    /**
     * 获取轮播图列表
     */
    public void getList() {
        compositeDisposable.add(RetrofitUtil.getApiService().getAdlist("")
                .compose(new SimpleTransFormer<AdlistBean>(AdlistBean.class))
                .subscribeWith(new DisposableWrapper<AdlistBean>() {
                    @Override
                    public void onNext(AdlistBean simpleString) {
                        imgList.clear();
                        imgList.addAll(simpleString.list);
                        banner.setData(imgList, null);
                    }
                }));
    }
}
