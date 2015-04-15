
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
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import java.util.Arrays;

public class SplashScreenFragmentSignIn extends Fragment {

    private LoginButton authButton;
    private UiLifecycleHelper uiHelper;
    private static final String TAG = "REGISTER SPLASH";
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


        authButton = (LoginButton) root.findViewById(R.id.authButton);
        authButton.setReadPermissions(Arrays.asList("email", "user_location", "user_birthday"));
        authButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    Log.e(TAG,"user: " + user + user.getName());
//                    username.setText("Username: " + user.getName());
//                    birthday.setText("birthday: " + user.asMap().get("birthday").toString());
//                    gender.setText("gender: " + user.asMap().get("gender").toString());
//                    email.setText("email: " + user.asMap().get("email").toString());
                    //Log.e(TAG,"userid: " +user.getId());
//                            Log.e(TAG,"email: " + user.asMap());
                    String imageURL = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
                    Log.e(TAG,"image: " + imageURL);
                    //new LoadProfileImage(profile_pic).execute(imageURL);
                } else {
                    Log.e(TAG,"You are not log in");
                }
            }
        });
    return root;
	}
    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                Log.d("MainActivity", "Facebook session opened.");
            } else if (state.isClosed()) {
                Log.d("MainActivity", "Facebook session closed.");
            }
        }
    };
}
