
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

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.udelvr.CustomerMode.CustomerMainActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.User.User;

import java.util.List;

import static com.udelvr.RESTClient.User.UserController.signinUser;

public class SplashScreenFragmentSignIn extends Fragment implements Validator.ValidationListener {

    private LoginButton authButton;

    @NotEmpty
    @Email(message = "You must enter your email.")
    EditText email;
    @NotEmpty(message = "You must enter your password.")
    EditText password;
    private UiLifecycleHelper uiHelper;
    private static final String TAG = "REGISTER SPLASH";
    Button btn_register,btn_signin;
    ViewGroup root;
    User user;
    SplashScreenFragmentSignIn splashScreenFragmentSignIn;
    private View otherView;
    Validator validator;


	public static Fragment newInstance(Context context) {
        SplashScreenFragmentSignIn f = new SplashScreenFragmentSignIn();

		return f;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(getActivity(), statusCallback);
        uiHelper.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 root = (ViewGroup) inflater.inflate(R.layout.activity_spash_screen_signup, container,false);
        user= new User();
        splashScreenFragmentSignIn=this;
        View view = inflater.inflate(R.layout.activity_spash_screen_signup, container, false);
//        otherView = view.findViewById(R.layout.activity_register_details);
//        otherView.setVisibility(View.GONE);
        user = new User();
        email = (EditText) view.findViewById(R.id.editText_email);
        password = (EditText) view.findViewById(R.id.editText_password);

        btn_register = (Button) view.findViewById(R.id.button_signup);
        btn_signin = (Button) view.findViewById(R.id.button_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();


            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SplashScreenFragmentRegisterNewUser.class);
                startActivity(intent);

            }
        });


        authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        return view;
    }
//        authButton.setReadPermissions(Arrays.asList("email", "user_location", "user_birthday"));
//        authButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
//            @Override
//            public void onUserInfoFetched(GraphUser user) {
//                if (user != null) {
//                    String imageURL = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
//                    Intent i = new Intent(getActivity(), SplashScreenFragmentRegisterNewUser.class);
//                    Bundle b = new Bundle();
//                    b.putString("name", user.getName());
//                    b.putString("email", user.asMap().get("email").toString());
//                    b.putString("image", imageURL);
//                    i.putExtras(b);
//                    startActivity(i);
//
//                    Log.d(TAG,"user: " + user + user.getName());
//                    username.setText("Username: " + user.getName());
//                    birthday.setText("birthday: " + user.asMap().get("birthday").toString());
//                    gender.setText("gender: " + user.asMap().get("gender").toString());
//                    email.setText("email: " + user.asMap().get("email").toString());
                    //Log.e(TAG,"userid: " +user.getId());
//                            Log.e(TAG,"email: " + user.asMap());
//                    Log.d(TAG,"image: " + imageURL);
//                    //new LoadProfileImage(profile_pic).execute(imageURL);
//                } else {
//                    Log.e(TAG,"You are not log in ");
//                }
//            }
//        });
//    return root;
//	}
    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                Request.newMeRequest(session, new Request.GraphUserCallback() {

                    // callback after Graph API response with user
                    // object
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (user != null) {
                            // Set view visibility to true
//                            otherView.setVisibility(View.VISIBLE);

                            String imageURL = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
//                            Log.i(TAG, user + " " + user.getName() + " " + user.asMap().get("email").toString() + " " + imageURL);
                            Intent i = new Intent(getActivity(), SplashScreenFragmentRegisterNewUser.class);
                            Bundle b = new Bundle();
                            b.putString("name", user.getName());
                            //b.putString("email", user.asMap().get("email").toString());
                           // b.putString("image", imageURL);
                            i.putExtras(b);
                            startActivity(i);
                            // Set User name
//                            name.setText("Hello " + user.getName());
//                            // Set Gender
//                            gender.setText("Your Gender: "
//                                    + user.getProperty("gender").toString());
//                            location.setText("Your Current Location: "
//                                    + user.getLocation().getProperty("name")
//                                    .toString());
                        }
                    }
                }).executeAsync();
            } else if (state.isClosed()) {
                Log.i(TAG, "Logged out...");
//                otherView.setVisibility(View.GONE);
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
        Toast.makeText(getActivity(), "Invalid credentials!", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "OnActivityResult...");
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onValidationSucceeded() {
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        signinUser(user,splashScreenFragmentSignIn);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
