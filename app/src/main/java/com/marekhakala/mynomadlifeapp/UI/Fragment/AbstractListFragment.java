package com.marekhakala.mynomadlifeapp.UI.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerView;
import com.marekhakala.mynomadlifeapp.UI.Component.OnCityItemClickListener;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class AbstractListFragment extends AbstractBaseFragment
        implements DataSourceRecyclerView.OnActionListener, OnCityItemClickListener {

    public interface Listener {
        void onCitySelected(CityEntity entity, View view);
        void onCitySelected(CityOfflineEntity entity, View view);

        Listener DUMMY = new Listener() {
            @Override
            public void onCitySelected(CityEntity entity, View view) {}

            @Override
            public void onCitySelected(CityOfflineEntity entity, View view) {}
        };
    }

    @Bind(R.id.main_list_recycler_view)
    DataSourceRecyclerView mRecyclerView;

    @Inject
    IMyNomadLifeRepository mRepository;

    protected Subscription mSubscription = Subscriptions.empty();
    protected static final String EXTRA_STATE_SELECTED_POSITION = "selected_position";
    protected static final String EXTRA_STATE_USE_CACHE_DATA = "use_cache_data";

    protected boolean mUseCacheData = false;
    protected int mSelectedPosition = -1;
    protected Listener mListener = Listener.DUMMY;
    protected boolean mFavouriteClicked = false;

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
        mSelectedPosition = mRecyclerView.getCurrentPosition();
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

    @Override
    public void onDetach() {
        mListener = Listener.DUMMY;
        super.onDetach();
    }

    @Override
    public void onClicked(CityEntity entity, View view, int position) {
        mSelectedPosition = position;
        mListener.onCitySelected(entity, view);
    }

    @Override
    public void onClicked(CityOfflineEntity entity, View view, int position) {
        mSelectedPosition = position;
        mListener.onCitySelected(entity, view);
    }

    @Override
    public void onFavouriteClicked(String slug, boolean state) {

        if(!mFavouriteClicked) {
            mFavouriteClicked = true;

            if (state)
                mRepository.addFavouriteCitySlugs(slug);
            else
                mRepository.removeFavouriteCitySlugs(slug);

            mFavouriteClicked = false;
        }
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
