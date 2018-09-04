package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/27
 */

public class MedicationTemplateBean {

    /**
     * data : /yihuan/api/pharmacytpl/getList
     * list : [{"id":16,"name":"银行","goods_list":[{"id":5,"name":"十八味党参丸","num":1,"price":"0.00","remark":"快乐","medicine_id":1,"pharmacy_id":16}]},{"id":15,"name":"飞机场","goods_list":[]},{"id":14,"name":"飞机场","goods_list":[]},{"id":13,"name":"飞机场","goods_list":[]},{"id":12,"name":"飞机场","goods_list":[{"id":4,"name":"十八味党参丸","num":1,"price":"0.00","remark":"覅方法","medicine_id":1,"pharmacy_id":12}]},{"id":11,"name":"飞机场","goods_list":[{"id":3,"name":"十八味党参丸","num":1,"price":"0.00","remark":"覅方法","medicine_id":1,"pharmacy_id":11}]}]
     */

    private String data;
    private List<ListBean> list;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * id : 16
         * name : 银行
         * goods_list : [{"id":5,"name":"十八味党参丸","num":1,"price":"0.00","remark":"快乐","medicine_id":1,"pharmacy_id":16}]
         */

        private int id;
        private String name;
        private List<GoodsListBean> goods_list;
        private boolean isShow;

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean implements Parcelable {
            /**
             * id : 5
             * name : 十八味党参丸
             * num : 1
             * price : 0.00
             * remark : 快乐
             * medicine_id : 1
             * pharmacy_id : 16
             */

            private int id;
            private String name;
            private int num;
            private String price;
            private String remark;
            private int medicine_id;
            private int pharmacy_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getMedicine_id() {
                return medicine_id;
            }

            public void setMedicine_id(int medicine_id) {
                this.medicine_id = medicine_id;
            }

            public int getPharmacy_id() {
                return pharmacy_id;
            }

            public void setPharmacy_id(int pharmacy_id) {
                this.pharmacy_id = pharmacy_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeInt(this.num);
                dest.writeString(this.price);
                dest.writeString(this.remark);
                dest.writeInt(this.medicine_id);
                dest.writeInt(this.pharmacy_id);
            }

            public GoodsListBean() {
            }

            protected GoodsListBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.num = in.readInt();
                this.price = in.readString();
                this.remark = in.readString();
                this.medicine_id = in.readInt();
                this.pharmacy_id = in.readInt();
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
            dest.writeList(this.goods_list);
            dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.goods_list = new ArrayList<GoodsListBean>();
            in.readList(this.goods_list, GoodsListBean.class.getClassLoader());
            this.isShow = in.readByte() != 0;
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
