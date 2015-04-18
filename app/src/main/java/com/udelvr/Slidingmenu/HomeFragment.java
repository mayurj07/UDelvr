package com.udelvr.Slidingmenu;

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
import android.widget.Button;

import com.udelvr.R;
import com.udelvr.Shipment.AddShipment;
import com.udelvr.Slidingmenu.model.PackageManager;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    Button add_shipment_btn;
    private PackageListAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PackageListAdapter(PackageManager.getInstance().getPackages(), R.layout.row_package, getActivity());
        mRecyclerView.setAdapter(mAdapter);

    }



	public HomeFragment(){}


    public static Fragment newInstance(Context context) {
        HomeFragment f = new HomeFragment();
        return f;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container,false);
        add_shipment_btn = (Button) root.findViewById(R.id.button_add_shipment);
        add_shipment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.add();
                Intent i = new Intent(getActivity(), AddShipment.class);
                startActivity(i);


            }
        });




        return root;
    }


}
