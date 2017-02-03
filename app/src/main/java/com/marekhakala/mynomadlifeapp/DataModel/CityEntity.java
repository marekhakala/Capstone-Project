package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CityEntity implements Parcelable {

    public CityEntity() {
    }

    protected CityEntity(Parcel in) {
    }

    public static final Creator<CityEntity> CREATOR = new Creator<CityEntity>() {
        @Override
        public CityEntity createFromParcel(Parcel in) {
            return new CityEntity(in);
        }

        @Override
        public CityEntity[] newArray(int size) {
            return new CityEntity[size];
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
