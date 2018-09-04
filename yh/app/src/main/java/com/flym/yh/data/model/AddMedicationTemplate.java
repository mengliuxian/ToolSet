package com.flym.yh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lishuqi
 * @date 2018/3/26
 */

public class AddMedicationTemplate implements Parcelable {
    private String name;
    private int num;
    private String remark;
    private int medicine_id;
    public String price;

    public int getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMum() {
        return num;
    }

    public void setMum(int num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.num);
        dest.writeString(this.remark);
        dest.writeInt(this.medicine_id);
        dest.writeString(this.price);
    }

    public AddMedicationTemplate() {
    }

    protected AddMedicationTemplate(Parcel in) {
        this.name = in.readString();
        this.num = in.readInt();
        this.remark = in.readString();
        this.medicine_id = in.readInt();
        this.price = in.readString();
    }

    public static final Parcelable.Creator<AddMedicationTemplate> CREATOR = new Parcelable.Creator<AddMedicationTemplate>() {
        @Override
        public AddMedicationTemplate createFromParcel(Parcel source) {
            return new AddMedicationTemplate(source);
        }

        @Override
        public AddMedicationTemplate[] newArray(int size) {
            return new AddMedicationTemplate[size];
        }
    };
}
