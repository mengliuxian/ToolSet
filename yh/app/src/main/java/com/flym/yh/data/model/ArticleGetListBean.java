package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mengl on 2018/3/28.
 */

public class ArticleGetListBean {

    /**
     * data : /yihuan/api/article/getList
     */

    public String data;
    public List<ListBean> list;

    public static class ListBean implements Parcelable {


        public int id;
        public String title;
        public String image;
        public String content;
        public int cate_id;
        public String create_time;
        public String username;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeString(this.image);
            dest.writeString(this.content);
            dest.writeInt(this.cate_id);
            dest.writeString(this.create_time);
            dest.writeString(this.username);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.image = in.readString();
            this.content = in.readString();
            this.cate_id = in.readInt();
            this.create_time = in.readString();
            this.username = in.readString();
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

}
