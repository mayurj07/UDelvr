package com.udelvr.CustomerMode.Shipment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udelvr.R;

import java.util.List;

/**
 * Created by Trey Robinson on 8/3/14.
 * Copyright 2014 MindMine LLC.
 */
public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder>{

    private static List<Package> packages;
    private int rowLayout;
    private Context mContext;

    public PackageListAdapter(List<Package> packages, int rowLayout, Context context) {
        this.packages = packages;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Package aPackage = packages.get(i);
        viewHolder.packageName.setText(aPackage.recipientName);
        viewHolder.packageImage.setImageDrawable(new BitmapDrawable(mContext.getResources(), aPackage.image));
    }

    @Override
    public int getItemCount() {
        return packages == null ? 0 : packages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView packageName;
        public ImageView packageImage;

        public ViewHolder(View itemView) {
            super(itemView);
            packageName = (TextView) itemView.findViewById(R.id.recipientsName);

            packageImage = (ImageView)itemView.findViewById(R.id.packageThumbnailImage);
        }

    }

    public void add(Package apackage)
    {
        packages.add(apackage);

    }

    public void notifyDataSetChanges()
    {
        this.notifyDataSetChanged();
    }
}
