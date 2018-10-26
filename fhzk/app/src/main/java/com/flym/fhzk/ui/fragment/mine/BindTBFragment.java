package com.flym.fhzk.ui.fragment.mine;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.base.TextWatcherWrapper;
import com.flym.fhzk.utils.FragmentUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/4 0004
 */

public class BindTBFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.terms)
    TextView terms;

    public static BindTBFragment newInstance() {

        Bundle args = new Bundle();

        BindTBFragment fragment = new BindTBFragment();
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
        TextWatcherWrapper textWatcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                next.setEnabled(!TextUtils.isEmpty(phone.getText().toString()));
            }
        };

        phone.addTextChangedListener(textWatcherWrapper);
        //下划线
        terms.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_bind_tb;
    }

    @OnClick({R.id.next, R.id.terms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.next:
                FragmentUtil.replaceFragmentToActivity(R.id.container, ValidationTBFragment.newInstance(), getFragmentManager(), "绑定淘宝");
                break;
            case R.id.terms:
                break;
        }
    }
}
