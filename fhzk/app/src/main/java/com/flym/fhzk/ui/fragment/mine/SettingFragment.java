package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.data.model.SimpleString;
import com.flym.fhzk.data.model.VersionBean;
import com.flym.fhzk.http.DisposableWrapper;
import com.flym.fhzk.http.RetrofitUtil;
import com.flym.fhzk.http.SimpleTransFormer;
import com.flym.fhzk.ui.activity.mine.BindTBActivity;
import com.flym.fhzk.utils.ActivityManager;
import com.flym.fhzk.utils.DataCleanManager;
import com.flym.fhzk.utils.SystemUtil;
import com.flym.fhzk.utils.ToastUtil;
import com.flym.fhzk.utils.UserManager;
import com.flym.fhzk.view.CustomMineItemView;
import com.flym.fhzk.view.UploadImagePopupWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author Administrator
 * @date 2017/12/1 0001
 */

public class SettingFragment extends BaseFragment implements UploadImagePopupWindow.OnImageSelectListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.zfb)
    CustomMineItemView zfb;
    @BindView(R.id.clear_cache)
    CustomMineItemView clearCache;
    @BindView(R.id.check_update)
    CustomMineItemView checkUpdate;
    @BindView(R.id.about_us)
    CustomMineItemView aboutUs;
    @BindView(R.id.out)
    TextView out;
    Unbinder unbinder;
    @BindView(R.id.update_logo)
    CustomMineItemView updateLogo;
    @BindView(R.id.bind_t_b)
    CustomMineItemView bindTB;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText("设置");
        return toolbar;
    }

    @Override
    protected void initData() {
        String versionName = SystemUtil.getversionName(context);
        checkUpdate.setRightText(versionName);

        String cacheSize = null;
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearCache.setRightText(cacheSize);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bindTB.setRightText(AlibcLogin.getInstance().isLogin() ? "解除绑定淘宝账户" : "绑定淘宝账户");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setting;
    }

    @OnClick({R.id.bind_t_b, R.id.zfb, R.id.clear_cache, R.id.check_update, R.id.about_us, R.id.out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.bind_t_b:
                if (AlibcLogin.getInstance().isLogin()) {
                    logoutTb();
                } else {
                    loginTb();
                }
                ActivityManager.getInstance().startNextActivity(BindTBActivity.class);
                break;
            case R.id.zfb:
                break;
            case R.id.clear_cache:
                DataCleanManager.clearAllCache(context);
                String cacheSize = null;
                try {
                    cacheSize = DataCleanManager.getTotalCacheSize(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                clearCache.setRightText(cacheSize);

                break;
            case R.id.check_update:
                getVersion();
                break;
            case R.id.about_us:
                aboutUs();
                break;
            case R.id.out:
                compositeDisposable.add(RetrofitUtil.getApiService().loginOut(null)
                        .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                        .subscribeWith(new DisposableWrapper<SimpleString>() {
                            @Override
                            public void onNext(SimpleString simpleString) {
                                UserManager.logout(context);
                            }
                        }));
                break;

        }
    }

    private void logoutTb() {
        AlibcLogin.getInstance().logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                bindTB.setRightText("绑定淘宝账户");
                Toast.makeText(context, "解除绑定成功",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "解除绑定失败",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginTb() {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                bindTB.setRightText("解除绑定淘宝账户");
                Toast.makeText(context, "登录成功 ",
                        Toast.LENGTH_LONG).show();
                //获取淘宝用户信息
                Log.i("获取淘宝用户信息", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession().nick);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onSDCardNotFound() {
        ToastUtil.showMessage(context, "没有找到SD卡");
    }

    @Override
    public void onSuccess(File file) {
        //上传图片
    }

    /**
     * 14.	版本更新
     */
    public void getVersion() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .getVersion(String.valueOf(SystemUtil.getVersionCode(context)))
                .compose(new SimpleTransFormer<VersionBean>(VersionBean.class))
                .subscribeWith(new DisposableWrapper<VersionBean>() {
                    @Override
                    public void onNext(VersionBean versionBean) {

                    }
                }));
    }


    /**
     * 33.	关于我们
     */
    public void aboutUs() {
        compositeDisposable.add(RetrofitUtil.getApiService().aboutUs(null)
                .compose(new SimpleTransFormer<String>(String.class))
                .subscribeWith(new DisposableWrapper<String>() {
                    @Override
                    public void onNext(String s) {
                        if (s != null) {
                            ActivityManager.getInstance().toWebActivity(null, s);
                        }
                    }
                })
        );
    }


}
