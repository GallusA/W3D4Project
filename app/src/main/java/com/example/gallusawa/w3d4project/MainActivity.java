package com.example.gallusawa.w3d4project;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Assignment.FlickrPictures;
import Assignment.Item;
import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static android.R.attr.bitmap;

import static com.example.gallusawa.w3d4project.R.id.imageView;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://api.flickr.com/services/feeds/photos_public.gne?tag=kitten&format=json&nojsoncallback=1";
    public static final String TAG = "MainActivity";
    RecyclerView myPictures;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Flickr Api");
        myPictures = (RecyclerView) findViewById(R.id.myImages);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAnimator = new DefaultItemAnimator();
        myPictures.setLayoutManager(layoutManager);
        myPictures.setItemAnimator(itemAnimator);


        final retrofit2.Call<FlickrPictures> flickrPicturesCall = RetrofitHelper.getFflickrPicturesCall();
        flickrPicturesCall.enqueue(new retrofit2.Callback<FlickrPictures>() {
            @Override
            public void onResponse(retrofit2.Call<FlickrPictures> call, retrofit2.Response<FlickrPictures> response) {
                ArrayList<Item> items = (ArrayList<Item>) response.body().getItems();
                DataAdaptor flirckrAdapter = new DataAdaptor(items);
                myPictures.setAdapter(flirckrAdapter);
                flirckrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<FlickrPictures> call, Throwable t) {

            }

        });


    }
}