package com.marekhakala.mynomadlifeapp.UI.Fragment.Detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflinePlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityPlaceToWorkEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.Repository.IMyNomadLifeRepository;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Adapter.CityPlacesToWorkDSRecyclerViewAdapter;
import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerView;
import com.marekhakala.mynomadlifeapp.UI.Component.OnCityPlaceToWorkItemClickListener;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractBaseFragment;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.util.ArrayList;
import javax.inject.Inject;

import butterknife.Bind;
import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class PlacesToWorkViewFragment extends AbstractBaseFragment implements OnCityPlaceToWorkItemClickListener {
    public static final String FRAGMENT_TAG = "fragment_detail_view_places_to_work";

    public static final String EXTRA_CITY_ENTITY = "city";
    protected static final String EXTRA_STATE_SELECTED_POSITION = "selected_position";
    protected static final String EXTRA_STATE_USE_CACHE_DATA = "use_cache_data";
    private static final String EXTRA_SEARCH_QUERY = "search_query";

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

    protected CityPlacesToWorkDSRecyclerViewAdapter mAdapter;
    protected Subscription mSubscription = Subscriptions.empty();

    protected String citySlug;

    protected boolean mUseCacheData = false;
    protected String mSearchQuery = "";
    protected int mSelectedPosition = -1;

    public static PlacesToWorkViewFragment newInstance(CityEntity entity) {
        PlacesToWorkViewFragment fragment = new PlacesToWorkViewFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_CITY_ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PlacesToWorkViewFragment() {}

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        setupSearchForm();

        CityEntity entity = (CityEntity) getArguments().get(EXTRA_CITY_ENTITY);
        setupInfoData(entity);

        if(bundle != null) {
            mUseCacheData = bundle.getBoolean(EXTRA_STATE_USE_CACHE_DATA, false);
            mSearchQuery = bundle.getString(EXTRA_SEARCH_QUERY, "");
            mSelectedPosition = bundle.getInt(EXTRA_STATE_SELECTED_POSITION, -1);
            searchEditText.setText(mSearchQuery);
        }

        mAdapter = new CityPlacesToWorkDSRecyclerViewAdapter(getContext(), new ArrayList<>());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
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
        if(!mSearchQuery.isEmpty()) {
            searchEditText.setText(mSearchQuery);
        }

        cleanButton.setOnClickListener(view -> {
            searchEditText.setText("");
            setupSearchQuery();
        });

        searchButton.setOnClickListener(view -> {
            setupSearchQuery();
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return setupSearchQuery();
            }
        });
    }

    protected boolean setupSearchQuery() {
        if(!mAdapter.isWaitingForData()) {
            if(!mAdapter.isWaitingForData()) {
                mSelectedPosition = 0;
                mSearchQuery = searchEditText.getText().toString();
                loadDataFromCache();
            }
            return true;
        }
        return false;
    }

    protected void setupInfoData(CityEntity entity) {
        citySlug = entity.getSlug();
    }

    public void loadData() {
        if(!mAdapter.isWaitingForData()) {
            if(mUseCacheData)
                loadDataFromCache();
            else
                loadDataFromAPI();
        }
    }

    protected void loadDataFromCache() {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        Realm realm = mRepository.getRealm();

        mSubscription = mRepository.cachedCityPlacesToWork(realm, citySlug, mSearchQuery)
                .subscribe(placesToWork -> {
                    mAdapter.updateData(placesToWork);

                    if (mSelectedPosition != -1)
                        mRecyclerView.scrollToPosition(mSelectedPosition);

                    UtilityHelper.closeDatabase(realm);
                }, throwable -> {
                    mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                    UtilityHelper.closeDatabase(realm);
                });
    }

    protected void loadDataFromAPI() {
        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.LOADING_STATE);

        if(mSubscription != null)
            mSubscription.unsubscribe();

        mSubscription = mRepository.cityPlacesToWork(citySlug, mSearchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placesToWork -> {
                    mAdapter.updateData(placesToWork);
                    mUseCacheData = true;
                }, throwable -> {
                    try {
                        mAdapter.setCurrentState(AbstractDataSourceRecyclerViewAdapter.StateType.ERROR_STATE);
                    } catch (Exception e) {
                        Timber.e("Error: " + e.getMessage());
                    }
                });
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
        String regexGpsFloatNumber = "^[-]{0,1}[0-9]+[.]{1}[0-9]+$";

        if(entity.getLat().matches(regexGpsFloatNumber) && entity.getLng().matches(regexGpsFloatNumber)) {
            String geoUri = "https://maps.google.com/maps?q=loc:" + entity.getLat() + "," + entity.getLng() + " (" + entity.getName() + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            mSelectedPosition = index;
            getContext().startActivity(intent);
        } else {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), getContext().getString(R.string.city_item_detail_view_places_to_work_gps_not_available), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onClicked(CityOfflinePlaceToWorkEntity entity, View view, int index) {}
}

