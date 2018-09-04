package com.flym.yh.ui.fragment.mine.myaccount;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.mine.IncomeDetailsAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetAccountBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CustomDatePicker;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收入明细
 * <p>
 * Created by mengl on 2018/3/26.
 */

public class IncomeDetailsFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.star_time)
    TextView starTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.spring)
    SpringView spring;

    private IncomeDetailsAdapter adapter;
    private ArrayList<DoctorGetAccountBean.ListBean> dataList = new ArrayList<>();
    private int page = 1;


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.income_details));
        return toolbar;
    }

    @Override
    protected void initData() {
        adapter = new IncomeDetailsAdapter(dataList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.
                VERTICAL, false));

        initSpring();
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
            }

            @Override
            public void onLoadmore() {
                page++;
                doctorGetaccountlog();
            }
        });
        spring.setFooter(new DefaultFooter(getActivity()));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frament_income_details;
    }

    @OnClick({R.id.star_time, R.id.end_time, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.star_time:
                String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                final CustomDatePicker customDatePicker1 = new CustomDatePicker(
                        context, new CustomDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) { // 回调接口，获得选中的时间
                        starTime.setText(time);
                    }
                }, now, now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

                customDatePicker1.showSpecificTime(false); // 不显示时和分
                customDatePicker1.showSecondTime(false);
                customDatePicker1.setIsLoop(false); // 不允许循环滚动
                customDatePicker1.show(now);
                break;
            case R.id.end_time:
                if (!TextUtils.isEmpty(starTime.getText().toString())) {
                    String now1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    final CustomDatePicker customDatePicker2 = new CustomDatePicker(
                            context, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                            endTime.setText(time);
                        }
                    }, now1, now1); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

                    customDatePicker2.showSpecificTime(false); // 不显示时和分
                    customDatePicker2.showSecondTime(false);
                    customDatePicker2.setIsLoop(false); // 不允许循环滚动
                    customDatePicker2.show(now1);
                } else {
                    ToastUtil.showMessage(context, "选择开始日期");
                }

                break;
            case R.id.search:
                if (!TextUtils.isEmpty(starTime.getText().toString()) && !TextUtils.isEmpty(endTime.getText().toString())) {
                    page = 1;
                    doctorGetaccountlog();
                } else {
                    ToastUtil.showMessage(context, "选择开始/结束日期");
                }
                break;
        }
    }

    /**
     * 获取我的收入明细
     */
    public void doctorGetaccountlog() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorGetaccountlog(page,
                starTime.getText().toString(),
                endTime.getText().toString())
                .compose(new SimpleTransFormer<DoctorGetAccountBean>(DoctorGetAccountBean.class))
                .subscribeWith(new DisposableWrapper<DoctorGetAccountBean>() {
                    @Override
                    public void onNext(DoctorGetAccountBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }

                        dataList.addAll(o.list);
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
