package com.marekhakala.mynomadlifeapp.DataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

import java.util.concurrent.TimeUnit;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class DataSourceModule {

    @Provides
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    HttpUrl provideHttpUrl() {
        return HttpUrl.parse(ConstantValues.ENDPOINT_URL);
    }

    @Provides
    @Named("Api")
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return client;
    }

    @Provides
    Retrofit provideRetrofit(@Named("Api") OkHttpClient client, HttpUrl url, Gson gson) {
        Timber.d("provideRetrofit()");
        return createRestAdapter(client, url, gson);
    }

    static Retrofit createRestAdapter(OkHttpClient okClient, HttpUrl url, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
    }

    @Provides
    MyNomadLifeAPI provideMyNomadLifeApi(Retrofit retrofit) {
        Timber.d("provideMyNomadLifeApi()");
        return retrofit.create(MyNomadLifeAPI.class);
    }
}
