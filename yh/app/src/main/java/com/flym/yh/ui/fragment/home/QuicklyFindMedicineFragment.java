package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.QuicklyFindMedicineLeftAdapter;
import com.flym.yh.adapter.home.QuicklyFindMedicineRightAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.QuicklyFindMedicineBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 快速找药
 */

public class QuicklyFindMedicineFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.recycler_left)
    RecyclerView recyclerLeft;
    @BindView(R.id.recycler_right)
    RecyclerView recyclerRight;

    private int oldPosition = -1;

    private List<QuicklyFindMedicineBean.ListBean> listLeft = new ArrayList<>();
    private List<QuicklyFindMedicineBean.ListBean.ChildBean> listRight = new ArrayList<>();
    private QuicklyFindMedicineLeftAdapter mAdapterLeft;
    private QuicklyFindMedicineRightAdapter mAdapterRight;

    public static QuicklyFindMedicineFragment newInstance() {

        Bundle args = new Bundle();
        //这个判断进来的类型  0 代表没有导出 和没有选择圈

        QuicklyFindMedicineFragment fragment = new QuicklyFindMedicineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getList();
    }

    @Override
    protected void initData() {
        if (listLeft.size() != 0) {
            listLeft.clear();
        }
        if (listRight.size() != 0) {
            listRight.clear();
        }
//        for (int i = 0; i < 30; i++) {
//            listLeft.add(i + "");
//            listRight.add(i + "");
//        }
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapterLeft = new QuicklyFindMedicineLeftAdapter(listLeft);
        recyclerLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerLeft.setAdapter(mAdapterLeft);
        recyclerLeft.addItemDecoration(new RecycleViewDivider(getActivity(), 1));
        mAdapterLeft.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (oldPosition != -1) {
                    listLeft.get(oldPosition).setShow(false);
                }
                listLeft.get(position).setShow(true);
                mAdapterLeft.notifyDataSetChanged();
                listRight.clear();
                listRight.addAll(listLeft.get(position).get_child());
                mAdapterRight.notifyDataSetChanged();
                oldPosition = position;
            }
        });

        mAdapterRight = new QuicklyFindMedicineRightAdapter(listRight);
        recyclerRight.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerRight.setAdapter(mAdapterRight);

        mAdapterRight.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toDrugList(listRight.get(position).getId(), "");
            }
        });
    }


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_quickly_find_medicine;
    }


    @OnClick({R.id.tv_search, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                if (!edContent.getText().toString().equals("")) {
                    toDrugList(0, edContent.getText().toString().trim());
                }
                break;
            case R.id.iv_clear:
                edContent.setText("");
                break;
            default:
                break;
        }

    }

    /**
     * 跳转到搜索  药物列表  药物目录
     */
    public void toDrugList(int id, String name) {
        FragmentUtil.replaceFragmentToActivity(R.id.container,
                DrugDirectoryFragment.newInstance(1, id, name),
                getFragmentManager(), getString(R.string.drug_liebiao));
    }


    /**
     * 获取药品目录
     */
    public void getList() {
        RetrofitUtil.getApiService().getDrugCatelist(0)
                .compose(new SimpleTransFormer<QuicklyFindMedicineBean>(Object.class))
                .subscribeWith(new DisposableWrapper<QuicklyFindMedicineBean>() {
                    @Override
                    public void onNext(QuicklyFindMedicineBean bean) {
                        listLeft.addAll(bean.getList());
                        listLeft.get(0).setShow(true);
                        oldPosition = 0;
                        mAdapterLeft.notifyDataSetChanged();
                        listRight.addAll(listLeft.get(0).get_child());
                        mAdapterRight.notifyDataSetChanged();
                    }
                });
    }
}
