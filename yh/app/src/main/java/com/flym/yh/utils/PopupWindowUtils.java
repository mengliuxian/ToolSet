package com.flym.yh.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.SelectCateNameAdapter;
import com.flym.yh.adapter.home.SelectLongTimeAdapter;
import com.flym.yh.adapter.home.SelectServiceAdapter;
import com.flym.yh.data.model.GetCateListBean;
import com.flym.yh.data.model.GetPhoneLongTimeBean;
import com.flym.yh.data.model.GetServicelistBean;
import com.flym.yh.ui.fragment.home.SetTelephoneConsultationFragment;
import com.flym.yh.ui.fragment.home.WriteArticleFragment;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class PopupWindowUtils {

    /**
     * 设置弹出popwindow时的页面背景为灰色
     * 显示   isShow = true
     */
    public static void setBackgruond(Activity activity, boolean isShow) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (isShow) {
            lp.alpha = 0.60f;
        } else {
            lp.alpha = 1f;
        }
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 选择药品名称
     */
    public static void selectCateName(final Context context, final List<GetCateListBean.ListBean> list, View vtiew, int width, final WriteArticleFragment.SelectCateType listeren) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_select_drug_name, null);
        final PopupWindow selectLogin = new PopupWindow(view.findViewById(R.id.pop_layout),
                width, LinearLayout.LayoutParams.WRAP_CONTENT);
        selectLogin.setContentView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        SelectCateNameAdapter mAdapter = new SelectCateNameAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < list.size(); i++) {
                    if (i != position) {
                        list.get(i).isShow = false;
                    } else {
                        list.get(i).isShow = true;
                    }
                }
                listeren.onselect(position);
                selectLogin.dismiss();
            }
        });
        selectLogin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        selectLogin.setFocusable(true);
        selectLogin.showAsDropDown(vtiew);
    }


    /**
     * 选择通话时长
     */
    public static void selectLongTime(final Context context, final List<GetPhoneLongTimeBean.ListBean> list,
                                      View vtiew, final SetTelephoneConsultationFragment.SelectCateType listeren) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_select_drug_name, null);
        final PopupWindow selectLogin = new PopupWindow(view.findViewById(R.id.pop_layout), vtiew.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        selectLogin.setContentView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        SelectLongTimeAdapter mAdapter = new SelectLongTimeAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                for (int i = 0; i < list.size(); i++) {
//                    if (i != position) {
//                        list.get(i).isShow = false;
//                    } else {
//                        list.get(i).isShow = true;
//                    }
//                }
                listeren.onselect(position);
                selectLogin.dismiss();
            }
        });
        selectLogin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        selectLogin.setFocusable(true);
        selectLogin.showAsDropDown(vtiew);
    }

    /**
     * 选择通话时长
     */
    public static void selectService(final Context context, final List<GetServicelistBean.ListBean> list,
                                     View vtiew, final SetTelephoneConsultationFragment.SelectCateType listeren) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_select_drug_name, null);
        final PopupWindow selectLogin = new PopupWindow(view.findViewById(R.id.pop_layout), vtiew.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        selectLogin.setContentView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        SelectServiceAdapter mAdapter = new SelectServiceAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                for (int i = 0; i < list.size(); i++) {
//                    if (i != position) {
//                        list.get(i).isShow = false;
//                    } else {
//                        list.get(i).isShow = true;
//                    }
//                }
                listeren.onselect(position);
                selectLogin.dismiss();
            }
        });
        selectLogin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        selectLogin.setFocusable(true);
        selectLogin.showAsDropDown(vtiew);
    }


}
