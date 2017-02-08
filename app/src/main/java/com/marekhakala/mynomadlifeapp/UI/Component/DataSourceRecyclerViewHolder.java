package com.marekhakala.mynomadlifeapp.UI.Component;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DataSourceRecyclerViewHolder extends RecyclerView.ViewHolder {

    public LoadMoreItemView loadMoreView;

    public DataSourceRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public DataSourceRecyclerViewHolder(LoadMoreItemView loadMoreView) {
        super(loadMoreView);
        this.loadMoreView = loadMoreView;
    }
}
