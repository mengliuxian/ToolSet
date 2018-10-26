package com.flym.fhzk.ui.fragment.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.base.TextWatcherWrapper;
import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.utils.FragmentUtil;
import com.flym.fhzk.utils.ToastUtil;
import com.flym.fhzk.view.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class ForgetFragment extends BaseFragment {


    int countDown = 0;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.validation_code)
    EditText validationCode;
    @BindView(R.id.get_check_code)
    Button getCheckCode;
    @BindView(R.id.check_code_layout)
    LinearLayout checkCodeLayout;
    @BindView(R.id.pass_word)
    EditText passWord;
    @BindView(R.id.pass_word_again)
    EditText passWordAgain;
    @BindView(R.id.next)
    Button next;


    private CountDownTimer timer;
    private LoadingDialog loadingDialog;


    public static ForgetFragment newInstance() {

        Bundle args = new Bundle();

        ForgetFragment fragment = new ForgetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("忘记密码");
        return toolbar;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        TextWatcherWrapper watcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                next.setEnabled(!TextUtils.isEmpty(account.getText().toString()) &&
                        !TextUtils.isEmpty(validationCode.getText().toString()) &&
                        !TextUtils.isEmpty(passWord.getText().toString()) &&
                        !TextUtils.isEmpty(passWordAgain.getText().toString()));
                getCheckCode.setEnabled(countDown == 0 && !TextUtils.isEmpty(account.getText().toString()));
            }

        };


        account.addTextChangedListener(watcherWrapper);

        validationCode.addTextChangedListener(watcherWrapper);

        passWord.addTextChangedListener(watcherWrapper);
        passWordAgain.addTextChangedListener(watcherWrapper);


    }

    //获取验证码倒计时
    private void timerStart() {
        countDown = 1;
        if (timer == null) {
            timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (getActivity() != null) {
                        getCheckCode.setEnabled(false);
                        getCheckCode.setText(String.format(getString(R.string.resend_code_tip),
                                String.valueOf(millisUntilFinished / 1000)));
                    } else {
                        cancel();
                    }
                }

                @Override
                public void onFinish() {
                    countDown = 0;
                    getCheckCode.setText("获取验证码");
                    getCheckCode.setEnabled(true);
                }
            };
        }
        timer.start();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_forger;
    }

    @OnClick({R.id.get_check_code, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_check_code:
                //1. 获取验证码
                compositeDisposable.add(
                        RetrofitUtil.getApiService().getCode(account.getText().toString(),"pwd")
                                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                                .subscribeWith(new DisposableWrapper<SimpleString>(loadingDialog) {
                                    @Override
                                    public void onNext(SimpleString simpleString) {
                                        validationCode.setText(simpleString.string);
                                        timerStart();

                                    }
                                })
                );
                break;
            case R.id.next:
                if (!passWord.getText().toString().equals(passWordAgain.getText().toString())) {
                    ToastUtil.showMessage(context, "两次密码不一致");
                    return;
                }
                loadingDialog = LoadingDialog.show(context, "");
                compositeDisposable.add(
                        RetrofitUtil.getApiService().setpwd(
                                account.getText().toString(),
                                validationCode.getText().toString(),
                                passWord.getText().toString())
                                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                                .subscribeWith(new DisposableWrapper<SimpleString>(loadingDialog) {
                                    @Override
                                    public void onNext(SimpleString simpleString) {
                                        FragmentUtil.replaceFragmentToActivity(R.id.container, LoginFragment.newInstance(), getFragmentManager());
                                    }
                                })
                );
                break;

        }
    }


    @Override
    public void onFix() {

    }

    @Override
    public void onReset() {

    }
}
