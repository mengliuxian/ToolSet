package com.flym.yh.utils;

import android.content.Context;

import com.flym.yh.data.model.Province;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Morphine on 2017/4/1.
 */

public class PropertiesUtil {

    public static String getApiSecret() {
        Properties properties = new Properties();
        String apiKey = null;
        try {
            properties.load(PropertiesUtil.class.getResourceAsStream("/assets/api.properties"));
            apiKey = properties.getProperty("Api_Secret");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiKey;
    }


    public static String getApiBaseUrl() {
        Properties properties = new Properties();
        String baseUrl = null;
        try {
            properties.load(PropertiesUtil.class.getResourceAsStream("/assets/Api.properties"));
            baseUrl = properties.getProperty("Api_BaseUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseUrl;
    }

    public static ArrayList<Province> getProvinceJson(Context context) {
        Properties properties = new Properties();
        String json = null;
        ArrayList<Province> provinces = null;
        try {
            InputStream inputStream = context.getAssets().open("city.properties");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bufferedReader);
            json = properties.getProperty("json");
            provinces = GsonUtil.fromJson(json, new TypeToken<ArrayList<Province>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return provinces;
    }


    public static String getAppKey() {
        Properties properties = new Properties();
        String appKey = null;
        try {
            properties.load(PropertiesUtil.class.getResourceAsStream("/assets/api.properties"));
            appKey = properties.getProperty("App_Key");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appKey;
    }
}
