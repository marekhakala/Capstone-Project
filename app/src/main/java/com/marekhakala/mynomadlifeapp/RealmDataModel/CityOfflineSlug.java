package com.marekhakala.mynomadlifeapp.RealmDataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityOfflineSlug extends RealmObject {

    @PrimaryKey
    private long id;

    private String slug;

    public CityOfflineSlug() {}

    public CityOfflineSlug(long id) {
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
}
