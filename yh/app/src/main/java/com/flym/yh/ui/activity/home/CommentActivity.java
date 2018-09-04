package com.flym.yh.ui.activity.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CircleImageView;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评论患者
 */
public class CommentActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.iv_good)
    CircleImageView ivGood;
    @BindView(R.id.iv_bad)
    CircleImageView ivBad;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.bt_next)
    Button btNext;

    private int level = 0;//评论级别 1：好 2：不好
    private String name;
    private String head;
    private int id;


    @Override
    protected String getPagerTitle() {
        return getString(R.string.comment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        name = getIntent().getStringExtra("name");
        head = getIntent().getStringExtra("head");
        id = getIntent().getIntExtra("id",0);
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(name)) {
            tvName.setText(name);
        }
        if (!TextUtils.isEmpty(head)) {

            GlideUtil.loadImage(this, head, ivHead);
        }

    }


    @OnClick({R.id.iv_good, R.id.iv_bad, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_good:
                level = 1;
                GlideUtil.loadImage(this, R.drawable.sy_wdzs_zxym_pl_huanzhe_xuanzhong, ivGood);
                GlideUtil.loadImage(this, R.drawable.sy_wdzs_zxym_pl_huanzhe, ivBad);
                break;
            case R.id.iv_bad:
                level = 2;
                GlideUtil.loadImage(this, R.drawable.sy_wdzs_zxym_pl_haohuanzhe, ivGood);
                GlideUtil.loadImage(this, R.drawable.sy_wdzs_zxym_pl_huanzhe_xuanzhong, ivBad);
                break;
            case R.id.bt_next:
                doctorComment();
                break;
        }
        if (level > 0) {
            btNext.setEnabled(true);
        } else {
            btNext.setEnabled(false);
        }
    }

    /**
     * 评论患者
     */
    public void doctorComment() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorComment(level,id)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString simpleString) {

                    }
                })
        );

    }

}
