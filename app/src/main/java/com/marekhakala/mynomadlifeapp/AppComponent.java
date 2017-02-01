package com.marekhakala.mynomadlifeapp;

import com.marekhakala.mynomadlifeapp.DataSource.DataSourceComponent;
import com.marekhakala.mynomadlifeapp.DataSource.DataSourceModule;
import com.marekhakala.mynomadlifeapp.Repository.RepositoryComponent;
import com.marekhakala.mynomadlifeapp.Repository.RepositoryModule;
import com.marekhakala.mynomadlifeapp.UI.UiComponent;
import com.marekhakala.mynomadlifeapp.UI.UiModule;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                DataSourceModule.class,
                RepositoryModule.class,
                UiModule.class
        }
)
public interface AppComponent extends DataSourceComponent, RepositoryComponent, UiComponent {
    void inject(MyNomadLifeApplication application);
}
