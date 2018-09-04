package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/26
 */

public class DrugDirectoryBean {

    /**
     * data : /yihuan/api/medicine/getList
     * list : [{"id":1,"name":"十八味党参丸","image":null,"content":"十八味党参丸","direction":"十八味党参丸","specification":"十八味党参丸","price":"10.00","sales":100,"cate_id":2}]
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
         * id : 1
         * name : 十八味党参丸
         * image : null
         * content : 十八味党参丸
         * direction : 十八味党参丸
         * specification : 十八味党参丸
         * price : 10.00
         * sales : 100
         * cate_id : 2
         */

        private int id;
        private String name;
        private String image;
        private String content;
        private String direction;
        private String specification;
        private String price;
        private int sales;
        private int cate_id;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

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
            dest.writeString(this.direction);
            dest.writeString(this.specification);
            dest.writeString(this.price);
            dest.writeInt(this.sales);
            dest.writeInt(this.cate_id);
            dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.image = in.readString();
            this.content = in.readString();
            this.direction = in.readString();
            this.specification = in.readString();
            this.price = in.readString();
            this.sales = in.readInt();
            this.cate_id = in.readInt();
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
