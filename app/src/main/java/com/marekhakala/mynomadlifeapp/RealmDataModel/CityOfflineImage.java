package com.marekhakala.mynomadlifeapp.RealmDataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityOfflineImage extends RealmObject {

    @PrimaryKey
    private long id;

    private String slug;
    private byte[] imageData;

    public CityOfflineImage() {}

    public CityOfflineImage(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
