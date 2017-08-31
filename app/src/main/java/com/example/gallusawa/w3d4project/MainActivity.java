package com.example.gallusawa.w3d4project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;



import java.util.ArrayList;
import Assignment.FlickrPictures;
import Assignment.Item;

import retrofit2.Call;
import retrofit2.Response;




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
            public void onResponse(Call<FlickrPictures> call, Response<FlickrPictures> response) {
                ArrayList<Item> items = (ArrayList<Item>) response.body().getItems();
               // adapter = new DataAdaptor(items);
                Log.d(TAG, "onResponse: " + "TEST2"+items);
                DataAdaptor dataAdaptor = new DataAdaptor(items);
                myPictures.setAdapter(dataAdaptor);
               dataAdaptor.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<FlickrPictures> call, Throwable t) {

            }
        });

    }
}