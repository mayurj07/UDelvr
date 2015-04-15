
package com.udelvr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class SplashScreenFragmentSignIn extends Fragment {

    private static final String TAG = "REGISTER SPLASH";
    private LoginButton authButton;
    private UiLifecycleHelper uiHelper;
    Button btn_register;
    ViewGroup root;

	public static Fragment newInstance(Context context) {
        SplashScreenFragmentSignIn f = new SplashScreenFragmentSignIn();

		return f;
	}



    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 root = (ViewGroup) inflater.inflate(R.layout.activity_spash_screen_signup, container,false);
        btn_register = (Button) root.findViewById(R.id.button_signup);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SplashScreenFragmentRegisterNewUser.class);
                startActivity(intent);

            }
        });
        return root;
	}
}
