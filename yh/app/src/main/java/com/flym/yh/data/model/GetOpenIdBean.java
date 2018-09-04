package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 * @date 2018/1/3 0003
 */

public class GetOpenIdBean implements Parcelable {

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     * unionid : o6_bmasdasdsad6_2sgVt7hMZOPfL
     */

    public String access_token;
    public int expires_in;
    public String refresh_token;
    public String openid;
    public String scope;
    public String unionid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeInt(this.expires_in);
        dest.writeString(this.refresh_token);
        dest.writeString(this.openid);
        dest.writeString(this.scope);
        dest.writeString(this.unionid);
    }

    public GetOpenIdBean() {
    }

    protected GetOpenIdBean(Parcel in) {
        this.access_token = in.readString();
        this.expires_in = in.readInt();
        this.refresh_token = in.readString();
        this.openid = in.readString();
        this.scope = in.readString();
        this.unionid = in.readString();
    }

    public static final Parcelable.Creator<GetOpenIdBean> CREATOR = new Parcelable.Creator<GetOpenIdBean>() {
        @Override
        public GetOpenIdBean createFromParcel(Parcel source) {
            return new GetOpenIdBean(source);
        }

        @Override
        public GetOpenIdBean[] newArray(int size) {
            return new GetOpenIdBean[size];
        }
    };
}
