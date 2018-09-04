package com.flym.yh.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.utils.FragmentUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.ContentValues.TAG;



/**
 * Created by Morphine on 2017/6/12.
 */

public abstract class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    public CompositeDisposable compositeDisposable;
    protected Context context;
    protected View root;
    Unbinder unbinder;
    private Toolbar toolbar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, root);
        init(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar = getToolbar();
        if (toolbar != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        initData();


        return root;
    }



    protected void setPagerTitle(String pagerTitle) {
        TextView toolBarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        if (toolBarTitle != null) toolBarTitle.setText(pagerTitle);
    }

    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: ");
        if (root != null && isVisibleToUser) {
            initData();
        }
    }

    /**
     * 在Fragment被detached的时候去重置ChildFragmentManager,
     * 解决fragment嵌套fragment发生的冲突
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field declaredField = Fragment.class.getDeclaredField("mChildFragmentManager");
            declaredField.setAccessible(true);
            declaredField.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    protected abstract void initData();

    protected abstract void init(Bundle savedInstanceState);

    @LayoutRes
    protected abstract int getLayoutRes();


    @Override
    public void onDestroy() {
        if (unbinder != null)
            unbinder.unbind();
//        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    protected void successPermissions(String phone) {
        //拨号
        String tel = Pattern.compile("[^0-9]")
                .matcher(phone)
                .replaceAll("")
                .trim();
        startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:+" + tel)));
    }


    public boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void hideBackBtn() {
        if (toolbar != null) toolbar.setNavigationIcon(null);
    }

    /**
     * tabLayout控制下划线
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (tabStrip == null) {
            return;
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        if (llTab == null) {
            return;
        }
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }
}
