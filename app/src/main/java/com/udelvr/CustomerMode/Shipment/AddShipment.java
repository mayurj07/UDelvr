package com.udelvr.CustomerMode.Shipment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.CustomerMode.CustomerMainActivity;
import com.udelvr.Map.EditMapActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.User.UserController;
import com.udelvr.RESTClient.Shipment.Shipment;
import com.udelvr.RESTClient.Shipment.ShipmentController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Authored by sophiango and prasadshirsath
 */
public class AddShipment extends Activity {

    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 101;
    Button add_shipment_btn;
    ImageButton timePicker, datePicker, camera, sourceAddressPick, destAddressPick;
    CircularImageView circularImageView;
    Bitmap image;

    private static final String TAG = "Date picker";
    // Widget GUI
    Button btnCalendar, btnTimePicker;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private EditText recipientsName, sourceAddress, destAddress, packageDesc, packageWeight, pickupTime, pickupDate ;
    private Shipment shipment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shipment);
        shipment = new Shipment();
        // row 1
        recipientsName=(EditText)findViewById(R.id.recipient);
        // row 2
        sourceAddress=(EditText)findViewById(R.id.input_address);
        sourceAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), EditMapActivity.class);
                startActivity(i);
            }
        });
        sourceAddressPick = (ImageButton) findViewById(R.id.choose_address);
        sourceAddressPick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), EditMapActivity.class);
                startActivity(i);
            }
        });
        // row 3
        destAddress=(EditText)findViewById(R.id.input_pickup_address);
        destAddressPick = (ImageButton) findViewById(R.id.choose_pickup_address);

        // row 4
        packageDesc=(EditText)findViewById(R.id.item_desc);
        packageWeight=(EditText)findViewById(R.id.item_weight);
        camera = (ImageButton)this.findViewById(R.id.shipment_img);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        // row 5
        pickupTime=(EditText)findViewById(R.id.pickup_time);
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        timePicker = (ImageButton)this.findViewById(R.id.timePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        // row 6
        pickupDate=(EditText)findViewById(R.id.pickup_date);
        pickupDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        datePicker = (ImageButton)this.findViewById(R.id.datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // button
        add_shipment_btn = (Button)this.findViewById(R.id.button_add_shipment);
        add_shipment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipment.setRecipientName(recipientsName.getText().toString());
//                shipment.setSourceAddress(editTextEmail.getText().toString());
//                shipment.setDestinationAddress(editTextPassword.getText().toString());
                shipment.setPackageDescription(packageDesc.getText().toString());
                shipment.setPackageWeight(packageWeight.getText().toString());
                shipment.setPickupTime(pickupTime.getText().toString());
                shipment.setPickupDate(pickupDate.getText().toString());

//                shipment.setCustomerId(editTextMobile.getText().toString());
                shipment.setCustomerID("12345");

                // temp hardcode
//                shipment.setShipmentImage("https://s3-us-west-1.amazonaws.com/project.bucket1/5");
                shipment.setSourceLat("37.3351870");
                shipment.setSourceLong("-121.8810720");
                shipment.setDestinationLat("37.3351870");
                shipment.setDestinationLong("-121.8810720");
                shipment.setSourceAddress("1 Washington Sq, San Jose, CA 95192");
                shipment.setDestinationAddress("1 Washington Sq, San Jose, CA 95192");

                if(ShipmentController.addNewShipment(shipment)){
//                    Log.d("Udelvr", user.getprofilePhoto().getAbsolutePath());
//                    Intent intent = new Intent(getApplication(), CustomerMainActivity.class);
//                    startActivity(intent);
                    Toast.makeText(ApplicationContextProvider.getContext(), "Add new shipment successfully!", Toast.LENGTH_LONG).show();
                    addShipment();

                }else{
                    Toast.makeText(ApplicationContextProvider.getContext(), "Registation Failed!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }



    private void showDatePicker(){
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
                        // Display Selected date in textbox
                        pickupDate.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
//                        Log.e(TAG,"Date set: " + mYear + "," + mMonth + "," + mDay);
                    }
                }, mYear, mMonth, mDay);

        dpd.show();
    }

    private void showTimePicker(){
        // Process to get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        pickupTime.setText(hourOfDay + ":" + minute);
//                        Log.e(TAG,"Time set: " + mHour + "," + mMinute + ",");
                    }
                }, mHour, mMinute, false);

        tpd.show();
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
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
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    System.out.println("bitmap: " + bm);
                    camera.setImageBitmap(bm);
                    try {
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                        shipment.setShipmentImage(file);
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
                shipment.setShipmentImage(new File(getPath(selectedImageUri, this)));
                camera.setImageBitmap(bm);
            }
        }
    }
    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    void addShipment()
    {
        Package p = new Package();
        p.recipientName=recipientsName.getText().toString();
//        p.dateTime=""+mMonth+"/"+mDay+"/"+mYear+" "+mHour+":"+mMinute;
        p.dateTime=shipment.getPickupTime() + " " + shipment.getPickupDate();
        BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
        Bitmap bmp = BitmapFactory.decodeFile(shipment.getShipmentImage().getAbsolutePath(),btmapOptions);
        p.image= bmp;
        p.status=shipment.getStatus();
        PackageManager.getInstance().add(p);
        finish();
    }
}
