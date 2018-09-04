package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.SetGohomeServiceAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.data.model.GetServicelistBean;
import com.flym.yh.data.model.GetServicesBean;
import com.flym.yh.data.model.SerGoHomeServiceBody;
import com.flym.yh.data.model.UpdateServiceCall;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.utils.FragmentUtil;
import com.flym.yh.utils.GsonUtil;
import com.flym.yh.utils.PopupWindowUtils;
import com.flym.yh.utils.SignUtil;
import com.flym.yh.utils.ToastUtil;
import com.flym.yh.utils.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author lishuqi
 * @date 2018/3/22
 * 设置预约上门
 */

public class SetGohomeServiceFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.switch_compat)
    Switch switchCompat;

    private List<GetServicesBean.ListBean> list = new ArrayList<>();
    private SetGohomeServiceAdapter mAdapter;
    private boolean isOpen;
    private DoctorGetdetailBean data;
    private List<GetServicelistBean.ListBean> listLongTime = new ArrayList<>(); //服务列表

    public static SetGohomeServiceFragment newInstance() {

        Bundle args = new Bundle();

        SetGohomeServiceFragment fragment = new SetGohomeServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        getServicelist();
        getListOld();
        data = UserManager.getUserDataFromNative(context);
        if (data != null) {
            if (data.info != null) {
                isOpen = data.info.call_status != 0;
                switch (data.info.appointment_status) {
                    case 1:
                        switchCompat.setChecked(true);
                        break;
                    case 0:
                        switchCompat.setChecked(false);
                        break;
                }
            }
        }
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new SetGohomeServiceAdapter(list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                PopupWindowUtils.selectService(getActivity(), listLongTime, view,
                        new SetTelephoneConsultationFragment.SelectCateType() {
                            @Override
                            public void onselect(int potion) {
                                GetServicelistBean.ListBean listBean = listLongTime.get(potion);
                                for (int i = 0; i < list.size(); i++) {
                                    if (listLongTime.get(potion).id == list.get(i).getType_id()) {
                                        ToastUtil.showMessage(getActivity(), "这个服务已经添加");
                                        return;
                                    }
                                }
                                list.get(position).setType_id(listLongTime.get(potion).id);
                                list.get(position).setContent(listLongTime.get(potion).name);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        switchCompat.setOnCheckedChangeListener(this);
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.set_reservation_gohome));
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_set_gohome_service;
    }

    @OnClick({R.id.tv_add, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                list.add(new GetServicesBean.ListBean());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_save:
                if (list.size() == 0) {
                    ToastUtil.showMessage(getActivity(), "至少选择一个服务");
                    return;
                }
                save();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isOpen = b;
    }

    public void save() {
        SerGoHomeServiceBody up = new SerGoHomeServiceBody();
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserManager.getToken(getActivity()));
        map.put("appKey", "android");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("appointment_status", isOpen ? 1 : 0);
        up.token = UserManager.getToken(getActivity());
        up.appKey = "android";
        up.appointment_status = isOpen ? 1 : 0;
        up.time = String.valueOf(System.currentTimeMillis() / 1000);
        List<UpdateServiceCall.Services> lis = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UpdateServiceCall.Services ser = new UpdateServiceCall.Services();
            ser.setPrice(list.get(i).getPrice());
            ser.setType_id(list.get(i).getType_id());
            lis.add(ser);
        }
        map.put("services", lis);
        up.sign = SignUtil.getSign(map);
        up.services = lis;

        String s = GsonUtil.toJson(up);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        RetrofitUtil.getApiService().UpdateServicesCall(requestBody)
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object bean) {
                        doctorGetdetail();
                    }
                });
    }

    /**
     * 获得通话时长列表
     */
    public void getListOld() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .getServicesList(1, 2)
                .compose(new SimpleTransFormer<GetServicesBean>(Object.class))
                .subscribeWith(new DisposableWrapper<GetServicesBean>() {
                    @Override
                    public void onNext(GetServicesBean o) {
                        if (o.getList().size() != 0) {
                            list.addAll(o.getList());
                        }
                        fomData();
                    }
                }));
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
                        UserManager.saveUserDataToNative(getActivity(), doctorGetdetailBean);
                        FragmentUtil.popBackStack(getFragmentManager(), getTag());
                    }
                }));
    }

    /**
     * 获取上门服务列表
     */
    public void getServicelist() {
        compositeDisposable.add(RetrofitUtil.getApiService().getServicelist("")
                .compose(new SimpleTransFormer<GetServicelistBean>(GetServicelistBean.class))
                .subscribeWith(new DisposableWrapper<GetServicelistBean>() {
                    @Override
                    public void onNext(GetServicelistBean getServicelistBean) {
                        listLongTime.addAll(getServicelistBean.list);
                        fomData();
                    }
                }));
    }

    public void fomData() {
        if (listLongTime.size() > 0 && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GetServicesBean.ListBean listBean = list.get(i);
                for (int i1 = 0; i1 < listLongTime.size(); i1++) {
                    GetServicelistBean.ListBean listBean1 = listLongTime.get(i1);
                    if (listBean1.id == listBean.getType_id()) {
                        listBean.setContent(listBean1.name);
                    }

                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}
