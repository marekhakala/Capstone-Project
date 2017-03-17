package com.marekhakala.mynomadlifeapp.UI.Fragment.Detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityScoresEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.ColorBarView;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractBaseV4Fragment;
import com.marekhakala.mynomadlifeapp.Utilities.UtilityHelper;

import java.text.DecimalFormat;

import butterknife.Bind;

public class ScoresViewFragment extends AbstractBaseV4Fragment {

    public static final String FRAGMENT_TAG = "fragment_detail_view_scores";

    public static final String EXTRA_SCORES_ENTITY = "scores";

    @Bind(R.id.city_item_detail_view_scores_nomad_score_value)
    ColorBarView nomadScoreValue;

    @Bind(R.id.city_item_detail_view_scores_life_score_value)
    ColorBarView lifeScoreValue;

    @Bind(R.id.city_item_detail_view_scores_fun_value)
    ColorBarView funValue;

    @Bind(R.id.city_item_detail_view_scores_safety_value)
    ColorBarView safetyValue;

    @Bind(R.id.city_item_detail_view_scores_peace_value)
    ColorBarView peaceValue;

    @Bind(R.id.city_item_detail_view_scores_nightlife_value)
    ColorBarView nightlifeValue;

    @Bind(R.id.city_item_detail_view_scores_free_wifi_in_city_value)
    ColorBarView freeWifiInCityValue;

    @Bind(R.id.city_item_detail_view_scores_places_to_work_from_value)
    ColorBarView placesToWorkFromValue;

    @Bind(R.id.city_item_detail_view_scores_ac_or_heating_value)
    ColorBarView acOrHeatingValue;

    @Bind(R.id.city_item_detail_view_scores_friendly_to_foreigners_value)
    ColorBarView friendlyToForeignersValue;

    @Bind(R.id.city_item_detail_view_scores_gay_friendly_value)
    ColorBarView gayFriendlyValue;

    @Bind(R.id.city_item_detail_view_scores_startup_score_value)
    ColorBarView startupScoreValue;

    @Bind(R.id.city_item_detail_view_scores_english_speaking_value)
    ColorBarView englishSpeakingValue;

    public static ScoresViewFragment newInstance(CityScoresEntity entity) {
        ScoresViewFragment fragment = new ScoresViewFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SCORES_ENTITY, entity);

        fragment.setArguments(bundle);
        return fragment;
    }

    public ScoresViewFragment() {}

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        CityScoresEntity entity = (CityScoresEntity)getArguments().get(EXTRA_SCORES_ENTITY);

        if(entity != null) {
            if(entity.getNomadScore() != null)
                setupPercentageText(entity.getNomadScore(), nomadScoreValue);

            if(entity.getLifeScore() != null)
                setupPercentageText(entity.getLifeScore(), lifeScoreValue);

            if(entity.getFun() != null)
                setupPercentageText2(entity.getFun(), funValue);

            if(entity.getSafety() != null)
                setupPercentageText2(entity.getSafety(), safetyValue);

            if(entity.getPeace() != null)
                setupPercentageText2(entity.getPeace(), peaceValue);

            if(entity.getNightlife() != null)
                setupPercentageText2(entity.getNightlife(), nightlifeValue);

            if(entity.getFreeWifiInCity() != null)
                setupPercentageText2(entity.getFreeWifiInCity(), freeWifiInCityValue);

            if(entity.getPlacesToWork() != null)
                setupPercentageText2(entity.getPlacesToWork(), placesToWorkFromValue);

            if(entity.getAcOrHeating() != null)
                setupPercentageText2(entity.getAcOrHeating(), acOrHeatingValue);

            if(entity.getFriendlyToForeigners() != null)
                setupPercentageText2(entity.getFriendlyToForeigners(), friendlyToForeignersValue);

            if(entity.getGayFriendly() != null)
                setupPercentageText2(entity.getGayFriendly(), gayFriendlyValue);

            if(entity.getStartupScore() != null)
                setupPercentageText(entity.getStartupScore(), startupScoreValue);

            if(entity.getEnglishSpeaking() != null)
                setupPercentageText2(entity.getEnglishSpeaking(), englishSpeakingValue);
        }
    }

    private void setupPercentageText(Float value, ColorBarView view) {
        DecimalFormat twoDForm = new DecimalFormat(getResources().getString(R.string.city_item_detail_view_scores_format));
        String formattedStringFull = twoDForm.format(value) + getResources().getString(R.string.city_item_detail_view_scores_format_full);

        view.setText(formattedStringFull);
        int percentage = UtilityHelper.getIntegerFromDoublePercentage(value);
        view.setPercentage(percentage);
    }

    private void setupPercentageText2(Integer value, ColorBarView view) {
        DecimalFormat twoDForm = new DecimalFormat(getResources().getString(R.string.city_item_detail_view_scores_format));
        String formattedStringFull = twoDForm.format(value) + getResources().getString(R.string.city_item_detail_view_scores_format2_full);
        view.setText(formattedStringFull);

        Double percentageDouble = ((value.doubleValue()/5.0)*100);
        int percentage = Math.round(percentageDouble.floatValue());
        view.setPercentage(percentage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_view_scores, container, false);
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_TAG;
    }

    @Override
    protected void setupComponent(AppComponent component) {}
}
