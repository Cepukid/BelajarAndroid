package com.example.yukngaji.ui.Item;

import android.os.Parcel;
import android.os.Parcelable;

public class itembokmark implements Parcelable {
    private String idayat;
    private String idsurat;
    private String namasurat;
    private String arti;

    public String getIdayat() {
        return idayat;
    }

    public void setIdayat(String idayat) {
        this.idayat = idayat;
    }

    public String getIdsurat() {
        return idsurat;
    }

    public void setIdsurat(String idsurat) {
        this.idsurat = idsurat;
    }

    public String getNamasurat() {
        return namasurat;
    }

    public void setNamasurat(String namasurat) {
        this.namasurat = namasurat;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idayat);
        dest.writeString(this.idsurat);
        dest.writeString(this.namasurat);
        dest.writeString(this.arti);
    }
    public itembokmark(){

    }
    private itembokmark(Parcel in) {
        this.idayat = in.readString();
        this.idsurat = in.readString();
        this.namasurat = in.readString();
        this.arti = in.readString();
    }
    public static final Creator<itembokmark> CREATOR = new Creator<itembokmark>() {
        @Override
        public itembokmark createFromParcel(Parcel source) {
            return new itembokmark(source);
        }
        @Override
        public itembokmark[] newArray(int size) {
            return new itembokmark[size];
        }
    };
}
