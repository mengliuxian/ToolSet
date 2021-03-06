package com.flym.yh.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.flym.yh.R;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by Morphine on 2017/6/12.
 */

public abstract class BaseViewPageFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    public CompositeDisposable compositeDisposable;
    protected Context context;
    protected View root;
    Unbinder unbinder;
    private boolean isVisible;
    private Bundle bundle;
    private boolean isFirst = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        isFirst = true;
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
        bundle = savedInstanceState;
        if (root != null) {
            return root;
        } else {
            root = inflater.inflate(getLayoutRes(), container, false);
            unbinder = ButterKnife.bind(this, root);
            mCreate();
            return root;
        }
    }

    public void mCreate() {
        if (isVisible && root != null ) {
            init(bundle);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            Toolbar toolbar = getToolbar();
            if (toolbar != null) {
                activity.setSupportActionBar(toolbar);
                activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            initData();
        }
    }

    @Override
    protected void setPagerTitle(String pagerTitle) {
        TextView toolBarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        if (toolBarTitle != null) {
            toolBarTitle.setText(pagerTitle);
        }
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (root != null && isVisibleToUser) {
            mCreate();
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

    @Override
    protected abstract void initData();

    @Override
    protected abstract void init(Bundle savedInstanceState);

    @Override
    @LayoutRes
    protected abstract int getLayoutRes();


    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        compositeDisposable.clear();
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



}
