package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CityPlacesToWorkResultEntity implements Parcelable {

    public CityPlacesToWorkResultEntity() {
    }

    protected CityPlacesToWorkResultEntity(Parcel in) {
    }

    public static final Creator<CityPlacesToWorkResultEntity> CREATOR = new Creator<CityPlacesToWorkResultEntity>() {
        @Override
        public CityPlacesToWorkResultEntity createFromParcel(Parcel in) {
            return new CityPlacesToWorkResultEntity(in);
        }

        @Override
        public CityPlacesToWorkResultEntity[] newArray(int size) {
            return new CityPlacesToWorkResultEntity[size];
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
