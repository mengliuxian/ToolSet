package com.flym.fhzk.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            properties.load(PropertiesUtil.class.getResourceAsStream("/assets/api.properties"));
            baseUrl = properties.getProperty("Api_BaseUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseUrl;
    }

    public static String getProvinceJson(Context context){
        Properties properties = new Properties();
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("city.properties");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bufferedReader);
            json = properties.getProperty("json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
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
