package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

public class CityOfflineSlugEntity implements Parcelable {
    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.slug);
    }

    public CityOfflineSlugEntity() {
    }

    protected CityOfflineSlugEntity(Parcel in) {
        this.slug = in.readString();
    }

    public static final Parcelable.Creator<CityOfflineSlugEntity> CREATOR = new Parcelable.Creator<CityOfflineSlugEntity>() {
        @Override
        public CityOfflineSlugEntity createFromParcel(Parcel source) {
            return new CityOfflineSlugEntity(source);
        }

        @Override
        public CityOfflineSlugEntity[] newArray(int size) {
            return new CityOfflineSlugEntity[size];
        }
    };
}
