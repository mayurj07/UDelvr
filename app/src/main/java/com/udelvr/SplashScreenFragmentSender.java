
package com.udelvr;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashScreenFragmentSender extends Fragment {


	public static Fragment newInstance(Context context) {
        SplashScreenFragmentSender f = new SplashScreenFragmentSender();
		
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_spash_screen_sender, null);
		return root;
	}
	
}