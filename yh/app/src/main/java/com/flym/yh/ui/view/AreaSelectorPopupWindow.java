package com.flym.yh.ui.view;

import android.content.Context;


import com.flym.yh.R;
import com.flym.yh.base.BasePopupWindow;
import com.flym.yh.data.model.Province;

import java.util.List;

/**
 * Created by Morphine on 2017/2/9.
 */

public class AreaSelectorPopupWindow extends BasePopupWindow {
    private List<Province> provinceList;
    private WheelView mProvinceView;
    private WheelView mCityView;
    private WheelView mAreaView;
    private Province currentProvine;
    private Province.City currentCity;
    private Province.Area currentArea;
    private OnAreaSelectorListener listener;

    public AreaSelectorPopupWindow(Context context) {
        super(context, R.layout.ppw_area_selector);
        mProvinceView = (WheelView) rootView.findViewById(R.id.list_province);
        mCityView = (WheelView) rootView.findViewById(R.id.list_city);
        mAreaView = (WheelView) rootView.findViewById(R.id.list_area);
        mProvinceView.setCircle(false);
        mCityView.setCircle(false);
        mAreaView.setCircle(false);
    }

    public void setProvinceList(List<Province> provinceList) {
        this.provinceList = provinceList;
        if (provinceList != null && provinceList.size() > 0) {
            currentProvine = provinceList.get(0);
            currentCity = currentProvine.cityList.get(0);
            currentArea = currentCity.areaList.get(0);
            mProvinceView.setProvinecData(provinceList);
            mCityView.setCityData(currentProvine.cityList);
            mAreaView.setCityData(currentCity.areaList);
            mProvinceView.setCenterItem(0);
            mCityView.setCenterItem(0);
            mAreaView.setCenterItem(0);
        }
        initData();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.OnAreaSelectorSuccess(currentProvine, currentCity, currentArea);
                    listener.onDismiss();
                }
                setWindowAlpha(1.0f);
            }
        });
    }

    private void initData() {
        mProvinceView.setOnScrollListener(new WheelView.OnScrollListener() {
            @Override
            public void onScrollStop(Object centerItem) {
                currentProvine = (Province) mProvinceView.getCenterItem();
                if (currentProvine.cityList.size() > 0) {
                    mCityView.setCenterItem(0);
                    mCityView.setCityData(currentProvine.cityList);
                    currentCity = currentProvine.cityList.get(0);
                    if (currentCity.areaList.size() > 0) {
                        currentArea = currentCity.areaList.get(0);
                        mAreaView.setCityData(currentCity.areaList);
                        mAreaView.setCenterItem(0);
                    } else {
                        currentArea = null;
                        mAreaView.setCityData(null);
                    }
                } else {
                    currentCity = null;
                    currentArea = null;
                    mCityView.setCityData(null);
                    mAreaView.setCityData(null);
                }
                mAreaView.setCityData(currentCity.areaList);
                currentArea = currentCity.areaList.get(0);
                if (listener != null) {
                    listener.OnAreaSelectorSuccess(currentProvine, currentCity, currentArea);
                }
            }

            @Override
            public void onScrollStart() {
            }
        });
        mCityView.setOnScrollListener(new WheelView.OnScrollListener() {
            @Override
            public void onScrollStop(Object centerItem) {
                currentCity = (Province.City) mCityView.getCenterItem();
                if (currentCity.areaList.size() > 0) {
                    currentArea = currentCity.areaList.get(0);
                    mAreaView.setCityData(currentCity.areaList);
                    mAreaView.setCenterItem(0);
                } else {
                    currentArea = null;
                    mAreaView.setCityData(null);
                }
                if (listener != null) {
                    listener.OnAreaSelectorSuccess(currentProvine, currentCity, currentArea);
                }
            }

            @Override
            public void onScrollStart() {

            }
        });
        mAreaView.setOnScrollListener(new WheelView.OnScrollListener() {
            @Override
            public void onScrollStop(Object centerItem) {
                currentArea = (Province.Area) mAreaView.getCenterItem();
                if (listener != null) {
                    listener.OnAreaSelectorSuccess(currentProvine, currentCity, currentArea);
                }
            }

            @Override
            public void onScrollStart() {
            }
        });
    }

    public void setAreaSelectorListener(OnAreaSelectorListener listener) {
        this.listener = listener;
    }


    public interface OnAreaSelectorListener {
        void OnAreaSelectorSuccess(Province currentProvine, Province.City currentCity, Province.Area currentArea);

        void onDismiss();
    }
}
