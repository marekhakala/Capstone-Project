package com.marekhakala.mynomadlifeapp.UI.Fragment.Detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Adapter.OfflineCityPlacesToWorkDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerView;
import com.marekhakala.mynomadlifeapp.UI.Component.OnCityPlaceToWorkItemClickListener;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractBaseV4Fragment;
import com.marekhakala.mynomadlifeapp.UI.Loader.OfflinePlacesToWorkLoader;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import butterknife.Bind;

public class OfflinePlacesToWorkViewFragment extends AbstractBaseV4Fragment implements OnCityPlaceToWorkItemClickListener,
        LoaderManager.LoaderCallbacks<List<CityOfflinePlaceToWorkEntity>> {
    public static final String FRAGMENT_TAG = "fragment_offline_detail_view_places_to_work";
    protected static final int PLACES_TO_WORK_LIST_ID = 1;

    public static final String EXTRA_CITY_ENTITY = "city";
    protected static final String EXTRA_STATE_SELECTED_POSITION = "selected_position";
    protected static final String EXTRA_STATE_USE_CACHE_DATA = "use_cache_data";
    protected static final String EXTRA_SEARCH_QUERY = "search_query";

    @Bind(R.id.city_item_detail_view_places_to_work_list)
    DataSourceRecyclerView mRecyclerView;

    @Bind(R.id.city_item_detail_view_places_to_work_search_edit_text)
    EditText searchEditText;

    @Bind(R.id.city_item_detail_view_places_to_work_clean_button)
    ImageButton cleanButton;

    @Bind(R.id.city_item_detail_view_places_to_work_search_button)
    ImageButton searchButton;

    @Inject
    IMyNomadLifeRepository mRepository;

    @Inject
    OfflinePlacesToWorkLoader offlinePlacesToWorkLoader;

    protected OfflineCityPlacesToWorkDSRecyclerViewAdapter mAdapter;
    protected String citySlug;

    protected boolean mUseCacheData = false;
    protected String mSearchQuery = "";
    protected int mSelectedPosition = -1;

    public static OfflinePlacesToWorkViewFragment newInstance(CityOfflineEntity entity) {
        OfflinePlacesToWorkViewFragment fragment = new OfflinePlacesToWorkViewFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_CITY_ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public OfflinePlacesToWorkViewFragment() {}

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        setupSearchForm();

        CityOfflineEntity entity = (CityOfflineEntity) getArguments().get(EXTRA_CITY_ENTITY);
        setupInfoData(entity);

        if(bundle != null) {
            mUseCacheData = bundle.getBoolean(EXTRA_STATE_USE_CACHE_DATA, false);
            mSearchQuery = bundle.getString(EXTRA_SEARCH_QUERY, "");
            mSelectedPosition = bundle.getInt(EXTRA_STATE_SELECTED_POSITION, -1);
            searchEditText.setText(mSearchQuery);
        }

        mAdapter = new OfflineCityPlacesToWorkDSRecyclerViewAdapter(getActivity(), new ArrayList<>());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(PLACES_TO_WORK_LIST_ID, null, this);
        offlinePlacesToWorkLoader.setCitySlug(citySlug);
        loadData();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putBoolean(EXTRA_STATE_USE_CACHE_DATA, true);
        bundle.putInt(EXTRA_STATE_SELECTED_POSITION, mSelectedPosition);
        bundle.putString(EXTRA_SEARCH_QUERY, mSearchQuery);
    }

    protected void setupSearchForm() {
        if(!mSearchQuery.isEmpty())
            searchEditText.setText(mSearchQuery);

        cleanButton.setOnClickListener(view -> {
            searchEditText.setText("");
            mSearchQuery = "";
            setupSearchQuery();
        });

        searchButton.setOnClickListener(view -> setupSearchQuery());
        searchEditText.setOnEditorActionListener((v, actionId, event) -> setupSearchQuery());
    }

    protected boolean setupSearchQuery() {
        if(!mAdapter.isWaitingForData()) {
            mSelectedPosition = 0;
            mSearchQuery = searchEditText.getText().toString();
            loadDataFromCache();
            return true;
        }
        return false;
    }

    protected void setupInfoData(CityOfflineEntity entity) {
        citySlug = entity.getSlug();
    }

    public void loadData() {
        if(!mAdapter.isWaitingForData())
                loadDataFromCache();
    }

    protected void loadDataFromCache() {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);
        offlinePlacesToWorkLoader.setSearchQuery(mSearchQuery);
        getLoaderManager().getLoader(PLACES_TO_WORK_LIST_ID).forceLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_view_places_to_work, container, false);
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_TAG;
    }

    @Override
    protected void setupComponent(AppComponent component) {
        component.inject(this);
    }

    @Override
    public void onClicked(CityPlaceToWorkEntity entity, View view, int index) {
    }

    @Override
    public void onClicked(CityOfflinePlaceToWorkEntity entity, View view, int index) {
        String regexGpsFloatNumber = "^[-]{0,1}[0-9]+[.]{1}[0-9]+$";

        if(entity.getLat().matches(regexGpsFloatNumber) && entity.getLng().matches(regexGpsFloatNumber)) {
            String geoUri = "https://maps.google.com/maps?q=loc:" + entity.getLat() + "," + entity.getLng() + " (" + entity.getName() + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            mSelectedPosition = index;
            getActivity().startActivity(intent);
        } else {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), getActivity().getString(R.string.city_item_detail_view_places_to_work_gps_not_available), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public Loader<List<CityOfflinePlaceToWorkEntity>> onCreateLoader(int id, Bundle args) {
        return offlinePlacesToWorkLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<CityOfflinePlaceToWorkEntity>> loader, List<CityOfflinePlaceToWorkEntity> data) {
        mAdapter.updateData(data);

        if (mSelectedPosition != -1)
            mRecyclerView.scrollToPosition(mSelectedPosition);
    }

    @Override
    public void onLoaderReset(Loader<List<CityOfflinePlaceToWorkEntity>> loader) {
        mSelectedPosition = -1;
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.EMPTY_STATE);
    }
}