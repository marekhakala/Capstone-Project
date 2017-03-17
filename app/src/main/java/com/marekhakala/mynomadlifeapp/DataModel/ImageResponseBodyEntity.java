package com.marekhakala.mynomadlifeapp.DataModel;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.marekhakala.mynomadlifeapp.Database.CitiesContract;

public class ImageResponseBodyEntity implements Parcelable {

    String slug;

    boolean isData;

    byte[] imageData;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.slug);
        dest.writeByte(this.isData ? (byte) 1 : (byte) 0);
        dest.writeByteArray(this.imageData);
    }

    public ImageResponseBodyEntity() {
    }

    protected ImageResponseBodyEntity(Parcel in) {
        this.slug = in.readString();
        this.isData = in.readByte() != 0;
        this.imageData = in.createByteArray();
    }

    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(CitiesContract.CitiesOfflineImages.CITY_IMAGE_SLUG, this.slug);
        values.put(CitiesContract.CitiesOfflineImages.CITY_IMAGE_DATA, this.imageData);

        return values;
    }

    public static final Creator<ImageResponseBodyEntity> CREATOR = new Creator<ImageResponseBodyEntity>() {
        @Override
        public ImageResponseBodyEntity createFromParcel(Parcel source) {
            return new ImageResponseBodyEntity(source);
        }

        @Override
        public ImageResponseBodyEntity[] newArray(int size) {
            return new ImageResponseBodyEntity[size];
        }
    };
}
