package com.organize4event.organize.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ContactType implements Parcelable {

    public static final Parcelable.Creator<ContactType> CREATOR = new Parcelable.Creator<ContactType>() {
        @Override
        public ContactType createFromParcel(Parcel source) {
            return new ContactType(source);
        }

        @Override
        public ContactType[] newArray(int size) {
            return new ContactType[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("locale")
    private String locale;
    @SerializedName("code_enum")
    private int code_enum;
    @SerializedName("name")
    private String name;

    public ContactType() {
    }

    protected ContactType(Parcel in) {
        this.id = in.readInt();
        this.locale = in.readString();
        this.code_enum = in.readInt();
        this.name = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getCode_enum() {
        return code_enum;
    }

    public void setCode_enum(int code_enum) {
        this.code_enum = code_enum;
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
        dest.writeString(this.locale);
        dest.writeInt(this.code_enum);
        dest.writeString(this.name);
    }
}
