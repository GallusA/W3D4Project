package com.example.gallusawa.w3d4project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Assignment.FlickrPictures;
import Assignment.Item;

/**
 * Created by gallusawa on 8/17/17.
 */

public class DataAdaptor extends RecyclerView.Adapter<DataAdaptor.ViewHolder> {


    private static final String TAG = "DataAdapter";

    ArrayList<Item> itemList = new ArrayList<>();
    Context context;

    public DataAdaptor(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;
        ImageView myPictures;

        public ViewHolder(View itemV) {
            super(itemV);

            textView1 = (TextView) itemV.findViewById(R.id.textView1);
           textView2 = (TextView) itemV.findViewById(R.id.textView2);
            textView3= (TextView) itemV.findViewById(R.id.textView3);
            myPictures = (ImageView) itemV.findViewById(R.id.myPictures);
        }
    }

    @Override
    public DataAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pictures_items, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(DataAdaptor.ViewHolder holder, int p) {
        final Item item = itemList.get(p);
        Log.d(TAG, "onBindViewHolder: " + item.getMedia().getM());
        holder.textView1.setText(item.getTitle());
        holder.textView2.setText(item.getDateTaken());
        holder.textView3.setText(item.getLink());
        Glide.with(holder.itemView.getContext()).load(item.getMedia().getM()).into(holder.myPictures);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(R.drawable.background)
                        .setTitle("Options")
                        .setMessage("Display image:")
                        .setNegativeButton("Small Image", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Dialog dialogCustom = new Dialog(v.getContext());
                                dialogCustom.setContentView(R.layout.thumb);
                                ImageView ivThumb = (ImageView) dialogCustom.findViewById(R.id.myThumb);
                                Glide.with(v.getContext()).load(item.getMedia().getM()).into(ivThumb);
                                dialogCustom.show();
                            }
                        })
                        .setPositiveButton("Full Image", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(v.getContext(),FlickrPicActivity.class);
                                intent.putExtra("picture",item.getMedia().getM());
                                v.getContext().startActivity(intent);
                            }
                        })
                        .show();

                return true;
            }
        });


        Log.d(TAG, "onBindViewHolder: " + p);
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

}