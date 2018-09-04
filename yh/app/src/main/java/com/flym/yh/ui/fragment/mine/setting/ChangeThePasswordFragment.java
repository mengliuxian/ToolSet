package com.flym.yh.ui.fragment.mine.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.TextWatcherWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by mengl on 2018/3/27.
 * 修改密码
 */

public class ChangeThePasswordFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.enter_new_password)
    EditText enterNewPassword;
    @BindView(R.id.enter_password_second)
    EditText enterPasswordSecond;
    @BindView(R.id.save)
    Button save;


    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.change_the_password));
        return toolbar;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TextWatcherWrapper watcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                save.setEnabled(!TextUtils.isEmpty(password.getText().toString()) &&
                        !TextUtils.isEmpty(enterPasswordSecond.getText().toString()) &&
                        !TextUtils.isEmpty(enterNewPassword.getText().toString()) &&
                        password.getText().toString().length() > 5 &&
                        enterPasswordSecond.getText().toString().length() > 5 &&
                        enterNewPassword.getText().toString().length() > 5
                );
            }
        };
        password.addTextChangedListener(watcherWrapper);
        enterPasswordSecond.addTextChangedListener(watcherWrapper);
        enterNewPassword.addTextChangedListener(watcherWrapper);


    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_change_password;
    }

    @OnClick(R.id.save)
    public void onViewClicked() {
        doctorUpdatepass();
    }

    /**
     * 更新医生账户密码
     */
    public void doctorUpdatepass() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdatepass(password.getText().toString(),
                enterNewPassword.getText().toString(), enterPasswordSecond.getText().toString())
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        FragmentUtil.popBackStack(getFragmentManager());
                    }
                }));
    }

}
