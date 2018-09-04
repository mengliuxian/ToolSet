package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DocGetDetailBean;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.view.CircleImageView;
import com.flym.yh.ui.view.JZMediaIjkplayer;
import com.flym.yh.ui.view.TextUpDownView;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.UserManager;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Author:mengl
 * Time:2018/4/17
 * <p>
 * 名医讲堂详情
 */
public class DoctorLectureDetailFragment extends BaseFragment {
    @BindView(R.id.jz_play)
    JZVideoPlayerStandard jzPlay;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.course_details)
    TextUpDownView courseDetails;
    @BindView(R.id.course_according)
    TextUpDownView courseAccording;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.layout)
    LinearLayout layout;

    private int id;

    public static DoctorLectureDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("id", id);
        DoctorLectureDetailFragment fragment = new DoctorLectureDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.details));
        return toolbar;
    }

    @Override
    protected void initData() {
        doctoritemsGetdetail();
    }



    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        JZVideoPlayer.setMediaInterface(new JZMediaIjkplayer());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_doctor_lecture_detail;
    }

    @OnClick(R.id.layout)
    public void onViewClicked() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("audio/*");
//        startActivityForResult(intent, 702);
    }


    /**
     * 获取服务详情
     */
    public void doctoritemsGetdetail() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctoritemsGetdetail(id)
                .compose(new SimpleTransFormer<DocGetDetailBean>(DocGetDetailBean.class))
                .subscribeWith(new DisposableWrapper<DocGetDetailBean>() {
                    @Override
                    public void onNext(DocGetDetailBean o) {
                        setData(o);
                    }
                })
        );
    }

    private void setData(DocGetDetailBean o) {
        if (o.info != null) {
            jzPlay.setUp(o.info.file_id, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
//            jzPlay.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
            tvName.setText(o.info.title);
            courseDetails.setContent(o.info.content);
            courseAccording.setContent(o.info.summary);
            tvMoney.setText(o.info.price);
            DoctorGetdetailBean data = UserManager.getUserDataFromNative(context);
            if (data.info != null) {
                tvName.setText(data.info.name + " " + data.info.department_name + " " + data.info.job);
                GlideUtil.loadImage(context, ApiConstants.getImageUrl(data.info.image), head, R.drawable.wd_grmp__touxiang);
                name.setText(data.info.name);
                department.setText(data.info.department_name + " " + data.info.job);
                hospital.setText(data.info.hospital);
            }

        }


    }

}
