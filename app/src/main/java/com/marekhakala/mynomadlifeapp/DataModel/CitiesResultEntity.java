package com.marekhakala.mynomadlifeapp.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CitiesResultEntity implements Parcelable {

    @Expose
    @SerializedName("total_entries")
    private long totalEntries;

    @Expose
    @SerializedName("total_pages")
    private int totalPages;

    @Expose
    @SerializedName("per_page")
    private long perPage;

    @Expose
    @SerializedName("current_page")
    private long currentPage;

    @Expose
    private List<CityEntity> entries;

    private boolean loadMore = true;

    public long getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(long totalEntries) {
        this.totalEntries = totalEntries;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public List<CityEntity> getEntries() {
        return entries;
    }

    public void setEntries(List<CityEntity> entries) {
        this.entries = entries;
    }

    public boolean isLoadMore() {
        return loadMore;
    }

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.totalEntries);
        dest.writeInt(this.totalPages);
        dest.writeLong(this.perPage);
        dest.writeLong(this.currentPage);
        dest.writeList(this.entries);
    }

    public CitiesResultEntity() {
    }

    protected CitiesResultEntity(Parcel in) {
        this.totalEntries = in.readLong();
        this.totalPages = in.readInt();
        this.perPage = in.readLong();
        this.currentPage = in.readLong();
        this.entries = new ArrayList<>();
        in.readList(this.entries, CityEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<CitiesResultEntity> CREATOR = new Parcelable.Creator<CitiesResultEntity>() {
        @Override
        public CitiesResultEntity createFromParcel(Parcel source) {
            return new CitiesResultEntity(source);
        }

        @Override
        public CitiesResultEntity[] newArray(int size) {
            return new CitiesResultEntity[size];
        }
    };
}
