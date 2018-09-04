package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.TelephoneCconsultationAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 电话咨询
 */

public class TelephoneCconsultationFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.spring)
    SpringView spring;

    private int page = 1;
    private List<GetServicesBean.ListBean> dataList = new ArrayList<>();
    private TelephoneCconsultationAdapter adapter;

    public static TelephoneCconsultationFragment newInstance() {

        Bundle args = new Bundle();

        TelephoneCconsultationFragment fragment = new TelephoneCconsultationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        getListOld();
        setAdapter();
        setSpring();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        adapter = new TelephoneCconsultationAdapter(dataList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new RecycleViewDivider(getActivity(), 1));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GetServicesBean.ListBean listBean = dataList.get(position);

                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        ReproductiveCallDetailsFragment.newInstance(listBean),
                        getFragmentManager(), getString(R.string.reproductive_call));
            }
        });
    }

    /**
     * 设置刷新控件
     */
    private void setSpring() {
        spring.setType(SpringView.Type.FOLLOW);
        spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //spring.onFinishFreshAndLoad();  完成
                page = 1;
                getListOld();
            }

            @Override
            public void onLoadmore() {
                page++;
                getListOld();
            }
        });
        spring.setFooter(new DefaultFooter(getActivity()));
        spring.setHeader(new DefaultHeader(getActivity()));
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
        return R.layout.fragment_telephone_consultation;
    }

    /**
     * 获取服务列表
     */
    public void getListOld() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .doctorGetorderlist(1, 3)
                .compose(new SimpleTransFormer<GetServicesBean>(Object.class))
                .subscribeWith(new DisposableWrapper<GetServicesBean>() {
                    @Override
                    public void onNext(GetServicesBean o) {
                        if (page == 1) {
                            dataList.clear();
                        }
                        if (o.getList() != null) {
                            dataList.addAll(o.getList());
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                }));
    }


    /**
     * 联系患者
     */
    public void doctorService(int order_id) {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorService(order_id)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {

                    }
                })
        );

    }

}
