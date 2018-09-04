package com.flym.yh.ui.fragment.mine.myaccount;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CustomMineItemView;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.UserManager;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mengl on 2018/3/26.
 * 我的账户
 */

public class MyAccountFragment extends BaseFragment {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.balance)
    CustomMineItemView balance;
    @BindView(R.id.can_carry)
    CustomMineItemView canCarry;
    @BindView(R.id.balance_carry)
    CustomMineItemView balanceCarry;
    @BindView(R.id.income_details)
    CustomMineItemView incomeDetails;

    private DoctorGetdetailBean data;

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.my_account));
        return toolbar;
    }

    @Override
    protected void initData() {
        if (data != null) {
            if (data.info != null) {
                balance.setRightText("￥" + data.info.account);
                canCarry.setRightText("￥" + new DecimalFormat("0.00")
                        .format((Float.parseFloat(data.info.account)
                                - Float.parseFloat(data.info.freeze))));
            }
        } else {
            doctorGetdetail();
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        data = UserManager.getUserDataFromNative(context);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_account;
    }


    @OnClick({R.id.balance_carry, R.id.income_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.balance_carry:
                FragmentUtil.replaceFragmentToActivity(R.id.container, new BalanceCarryFragment(), getFragmentManager(), getString(R.string.my_account));
                break;
            case R.id.income_details:
                FragmentUtil.replaceFragmentToActivity(R.id.container, new IncomeDetailsFragment(), getFragmentManager(), getString(R.string.my_account));
                break;
        }
    }

    /**
     * 获取登录医生详情
     */
    public void doctorGetdetail() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorGetdetail("")
                .compose(new SimpleTransFormer<DoctorGetdetailBean>(DoctorGetdetailBean.class))
                .subscribeWith(new DisposableWrapper<DoctorGetdetailBean>() {
                    @Override
                    public void onNext(DoctorGetdetailBean doctorGetdetailBean) {
                        UserManager.saveUserDataToNative(context, doctorGetdetailBean);
                        initData();
                    }
                }));
    }

}
