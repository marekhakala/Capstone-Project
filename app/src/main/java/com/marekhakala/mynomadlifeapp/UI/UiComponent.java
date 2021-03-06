package com.marekhakala.mynomadlifeapp.UI;

import com.marekhakala.mynomadlifeapp.UI.Activity.AboutActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.DetailViewActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.ExchangeRatesActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.FilterActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.MainListActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.OfflineDetailViewActivity;
import com.marekhakala.mynomadlifeapp.UI.Activity.SearchActivity;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.OfflinePlacesToWorkViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.Detail.PlacesToWorkViewFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.FavouritesListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.MainListFragment;
import com.marekhakala.mynomadlifeapp.UI.Fragment.OfflineModeListFragment;

public interface UiComponent {

    // Activity injections
    void inject(MainListActivity activity);
    void inject(FilterActivity activity);
    void inject(SearchActivity activity);
    void inject(DetailViewActivity activity);
    void inject(OfflineDetailViewActivity activity);
    void inject(ExchangeRatesActivity activity);
    void inject(AboutActivity activity);

    // Fragment injections
    void inject(AbstractListFragment fragment);
    void inject(MainListFragment fragment);
    void inject(FavouritesListFragment fragment);
    void inject(OfflineModeListFragment fragment);
    void inject(PlacesToWorkViewFragment fragment);
    void inject(OfflinePlacesToWorkViewFragment fragment);
}
