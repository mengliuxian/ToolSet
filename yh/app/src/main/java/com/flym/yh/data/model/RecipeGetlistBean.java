package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecipeGetlistBean {


    /**
     * data : /yihuan/api/recipe/getList
     * list : [{"id":6,"name":"1234","content":"","total_price":"10.00","user_name":"1","age":12,"sex":"123","user_id":1,"status":1,"create_time":"2018-04-04 17:00:24","goods_list":[{"id":8,"num":2,"price":"10.00","name":"同意堂  益安林丸","image":null,"unit":null}]}]
     */

    public String data;
    public List<ListBean> list;

    public static class ListBean implements Parcelable {
        /**
         * id : 6
         * name : 1234
         * content :
         * total_price : 10.00
         * user_name : 1
         * age : 12
         * sex : 123
         * user_id : 1
         * status : 1
         * create_time : 2018-04-04 17:00:24
         * goods_list : [{"id":8,"num":2,"price":"10.00","name":"同意堂  益安林丸","image":null,"unit":null}]
         */

        public int id;
        public String name;
        public String content;
        public String total_price;
        public String user_name;
        public int age;
        public String sex;
        public int user_id;
        public int status;
        public String create_time;
        public ArrayList<GoodsGetlistBean.ListBean> goods_list;

        public static class GoodsListBean implements Parcelable {
            /**
             * id : 8
             * num : 2
             * price : 10.00
             * name : 同意堂  益安林丸
             * image : null
             * unit : null
             */

            public int id;
            public int num;
            public String price;
            public String name;
            public String image;
            public String unit;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeInt(this.num);
                dest.writeString(this.price);
                dest.writeString(this.name);
                dest.writeString(this.image);
                dest.writeString(this.unit);
            }

            public GoodsListBean() {
            }

            protected GoodsListBean(Parcel in) {
                this.id = in.readInt();
                this.num = in.readInt();
                this.price = in.readString();
                this.name = in.readString();
                this.image = in.readString();
                this.unit = in.readString();
            }

            public static final Creator<GoodsListBean> CREATOR = new Creator<GoodsListBean>() {
                @Override
                public GoodsListBean createFromParcel(Parcel source) {
                    return new GoodsListBean(source);
                }

                @Override
                public GoodsListBean[] newArray(int size) {
                    return new GoodsListBean[size];
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
            dest.writeString(this.name);
            dest.writeString(this.content);
            dest.writeString(this.total_price);
            dest.writeString(this.user_name);
            dest.writeInt(this.age);
            dest.writeString(this.sex);
            dest.writeInt(this.user_id);
            dest.writeInt(this.status);
            dest.writeString(this.create_time);
            dest.writeList(this.goods_list);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.content = in.readString();
            this.total_price = in.readString();
            this.user_name = in.readString();
            this.age = in.readInt();
            this.sex = in.readString();
            this.user_id = in.readInt();
            this.status = in.readInt();
            this.create_time = in.readString();
            this.goods_list = new ArrayList<GoodsGetlistBean.ListBean>();
            in.readList(this.goods_list, GoodsGetlistBean.ListBean.class.getClassLoader());
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
