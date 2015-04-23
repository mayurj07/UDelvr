package com.udelvr.SplashScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.udelvr.CustomerMode.CustomerMainActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.User.User;
import com.udelvr.RESTClient.User.UserController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SplashScreenFragmentRegisterNewUser extends Activity {

    private static String TAG = "register activity";
    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 101;
    ViewGroup root;
    Button btn_register;
    CircularImageView circularImageView;
    EditText editTextFullName, editTextEmail, editTextPassword, editTextMobile;
    User user;
    SplashScreenFragmentRegisterNewUser splashScreenFragmentRegisterNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_details);
        getActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        user = new User();
        splashScreenFragmentRegisterNewUser = this;

        editTextFullName = (EditText) findViewById(R.id.editText_fullname);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextMobile = (EditText) findViewById(R.id.editText_mobile);
        circularImageView = (CircularImageView) this.findViewById(R.id.profilepic);
        //circularImageView.setBorderColor(getResources().getColor());
        circularImageView.setBorderWidth(5);
        circularImageView.addShadow();
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            editTextFullName.setText(b.getString("name"));
            //editTextEmail.setText(b.getString("email"));
            //Log.e(TAG, "image: " + b.getString("image"));
            //new LoadProfileImage(circularImageView).execute(b.getString("image"));
        }

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
                user.setFullName(editTextFullName.getText().toString());
                user.setEmail(editTextEmail.getText().toString());
                user.setPassword(editTextPassword.getText().toString());
                user.setMobileNo(editTextMobile.getText().toString());

                UserController.registerNewUser(user, splashScreenFragmentRegisterNewUser);

            }
        });
    }

    public void startCustomerMainActivity() {
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
            } catch (Exception e) {
                Log.e("something", e.getMessage());
//                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            profileImg.setImageBitmap(result);
        }
    }

        public void onRegistrationfailed(String error) {
            Toast.makeText(this, "Registration Failed.Try Again!" + error, Toast.LENGTH_LONG).show();
        }

}
