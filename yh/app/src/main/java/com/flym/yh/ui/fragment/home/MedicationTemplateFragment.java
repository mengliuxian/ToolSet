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
import com.flym.yh.adapter.home.MedicationTemplateAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.MedicationTemplateBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.spring.DefaultFooter;
import com.flym.yh.ui.view.spring.DefaultHeader;
import com.flym.yh.ui.view.spring.SpringView;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/23
 * 用药模板
 */

public class MedicationTemplateFragment extends BaseFragment {


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

    private List<MedicationTemplateBean.ListBean> list = new ArrayList<>();
    private MedicationTemplateAdapter mAdapter;
    private int type1;

    public static MedicationTemplateFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        MedicationTemplateFragment fragment = new MedicationTemplateFragment();
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
        mAdapter = new MedicationTemplateAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MedicationTemplateBean.ListBean listBean = list.get(position);
                if (type == 0) {
                    if (type1 == 1) {
                        Intent intent = new Intent();
                        intent.putExtra("bean", listBean);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        ActivityManager.getInstance().popActivity(getActivity());
                    } else {
                        FragmentUtil.replaceFragmentToActivity(R.id.container,
                                AddMedicationTemplateFragment.newInstance(listBean),
                                getFragmentManager(), getString(R.string.edit));
                    }
                }
            }
        });
        recycler.addItemDecoration(new RecycleViewDivider(getActivity(), 1, 10, getActivity().getResources().getColor(R.color.windowBackground)));
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
                    mAdapter.notifyDataSetChanged();
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
        toolbarTitle.setText(getTag());
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
                        MedicationTemplateBean.ListBean listBean = list.get(i);
                        listBean.setShow(false);
                    }

                    type = 0;
                    tvAdd.setText(getString(R.string.add_template));
                    toolbarRight.setText(getString(R.string.manage));
                }
                mAdapter.setType(type);
            }
        });
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.frament_medication_template;
    }

    @OnClick(R.id.tv_add)
    public void onClick(View view) {
        if (type == 0) {
            FragmentUtil.replaceFragmentToActivity(R.id.container,
                    AddMedicationTemplateFragment.newInstance(null),
                    getFragmentManager(),
                    getString(R.string.addMed));
        } else {
            if (oldPosition != -1) {
                del();
            }
        }

    }


    /**
     * 获得用药模板列表
     */
    public void getList() {
        RetrofitUtil.getApiService().getMedicationTemplate(page, "")
                .compose(new SimpleTransFormer<MedicationTemplateBean>(Object.class))
                .subscribeWith(new DisposableWrapper<MedicationTemplateBean>() {
                    @Override
                    public void onNext(MedicationTemplateBean bean) {
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
     * 删除用药模板列表
     */
    public void del() {
        RetrofitUtil.getApiService().delMedicationTemplate(list.get(oldPosition).getId())
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
