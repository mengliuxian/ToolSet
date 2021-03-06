package com.flym.yh.utils;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Morphine on 2017/6/12.
 */

public class FragmentUtil {


    public static void replaceFragmentToActivity(@IdRes int containerId, Fragment fragment, FragmentManager fragmentManager) {
        replaceFragmentToActivity(containerId, fragment, fragmentManager, null);
    }

    public static void hideFragmentToActivity(int containerId, Fragment oldFragment, Fragment fragment, FragmentManager fragmentManager, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(oldFragment);
        if (TextUtils.isEmpty(tag)) {
            ft.add(containerId, fragment);
        } else {
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);
            if (fragmentByTag != null) {
                ft.show(fragmentByTag);
            } else {
                ft.add(containerId, fragment, tag);
            }

        }
        ft.commit();
    }

    public static void hideFragmentToActivity(int containerId, Fragment oldFragment, Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (fragment != null)
        {
        ft.hide(fragment);

        }
        ft.show(oldFragment);
        ft.commit();
    }


    public static void replaceFragmentToActivity(@IdRes int containerId, Fragment fragment, FragmentManager fragmentManager, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(tag)) {
            ft.replace(containerId, fragment);
        } else {
            ft.replace(containerId, fragment, tag);
            ft.addToBackStack(tag);
        }
        ft.commit();
    }

    //判断当前可见的fragment
    public static Fragment getVisibleFragment(FragmentManager fragmentManager) {
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    public static boolean popBackStack(FragmentManager fragmentManager) {
        return popBackStack(fragmentManager, null);
    }

    public static boolean popBackStack(FragmentManager fragmentManager, String tag) {
        boolean isBackSuccess = false;
        if (fragmentManager.getBackStackEntryCount() > 0) {
            if (TextUtils.isEmpty(tag)) {
                fragmentManager.popBackStack();
            } else {
                fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            isBackSuccess = true;
        }
        return isBackSuccess;
    }

    public static void clearBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
