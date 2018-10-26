package com.flym.fhzk.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Morphine on 2017/7/27.
 */

public class SignUtil {

    private static final String appSecret = PropertiesUtil.getApiSecret();


    public static String getSign(Map<String, Object> pair) {
        List<NameValuePair> nameValuePairs = map2Pair(pair);
        try {
            return signHandle(nameValuePairs, appSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static List<NameValuePair> map2Pair(Map<String, Object> paramsMap) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        for (String key : paramsMap.keySet()) {
            pairList.add(new NameValuePair(key, paramsMap.get(key).toString()));
        }
        return pairList;
    }

    private static class PairComparator implements Comparator<NameValuePair> {
        @Override
        public int compare(NameValuePair lhs, NameValuePair rhs) {
            return lhs.name.compareTo(rhs.name);
        }
    }


    private static String signHandle(List<NameValuePair> params, String appSecret) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        // 所有请求参数按参数名升序排序
        PairComparator comparator = new PairComparator();
        Collections.sort(params, comparator);
        // 按请求参数名和参数值相互连接组成一个字符串
        StringBuilder stringBuffer = new StringBuilder();
        for (NameValuePair pair : params) {
            stringBuffer.append(pair.name).append("=").append(pair.value);
        }
        // 尾部添加APP_SECRET
        stringBuffer.append(appSecret);
        return makeMD5(stringBuffer.toString());
    }


    public static String makeMD5(String password) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            int i = b & 0xFF;
            String s = Integer.toHexString(b & 0xFF);
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    public static class NameValuePair {

        public NameValuePair(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String name;
        public Object value;

    }

}
