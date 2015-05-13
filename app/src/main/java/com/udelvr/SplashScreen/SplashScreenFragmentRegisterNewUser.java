package com.udelvr.SplashScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.CustomerMode.CustomerMainActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.User.User;
import com.udelvr.RESTClient.User.UserController;
import com.udelvr.SMSverification.SMSBroadcastReceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenFragmentRegisterNewUser extends Activity implements Validator.ValidationListener {

    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 101;
    private static String TAG = "register activity";
    ViewGroup root;
    Button btn_register;

    CircularImageView circularImageView;
    @NotEmpty(message = "You must enter your full name.")
    EditText editTextFullName;
    @NotEmpty
    @Email(message = "You must enter your email.")
    EditText editTextEmail;
    @NotEmpty
    @Password(message = "You must enter password.")
    EditText editTextPassword;
    @NotEmpty(message = "You must enter your mobile no.")
    EditText editTextMobile;
    static User user;
    SplashScreenFragmentRegisterNewUser splashScreenFragmentRegisterNewUser;

    Validator validator;
    ProgressDialog mProgressDialog = null;
    SMSBroadcastReceiver smsBroadcastReceiver = null;
    WaitTime wait = new WaitTime();
//profile pic from fb
    static File storagePath,fbImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_details);
        getActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        user = new User();
        splashScreenFragmentRegisterNewUser = this;
        validator = new Validator(this);
        validator.setValidationListener(this);

        editTextFullName = (EditText) findViewById(R.id.editText_fullname);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextMobile = (EditText) findViewById(R.id.editText_mobile);
        circularImageView = (CircularImageView) this.findViewById(R.id.profilepic);
        //circularImageView.setBorderColor(getResources().getColor());
        circularImageView.setBorderWidth(5);
        circularImageView.addShadow();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            editTextFullName.setText(b.getString("name"));
            if (b.getString("email")!=null) {
                editTextEmail.setText(b.getString("email"));
            }
            //Log.e(TAG, "image: " + b.getString("image");
            new LoadProfileImage(circularImageView).execute(b.getString("imageStr"));


        }
//        if(b.getString("email")!=null){
//            editTextEmail.setText(b.getString("email"));
//        }

//        Log.e(TAG, "image: " + b.getString("image"));
//        new LoadProfileImage(circularImageView).execute(b.getString("image"));
//        Bitmap bm = new LoadProfileImage(circularImageView).doInBackground(b.getString("image"));
//        circularImageView.setImageBitmap(bm);
//        File file = null;
//        URL url = new URL(b.getString("image"));
//        file = new URL(url).toURI();
//        user.setprofilePhoto(file);

//        circularImageView.setImageBitmap(bm);
        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btn_register = (Button) this.findViewById(R.id.button_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getprofilePhoto() == null) {
                    Toast.makeText(ApplicationContextProvider.getContext(), "Please add your profile picture.", Toast.LENGTH_LONG).show();
                } else {
                    validator.validate();
                }
            }
        });
    }

    public void startCustomerMainActivity() {
        Log.d("prasad","FB I'M HERE");
        Intent intent = new Intent(getApplication(), CustomerMainActivity.class);
        startActivity(intent);
        finish();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bm;
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);

                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);


                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    // f.delete();
                    OutputStream fOut = null;
//                    File file = new File(path, String.valueOf(System
//                            .currentTimeMillis()) + ".jpg");

                    File file = getOutputFromCamera();
                    circularImageView.setImageBitmap(bm);

                    try {
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                        user.setprofilePhoto(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, this);
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                user.setprofilePhoto(new File(getPath(selectedImageUri, this)));
                circularImageView.setImageBitmap(bm);


            }
        }
    }

    private File getOutputFromCamera() {

        File storageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Prasad");
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {


                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File imageFile = new File(storageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".png");

        return imageFile;
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onValidationSucceeded() {
        registerSMSBroadcastReceiver();
        UserController.sendSMSVerificationCode(editTextMobile.getText().toString().trim(), splashScreenFragmentRegisterNewUser);

    }

    void registerSMSBroadcastReceiver() {
        smsBroadcastReceiver = new SMSBroadcastReceiver(this);
        //smsBroadcastReceiver.setMainActivityHandler(this);
        IntentFilter fltr_smsreceived = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsBroadcastReceiver, fltr_smsreceived);
    }

    public void verifyMobileNo(String verificationCode) {
        smsBroadcastReceiver.setVerificationCode(verificationCode);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.setMessage("Verifying mobile No...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        wait.execute();
    }

    public void onMobileNoVerificationSuccess() {
        Toast.makeText(this, "Mobile no. verified successfully.", Toast.LENGTH_LONG).show();
        mProgressDialog.setMessage("Mobile no verified!");
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        user.setFullName(editTextFullName.getText().toString());
        user.setEmail(editTextEmail.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
        user.setMobileNo(editTextMobile.getText().toString());
        Log.d("prasad", "In mobile success.....");

        UserController.registerNewUser(user, splashScreenFragmentRegisterNewUser);
    }

    void unregisterSMSBroadcastReceiver() {
        try {
            unregisterReceiver(smsBroadcastReceiver);
        } catch (IllegalArgumentException exception) {

        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onSendSMSVerifiactionCodeFailure() {

    }

    public void onRegistrationfailed(String error) {
        Toast.makeText(this, "Registration Failed.Try Again!" + error, Toast.LENGTH_LONG).show();
    }

    public static class LoadProfileImage extends
            AsyncTask<String, Void, Bitmap> {
        CircularImageView profileImg;

        public LoadProfileImage() {
        }

        public LoadProfileImage(CircularImageView userImg) {
            this.profileImg = userImg;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();


                try {
                    storagePath = Environment.getExternalStorageDirectory();
                    fbImage= new File(storagePath,"myImage.jpg");

                    FileOutputStream fOut = new FileOutputStream(fbImage);
                    mIcon11.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.close();

                } finally {
                }
            } catch (Exception e) {
                Log.e("something", e.getMessage());
//                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            profileImg.setImageBitmap(result);
            user.setprofilePhoto(fbImage);
            Log.d("Prasad",""+fbImage.getPath());

        }
    }

    private class WaitTime extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        protected void onPostExecute() {
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            unregisterSMSBroadcastReceiver();
        }

        @Override
        protected void onCancelled() {
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            unregisterSMSBroadcastReceiver();
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {
            long delayInMillis = 30 * 1000;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    unregisterSMSBroadcastReceiver();
                }
            }, delayInMillis);
            return null;
        }
    }

}
