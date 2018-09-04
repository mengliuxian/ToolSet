package com.flym.yh.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.mine.MyNewsAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.DoctorGetmessagelistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.TestDataUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 我的消息
 */
public class MyNewsActivity extends BaseActivity {

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

    private MyNewsAdapter adapter;
    private int page = 1;
    private ArrayList<DoctorGetmessagelistBean> dataList = new ArrayList<>();

    @Override
    protected String getPagerTitle() {
        return getString(R.string.my_news);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_news;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initSpring();

    }

    @Override
    protected void initData() {
        doctorGetmessagelist();

        adapter = new MyNewsAdapter(TestDataUtil.getTestData());
        recyclerView.setAdapter(adapter);
    }

    /**
     * 设置刷新控件
     */
    private void initSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                doctorGetmessagelist();
            }

            @Override
            public void onLoadmore() {
                page++;
                doctorGetmessagelist();

            }
        });
        spring.setFooter(new DefaultFooter(this));
        spring.setHeader(new DefaultHeader(this));

    }

    /**
     * 获取消息通知列表
     */
    public void doctorGetmessagelist() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorGetmessagelist(page)
                .compose(new SimpleTransFormer<DoctorGetmessagelistBean>(DoctorGetmessagelistBean.class))
                .subscribeWith(new DisposableWrapper<DoctorGetmessagelistBean>() {
                    @Override
                    public void onNext(DoctorGetmessagelistBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
//                        dataList.addAll(o);
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
