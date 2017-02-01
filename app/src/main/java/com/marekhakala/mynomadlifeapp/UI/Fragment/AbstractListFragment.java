package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;

import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class AbstractListFragment extends AbstractBaseFragment {

    @Inject
    IMyNomadLifeRepository mRepository;

    protected Subscription mSubscription = Subscriptions.empty();
    protected static final String EXTRA_STATE_SELECTED_POSITION = "selected_position";
    protected static final String EXTRA_STATE_USE_CACHE_DATA = "use_cache_data";

    protected boolean mUseCacheData = false;
    protected int mSelectedPosition = -1;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_main_list, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        mSelectedPosition = -1;

        if(bundle != null) {
            mUseCacheData = bundle.getBoolean(EXTRA_STATE_USE_CACHE_DATA);
            mSelectedPosition = bundle.getInt(EXTRA_STATE_SELECTED_POSITION, -1);
        }

        setupActivityListener();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(EXTRA_STATE_SELECTED_POSITION, mSelectedPosition);
        bundle.putBoolean(EXTRA_STATE_USE_CACHE_DATA, true);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mSubscription != null)
            mSubscription.unsubscribe();
    }

    @Override
    public void onStop() {
        super.onStop();

        if(mSubscription != null)
            mSubscription.unsubscribe();
    }

    protected void loadData() {
        if(mUseCacheData)
            loadDataFromDB();
        else
            loadDataFromAPI();
    }

    protected abstract void setupActivityListener();
    protected abstract void loadDataFromDB();
    protected abstract void loadDataFromAPI();
}
