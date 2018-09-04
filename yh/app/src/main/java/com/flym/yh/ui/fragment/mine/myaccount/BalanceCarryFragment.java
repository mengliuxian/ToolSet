package com.flym.yh.ui.fragment.mine.myaccount;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 余额提现
 * <p>
 * Created by mengl on 2018/3/26.
 */

public class BalanceCarryFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.confirms)
    Button confirms;
    @BindView(R.id.money_layout)
    LinearLayout moneyLayout;
    @BindView(R.id.zfb)
    ImageView zfb;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.r)
    FrameLayout r;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.wx)
    ImageView wx;
    @BindView(R.id.iv_select_dwon)
    ImageView ivSelectDwon;
    @BindView(R.id.layout)
    FrameLayout layout;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.canfim)
    TextView canfim;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.zfb_wx_layout)
    RelativeLayout zfbWxLayout;

    private int changeType = 1; //1：选择支付宝，0：选择微信

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.balance_carry));
        return toolbar;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_balance_carry;
    }

    @OnClick({R.id.title_layout, R.id.confirms, R.id.r, R.id.layout, R.id.canfim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_layout:
                if (moneyLayout.getVisibility() == View.VISIBLE &&
                        zfbWxLayout.getVisibility() == View.GONE) {
                    moneyLayout.setVisibility(View.GONE);
                    zfbWxLayout.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.confirms:
                //提现申请
                if (!TextUtils.isEmpty(money.getText().toString())) {
                    doctorWithdraw();
                } else {
                    ToastUtil.showMessage(context, getString(R.string.enter_money_num));
                }

                break;
            case R.id.r:
                if (ivSelect.getVisibility() == View.GONE) {
                    ivSelect.setVisibility(View.VISIBLE);
                    ivSelectDwon.setVisibility(View.GONE);
                    changeType = 1; //支付宝
                }

                break;
            case R.id.layout:
                if (ivSelectDwon.getVisibility() == View.GONE) {
                    ivSelectDwon.setVisibility(View.VISIBLE);
                    ivSelect.setVisibility(View.GONE);
                    changeType = 0;//微信
                }
                break;
            case R.id.canfim:
                //确认
                moneyLayout.setVisibility(View.VISIBLE);
                zfbWxLayout.setVisibility(View.GONE);
                if (changeType == 1) {
                    titleText.setText(getString(R.string.zfb));
                    logo.setImageResource(R.drawable.wd_wdzh_yetx_txfs_zhifubao);
                } else if (changeType == 0) {
                    titleText.setText(getString(R.string.wx));
                    logo.setImageResource(R.drawable.wd_wdzh_yetx_txfs_weixin);
                }
                break;
        }
    }

    /**
     * 提现余额
     */
    public void doctorWithdraw() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorWithdraw(
                Integer.parseInt(money.getText().toString()), changeType
                )
                        .compose(new SimpleTransFormer<Object>(Object.class))
                        .subscribeWith(new DisposableWrapper<Object>() {
                            @Override
                            public void onNext(Object o) {

                            }
                        })
        );
    }


}
