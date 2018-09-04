package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.adapter.home.HealthyPrescriptionDetailAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.GoodsGetlistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author lishuqi
 * @date 2018/3/26
 * 处方详情
 */

public class HealthyPrescriptionDetailsFragment extends BaseFragment {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_disease_name)
    TextView tvDiseaseName;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_note)
    TextView tvNote;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_del)
    TextView tvDel;
    Unbinder unbinder;

    private List<GoodsGetlistBean.ListBean> dataList = new ArrayList<>();
    private HealthyPrescriptionDetailAdapter mAdapter;

    public static HealthyPrescriptionDetailsFragment newInstance() {

        Bundle args = new Bundle();

        HealthyPrescriptionDetailsFragment fragment = new HealthyPrescriptionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (dataList.size() != 0) {
            dataList.clear();
        }
        setAdapter();
        setData();
    }

    /**
     * 设置数据
     */
    private void setData() {
//        if (status == 1) {
//            tvReturn.setText(getString(R.string.return_prescription));
//        } else if (status == 0) {
//            tvReturn.setText("修改");
//        } else if (status == 2) {
//            tvReturn.setVisibility(View.GONE);
//        }
        tvPatientName.setText("");
        tvSex.setText("");
        tvAge.setText("");
        tvDiseaseName.setText("");
        tvNote.setText("");
        tvTime.setText("");
//        dataList.addAll();
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 设置适配器
     */
    private void setAdapter() {

        mAdapter = new HealthyPrescriptionDetailAdapter(dataList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
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
        return R.layout.fragment_healthy_prescription_details;
    }

    @OnClick({R.id.tv_return, R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_return:
//                if (status == 1) {
//                    tvReturn.setText(getString(R.string.return_prescription));
//                } else if (status == 0) {
//                    tvReturn.setText("修改");
//                    OpenHealthyPrescriptionFragment openHealthyPrescriptionFragment =
//                            OpenHealthyPrescriptionFragment.newInstance();
//                    openHealthyPrescriptionFragment.setLs(this);
//                    FragmentUtil.replaceFragmentToActivity(R.id.container,
//                            openHealthyPrescriptionFragment
//                            , getFragmentManager(),
//                            "健康处方");
//                    FragmentUtil.
//                } else if (status == 2) {
//                    tvReturn.setVisibility(View.GONE);
//                }
                break;
            case R.id.tv_del:
                break;
            default:
                break;
        }
    }

    /**
     * 撤回健康处方
     */
    public void recipeRecall() {
        compositeDisposable.add(RetrofitUtil.getApiService().recipeRecall(0)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {

                    }
                }));
    }


    public static interface OnReult {
        void onR(int i);
    }

}
