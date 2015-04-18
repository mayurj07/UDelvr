package com.udelvr.Slidingmenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udelvr.R;
import com.udelvr.Slidingmenu.model.Package;

import java.util.List;

/**
 * Created by Trey Robinson on 8/3/14.
 * Copyright 2014 MindMine LLC.
 */
public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder>{

    private List<Package> packages;
    private int rowLayout;
    private Context mContext;

    public PackageListAdapter(List<Package> packages, int rowLayout, Context context) {
        this.packages = packages;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public void add()
    {
        Package apackage=new Package();
        apackage.recipientName="Prasd";
        packages.add(apackage);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Package aPackage = packages.get(i);
        viewHolder.countryName.setText(aPackage.recipientName);
       // viewHolder.countryImage.setImageDrawable(mContext.getDrawable(aPackage.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {
        return packages == null ? 0 : packages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName;
        public ImageView countryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.countryName);
            countryImage = (ImageView)itemView.findViewById(R.id.countryImage);
        }

    }
}
