package com.flym.yh.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flym.yh.R;
import com.flym.yh.adapter.home.SetTelephoneConsultationAdapter;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.data.model.GetPhoneLongTimeBean;
import com.flym.yh.data.model.GetServicesBean;
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
 * 电话咨询
 */

public class SetTelephoneConsultationFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.ed_return_time)
    EditText edReturnTime;
    @BindView(R.id.switch_compat)
    Switch switchCompat;

    private boolean isOpen;
    private String callTime = "";

    private List<GetServicesBean.ListBean> list = new ArrayList<>();
    private SetTelephoneConsultationAdapter mAdapter;
    private List<GetPhoneLongTimeBean.ListBean> listLongTime = new ArrayList();

    public static SetTelephoneConsultationFragment newInstance() {

        Bundle args = new Bundle();

        SetTelephoneConsultationFragment fragment = new SetTelephoneConsultationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        if (list.size() != 0) {
            list.clear();
            listLongTime.clear();
        }
        setAdapter();
        getListOld();
        getPhoneList();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new SetTelephoneConsultationAdapter(list, listLongTime);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                PopupWindowUtils.selectLongTime(getActivity(), listLongTime, view, new SelectCateType() {
                    @Override
                    public void onselect(int potion) {
                        for (int i = 0; i < list.size(); i++) {
                            if (listLongTime.get(potion).getId() == list.get(i).getType_id()) {
                                ToastUtil.showMessage(getActivity(), "这个时间段已经添加");
                                return;
                            }
                        }
                        list.get(position).setType_id(listLongTime.get(potion).getId());
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        DoctorGetdetailBean.InfoBean s = UserManager.getUserDataFromNative(getActivity()).info;
        isOpen = (s.call_status == 0) ? false : true;
        callTime = s.call_time;
        switchCompat.setChecked(isOpen);
        switchCompat.setOnCheckedChangeListener(this);
        edReturnTime.setText(callTime);
    }

    @Override
    public Toolbar getToolbar() {
        toolbarTitle.setText(getString(R.string.set_telephone_consultation));
        return toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_set_telephone_consultation;
    }

    @OnClick({R.id.tv_add, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                list.add(new GetServicesBean.ListBean());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_save:
                if (edReturnTime.getText().toString().trim().equals("")) {
                    ToastUtil.showMessage(getActivity(), "请输入回电信息");
                    return;
                }
                if (list.size() == 0) {
                    ToastUtil.showMessage(getActivity(), "至少输入一个通话时长");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() == 0) {
                        ToastUtil.showMessage(getActivity(), "请选择正确的通话时长");
                        break;
                    }
                    if (list.get(i).getPrice().equals("")) {
                        ToastUtil.showMessage(getActivity(), "请输入金额");
                        break;
                    }
                }
                save();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isOpen = b;
        switchCompat.setChecked(isOpen);
    }

    /**
     * 获得通话时长列表
     */
    public void getListOld() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .getServicesList(1, 3)
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
     * 获得电话时长列表
     */
    public void getPhoneList() {
        compositeDisposable.add(RetrofitUtil.getApiService()
                .getPhoneLong("")
                .compose(new SimpleTransFormer<GetPhoneLongTimeBean>(Object.class))
                .subscribeWith(new DisposableWrapper<GetPhoneLongTimeBean>() {
                    @Override
                    public void onNext(GetPhoneLongTimeBean o) {
                        listLongTime.addAll(o.getList());
                        fomData();
                    }
                }));
    }

    /**
     * 保存
     */
    public void save() {
        UpdateServiceCall up = new UpdateServiceCall();
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserManager.getToken(getActivity()));
        map.put("appKey", "android");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("call_status", isOpen ? 1 : 0);
        map.put("call_time", edReturnTime.getText().toString().trim());
        up.setToken(UserManager.getToken(getActivity()));
        up.setAppKey("android");
        up.setCall_status(isOpen ? 1 : 0);
        up.setCall_time(edReturnTime.getText().toString().trim());
        up.setTime(String.valueOf(System.currentTimeMillis() / 1000));
        List<UpdateServiceCall.Services> lis = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UpdateServiceCall.Services ser = new UpdateServiceCall.Services();
            ser.setPrice(list.get(i).getPrice());
            ser.setType_id(list.get(i).getType_id());
            lis.add(ser);
        }
        map.put("services", lis);
        up.setSign(SignUtil.getSign(map));
        up.setServices(lis);

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

    public interface SelectCateType {
        void onselect(int potion);
    }

    public void fomData() {
        if (listLongTime.size() > 0 && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GetServicesBean.ListBean listBean = list.get(i);
                for (int i1 = 0; i1 < listLongTime.size(); i1++) {
                    GetPhoneLongTimeBean.ListBean listBean1 = listLongTime.get(i1);
                    if (listBean1.getId() == listBean.getType_id()) {
                        listBean.setContent(listBean1.getName());
                    }

                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}
