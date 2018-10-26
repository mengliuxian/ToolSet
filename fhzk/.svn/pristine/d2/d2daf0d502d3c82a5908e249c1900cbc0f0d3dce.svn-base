package com.flym.fhzk.ui.activity.home;

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

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;
import com.flym.fhzk.base.BaseFragment;
import com.flym.fhzk.ui.fragment.findcoupon.FindCouponFragment;
import com.flym.fhzk.ui.fragment.home.HomeFragment;
import com.flym.fhzk.ui.fragment.mine.MineFragment;
import com.flym.fhzk.utils.DensityUtil;
import com.flym.fhzk.utils.HomeTab;
import com.flym.fhzk.utils.StatusBarUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.tab_layout)
    android.support.design.widget.TabLayout tabLayout;
    @BindArray(R.array.MainBottomTabTitle)
    TypedArray titles;
    @BindArray(R.array.MainBottomTabIcon)
    TypedArray icons;
    private BaseFragment currentFragment;
    private List<String> strings;

    @Override
    protected void onResume() {
        super.onResume();
        if (HomeTab.getFlag()) {
            int curPosition = HomeTab.getCurPosition();
            selectPosition(curPosition);
        }
    }

    @Override
    protected int getLayoutResId() {
        StatusBarUtil.transparencyBar(this);
        return R.layout.activity_home;

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        strings = Arrays.asList(getResources().getStringArray(R.array.MainBottomTabTitle));
        formalTitle(strings);
        switchTab(R.string.home);
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

    }

    public void formalTitle(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String title = list.get(i);
            View view = View.inflate(this, R.layout.bottom_tab_view, null);
            TextView textView = (TextView) view.findViewById(R.id.title);
            textView.setText(title);
            Drawable drawable = getResources().getDrawable(icons.getResourceId(i, 0));
            drawable.setBounds(0, 0, DensityUtil.dp2px(this, 20), DensityUtil.dp2px(this, 20));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            tabLayout.addTab(tabLayout.newTab().setCustomView(view));
        }
    }

    public void switchTab(int id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        currentFragment = null;
        switch (id) {
            default:
                break;
            case R.string.home:
                currentFragment = HomeFragment.newInstance();
                break;
            case R.string.find:
                currentFragment = FindCouponFragment.newInstance();
                break;
            case R.string.mine:
                currentFragment = MineFragment.newInstance();
                break;
        }

        String tag = getString(id);
        if (fm.findFragmentByTag(tag) != null) {
            ft.show(fm.findFragmentByTag(tag));
        } else {
            ft.add(R.id.container, currentFragment, tag);
        }
        for (int i = 0; i < strings.size(); i++) {
            if (id != titles.getResourceId(i, 0)) {
                Fragment fragment = fm.findFragmentByTag(getString(titles.getResourceId(i, 0)));
                if (fragment != null) {
                    ft.hide(fragment);
                }
            }
        }
        ft.commitAllowingStateLoss();
    }

    public void selectPosition(int position) {
        tabLayout.getTabAt(position).select();
    }


}
