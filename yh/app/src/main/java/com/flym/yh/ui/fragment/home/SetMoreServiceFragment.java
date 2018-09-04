package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleString;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.UserManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 设置图文咨询 医学阅读  名师讲堂  名医广播
 */

public class SetMoreServiceFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ed_set_price)
    EditText edSetPrice;
    @BindView(R.id.l_set_price)
    LinearLayout lSetPrice;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.switch_compat)
    Switch switchCompat;

    private DoctorGetdetailBean data;
    private boolean isOpen;

    public static SetMoreServiceFragment newInstance() {

        Bundle args = new Bundle();

        SetMoreServiceFragment fragment = new SetMoreServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        data = UserManager.getUserDataFromNative(context);
        setData();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        switchCompat.setOnCheckedChangeListener(this);
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setData() {
        String tag = getTag();
        if (tag.equals(getString(R.string.graphic_consultation))) {
            toolbarTitle.setText(R.string.set_graphic_consultation);
            lSetPrice.setVisibility(View.VISIBLE);
            tvPrompt.setText(R.string.prompt_graphic);
            getListOld();
            if (data != null && data.info != null) {
                isOpen = data.info.inquiry_status != 0;
                switch (data.info.inquiry_status) {
                    case 1:
                        switchCompat.setChecked(true);
                        break;
                    case 0:
                        switchCompat.setChecked(false);
                        break;
                }
            }
        } else if (getTag().equals(getString(R.string.medical_reading))) {
            toolbarTitle.setText(R.string.set_medical_reading);
            tvPrompt.setText(R.string.prompt_reading);
            if (data != null && data.info != null) {
                isOpen = data.info.read_status != 0;
                switch (data.info.read_status) {
                    case 1:
                        switchCompat.setChecked(true);
                        break;
                    case 0:
                        switchCompat.setChecked(false);
                        break;
                }
            }
        } else if (getTag().equals(getString(R.string.doctor_lecture))) {
            toolbarTitle.setText(R.string.set_doctor_lecture);
            tvPrompt.setText(R.string.prompt_lecture);
            if (data != null && data.info != null) {
                isOpen = data.info.lecture_status != 0;
                switch (data.info.lecture_status) {
                    case 1:
                        switchCompat.setChecked(true);
                        break;
                    case 0:
                        switchCompat.setChecked(false);
                        break;
                }
            }
        } else if (getTag().equals(getString(R.string.doctor_broadcast))) {
            toolbarTitle.setText(R.string.set_doctor_broadcast);
            tvPrompt.setText(R.string.prompt_broadcast);
            if (data != null && data.info != null) {
                isOpen = data.info.broadcast_status != 0;
                switch (data.info.broadcast_status) {
                    case 1:
                        switchCompat.setChecked(true);
                        break;
                    case 0:
                        switchCompat.setChecked(false);
                        break;
                }
            }
        }
        tvType.setText(tag);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_set_more;
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        String tag = getTag();
        if (tag.equals(getString(R.string.graphic_consultation))) {
            doctorUpdateServiceConsulting();
        } else if (getTag().equals(getString(R.string.medical_reading))) {
            doctorUpdateServiceReading();
        } else if (getTag().equals(getString(R.string.doctor_lecture))) {
            doctorUpdateServiceClassroom();
        } else if (getTag().equals(getString(R.string.doctor_broadcast))) {
            doctorUpdateServiceRadio();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isOpen = b;
    }


    /**
     * 图文咨询
     */
    public void doctorUpdateServiceConsulting() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateServiceConsulting(isOpen ? 1 : 0, edSetPrice.getText().toString()).compose(new SimpleTransFormer<SimpleString>(SimpleString.class)).subscribeWith(new DisposableWrapper<SimpleString>() {
            @Override
            public void onNext(SimpleString simpleString) {
                if (data.info != null) {
                    data.info.inquiry_status = isOpen ? 1 : 0;
                }
                UserManager.saveUserDataToNative(context, data);
                FragmentUtil.popBackStack(getFragmentManager(), getTag());
            }
        }));
    }

    /**
     * 医学阅读
     */
    public void doctorUpdateServiceReading() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateServiceReading(isOpen ? 1 : 0).compose(new SimpleTransFormer<SimpleString>(SimpleString.class)).subscribeWith(new DisposableWrapper<SimpleString>() {
            @Override
            public void onNext(SimpleString simpleString) {
                if (data.info != null) {
                    data.info.read_status = isOpen ? 1 : 0;
                }
                UserManager.saveUserDataToNative(context, data);
                FragmentUtil.popBackStack(getFragmentManager(), getTag());
            }
        }));
    }

    /**
     * 名师讲堂
     */
    public void doctorUpdateServiceClassroom() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateServiceClassroom(isOpen ? 1 : 0).compose(new SimpleTransFormer<SimpleString>(SimpleString.class)).subscribeWith(new DisposableWrapper<SimpleString>() {
            @Override
            public void onNext(SimpleString simpleString) {
                if (data.info != null) {
                    data.info.lecture_status = isOpen ? 1 : 0;
                }
                UserManager.saveUserDataToNative(context, data);
                FragmentUtil.popBackStack(getFragmentManager(), getTag());
            }
        }));
    }

    /**
     * 名医广播
     */
    public void doctorUpdateServiceRadio() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateServiceRadio(isOpen ? 1 : 0).compose(new SimpleTransFormer<SimpleString>(SimpleString.class)).subscribeWith(new DisposableWrapper<SimpleString>() {
            @Override
            public void onNext(SimpleString simpleString) {
                if (data.info != null) {
                    data.info.broadcast_status = isOpen ? 1 : 0;
                }
                UserManager.saveUserDataToNative(context, data);
                FragmentUtil.popBackStack(getFragmentManager(), getTag());
            }
        }));
    }


    /**
     * 获取服务列表
     */
    public void getListOld() {
        compositeDisposable.add(RetrofitUtil.getApiService().getServicesList(1, 32).compose(new SimpleTransFormer<GetServicesBean>(Object.class)).subscribeWith(new DisposableWrapper<GetServicesBean>() {
            @Override
            public void onNext(GetServicesBean o) {
//
                edSetPrice.setText(o.getList().get(0).getPrice());
            }
        }));
    }


}
