package com.flym.yh.utils;

import android.content.Intent;
import android.net.Uri;
import android.telecom.Call;

import com.flym.yh.MyApplication;

import java.util.regex.Pattern;

public class PhoneUtils {



    public static void call(String phone) {
        String tel = Pattern.compile("[^0-9]")
                .matcher(phone)
                .replaceAll("")
                .trim();
        MyApplication.mContext.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:+" + tel)));
    }


}
