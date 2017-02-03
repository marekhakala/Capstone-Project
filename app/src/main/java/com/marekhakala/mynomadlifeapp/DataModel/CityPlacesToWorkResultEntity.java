package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityPlacesToWorkResultEntity implements Parcelable {

    @Expose
    @SerializedName("total_entries")
    private Integer totalEntries;

    @Expose
    private List<CityPlaceToWorkEntity> entries;

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(Integer totalEntries) {
        this.totalEntries = totalEntries;
    }

    public List<CityPlaceToWorkEntity> getEntries() {
        return entries;
    }

    public void setEntries(List<CityPlaceToWorkEntity> entries) {
        this.entries = entries;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.totalEntries);
        dest.writeTypedList(this.entries);
    }

    public CityPlacesToWorkResultEntity() {
    }

    protected CityPlacesToWorkResultEntity(Parcel in) {
        this.totalEntries = (Integer) in.readValue(Integer.class.getClassLoader());
        this.entries = in.createTypedArrayList(CityPlaceToWorkEntity.CREATOR);
    }

    public static final Parcelable.Creator<CityPlacesToWorkResultEntity> CREATOR = new Parcelable.Creator<CityPlacesToWorkResultEntity>() {
        @Override
        public CityPlacesToWorkResultEntity createFromParcel(Parcel source) {
            return new CityPlacesToWorkResultEntity(source);
        }

        @Override
        public CityPlacesToWorkResultEntity[] newArray(int size) {
            return new CityPlacesToWorkResultEntity[size];
        }
    };
}
