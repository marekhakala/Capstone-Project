package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CitiesResultEntity implements Parcelable {

    public CitiesResultEntity() {
    }

    protected CitiesResultEntity(Parcel in) {
    }

    public static final Creator<CitiesResultEntity> CREATOR = new Creator<CitiesResultEntity>() {
        @Override
        public CitiesResultEntity createFromParcel(Parcel in) {
            return new CitiesResultEntity(in);
        }

        @Override
        public CitiesResultEntity[] newArray(int size) {
            return new CitiesResultEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
