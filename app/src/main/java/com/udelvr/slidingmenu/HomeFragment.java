package com.udelvr.slidingmenu;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.udelvr.AddShipment;
import com.udelvr.SplashScreenFragmentRegisterNewUser;

import com.udelvr.R;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}

    Button add_shipment_btn;

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
                Intent i = new Intent(getActivity(), AddShipment.class);
                startActivity(i);
            }
        });
        return root;
    }
}
