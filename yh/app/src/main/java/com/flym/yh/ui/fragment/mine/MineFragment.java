package com.flym.yh.ui.fragment.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.mine.CustomerServiceActivity;
import com.flym.yh.ui.activity.mine.IdCardActivity;
import com.flym.yh.ui.activity.mine.MyAccountActivity;
import com.flym.yh.ui.activity.mine.MyEvaluateActivity;
import com.flym.yh.ui.activity.mine.MyForumActivity;
import com.flym.yh.ui.activity.mine.MyNewsActivity;
import com.flym.yh.ui.activity.mine.PersonalDataActivity;
import com.flym.yh.ui.activity.mine.PersonalHomepageActivity;
import com.flym.yh.ui.activity.mine.SettingActivity;
import com.flym.yh.ui.view.CircleImageView;
import com.flym.yh.ui.view.CustomDialog;
import com.flym.yh.ui.view.CustomMineItemView;
import com.flym.yh.utils.ActivityManager;
import com.flym.yh.utils.ApiConstants;
import com.flym.yh.utils.Constants;
import com.flym.yh.utils.GlideUtil;
import com.flym.yh.utils.UserManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mengl on 2018/3/23.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.id_card)
    TextView idCard;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.personal_homepage)
    CustomMineItemView personalHomepage;
    @BindView(R.id.authentication)
    CustomMineItemView authentication;
    @BindView(R.id.my_account)
    CustomMineItemView myAccount;
    @BindView(R.id.my_forum)
    CustomMineItemView myForum;
    @BindView(R.id.my_news)
    CustomMineItemView myNews;
    @BindView(R.id.my_evaluate)
    CustomMineItemView myEvaluate;
    @BindView(R.id.customer_service)
    CustomMineItemView customerService;
    @BindView(R.id.set_time)
    CustomMineItemView setTime;
    @BindView(R.id.bind_zfb)
    CustomMineItemView bindZfb;
    @BindView(R.id.bind_wx)
    CustomMineItemView bindWx;


    private Dialog dialog;
    private DoctorGetdetailBean data;
    private String rTime;


    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {
        data = UserManager.getUserDataFromNative(context);
        if (data != null && data.info != null) {
            GlideUtil.loadImage(context, ApiConstants.getImageUrl(data.info.image), head, R.drawable.wd_grmp__touxiang);
            name.setText(data.info.name);
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.id_card, R.id.setting, R.id.head, R.id.personal_homepage, R.id.authentication, R.id.my_account, R.id.my_forum, R.id.my_news, R.id.my_evaluate, R.id.customer_service, R.id.set_time, R.id.bind_zfb, R.id.bind_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_card:
                ActivityManager.getInstance().startNextActivity(IdCardActivity.class);
                break;
            case R.id.setting:
                ActivityManager.getInstance().startNextActivity(SettingActivity.class);
                break;
            case R.id.head:
                ActivityManager.getInstance().startNextActivity(PersonalDataActivity.class);
                break;
            case R.id.personal_homepage:
                ActivityManager.getInstance().startNextActivity(PersonalHomepageActivity.class);
                break;
            case R.id.authentication:
//                ActivityManager.getInstance().startNextActivity(AuthenticationActivity.class);
                break;
            case R.id.my_account:
                ActivityManager.getInstance().startNextActivity(MyAccountActivity.class);
                break;
            case R.id.my_forum:
                ActivityManager.getInstance().startNextActivity(MyForumActivity.class);
                break;
            case R.id.my_news:
                ActivityManager.getInstance().startNextActivity(MyNewsActivity.class);
                break;
            case R.id.my_evaluate:
                ActivityManager.getInstance().startNextActivity(MyEvaluateActivity.class);
                break;
            case R.id.customer_service:
                ActivityManager.getInstance().startNextActivity(CustomerServiceActivity.class);
                break;
            case R.id.set_time:
                rTime = "";
                dialog = CustomDialog.amendContentHint(context, getString(R.string.set_time),
                        getString(R.string.input_the_outpatient_time), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                doctorUpdateServiceTime();
                            }
                        }, new CustomDialog.EditTextReultListence() {
                            @Override
                            public void onEditReult(String s) {
                                rTime = s;
                            }
                        });
                break;
            case R.id.bind_zfb:
                break;
            case R.id.bind_wx:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.isChange) {
            doctorGetdetail();
            Constants.isChange = false;
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
                        UserManager.saveUserDataToNative(context, doctorGetdetailBean);
                        initData();
                    }
                }));
    }

    /**
     * 更新医生服务状态(门诊时间)
     */
    public void doctorUpdateServiceTime() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateServiceTime(rTime)
                .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
                .subscribeWith(new DisposableWrapper<SimpleString>() {
                    @Override
                    public void onNext(SimpleString o) {
                        doctorGetdetail();
                    }
                }));
    }


    /**
     * 绑定第三方
     */
    public void doctorBindthird(String wx, String ali) {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorBindthird(wx, ali)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {


                    }
                })
        );


    }

}
