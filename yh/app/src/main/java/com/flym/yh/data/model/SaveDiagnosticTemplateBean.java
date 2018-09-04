package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author lishuqi
 * @date 2018/3/26
 */

public class SaveDiagnosticTemplateBean {

    /**
     * data : /yihuan/api/diagnosetpl/getList
     * list : [{"id":2,"name":"模板2号"},{"id":1,"name":"模板1号"}]
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
         * id : 2
         * name : 模板2号
         */

        private int id;
        private String name;
        /**
         * 用来判断删除
         */
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
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
