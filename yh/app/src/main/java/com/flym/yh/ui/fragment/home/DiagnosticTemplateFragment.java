package com.flym.yh.ui.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.DiagnosticTemplateAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.SaveDiagnosticTemplateBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.FragmentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 诊断模板
 */

public class DiagnosticTemplateFragment extends BaseFragment {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.spring)
    SpringView spring;
    @BindView(R.id.tv_add)
    TextView tvAdd;

    private int page = 1;
    /**
     * 控制删除  记录删除的position
     */
    private int type = 0;
    private int oldPosition = -1;

    private List<SaveDiagnosticTemplateBean.ListBean> list = new ArrayList<>();
    private DiagnosticTemplateAdapter mAdapter;
    private int type1; //判断是否是聊天跳转过来 1：是

    public static DiagnosticTemplateFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        DiagnosticTemplateFragment fragment = new DiagnosticTemplateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        getList();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        type1 = getArguments().getInt("type");

        if (list.size() != 0) {
            list.clear();
        }
        setAdapter();
        setSpring();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new DiagnosticTemplateAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (type != 0) {
                    if (oldPosition != -1) {
                        if (position == oldPosition) {
                            return;
                        }
                        list.get(oldPosition).setShow(false);
                        list.get(position).setShow(true);
                    } else {
                        //第一次修改
                        list.get(position).setShow(true);
                    }
                    oldPosition = position;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SaveDiagnosticTemplateBean.ListBean listBean = list.get(position);
                if (type == 0) {
                    if (type1 == 1) {
                        Intent intent = new Intent();
                        intent.putExtra("bean", listBean.getName());
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        ActivityManager.getInstance().popActivity(getActivity());
                    } else {
                        FragmentUtil.replaceFragmentToActivity(R.id.container, AddDiagnosticTemplateFragment.newInstance(list.get(position).getName(), list.get(position).getId()), getFragmentManager(), getString(R.string.edit));

                    }

                }
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
                getList();
            }

            @Override
            public void onLoadmore() {
                page++;
                getList();
            }
        });
        spring.setFooter(new DefaultFooter(getActivity()));
        spring.setHeader(new DefaultHeader(getActivity()));
    }


    @Override
    public Toolbar getToolbar() {
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.manage));
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 0 && list.size() != 0) {
                    type = 1;
                    tvAdd.setText(getString(R.string.del));
                    toolbarRight.setText(getString(R.string.cancel));
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        SaveDiagnosticTemplateBean.ListBean listBean = list.get(i);
                        listBean.setShow(false);
                    }
                    type = 0;
                    tvAdd.setText(getString(R.string.add_template));
                    toolbarRight.setText(getString(R.string.manage));
                }
                mAdapter.setType(type);
            }
        });
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frament_diagnostic_template;
    }

    @OnClick(R.id.tv_add)
    public void onClick(View view) {
        if (type == 0) {
            FragmentUtil.replaceFragmentToActivity(R.id.container, AddDiagnosticTemplateFragment.newInstance("", 0), getFragmentManager(), getString(R.string.new_));
        } else {
            if (oldPosition != -1) {
                del();
            }
        }
    }

    /**
     * 获得诊断模板列表
     */
    public void getList() {
        RetrofitUtil.getApiService().getDiagnosticTemplate(page, "")
                .compose(new SimpleTransFormer<SaveDiagnosticTemplateBean>(Object.class))
                .subscribeWith(new DisposableWrapper<SaveDiagnosticTemplateBean>() {
                    @Override
                    public void onNext(SaveDiagnosticTemplateBean bean) {
                        if (page == 1) {
                            list.clear();
                        }
                        list.addAll(bean.getList());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        spring.onFinishFreshAndLoad();
                    }
                });
    }

    /**
     * 删除诊断模板
     */
    public void del() {
        RetrofitUtil.getApiService().delDiagnosticTemplate(list.get(oldPosition).getId())
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object bean) {
                        list.remove(oldPosition);
                        oldPosition = -1;
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
