package com.flym.yh.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.TextWatcherWrapper;
import com.flym.yh.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客服中心
 */
public class CustomerServiceActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed)
    EditText ed;
    @BindView(R.id.tv)
    TextView tv;

    private String content = "";

    @Override
    protected String getPagerTitle() {
        return getString(R.string.customer_service);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ed.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                content = s.toString();

            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tv)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(content)) {
            doctorBeedback();
        } else {
            ToastUtil.showMessage(this, getString(R.string.please_provide_feedback));
        }

    }

    /**
     * 反馈
     */
    public void doctorBeedback() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorBeedback(content)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {
                        ActivityManager.getInstance().popActivity(CustomerServiceActivity.this);
                    }
                })
        );

    }

}
