package com.flym.yh.ui.activity.home;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.fragment.forum.ForumFragment;
import com.flym.yh.ui.fragment.home.HomeFragment;
import com.flym.yh.ui.fragment.information.InformationFragment;
import com.flym.yh.ui.fragment.mine.MineFragment;
import com.flym.yh.utils.DensityUtil;
import com.flym.yh.utils.UserManager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

import static com.flym.yh.utils.Constants.ERROR_LOGIN;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindArray(R.array.home_tab_icon)
    TypedArray icons;
    @BindArray(R.array.home_tab_title)
    TypedArray titles;


    private BaseFragment currentFragment;
    private List<String> titleList;
    private TabLayout.Tab tab;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        titleList = Arrays.asList(getResources().getStringArray(R.array.home_tab_title));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                int resourceId = titles.getResourceId(position, 0);
                switchTab(resourceId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        doctorGetdetail();
        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i);
            View view = View.inflate(this, R.layout.bottom_tab_view, null);
            TextView textView = (TextView) view.findViewById(R.id.title);
            textView.setText(title);
            Drawable drawable = getResources().getDrawable(icons.getResourceId(i, 0));
            drawable.setBounds(0, 0, DensityUtil.dp2px(this, 15),
                    DensityUtil.dp2px(this, 15));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            tabLayout.addTab(tabLayout.newTab().setCustomView(view));
        }
    }


    public void switchTab(int id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        currentFragment = null;
        switch (id) {
            case R.string.home:
                currentFragment = HomeFragment.newInstance();
                break;
            case R.string.information:
                currentFragment = InformationFragment.newInstance();
                break;
            case R.string.forum:
                currentFragment = ForumFragment.newInstance();
                break;
            case R.string.mine:
                currentFragment = MineFragment.newInstance();
                break;
            default:
                break;
        }

        String tag = getString(id);
        if (fm.findFragmentByTag(tag) != null) {
            ft.show(fm.findFragmentByTag(tag));
        } else {
            ft.add(R.id.container, currentFragment, tag);
        }
        for (int i = 0; i < titleList.size(); i++) {
            if (id != titles.getResourceId(i, 0)) {
                Fragment fragment = fm.findFragmentByTag(getString(titles.getResourceId(i, 0)));
                if (fragment != null) {
                    ft.hide(fragment);
                }
            }
        }
        ft.commitAllowingStateLoss();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ERROR_LOGIN) {
            tabLayout.getTabAt(0).select();
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
                        UserManager.saveUserDataToNative(HomeActivity.this, doctorGetdetailBean);
                    }
                }));
    }
}
