package com.flym.yh.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.mine.DepartmentClassifyAdapter;
import com.flym.yh.adapter.mine.DetailDepartmentClassifyAdapter;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.data.model.GetDepartmenglistBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.ActivityManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 选择科室
 */
public class DepartmentActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.classify)
    RecyclerView classify; // 左侧recyclerview
    @BindView(R.id.detail_classify)
    RecyclerView detailClassify; // 右侧recyclerview

    private DepartmentClassifyAdapter classifyAdapter; //科室父adapter
    private DetailDepartmentClassifyAdapter departmentClassifyAdapter; // 科室子adapter

    private int oldPosition = 0; //记录上一次点击的item下标
    private int oldChildPosition = 0; //记录上一次点击子数据的item下标

    ArrayList<GetDepartmenglistBean.ListBean> dataList = new ArrayList<>(); //科室父数据
    ArrayList<GetDepartmenglistBean.ListBean.ChildBean> detailDataList = new ArrayList<>(); //科室子数据

    @Override
    protected String getPagerTitle() {
        return getString(R.string.change_department_ac);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_department;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        classify.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        detailClassify.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void initData() {
        getDepartmentlist();

        classifyAdapter = new DepartmentClassifyAdapter(dataList);
        departmentClassifyAdapter = new DetailDepartmentClassifyAdapter(detailDataList);

        classify.setAdapter(classifyAdapter);
        detailClassify.setAdapter(departmentClassifyAdapter);

        //父类点击回调
        classifyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (oldPosition == position) {
                    return;
                }
                GetDepartmenglistBean.ListBean listBean = dataList.get(position);
                GetDepartmenglistBean.ListBean oldListBean = dataList.get(oldPosition);

                switch (view.getId()) {
                    case R.id.layout:
                        detailDataList.clear();
                        if (listBean._child != null) {
                            detailDataList.addAll(listBean._child);
                        }
                        departmentClassifyAdapter.notifyDataSetChanged();
                        listBean.selecter = 1;
                        oldListBean.selecter = 0;
                        break;
                }
                classifyAdapter.notifyDataSetChanged();
                oldPosition = position;
            }
        });
        //子类点击回调
        departmentClassifyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GetDepartmenglistBean.ListBean.ChildBean childBean = detailDataList.get(position);
                switch (view.getId()) {
                    case R.id.layout:
                        childBean.selecter = 1;

                        break;
                }
                departmentClassifyAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("ChildBean", childBean);
                setResult(RESULT_OK, intent);
                ActivityManager.getInstance().popActivity(DepartmentActivity.this);
            }
        });


    }

    /**
     * 6.获取科室列表
     */
    public void getDepartmentlist() {
        compositeDisposable.add(RetrofitUtil.getApiService().getDepartmentlist(null)
                .compose(new SimpleTransFormer<GetDepartmenglistBean>(GetDepartmenglistBean.class))
                .subscribeWith(new DisposableWrapper<GetDepartmenglistBean>() {
                    @Override
                    public void onNext(GetDepartmenglistBean getDepartmenglistBean) {
                        dataList.clear();
                        if (getDepartmenglistBean.list != null) {
                            dataList.addAll(getDepartmenglistBean.list);
                        }
                        if (dataList.size() > 0) {
                            GetDepartmenglistBean.ListBean listBean = dataList.get(0);
                            listBean.selecter = 1;
                            detailDataList.addAll(listBean._child);
                        }

                        classifyAdapter.notifyDataSetChanged();
                        departmentClassifyAdapter.notifyDataSetChanged();
                    }
                }));
    }


}
