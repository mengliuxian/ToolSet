package com.flym.fhzk.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class ClassifyBean implements Parcelable {


    public List<SkuInfoBean> skuInfo;

    public static class SkuInfoBean implements Parcelable {
        public SkuInfoBean(int id, boolean is, String name, int sort, String pic, int pid, List<SonBean> son) {
            this.id = id;
            this.is = is;
            this.name = name;
            this.sort = sort;
            this.pic = pic;
            this.pid = pid;
            this.son = son;
        }

        /**
         * id : 1
         * name : 女装
         * sort : 0
         * pic :
         * pid : 0
         * son : [{"id":2,"name":"棉服","sort":0,"pic":"","pid":1},{"id":6,"name":"毛衣","sort":1,"pic":"","pid":1}]
         */

        public int id;
        public boolean is;
        public String name;
        public int sort;
        public String pic;
        public int pid;
        public List<SonBean> son;

        public static class SonBean implements Parcelable {


            /**
             * id : 2
             * name : 棉服
             * sort : 0
             * pic :
             * <p>
             * pid : 1
             */

            public int id;
            public String name;
            public int sort;
            public String pic;
            public int pid;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeInt(this.sort);
                dest.writeString(this.pic);
                dest.writeInt(this.pid);
            }

            public SonBean() {
            }

            protected SonBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.sort = in.readInt();
                this.pic = in.readString();
                this.pid = in.readInt();
            }

            public static final Creator<SonBean> CREATOR = new Creator<SonBean>() {
                @Override
                public SonBean createFromParcel(Parcel source) {
                    return new SonBean(source);
                }

                @Override
                public SonBean[] newArray(int size) {
                    return new SonBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeByte(this.is ? (byte) 1 : (byte) 0);
            dest.writeString(this.name);
            dest.writeInt(this.sort);
            dest.writeString(this.pic);
            dest.writeInt(this.pid);
            dest.writeList(this.son);
        }

        protected SkuInfoBean(Parcel in) {
            this.id = in.readInt();
            this.is = in.readByte() != 0;
            this.name = in.readString();
            this.sort = in.readInt();
            this.pic = in.readString();
            this.pid = in.readInt();
            this.son = new ArrayList<SonBean>();
            in.readList(this.son, SonBean.class.getClassLoader());
        }

        public static final Creator<SkuInfoBean> CREATOR = new Creator<SkuInfoBean>() {
            @Override
            public SkuInfoBean createFromParcel(Parcel source) {
                return new SkuInfoBean(source);
            }

            @Override
            public SkuInfoBean[] newArray(int size) {
                return new SkuInfoBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.skuInfo);
    }

    public ClassifyBean() {
    }

    protected ClassifyBean(Parcel in) {
        this.skuInfo = new ArrayList<SkuInfoBean>();
        in.readList(this.skuInfo, SkuInfoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ClassifyBean> CREATOR = new Parcelable.Creator<ClassifyBean>() {
        @Override
        public ClassifyBean createFromParcel(Parcel source) {
            return new ClassifyBean(source);
        }

        @Override
        public ClassifyBean[] newArray(int size) {
            return new ClassifyBean[size];
        }
    };
}
