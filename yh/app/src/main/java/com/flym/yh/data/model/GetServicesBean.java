package com.flym.yh.data.model;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

/**
 * @author lishuqi
 * @date 2018/3/30
 */

public class GetServicesBean {

    /**
     * data : /yihuan/api/doctoritems/getList
     * list : [{"id":12,"title":null,"summary":null,"content":null,"price":"10.00","image":null,"file_id":0,"type_id":21,"create_time":"2018-03-30 14:51:00"}]
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
         * id : 12
         * title : null
         * summary : null
         * content : null
         * price : 10.00
         * image : null
         * file_id : 0
         * type_id : 21
         * create_time : 2018-03-30 14:51:00
         */

        private int id;
        private String title;
        private String summary;
        public String nickname;
        public String headimgurl;
        public String order_sn;
        public String total_price;
        private String content;
        private String price;
        private String image;
        private int file_id;
        private int type_id;
        private String create_time;
        public boolean selected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getFile_id() {
            return file_id;
        }

        public void setFile_id(int file_id) {
            this.file_id = file_id;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }


        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListBean listBean = (ListBean) o;
            return id == listBean.id &&
                    file_id == listBean.file_id &&
                    type_id == listBean.type_id &&
                    selected == listBean.selected &&
                    Objects.equals(title, listBean.title) &&
                    Objects.equals(summary, listBean.summary) &&
                    Objects.equals(content, listBean.content) &&
                    Objects.equals(price, listBean.price) &&
                    Objects.equals(image, listBean.image) &&
                    Objects.equals(create_time, listBean.create_time);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public int hashCode() {

            return Objects.hash(id, title, summary, content, price, image, file_id, type_id, create_time, selected);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeString(this.summary);
            dest.writeString(this.content);
            dest.writeString(this.price);
            dest.writeString(this.image);
            dest.writeInt(this.file_id);
            dest.writeInt(this.type_id);
            dest.writeString(this.create_time);
            dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.summary = in.readString();
            this.content = in.readString();
            this.price = in.readString();
            this.image = in.readString();
            this.file_id = in.readInt();
            this.type_id = in.readInt();
            this.create_time = in.readString();
            this.selected = in.readByte() != 0;
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
