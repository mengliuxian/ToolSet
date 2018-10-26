package com.flym.fhzk.utils;

/**
 * Created by Morphine on 2017/8/5.
 */

public class LocationUtil {
//    public static LocationClient mLocationClient = null;
//    public static BDLocationListener myListener = new MyLocationListener();
//    public static LocationCallback callback;
//
//    public static void getCurtLocation(Context context, LocationCallback callback) {
//        mLocationClient = new LocationClient(context);
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);
//        //注册监听函数
//        LocationUtil.callback = callback;
//        initLocation();
//        mLocationClient.start();
//    }
//
//    private static void initLocation() {
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");
//        //可选，默认gcj02，设置返回的定位结果坐标系
//        option.setScanSpan(0);
//        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);
//        //可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);
//        //可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);
//        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);
//        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);
//        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);
//        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);
//        //可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);
//        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//        mLocationClient.setLocOption(option);
//    }
//
//    public static class MyLocationListener implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            StringBuffer sb = new StringBuffer(256);
//            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                final String province = location.getProvince();
//                final String city = location.getCity();
//                final String area = location.getDistrict();
//                final double latitude = location.getLatitude();
//                final double longitude = location.getLongitude();
//
//                if (callback != null) {
//                    new Handler(Looper.getMainLooper())
//                            .post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    callback.onSuccess(latitude, longitude);
//                                }
//                            });
//                }
//            } else {
//                if (callback != null) {
//                    new Handler(Looper.getMainLooper())
//                            .post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    callback.onFail();
//                                }
//                            });
//                }
//            }
//            mLocationClient.stop();
//        }
//
//
//    }
//
//    public interface LocationCallback {
//        void onSuccess(double lat, double lon);
//
//        void onFail();
//    }

}
