package com.flym.fhzk.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class AboutUsFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.textView_one)
    TextView textViewOne;
    @BindView(R.id.textView_two)
    TextView textViewTwo;
    @BindView(R.id.textView_five)
    TextView textViewFive;
    @BindView(R.id.textView_four)
    TextView textViewFour;
    Unbinder unbinder;

    public static AboutUsFragment newInstance() {

        Bundle args = new Bundle();

        AboutUsFragment fragment = new AboutUsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Toolbar getToolbar() {
        toolbarTitle.setText("关于我们");
        return toolbar;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ImageSpan imageSpan = new ImageSpan(context, R.drawable.ic_c);
        SpannableString spannableString = new SpannableString("Copyright  2017");
        spannableString.setSpan(imageSpan, 9, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewFour.setText(spannableString);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_about_us;
    }

}
