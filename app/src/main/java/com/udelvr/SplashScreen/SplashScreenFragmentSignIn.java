
package com.udelvr.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.udelvr.CustomerMode.CustomerMainActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.User.User;

import java.util.Arrays;

import static com.udelvr.RESTClient.User.UserController.signinUser;

public class SplashScreenFragmentSignIn extends Fragment {

    private LoginButton authButton;
    EditText email,password;
    private UiLifecycleHelper uiHelper;
    private static final String TAG = "REGISTER SPLASH";
    Button btn_register,btn_signin;
    ViewGroup root;
    User user;
    SplashScreenFragmentSignIn splashScreenFragmentSignIn;

	public static Fragment newInstance(Context context) {
        SplashScreenFragmentSignIn f = new SplashScreenFragmentSignIn();

		return f;
	}

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 root = (ViewGroup) inflater.inflate(R.layout.activity_spash_screen_signup, container,false);
        user= new User();
        email=(EditText)root.findViewById(R.id.editText_email);
        password=(EditText)root.findViewById(R.id.editText_password);
        splashScreenFragmentSignIn=this;

        btn_register = (Button) root.findViewById(R.id.button_signup);
        btn_signin = (Button)root.findViewById(R.id.button_signin);
        btn_signin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                signinUser(user,splashScreenFragmentSignIn);

            }
        });
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
                    Log.d(TAG,"user: " + user + user.getName());
//                    username.setText("Username: " + user.getName());
//                    birthday.setText("birthday: " + user.asMap().get("birthday").toString());
//                    gender.setText("gender: " + user.asMap().get("gender").toString());
//                    email.setText("email: " + user.asMap().get("email").toString());
                    //Log.e(TAG,"userid: " +user.getId());
//                            Log.e(TAG,"email: " + user.asMap());
                    String imageURL = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
                    Log.d(TAG,"image: " + imageURL);
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

    public void startCustomerMainActivity()
    {
        Intent intent = new Intent(getActivity(), CustomerMainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void OnsignInfailed() {
        Toast.makeText(getActivity(), "Invalid credencials!", Toast.LENGTH_LONG).show();

    }
}
