package com.udelvr.CustomerMode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.DriverMode.DriverMainActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.Driver.DriverDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import retrofit.RetrofitError;

import static com.udelvr.RESTClient.Driver.DriverController.addDriverDetails;

/**
 * Created by sophiango on 4/14/15.
 */
public class ApplyToBeDriver extends Activity {

    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 101;
    private static final String TAG = "driver application activity";
    Context mContext;
    ImageView circularImageView;
    Bitmap image;
    // Widget GUI
    private ImageButton datePicker, camera, close;
    private Button apply;
    private TextView date_expire;
    private EditText driver_license;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private AuthStore auth;
    ApplyToBeDriver applyToBeDriver;

    private DriverDetails driverDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = this;
        applyToBeDriver=this;
        super.onCreate(savedInstanceState);
        driverDetails = new DriverDetails();
        auth = new AuthStore(ApplicationContextProvider.getContext());
        setContentView(R.layout.new_driver_details_popup);
        driver_license = (EditText) findViewById(R.id.edittext_driver_licence);
        date_expire = (TextView) findViewById(R.id.edittext_expire_date);
        close = (ImageButton) findViewById(R.id.popoup_close);
        apply = (Button) findViewById(R.id.apply_btn);
        camera = (ImageButton) findViewById(R.id.camera_driver_licence);
        File licencePhoto;

        datePicker = (ImageButton) this.findViewById(R.id.datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                driverDetails.setdriverLicenseNo(driver_license.getText().toString());
                driverDetails.setlicenseExpiry(date_expire.getText().toString());
               addDriverDetails(applyToBeDriver, auth.getUserId(), driverDetails);

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
public void startDriverHomeActivity()
{
    Intent i = new Intent(mContext, DriverMainActivity.class);
    startActivity(i);
    finish();
}

    public void OnFailedResponse(RetrofitError error)
    {
        Toast.makeText(this,"There was some problem"+error.getMessage(),Toast.LENGTH_LONG).show();
        finish();
    }
    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date_expire.setText((monthOfYear + 1) + "/"
                                + dayOfMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
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
                    File f = new File(Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                    image = bm;
                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
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
                driverDetails.setLicencePhoto(new File(getPath(selectedImageUri, this)));
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                image = bm;
            }

        }
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
