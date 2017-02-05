package com.marekhakala.mynomadlifeapp.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.marekhakala.mynomadlifeapp.DataModel.CitiesResultEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityEntity;
import com.marekhakala.mynomadlifeapp.DataModel.CityOfflineEntity;
import com.marekhakala.mynomadlifeapp.DataModel.ImageResponseBodyEntity;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityFavouriteSlug;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflineImage;
import com.marekhakala.mynomadlifeapp.RealmDataModel.CityOfflineSlug;
import com.marekhakala.mynomadlifeapp.Repository.RepositoryHelpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

public class UtilityHelper {
    public static byte[] bitmapToByteArray(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 90, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static void setupImageFromDatabase(CityOfflineImage cityOfflineImage, CityOfflineEntity cityOfflineEntity) {
        Bitmap cityImage = UtilityHelper.byteArrayToBitmap(cityOfflineImage.getImageData());
        cityOfflineEntity.setBitmapImage(cityImage);
    }

    public static ImageResponseBodyEntity processImageFromApi(String slug, Response<ResponseBody> response) {
        ImageResponseBodyEntity imageResponseBodyEntity = new ImageResponseBodyEntity();

        imageResponseBodyEntity.setData(response.isSuccessful());
        imageResponseBodyEntity.setSlug(slug);

        if(response.isSuccessful()) {
            try {
                imageResponseBodyEntity.setImageData(response.body().bytes());
            } catch (IOException e) {
                imageResponseBodyEntity.setData(false);
                Timber.d("Error");
            }
        }

        return imageResponseBodyEntity;
    }

    public static List<Boolean> processImagesFromApi(Realm innerRealm, Object[] imageResponses) {
        List<Boolean> imageResults = new ArrayList<>();

        if(imageResponses != null) {
            for(Object responseObject : imageResponses) {
                ImageResponseBodyEntity imageResponseBodyEntity = (ImageResponseBodyEntity) responseObject;

                if(imageResponseBodyEntity != null && imageResponseBodyEntity.isData()) {
                    RepositoryHelpers.saveImageToDatabase(innerRealm, imageResponseBodyEntity.getSlug(),
                            BitmapFactory.decodeByteArray(imageResponseBodyEntity.getImageData(), 0,
                                    imageResponseBodyEntity.getImageData().length));
                    imageResults.add(true);
                }
                imageResults.add(false);
            }
        }

        UtilityHelper.closeDatabase(innerRealm);
        return imageResults;
    }

    public static List<ImageResponseBodyEntity> processImageFromDatabase(String citySlug,
                                                                         RealmResults<CityOfflineImage> cityOfflineImages) {
        List<ImageResponseBodyEntity> images = new ArrayList<>();

        for(CityOfflineImage image : cityOfflineImages) {
            ImageResponseBodyEntity imageResponseBodyEntity = new ImageResponseBodyEntity();
            imageResponseBodyEntity.setSlug(citySlug);

            if(image.getImageData().length > 0) {
                imageResponseBodyEntity.setData(true);
                imageResponseBodyEntity.setImageData(image.getImageData());
            } else
                imageResponseBodyEntity.setData(false);

            images.add(imageResponseBodyEntity);
        }

        return images;
    }


    public static String cityIdFactory(CityFavouriteSlug result) {
        return result.getSlug();
    }

    public static String cityIdFactory(CityOfflineSlug result) {
        return result.getSlug();
    }

    public static CitiesResultEntity setupFavouriteOfflineItems(CitiesResultEntity result,
                                                                List<String> favouriteSlugs, List<String> offlineSlugs) {
        for(CityEntity city : result.getEntries()) {
            city.setFavourite(favouriteSlugs.contains(city.getSlug()));
            city.setOffline(offlineSlugs.contains(city.getSlug()));
        }

        return result;
    }

    public static List<CityEntity> setupFavouriteOfflineItems(List<CityEntity> cityEntities,
                                                              List<String> favouriteSlugs, List<String> offlineSlugs) {
        for(CityEntity city : cityEntities) {
            city.setFavourite(favouriteSlugs.contains(city.getSlug()));
            city.setOffline(offlineSlugs.contains(city.getSlug()));
        }

        return cityEntities;
    }

    public static List<CityOfflineEntity> setupOfflineFavouriteOfflineItems(List<CityOfflineEntity> cityEntities,
                                                                            List<String> favouriteSlugs, List<String> offlineSlugs) {
        for(CityOfflineEntity city : cityEntities) {
            city.setFavourite(favouriteSlugs.contains(city.getSlug()));
            city.setOffline(offlineSlugs.contains(city.getSlug()));
        }

        return cityEntities;
    }

    public static void closeDatabase(Realm realm) {
        if(realm != null && !realm.isClosed())
            realm.close();
    }
}
