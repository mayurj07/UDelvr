
package com.udelvr.SplashScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udelvr.R;

public class SplashScreenFragmentHome extends Fragment {


	public static Fragment newInstance(Context context) {
        SplashScreenFragmentHome f = new SplashScreenFragmentHome();
		
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_spash_screen, null);
		return root;
	}
	
}
