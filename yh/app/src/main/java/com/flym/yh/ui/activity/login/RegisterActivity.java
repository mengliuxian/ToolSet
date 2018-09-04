package com.flym.yh.ui.activity.login;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CustomDialog;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.TextWatcherWrapper;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.code_layout)
    LinearLayout codeLayout;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_second)
    EditText passwordSecond;
    @BindView(R.id.recommended_code)
    EditText recommendedCode;
    @BindView(R.id.register)
    Button register;


    private int down = 0;
    private CountDownTimer timer;
    private Dialog dialog;


    @Override
    protected String getPagerTitle() {
        return getString(R.string.register);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TextWatcherWrapper textWatcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                register.setEnabled(!TextUtils.isEmpty(phone.getText().toString()) &&
                        !TextUtils.isEmpty(code.getText().toString()) &&
                        !TextUtils.isEmpty(password.getText().toString()) &&
                        password.getText().toString().length() > 5 &&
                        !TextUtils.isEmpty(passwordSecond.getText().toString()) &&
                        passwordSecond.getText().toString().length() > 5);
                getCode.setEnabled(down == 0
                        && !TextUtils.isEmpty(phone.getText().toString()));
            }
        };
        phone.addTextChangedListener(textWatcherWrapper);
        code.addTextChangedListener(textWatcherWrapper);
        password.addTextChangedListener(textWatcherWrapper);
        passwordSecond.addTextChangedListener(textWatcherWrapper);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.get_code, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                getCode();
                getCodeHttp();
                break;
            case R.id.register:
                resgiter();
                break;
            default:
                break;
        }
    }

    /**
     * 倒计时
     */
    public void getCode() {
        down = 1;
        if (timer == null) {
            timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    getCode.setEnabled(false);
                    getCode.setText(String.format(getString(R.string.resend_code_tip),
                            String.valueOf(millisUntilFinished / 1000)));
                }

                @Override
                public void onFinish() {
                    down = 0;
                    getCode.setText(getString(R.string.get_code));
                    getCode.setEnabled(true);
                }
            };
        }
        timer.start();
    }

    /**
     * 2.注册
     */
    public void resgiter() {
        compositeDisposable.add(RetrofitUtil.getApiService().register(phone.getText().toString(), password.getText().toString(),
                passwordSecond.getText().toString(), code.getText().toString(), recommendedCode.getText().toString())
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        dialog = CustomDialog.registerSuccess(RegisterActivity.this, null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                ActivityManager.getInstance().popActivity(RegisterActivity.this);
                            }
                        });
                    }
                }));
    }

    /**
     * 1.获取验证码
     */
    public void getCodeHttp() {
        String s = phone.getText().toString();
        compositeDisposable.add(RetrofitUtil.getApiService().getCode(s,"register")
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {

                    }
                }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        dialog.dismiss();
    }
}
