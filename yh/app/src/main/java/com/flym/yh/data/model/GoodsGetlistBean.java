package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GoodsGetlistBean implements Parcelable {


    /**
     * data : /yihuan/api/goods/getList
     * list : [{"id":1,"name":"同意堂  益安林丸","image":null,"content":"同意堂  益安林丸","cate_id":2,"price":"10.00","unit":null,"sales":100}]
     */

    public String data;
    public List<ListBean> list;

    public static class ListBean implements Parcelable {
        /**
         * id : 1
         * name : 同意堂  益安林丸
         * image : null
         * content : 同意堂  益安林丸
         * cate_id : 2
         * price : 10.00
         * unit : null
         * sales : 100
         */

        public int selecter;
        public int id;
        public String name;
        public String image;
        public String content;
        public int cate_id;
        public String price;
        public String unit;
        public int sales;
        public int num;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.image);
            dest.writeString(this.content);
            dest.writeInt(this.cate_id);
            dest.writeString(this.price);
            dest.writeString(this.unit);
            dest.writeInt(this.sales);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.image = in.readString();
            this.content = in.readString();
            this.cate_id = in.readInt();
            this.price = in.readString();
            this.unit = in.readString();
            this.sales = in.readInt();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
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
        dest.writeList(this.list);
    }

    public GoodsGetlistBean() {
    }

    protected GoodsGetlistBean(Parcel in) {
        this.data = in.readString();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GoodsGetlistBean> CREATOR = new Parcelable.Creator<GoodsGetlistBean>() {
        @Override
        public GoodsGetlistBean createFromParcel(Parcel source) {
            return new GoodsGetlistBean(source);
        }

        @Override
        public GoodsGetlistBean[] newArray(int size) {
            return new GoodsGetlistBean[size];
        }
    };
}
