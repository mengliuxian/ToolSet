package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mengl on 2018/3/23.
 */

public class GetDepartmenglistBean implements Parcelable {

        /**
         * data : /yihuan/api/app/getDepartmentlist
         * list : [{"id":1,"parent_id":0,"name":"内科","_child":[{"id":2,"parent_id":1,"name":"呼吸内科"},{"id":3,"parent_id":1,"name":"心血管内科"}]}]
         */

        public String data;
        public List<ListBean> list;

        public static class ListBean implements Parcelable {
            /**
             * id : 1
             * parent_id : 0
             * name : 内科
             * _child : [{"id":2,"parent_id":1,"name":"呼吸内科"},{"id":3,"parent_id":1,"name":"心血管内科"}]
             */

            public String id;
            public int parent_id;
            public String name;
            public int selecter ;
            public List<ChildBean> _child;

            public static class ChildBean implements Parcelable {
                /**
                 * id : 2
                 * parent_id : 1
                 * name : 呼吸内科
                 */

                public String id;
                public int selecter ;
                public int parent_id;
                public String name;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.id);
                    dest.writeInt(this.selecter);
                    dest.writeInt(this.parent_id);
                    dest.writeString(this.name);
                }

                public ChildBean() {
                }

                protected ChildBean(Parcel in) {
                    this.id = in.readString();
                    this.selecter = in.readInt();
                    this.parent_id = in.readInt();
                    this.name = in.readString();
                }

                public static final Parcelable.Creator<ChildBean> CREATOR = new Parcelable.Creator<ChildBean>() {
                    @Override
                    public ChildBean createFromParcel(Parcel source) {
                        return new ChildBean(source);
                    }

                    @Override
                    public ChildBean[] newArray(int size) {
                        return new ChildBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeInt(this.parent_id);
                dest.writeString(this.name);
                dest.writeInt(this.selecter);
                dest.writeTypedList(this._child);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.parent_id = in.readInt();
                this.name = in.readString();
                this.selecter = in.readInt();
                this._child = in.createTypedArrayList(ChildBean.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.data);
        dest.writeTypedList(this.list);
    }

    public GetDepartmenglistBean() {
    }

    protected GetDepartmenglistBean(Parcel in) {
        this.data = in.readString();
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<GetDepartmenglistBean> CREATOR = new Parcelable.Creator<GetDepartmenglistBean>() {
        @Override
        public GetDepartmenglistBean createFromParcel(Parcel source) {
            return new GetDepartmenglistBean(source);
        }

        @Override
        public GetDepartmenglistBean[] newArray(int size) {
            return new GetDepartmenglistBean[size];
        }
    };
}
