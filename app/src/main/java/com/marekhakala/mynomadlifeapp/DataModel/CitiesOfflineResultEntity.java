package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CitiesOfflineResultEntity implements Parcelable {

    public CitiesOfflineResultEntity() {
    }

    protected CitiesOfflineResultEntity(Parcel in) {
    }

    public static final Creator<CitiesOfflineResultEntity> CREATOR = new Creator<CitiesOfflineResultEntity>() {
        @Override
        public CitiesOfflineResultEntity createFromParcel(Parcel in) {
            return new CitiesOfflineResultEntity(in);
        }

        @Override
        public CitiesOfflineResultEntity[] newArray(int size) {
            return new CitiesOfflineResultEntity[size];
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
