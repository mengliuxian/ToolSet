package com.flym.yh.ui.fragment.mine.certification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BaseFragment;
import com.flym.yh.data.model.DoctorGetdetailBean;
import com.flym.yh.data.model.GetDepartmenglistBean;
import com.flym.yh.data.model.Province;
import com.flym.yh.net.DisposableWrapper;
import com.flym.yh.net.RetrofitUtil;
import com.flym.yh.net.SimpleTransFormer;
import com.flym.yh.ui.activity.mine.AuthenticationActivity;
import com.flym.yh.ui.activity.mine.DepartmentActivity;
import com.flym.yh.ui.view.AreaSelectorPopupWindow;
import com.flym.yh.ui.view.CustomMineItemView;
import com.flym.yh.ui.view.PositionPop;
import com.flym.yh.utils.Constants;
import com.flym.yh.utils.PropertiesUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mengl on 2018/3/21.
 */

public class BaseInformationFragment extends BaseFragment implements AuthenticationActivity.BaseListence {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.enter_name)
    EditText enterName;
    @BindView(R.id.department)
    CustomMineItemView department;
    @BindView(R.id.enter_hospital)
    EditText enterHospital;
    @BindView(R.id.enter_position)
    TextView enterPosition;
    @BindView(R.id.in_the_area)
    CustomMineItemView inTheArea;
    @BindView(R.id.enter_detail_address)
    EditText enterDetailAddress;
    @BindView(R.id.next_text)
    TextView nextText;

    private PositionPop positionPop;

    private AreaSelectorPopupWindow areaPop;
    private int currentAreaId;
    private String id = "";
    ArrayList<Province> addressList;
    private GetDepartmenglistBean.ListBean.ChildBean child;
    private String rData;
    private String provinceName;
    private String cityName;
    private String areaName;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AuthenticationActivity) getActivity()).setBaseListence(this);
    }

    @Override
    protected void initData() {
        setData();

        if (addressList == null || addressList.size() == 0) {
            addressList = PropertiesUtil.getProvinceJson(context);
        }

        areaPop.setAreaSelectorListener(new AreaSelectorPopupWindow.OnAreaSelectorListener() {
            @Override
            public void OnAreaSelectorSuccess(Province currentProvine, Province.City currentCity, Province.Area currentArea) {
                if (currentCity == null) {
                    currentAreaId = currentProvine.provinceId;
                    provinceName = currentProvine.provinceName;
                    inTheArea.setRightText(currentProvine.provinceName);
                } else if (currentCity != null && currentArea == null) {
                    currentAreaId = currentCity.cityId;
                    provinceName = currentProvine.provinceName;
                    cityName = currentCity.cityName;
                    inTheArea.setRightText(currentProvine.provinceName + "-" + currentCity.cityName);
                } else {
                    currentAreaId = currentArea.areaId;
                    provinceName = currentProvine.provinceName;
                    cityName = currentCity.cityName;
                    areaName = currentArea.areaName;
                    inTheArea.setRightText(currentProvine.provinceName + "-" + currentCity.cityName
                            + "-" + currentArea.areaName);
                }
            }

            @Override
            public void onDismiss() {
            }
        });
    }

    private void setData() {
        if (AuthenticationActivity.baseInfo != null && AuthenticationActivity.baseInfo.info != null) {
            DoctorGetdetailBean.InfoBean info = AuthenticationActivity.baseInfo.info;
            enterName.setText(info.name);
            department.setRightText(info.department_name);
            id = String.valueOf(info.department_id);
            enterHospital.setText(info.hospital);
            enterPosition.setText(info.job);
            inTheArea.setRightText(info.province + "-" + info.city
                    + "-" + info.district);
            provinceName = info.province;
            cityName = info.city;
            areaName = info.district;
            enterDetailAddress.setText(info.address);
        }

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        areaPop = new AreaSelectorPopupWindow(context);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base_information;
    }


    @OnClick({R.id.department, R.id.enter_position, R.id.in_the_area, R.id.next_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.department:
                startActivityForResult(new Intent(context, DepartmentActivity.class), Constants.DEPART);
                break;
            case R.id.enter_position:
                if (positionPop == null) {
                    int width = enterPosition.getWidth();
                    positionPop = new PositionPop(context, width, new PositionPop.ReturnDataListence() {
                        @Override
                        public void returnData(String content) {
                            if (!TextUtils.isEmpty(content)) {
                                rData = content;
                                enterPosition.setText(content);
                            }
                        }
                    });
                }
                positionPop.showAtLocation(enterPosition, Gravity.NO_GRAVITY, 0, 0);
                break;
            case R.id.in_the_area:
                areaPop.setProvinceList(addressList);
                areaPop.showAtLocation(getActivity().findViewById(R.id.container), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.next_text:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.DEPART && resultCode == RESULT_OK) {
            child = data.getParcelableExtra("ChildBean");
            id = child.id;
            department.setRightText(child.name);
        }
    }

    /**
     * 更新医生个人信息
     */
    public void doctorUpdateaccount() {
        compositeDisposable.add(RetrofitUtil.getApiService().doctorUpdateaccount(
                "", enterName.getText().toString(), "0", enterHospital.getText().toString(),
                id, rData, "", provinceName, cityName, areaName, enterDetailAddress.getText().toString(),
                "")
                .compose(new SimpleTransFormer<Object>(Object.class))
                .subscribeWith(new DisposableWrapper<Object>() {
                    @Override
                    public void onNext(Object o) {



                        ((AuthenticationActivity) getActivity()).BaneNext();
                    }
                }));
    }

    /**
     * activity的提交按钮的回调
     *
     * @return
     */
    @Override
    public void onNext() {
        doctorUpdateaccount();

    }
}
