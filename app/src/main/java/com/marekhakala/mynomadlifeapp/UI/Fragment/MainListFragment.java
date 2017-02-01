package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.view.View;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;

public class MainListFragment extends AbstractListFragment {

    public static final String FRAGMENT_TAG = "fragment_main_list";
    public static final String EXTRA_STATE_CURRENT_PAGE = "current_page";
    public static final String EXTRA_STATE_TOTAL_NUMBER_OF_PAGES = "total_number_of_pages";

    protected Integer mCurrentPage = 1;
    protected Integer mTotalNumberOfPages = 1;

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        if(bundle != null) {
            mCurrentPage = bundle.getInt(EXTRA_STATE_CURRENT_PAGE, 1);
            mTotalNumberOfPages = bundle.getInt(EXTRA_STATE_TOTAL_NUMBER_OF_PAGES, 1);
        }

        loadData();
    }

    @Override
    protected void setupActivityListener() {
        MainListActivity activity = (MainListActivity) getActivity();
        activity.setListener(new MainListActivity.OnFragmentActionsListener() {
            @Override
            public void onGoToTop() {}

            @Override
            public void onReloadData() {}

            @Override
            public void onReloadDataFromCache() {}
        });
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(EXTRA_STATE_CURRENT_PAGE, mCurrentPage);
        bundle.getInt(EXTRA_STATE_TOTAL_NUMBER_OF_PAGES, mTotalNumberOfPages);
    }

    @Override
    public void onDestroyView() {
        if(mSubscription != null)
            mSubscription.unsubscribe();
        super.onDestroyView();
    }

    @Override
    protected void loadDataFromDB() {}

    @Override
    protected void loadDataFromAPI() {}

    @Override
    public String getFragmentName() {
        return FRAGMENT_TAG;
    }

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }
}
