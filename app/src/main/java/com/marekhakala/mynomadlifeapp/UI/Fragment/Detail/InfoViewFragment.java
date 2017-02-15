package com.marekhakala.mynomadlifeapp.UI.Fragment.Detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractBaseFragment;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import butterknife.Bind;

public class InfoViewFragment extends AbstractBaseFragment {

    public static final String FRAGMENT_TAG = "fragment_detail_view_info";

    public static final String EXTRA_CITY_ENTITY = "city";
    public static final String EXTRA_CITY_ENTITY_TYPE = "type";

    @Bind(R.id.city_item_detail_view_info_rank_value)
    TextView rankValue;

    @Bind(R.id.city_item_detail_view_info_region_value)
    TextView regionValue;

    @Bind(R.id.city_item_detail_view_info_country_value)
    TextView countryValue;

    @Bind(R.id.city_item_detail_view_info_temperature_value)
    TextView temperatureValue;

    @Bind(R.id.city_item_detail_view_info_humidity_value)
    TextView humidityValue;

    @Bind(R.id.city_item_detail_view_info_internet_speed_value)
    TextView internetSpeedValue;

    @Bind(R.id.city_item_detail_view_info_population_value)
    TextView populationValue;

    @Bind(R.id.city_item_detail_view_info_gender_ratio_value)
    TextView genderRatioValue;

    @Bind(R.id.city_item_detail_view_info_religious_value)
    TextView religiousValue;

    @Bind(R.id.city_item_detail_view_info_city_currency_value)
    TextView cityCurrencyValue;

    public static InfoViewFragment newInstance(CityEntity entity) {
        InfoViewFragment fragment = new InfoViewFragment();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CITY_ENTITY_TYPE, ConstantValues.CITY_ENTITY);
        bundle.putParcelable(EXTRA_CITY_ENTITY, entity);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static InfoViewFragment newInstance(CityOfflineEntity entity) {
        InfoViewFragment fragment = new InfoViewFragment();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CITY_ENTITY_TYPE, ConstantValues.CITY_OFFLINE_ENTITY);
        bundle.putParcelable(EXTRA_CITY_ENTITY, entity);
        fragment.setArguments(bundle);

        return fragment;
    }

    public InfoViewFragment() {}

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        String cityEntityType = getArguments().getString(EXTRA_CITY_ENTITY_TYPE);

        if(cityEntityType != null && cityEntityType.equals(ConstantValues.CITY_OFFLINE_ENTITY)) {
            CityOfflineEntity entity = (CityOfflineEntity) getArguments().get(EXTRA_CITY_ENTITY);
            setupInfoData(entity);
        } else {
            CityEntity entity = (CityEntity) getArguments().get(EXTRA_CITY_ENTITY);
            setupInfoData(entity);
        }
    }

    protected void setupInfoData(CityEntity entity) {
        if(entity != null) {
            String naString = getContext().getString(R.string.na_text_placeholder);

            rankValue.setText((entity.getRank() != null) ? entity.getRank().toString() : naString);
            regionValue.setText((entity.getRegion() != null) ? entity.getRegion() : naString);
            countryValue.setText((entity.getCountry() != null) ? entity.getCountry() : naString);
            temperatureValue.setText((entity.getTemperatureC() != null && entity.getTemperatureF() != null)
                    ? UtilityHelper.getTemperature(entity, getContext()) : naString);
            humidityValue.setText((entity.getHumidity() != null) ? UtilityHelper.getHumidity(entity, getContext()) : naString);
            internetSpeedValue.setText((entity.getInternetSpeed() != null)
                    ? UtilityHelper.getInternetSpeed(entity.getInternetSpeed(), getContext()) : naString);
            populationValue.setText((entity.getPopulation() != null) ? UtilityHelper.getPopulation(entity.getPopulation(), getContext()) : naString);
            genderRatioValue.setText((entity.getGenderRatio() != null) ? entity.getGenderRatio() : naString);
            religiousValue.setText((entity.getReligious() != null) ? entity.getReligious() : naString);

            String noneString = getContext().getString(R.string.none_text_placeholder);
            if(entity.getCityCurrency() != null) {
                if(entity.getCityCurrency().toLowerCase().equals(noneString))
                    cityCurrencyValue.setText(ConstantValues.USD_CURRENCY);
                else
                    cityCurrencyValue.setText(entity.getCityCurrency());
            } else
                cityCurrencyValue.setText(naString);
        } else {
            String noneString = getContext().getString(R.string.na_text_placeholder);

            rankValue.setText(noneString);
            regionValue.setText(noneString);
            countryValue.setText(noneString);
            temperatureValue.setText(noneString);
            humidityValue.setText(noneString);
            internetSpeedValue.setText(noneString);
            populationValue.setText(noneString);
            genderRatioValue.setText(noneString);
            religiousValue.setText(noneString);
            cityCurrencyValue.setText(noneString);
        }
    }

    protected void setupInfoData(CityOfflineEntity entity) {
        if(entity != null) {
            String noneString = getContext().getString(R.string.na_text_placeholder);
            rankValue.setText((entity.getRank() != null) ? entity.getRank().toString() : noneString);
            regionValue.setText((entity.getRegion() != null) ? entity.getRegion() : noneString);
            countryValue.setText((entity.getCountry() != null) ? entity.getCountry() : noneString);
            temperatureValue.setText((entity.getTemperatureC() != null && entity.getTemperatureF() != null)
                    ? UtilityHelper.getTemperature(entity, getContext()) : noneString);
            humidityValue.setText((entity.getHumidity() != null) ? UtilityHelper.getHumidity(entity, getContext()) : noneString);
            internetSpeedValue.setText((entity.getInternetSpeed() != null)
                    ? UtilityHelper.getInternetSpeed(entity.getInternetSpeed(), getContext()) : noneString);
            populationValue.setText((entity.getPopulation() != null) ? UtilityHelper.getPopulation(entity.getPopulation(), getContext()) : noneString);
            genderRatioValue.setText((entity.getGenderRatio() != null) ? entity.getGenderRatio() : noneString);
            religiousValue.setText((entity.getReligious() != null) ? entity.getReligious() : noneString);
            cityCurrencyValue.setText((entity.getCityCurrency() != null) ? entity.getCityCurrency() : noneString);
        } else {
            String noneString = getContext().getString(R.string.na_text_placeholder);

            rankValue.setText(noneString);
            regionValue.setText(noneString);
            countryValue.setText(noneString);
            temperatureValue.setText(noneString);
            humidityValue.setText(noneString);
            internetSpeedValue.setText(noneString);
            populationValue.setText(noneString);
            genderRatioValue.setText(noneString);
            religiousValue.setText(noneString);
            cityCurrencyValue.setText(noneString);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_view_info, container, false);
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_TAG;
    }

    @Override
    protected void setupComponent(AppComponent component) {}
}
