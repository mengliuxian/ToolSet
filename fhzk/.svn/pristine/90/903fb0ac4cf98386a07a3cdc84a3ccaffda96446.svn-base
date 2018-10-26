package com.flym.fhzk.ui.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.mine.CollectActivity;
import com.flym.fhzk.ui.activity.mine.FootPrintActivity;
import com.flym.fhzk.ui.activity.mine.HelpActivity;
import com.flym.fhzk.ui.activity.mine.InviteActivity;
import com.flym.fhzk.ui.activity.mine.MyOrderActivity;
import com.flym.fhzk.ui.activity.mine.NewsActivity;
import com.flym.fhzk.ui.activity.mine.RebatesActivity;
import com.flym.fhzk.ui.activity.mine.SettingActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.GlideUtil;
import com.flym.fhzk.utils.StatusBarUtil;
import com.flym.fhzk.view.CircleImageView;
import com.flym.fhzk.view.CustomMineItemView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2017/12/1 0001
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.logo)
    CircleImageView logo;
    @BindView(R.id.news)
    CircleImageView news;
    @BindView(R.id.login_text)
    TextView loginText;
    @BindView(R.id.collect)
    TextView collect;
    @BindView(R.id.return_money)
    TextView returnMoney;
    @BindView(R.id.taobao)
    TextView taobao;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.my_order)
    CustomMineItemView myOrder;
    @BindView(R.id.all_order)
    TextView allOrder;
    @BindView(R.id.about_money)
    TextView aboutMoney;
    @BindView(R.id.get_money)
    TextView getMoney;
    @BindView(R.id.error_order)
    TextView errorOrder;
    @BindView(R.id.two_layout)
    LinearLayout twoLayout;
    @BindView(R.id.friend)
    CustomMineItemView friend;
    @BindView(R.id.sign_in)
    CustomMineItemView signIn;
    @BindView(R.id.foot)
    CustomMineItemView foot;
    @BindView(R.id.get_coupon)
    CustomMineItemView getCoupon;
    @BindView(R.id.setting)
    CustomMineItemView setting;
    @BindView(R.id.help)
    CustomMineItemView help;
    @BindView(R.id.point)
    CircleImageView point;
    @BindView(R.id.root_view)
    RelativeLayout rootView;

    GestureDetector gestureDetector;


    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        StatusBarUtil.setStatusBarColor(getActivity(), R.color.transparent);
    }

    @Override
    protected void initData() {
        unreadMsg();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarColor(getActivity(), R.color.transparent);
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("onScroll", distanceY + "");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }


    @Override
    public void onResume() {
        if (AlibcLogin.getInstance().isLogin()) {
            String nickName = AlibcLogin.getInstance().getSession().nick;
            loginText.setText(nickName);
            GlideUtil.loadImage(context, AlibcLogin.getInstance().getSession().avatarUrl, logo);
        } else {
            loginText.setText("点击登录淘宝账户");
            GlideUtil.loadImage(context, R.drawable.ic_simple_head, logo);
        }
        super.onResume();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.logo, R.id.news, R.id.login_text, R.id.collect, R.id.return_money, R.id.taobao, R.id.my_order, R.id.all_order, R.id.about_money, R.id.get_money, R.id.error_order, R.id.friend, R.id.sign_in, R.id.foot, R.id.get_coupon, R.id.setting, R.id.help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.logo:
                if (AlibcLogin.getInstance().isLogin()) return;
                loginTaoBao(view);
                break;
            case R.id.news:
                ActivityManager.getInstance().startNextActivity(NewsActivity.class);
                point.setVisibility(View.GONE);
                break;
            case R.id.login_text:
                break;
            case R.id.collect:
                ActivityManager.getInstance().startNextActivity(CollectActivity.class);
                break;
            case R.id.return_money:
                ActivityManager.getInstance().startNextActivity(RebatesActivity.class);
                break;
            case R.id.taobao:
                AlibcBasePage ordersPage = new AlibcMyOrdersPage(0, true);
                Map<String, String> exParams = new HashMap<>();
                exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
                AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, true);
                //使用百川sdk提供默认的Activity打开detail
                AlibcTrade.show(getActivity(), ordersPage, alibcShowParams, null, exParams,
                        new AlibcTradeCallback() {
                            @Override
                            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
//                ActivityManager.getInstance().startNextActivity(TBActivity.class);
                break;
            case R.id.my_order:
                ActivityManager.getInstance().startNextActivity(MyOrderActivity.class);
                break;
            case R.id.all_order:
                ActivityManager.getInstance().startNextActivity(MyOrderActivity.class);
                break;
            case R.id.about_money:
                ActivityManager.getInstance().startNextActivity(new Intent(context, MyOrderActivity.class)
                        .putExtra("tab", 1));
                break;
            case R.id.get_money:
                ActivityManager.getInstance().startNextActivity(new Intent(context, MyOrderActivity.class)
                        .putExtra("tab", 2));
                break;
            case R.id.error_order:
                ActivityManager.getInstance().startNextActivity(new Intent(context, MyOrderActivity.class)
                        .putExtra("tab", 3));
                break;
            case R.id.friend:
                ActivityManager.getInstance().startNextActivity(InviteActivity.class);
                break;
            case R.id.sign_in:
                break;
            case R.id.foot:
                ActivityManager.getInstance().startNextActivity(FootPrintActivity.class);
                break;
            case R.id.get_coupon:
                //领券指南
                break;
            case R.id.setting:
                ActivityManager.getInstance().startNextActivity(SettingActivity.class);
                break;
            case R.id.help:
                ActivityManager.getInstance().startNextActivity(HelpActivity.class);
                break;
        }
    }

    private void loginTaoBao(View view) {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(context, "登录成功 ",
                        Toast.LENGTH_LONG).show();
                //获取淘宝用户信息
                String nickName = AlibcLogin.getInstance().getSession().nick;
                loginText.setText(nickName);
                GlideUtil.loadImage(context, AlibcLogin.getInstance().getSession().avatarUrl, logo);
                Log.i("获取淘宝用户信息", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession().nick);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 26.	未读消息
     */
    public void unreadMsg() {
        compositeDisposable.add(RetrofitUtil.getApiService().unreadMsg(null)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {
                        if (simpleString.msg > 0) {
                            point.setVisibility(View.INVISIBLE);
                        }
                    }
                }));
    }

}
