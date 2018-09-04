package com.flym.yh.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class SimpleString {

    @SerializedName(value = "string", alternate = {"data","token","url"})
    public String string;
    public int auth_status;
}
