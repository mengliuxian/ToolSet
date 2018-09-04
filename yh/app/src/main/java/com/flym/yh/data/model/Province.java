package com.flym.yh.data.model;

import com.flym.yh.ui.view.WheelViewData;

import java.util.List;

/**
 * Created by Morphine on 2017/8/3.
 */

public class Province extends WheelViewData {

    public String provinceName;
    public int provinceId;
    public List<City> cityList;

    @Override
    public String getName() {
        return provinceName;
    }


    public class City extends WheelViewData {
        public String cityName;
        public int cityId;

        public List<Area> areaList;

        @Override
        public String getName() {
            return cityName;
        }
    }

    public class Area extends WheelViewData {
        public String areaName;
        public int areaId;

        @Override
        public String getName() {
            return areaName;
        }
    }

    @Override
    public String toString() {
        return "provinceName:" + provinceName;
    }
}
