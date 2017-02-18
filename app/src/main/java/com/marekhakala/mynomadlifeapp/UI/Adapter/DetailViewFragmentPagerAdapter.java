package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.CostOfLivingViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.InfoViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.PlacesToWorkViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.ScoresViewFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailViewFragmentPagerAdapter extends FragmentPagerAdapter {

    protected Context context;
    protected CityEntity city;
    protected CityOfflineEntity cityOffline;
    protected List<String> listOfTitles = new ArrayList<>();

    public DetailViewFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;

        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_scores_title));
        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_info_title));
        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_cost_of_living));
        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_places_to_work));
    }

    public void setCityEntity(CityEntity city) {
        this.city = city;
    }

    public void setCityOfflineEntity(CityOfflineEntity city) {
        this.cityOffline = city;
    }

    @Override
    public int getCount() {
        return listOfTitles.size();
    }

    protected ScoresViewFragment createScoresView() {
        if(city != null)
            return ScoresViewFragment.newInstance(city.getScores());

        return null;
    }

    protected InfoViewFragment createInfoView() {
        if(city != null)
            return InfoViewFragment.newInstance(city);

        return null;
    }

    protected CostOfLivingViewFragment createCostOfLivingView() {
        if(city != null) {
            return CostOfLivingViewFragment.newInstance(city.getCostOfLiving(),
                    city.getCityCurrency(), city.getCityCurrencyRate());
        }

        return null;
    }

    protected PlacesToWorkViewFragment createPlacesToWorkView() {
        if(city != null) {
            return PlacesToWorkViewFragment.newInstance(city);
        }

        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return createScoresView();
            case 1: return createInfoView();
            case 2: return createCostOfLivingView();
            case 3: return createPlacesToWorkView();
            default: return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listOfTitles.get(position);
    }
}