package com.flym.yh.ui.fragment.mine.setting;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.ui.activity.login.LoginActivity;
import com.flym.yh.ui.view.CustomMineItemView;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.DataCleanManager;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.SystemUtil;
import com.flym.yh.utils.ToastUtil;
import com.flym.yh.utils.UserManager;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by mengl on 2018/3/27.
 * 设置
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.change_the_password)
    CustomMineItemView changeThePassword;
    @BindView(R.id.switch_push)
    Switch switchPush;
    @BindView(R.id.clear_cache)
    CustomMineItemView clearCache;
    @BindView(R.id.login_out)
    Button loginOut;

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.set_up));
        return toolbar;
    }

    @Override
    protected void initData() {
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(context);
            clearCache.setRightText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean push = UserManager.getPush(context);
        switchPush.setChecked(push);
        if (push)
        {
            JPushInterface.resumePush(context);
            UserManager.savePush(context, true);
        }

    }

    @Override
    protected void init(Bundle savedInstanceState) {

        switchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //接收通知
                    JPushInterface.resumePush(context);
                    UserManager.savePush(context, true);
                } else {
                    //拒绝通知
                    JPushInterface.stopPush(context);
                    UserManager.savePush(context, false);
                }
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setting;
    }


    @OnClick({R.id.change_the_password, R.id.clear_cache, R.id.login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_the_password:
                FragmentUtil.replaceFragmentToActivity(R.id.container,
                        new ChangeThePasswordFragment(), getFragmentManager(), getString(R.string.set_up));
                break;
            case R.id.clear_cache:
                DataCleanManager.clearAllCache(context);
                clearCache.setRightText("");
                break;
            case R.id.login_out:
                //环信退出登陆
                EMClient.getInstance().logout(true);
                UserManager.logout(context);
                break;
        }
    }
}
