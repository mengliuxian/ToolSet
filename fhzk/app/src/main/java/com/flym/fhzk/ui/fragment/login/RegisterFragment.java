package com.flym.fhzk.ui.fragment.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterFragment extends BaseFragment {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.validation_code)
    EditText validationCode;
    @BindView(R.id.get_check_code)
    Button getCheckCode;

    @BindView(R.id.pass_word)
    EditText passWord;
    @BindView(R.id.pass_word_again)
    EditText passWordAgain;
    @BindView(R.id.next)
    Button next;

    int countDown = 0;

    private CountDownTimer timer;
    private LoadingDialog loadingDialog;
    private String generalizeId = "";


    public static RegisterFragment newInstance(String generalizeId) {

        Bundle args = new Bundle();
        args.putString("generalizeId", generalizeId);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("注册");
        return toolbar;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        generalizeId = getArguments().getString("generalizeId");
        TextWatcherWrapper watcherWrapper = new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                next.setEnabled(
                        !TextUtils.isEmpty(phone.getText().toString()) &&
                        !TextUtils.isEmpty(validationCode.getText().toString()) &&
                        !TextUtils.isEmpty(passWord.getText().toString()) &&
                        !TextUtils.isEmpty(passWordAgain.getText().toString()));
                getCheckCode.setEnabled(countDown == 0 && phone.getText().toString().length() == 11);
            }

        };


        account.addTextChangedListener(watcherWrapper);
        phone.addTextChangedListener(watcherWrapper);
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
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @OnClick({R.id.get_check_code, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_check_code:
                loadingDialog = LoadingDialog.show(context, "");
                compositeDisposable.add(
                        RetrofitUtil.getApiService().getCode(phone.getText().toString(),"reg")
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
                //注册
                compositeDisposable.add(
                        RetrofitUtil.getApiService().reg(
                                phone.getText().toString(),
                                passWord.getText().toString(),
                                validationCode.getText().toString(),
                                account.getText().toString())
                                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                                .subscribeWith(new DisposableWrapper<SimpleString>(loadingDialog) {
                                    @Override
                                    public void onNext(SimpleString registerBean) {
                                        FragmentUtil.popBackStack(getFragmentManager());
                                    }
                                })
                );
                break;

        }
    }

}
