package com.flym.yh.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.home.HomeActivity;
import com.flym.yh.ui.activity.mine.AuthenticationActivity;
import com.flym.yh.ui.view.ToolbarLayout;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.TextWatcherWrapper;
import com.flym.yh.utils.UserManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.toolbarLayout)
    ToolbarLayout toolbarLayout;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.find_back_the_password)
    TextView findBackThePassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TextWatcherWrapper textWatcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                login.setEnabled(!TextUtils.isEmpty(phone.getText().toString()));
            }
        };
        phone.addTextChangedListener(textWatcherWrapper);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.find_back_the_password, R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.find_back_the_password:
                ActivityManager.getInstance().startNextActivity(FindPassActivity.class);
                break;
            case R.id.login:
                login();
                break;
            case R.id.register:
                ActivityManager.getInstance().startNextActivity(RegisterActivity.class);
                break;
        }
    }


    /**
     * 3.登陆
     */
    public void login() {
        compositeDisposable.add(RetrofitUtil.getApiService().login(phone.getText().toString(),
                password.getText().toString())
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        UserManager.login(LoginActivity.this, simpleString);
                        if (simpleString.auth_status == 0) {
                            {
                                //跳转到认证
                                ActivityManager.getInstance().startNextActivity(AuthenticationActivity.class);
                            }
                        } else if (simpleString.auth_status == 2) {
                            //跳转到首页
                            ActivityManager.getInstance().startNextActivity(HomeActivity.class, true);
                        } else if (simpleString.auth_status == 3) {
                            ActivityManager.getInstance().startNextActivity(AuthenticationStyleActivity.class);
                        } else if (simpleString.auth_status == 1) {
                            ActivityManager.getInstance().startNextActivity(
                                    new Intent(LoginActivity.this, AuthenticationStyleActivity.class)
                                            .putExtra("vis", 0)
                            );

                        }

                    }
                }));
    }
}
