package com.example.gallusawa.w3d4project;

import android.database.Observable;

import Assignment.FlickrPictures;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


import static com.example.gallusawa.w3d4project.MainActivity.BASE_URL;


/**
 * Created by gallusawa on 8/17/17.
 */

public class RetrofitHelper {

    public static final String BASE_URL_TWO = "http://api.flickr.com/";
    public static final String PATH = "services/feeds/photos_public.gne";
    public static final String QUERY_TAG = "kitten";
    public static final String QUERY_FORMAT = "json";
    public static final String QUERY_CALL = "1";







    public static Retrofit create(){

        //create a logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        //create a custom client to add the interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static Call<FlickrPictures> getFflickrPicturesCall(){

        Retrofit retrofit = create();
       PictureService pictureService = retrofit.create(PictureService.class);
        return pictureService.getPicturedata(QUERY_TAG, QUERY_FORMAT, QUERY_CALL);

    }

    public static Observable<FlickrPictures> getFflickrPicturesObs(){

        Retrofit retrofit = create();
        PictureService pictureService = retrofit.create(PictureService.class);
        return pictureService.getPictureObservable(QUERY_TAG, QUERY_FORMAT, QUERY_CALL);

    }

    public interface PictureService{

        @GET(PATH)
        Call<FlickrPictures> getPicturedata(@Query("tag") String title, @Query("format") String media, @Query("call") String datetaken);


        @GET(PATH)
        Observable<FlickrPictures> getPictureObservable(@Query("tag") String title, @Query("format") String media, @Query("call") String datetaken);
    }

}