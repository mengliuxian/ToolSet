package com.flym.yh.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.mine.PublishedArticlesAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.data.model.GetDoctorArtivleListBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CircleImageView;
import com.flym.yh.ui.view.UpTextDownTextView;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.UserManager;
import com.flym.yh.utils.itemdecoration.LinearDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 个人主页
 */
public class PersonalHomepageActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.fans)
    UpTextDownTextView fans;
    @BindView(R.id.consulting_and_services)
    UpTextDownTextView consultingAndServices;
    @BindView(R.id.rate)
    UpTextDownTextView rate;
    @BindView(R.id.doctor_feat)
    TextView doctorFeat;
    @BindView(R.id.outpatient_service_time)
    TextView outpatientServiceTime;
    @BindView(R.id.published_articles)
    TextView publishedArticles;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.spring)
    SpringView spring;

    private PublishedArticlesAdapter adapter;
    private DoctorGetdetailBean baseInfo;
    private DoctorGetdetailBean data;
    private ArrayList<GetDoctorArtivleListBean.ListBean> dataList = new ArrayList<>();
    private int page = 1;

    @Override
    protected String getPagerTitle() {
        return getString(R.string.personal_homepage);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personal_homepage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initSpring();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(this, LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL));
    }

    private void initSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {
                page++;
                getList();
            }
        });
        spring.setFooter(new DefaultFooter(this));

    }

    @Override
    protected void initData() {
        getList();
        data = UserManager.getUserDataFromNative(this);
        adapter = new PublishedArticlesAdapter(dataList);
        recyclerView.setAdapter(adapter);
        setData();

    }

    public void setData() {
        if (data != null) {
            if (data.info != null) {
                GlideUtil.loadImage(this, ApiConstants.getImageUrl(data.info.image), head, R.drawable.wd_grmp__touxiang);
                name.setText(data.info.name);
                department.setText(data.info.department_name + " " + data.info.job);
                hospital.setText(data.info.hospital);
                doctorFeat.setText(data.info.specialty);
                outpatientServiceTime.setText(data.info.inquiry_time);
            }
            if (data.stats != null) {
                fans.setUpString(data.stats.fans);
                consultingAndServices.setUpString(data.stats.orders);
                rate.setUpString(data.stats.favor_rate);
            }
        } else {
            doctorGetdetail();
        }


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
                        UserManager.saveUserDataToNative(PersonalHomepageActivity.this, doctorGetdetailBean);
                        data = doctorGetdetailBean;
                        setData();
                    }
                }));
    }

    /**
     * 获取医生投稿列表
     */
    public void getList() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .getDoctorArtivleList(page, 1)
                .compose(new SimpleTransFormer<GetDoctorArtivleListBean>(GetDoctorArtivleListBean.class))
                .subscribeWith(new DisposableWrapper<GetDoctorArtivleListBean>() {
                    @Override
                    public void onNext(GetDoctorArtivleListBean o) {
                        if (page == 1)
                        {
                            dataList.clear();
                        }
                        dataList.addAll(o.getList());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                }));
    }
}
