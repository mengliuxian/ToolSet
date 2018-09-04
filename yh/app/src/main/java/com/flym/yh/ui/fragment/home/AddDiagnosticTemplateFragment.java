package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 添加诊断模板  编辑诊断模板
 */

public class AddDiagnosticTemplateFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed)
    EditText ed;
    @BindView(R.id.tv)
    TextView tv;

    private int id = 0;

    public static AddDiagnosticTemplateFragment newInstance(String diseaseName, int id) {

        Bundle args = new Bundle();
        args.putString("diseaseName", diseaseName);
        args.putInt("id", id);
        AddDiagnosticTemplateFragment fragment = new AddDiagnosticTemplateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (getArguments().getInt("id", 0) != 0) {
            id = getArguments().getInt("id", 0);
            ed.setText(getArguments().getString("diseaseName"));
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getTag());
        if (getTag().equals(getString(R.string.new_))) {
            tv.setText(getString(R.string.save));
        } else {
            tv.setText(getString(R.string.complete));
            ed.setText(getArguments().getString("diseaseName"));
        }
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_add_diagnostic_template;
    }

    @OnClick(R.id.tv)
    public void onViewClicked() {
        if (!ed.getText().toString().equals("")) {
            getList();
        }
    }

    /**
     * 新增诊断模板
     */
    public void getList() {
        RetrofitUtil.getApiService().saveDiagnosticTemplate(ed.getText().toString(), id)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object simpleString) {
                        FragmentUtil.popBackStack(getFragmentManager());
                    }
                });
    }


}
