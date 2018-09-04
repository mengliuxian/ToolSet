package com.flym.yh.ui.fragment.mine.certification;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.ui.activity.mine.AuthenticationActivity;
import com.flym.yh.utils.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by mengl on 2018/3/21.
 */

public class SubmitAuditFragment extends BaseFragment {
    @BindView(R.id.next_bt)
    Button nextBt;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_submit_audit;
    }


    @OnClick(R.id.next_bt)
    public void onViewClicked() {
        ActivityManager.getInstance().popActivity(getActivity());
    }
}
