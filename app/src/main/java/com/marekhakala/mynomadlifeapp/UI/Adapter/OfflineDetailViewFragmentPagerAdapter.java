package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.CostOfLivingViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.InfoViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.OfflinePlacesToWorkViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.ScoresViewFragment;

import java.util.ArrayList;
import java.util.List;

public class OfflineDetailViewFragmentPagerAdapter extends FragmentPagerAdapter {

    protected Context context;
    protected CityOfflineEntity cityOffline;
    protected List<String> listOfTitles = new ArrayList<>();

    public OfflineDetailViewFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;

        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_scores_title));
        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_info_title));
        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_cost_of_living));
        listOfTitles.add(this.context.getString(R.string.city_item_detail_view_places_to_work));
    }

    public void setCityOfflineEntity(CityOfflineEntity cityOffline) {
        this.cityOffline = cityOffline;
    }

    @Override
    public int getCount() {
        return listOfTitles.size();
    }

    protected ScoresViewFragment createScoresView() {
        if(cityOffline != null)
            return ScoresViewFragment.newInstance(cityOffline.getScores());

        return null;
    }

    protected InfoViewFragment createInfoView() {
        if(cityOffline != null)
            return InfoViewFragment.newInstance(cityOffline);

        return null;
    }

    protected CostOfLivingViewFragment createCostOfLivingView() {
        if(cityOffline != null) {
            return CostOfLivingViewFragment.newInstance(cityOffline.getCostOfLiving(),
                    cityOffline.getCityCurrency(), cityOffline.getCityCurrencyRate());
        }

        return null;
    }

    protected OfflinePlacesToWorkViewFragment createPlacesToWorkView() {
        if(cityOffline != null) {
            return OfflinePlacesToWorkViewFragment.newInstance(cityOffline);
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