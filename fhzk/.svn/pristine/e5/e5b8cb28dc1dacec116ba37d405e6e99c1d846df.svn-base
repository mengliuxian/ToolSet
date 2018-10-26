package com.flym.fhzk.ui.fragment.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.base.TextWatcherWrapper;
import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.data.model.User;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.home.HomeActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.FragmentUtil;
import com.flym.fhzk.utils.UserManager;
import com.flym.fhzk.view.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class LoginFragment extends BaseFragment {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_logo)
    ImageView loginLogo;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.pass_word)
    EditText passWord;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.forget)
    TextView forget;


    private LoadingDialog loadingDialog;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("登录");
        return toolbar;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TextWatcherWrapper watcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                next.setEnabled(!TextUtils.isEmpty(account.getText().toString()) &&
                        !TextUtils.isEmpty(passWord.getText().toString()));
            }
        };

        account.addTextChangedListener(watcherWrapper);
        passWord.addTextChangedListener(watcherWrapper);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @OnClick({R.id.next, R.id.register, R.id.forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.next:
                loadingDialog = LoadingDialog.show(context, "");
                compositeDisposable.add(
                        RetrofitUtil.getApiService().login(account.getText().toString(),
                                passWord.getText().toString(), "android")
                                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                                .subscribeWith(new DisposableWrapper<SimpleString>(loadingDialog) {
                                    @Override
                                    public void onNext(SimpleString simpleString) {
                                        if (simpleString != null) {
                                            User user = new User(account.getText().toString(),passWord.getText().toString(), simpleString.string);
                                            UserManager.login(context, user);
                                        }
                                        ActivityManager.getInstance().startNextActivity(HomeActivity.class, true);
                                    }

                                })
                );
                break;
            case R.id.register:
                FragmentUtil.replaceFragmentToActivity(R.id.container, RegisterFragment.newInstance(""), getFragmentManager(), "注册");
                break;
            case R.id.forget:
                FragmentUtil.replaceFragmentToActivity(R.id.container, ForgetFragment.newInstance(), getFragmentManager(), "忘记密码");
                break;
        }

    }


}
