package com.marekhakala.mynomadlifeapp.UI.Component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.marekhakala.mynomadlifeapp.R;

public class LoadMoreItemView extends FrameLayout {

    ProgressBar spinner;

    public LoadMoreItemView(Context context) {
        super(context);
        inflate(context, R.layout.item_load_more, this);

        spinner = (ProgressBar)findViewById(R.id.load_more_spinner);

        setLayoutParams(new DataSourceRecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.load_more_item_height)));
    }

    public void hideSpinner() {
        spinner.setVisibility(View.GONE);
    }

    public void showSpinner() {
        spinner.setVisibility(View.VISIBLE);
    }
}
