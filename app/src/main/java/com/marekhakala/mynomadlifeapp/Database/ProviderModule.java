package com.marekhakala.mynomadlifeapp.Database;

import android.app.Application;
import android.content.ContentResolver;

import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class ProviderModule {

    @Provides
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    ContentResolver provideContentResolver(Application application) {
        return application.getContentResolver();
    }

    @Provides
    BriteContentResolver provideBrideContentResolver(SqlBrite sqlBrite, ContentResolver contentResolver) {
        return sqlBrite.wrapContentProvider(contentResolver, Schedulers.io());
    }
}
