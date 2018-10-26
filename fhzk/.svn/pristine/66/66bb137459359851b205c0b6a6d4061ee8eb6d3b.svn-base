package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/4 0004
 */

public class ValidationTBFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.next)
    Button next;

    public static ValidationTBFragment newInstance() {

        Bundle args = new Bundle();

        ValidationTBFragment fragment = new ValidationTBFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("绑定淘宝");
        return toolbar;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_validation;
    }

    @OnClick(R.id.next)
    public void onViewClicked() {


    }
}
