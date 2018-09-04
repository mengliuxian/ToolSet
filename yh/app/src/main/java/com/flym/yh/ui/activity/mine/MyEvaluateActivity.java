package com.flym.yh.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.mine.MyEvaluteAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DoctorGetcommentlistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.TestDataUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class MyEvaluateActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.spring)
    SpringView spring;

    private MyEvaluteAdapter adapter;
    private int page = 1;
    private ArrayList<DoctorGetcommentlistBean> dataList = new ArrayList<>();

    @Override
    protected String getPagerTitle() {
        return getString(R.string.my_evaluate);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_evaluate;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initSpring();

    }

    private void initSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                doctorGetcommentlist();
            }

            @Override
            public void onLoadmore() {
                page++;
                doctorGetcommentlist();
            }
        });
        spring.setFooter(new DefaultFooter(this));
        spring.setHeader(new DefaultHeader(this));
    }

    @Override
    protected void initData() {
        doctorGetcommentlist();
        adapter = new MyEvaluteAdapter(TestDataUtil.getTestData());
        recyclerView.setAdapter(adapter);
    }


    /**
     * 获取评价列表
     */
    public void doctorGetcommentlist() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorGetcommentlist(page)
                        .compose(new SimpleTransFormer<DoctorGetcommentlistBean>(DoctorGetcommentlistBean.class))
                        .subscribeWith(new DisposableWrapper<DoctorGetcommentlistBean>() {
                            @Override
                            public void onNext(DoctorGetcommentlistBean doctorGetcommentlistBean) {
                                if (page == 1) {
                                    dataList.clear();
                                }
//                        dataList.addAll(doctorGetcommentlistBean);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                spring.onFinishFreshAndLoad();
                            }
                        })
        );
    }

}
