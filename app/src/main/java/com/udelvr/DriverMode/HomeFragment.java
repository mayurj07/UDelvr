package com.udelvr.DriverMode;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.udelvr.CustomerMode.CustomerMainActivity;
import com.udelvr.DriverMode.Shipment.PackageListAdapter;
import com.udelvr.DriverMode.Shipment.PackageManager;
import com.udelvr.R;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PackageListAdapter mAdapter;

    private FloatingActionButton fab_driver;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fab_driver=(FloatingActionButton)getActivity().findViewById(R.id.fab_driver);
        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PackageListAdapter(PackageManager.getInstance().getPackages(), R.layout.row_package, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        fab_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CustomerMainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

    }



	public HomeFragment(){}


    public static Fragment newInstance(Context context) {
        HomeFragment f = new HomeFragment();
        return f;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_driver_home, container,false);

        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

}
