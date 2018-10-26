package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.base.TextWatcherWrapper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/6 0006
 */

public class WithdrawalFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.zfb)
    TextView zfb;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.can_money)
    TextView canMoney;
    @BindView(R.id.next)
    TextView next;

    public static WithdrawalFragment newInstance() {

        Bundle args = new Bundle();

        WithdrawalFragment fragment = new WithdrawalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("提现");
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

                next.setEnabled(!TextUtils.isEmpty(money.getText().toString()));
            }
        };

        money.addTextChangedListener(textWatcherWrapper);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_ithdrawal;
    }

    @OnClick({R.id.zfb, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zfb:
                break;
            case R.id.next:
                break;
        }
    }
}
