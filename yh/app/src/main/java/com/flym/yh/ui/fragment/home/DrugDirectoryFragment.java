package com.flym.yh.ui.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.DrugDirectoryAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DrugDirectoryBean;
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
 * 药物目录 药物列表
 */

public class DrugDirectoryFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.spring)
    SpringView spring;
    @BindView(R.id.tv_input)
    TextView tvInput;
    @BindView(R.id.tv_search_bottom)
    TextView tvSearchBottom;

    private int page = 1;
    private String name;
    private int cate_id;

    private List<DrugDirectoryBean.ListBean> list = new ArrayList<>();
    private ArrayList<DrugDirectoryBean.ListBean> selecterList = new ArrayList<>();
    private DrugDirectoryAdapter mAdapter;

    public static DrugDirectoryFragment newInstance(int type, int id, String name) {

        Bundle args = new Bundle();
        //这个判断进来的类型  0 代表没有导出 和没有选择圈
        args.putInt("type", type);
        //这个传id  ""
        args.putInt("id", id);
        //这个是模糊搜索 name
        args.putString("name", name);
        DrugDirectoryFragment fragment = new DrugDirectoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (getArguments().getInt("type") == 1) {
            tvInput.setVisibility(View.GONE);
            tvSearchBottom.setText(getString(R.string.input));
        }
        name = getArguments().getString("name");
        if (!name.isEmpty()) {
            edContent.setText(name);
        }
        cate_id = getArguments().getInt("id");
        getList();
    }

    @Override
    protected void initData() {
        if (list.size() != 0) {
            list.clear();
            page = 1;
        }
        setAdapter();
        setSpring();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new DrugDirectoryAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DrugDirectoryBean.ListBean listBean = list.get(position);
                if (listBean.isShow()) {
                    listBean.setShow(false);
                    selecterList.remove(listBean);
                } else {
                    listBean.setShow(true);
                    selecterList.add(listBean);
                }
                mAdapter.notifyItemChanged(position);

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
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_drug_directory;
    }

    @OnClick({R.id.tv_search, R.id.tv_input, R.id.tv_search_bottom, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                if (!TextUtils.isEmpty(edContent.getText().toString())) {
                    name = edContent.getText().toString().trim();
                    page = 1;
                    list.clear();
                    getList();
                }
                break;
            case R.id.tv_input:
                if (selecterList.size() > 0) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("list", selecterList);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                }
                ActivityManager.getInstance().popActivity(getActivity());
                break;
            case R.id.tv_search_bottom:
                if (getArguments().getInt("type") == 1) {
                    if (selecterList.size() > 0) {
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("list", selecterList);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                    }
                    ActivityManager.getInstance().popActivity(getActivity());
                    return;
                }
                FragmentUtil.popBackStack(getFragmentManager(), getString(R.string.quickly_find_medicine));
                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        QuicklyFindMedicineFragment.newInstance(),
                        getFragmentManager(),
                        getString(R.string.quickly_find_medicine));
                break;
            case R.id.iv_clear:
                edContent.setText("");
                break;
            default:
                break;
        }
    }



    /**
     * 获取药物列表
     */
    public void getList() {
        RetrofitUtil.getApiService().getDrugDirectory(name, page, cate_id)
                .compose(new SimpleTransFormer<DrugDirectoryBean>(Object.class))
                .subscribeWith(new DisposableWrapper<DrugDirectoryBean>() {
                    @Override
                    public void onNext(DrugDirectoryBean bean) {
                        if (page == 1) {
                            list.clear();
                        }
                        list.addAll(bean.getList());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        selecterList.clear();
                        spring.onFinishFreshAndLoad();
                    }
                });
    }
}
