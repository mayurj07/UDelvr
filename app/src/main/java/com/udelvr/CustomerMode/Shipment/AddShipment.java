package com.udelvr.CustomerMode.Shipment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import com.udelvr.Map.PlaceAutocompleteAdapter;
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
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
// google place api
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.Map.EditMapActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.Shipment.Shipment;
import com.udelvr.RESTClient.Shipment.ShipmentController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Authored by sophiango and prasadshirsath
 */
public class AddShipment extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 101;
    Button add_shipment_btn;
    ImageButton timePicker, datePicker, camera, sourceAddressPick, destAddressPick;
    CircularImageView circularImageView;
    Bitmap image;
    AuthStore authStore;
    AddShipment mContext;
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private static final String TAG = "Date picker";
    // Widget GUI
    Button btnCalendar, btnTimePicker;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private AutoCompleteTextView sourceAddress, destAddress;
    private EditText recipientsName, packageDesc, packageWeight, pickupTime, pickupDate ;
    private Shipment shipment;
    //private String static_src_address = "1 Washington Sq, San Jose, CA 95192";
    //private String static_dest_address = "4900 Marie P. DeBartolo Way, Santa Clara, CA";
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUND_US = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090)); // Mountain view bound

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shipment);
        mGoogleApiClient = new GoogleApiClient.Builder(AddShipment.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        authStore=new AuthStore(ApplicationContextProvider.getContext());
        mContext=this;
        shipment = new Shipment();
        // row 1
        recipientsName=(EditText)findViewById(R.id.recipient);
        // row 2
        sourceAddress=(AutoCompleteTextView)findViewById(R.id.input_address);
        sourceAddress.setOnItemClickListener(mAutocompleteClickListener);
        mAdapter = new PlaceAutocompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUND_US, null);
        sourceAddress.setAdapter(mAdapter);
        //shipment.setSourceAddress(sourceAddress.getText());
//        sourceAddress.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplication(), EditMapActivity.class);
//                startActivity(i);
//                sourceAddress.setText(static_src_address);
//                shipment.setSourceAddress(static_src_address);
//            }
//        });
        sourceAddressPick = (ImageButton) findViewById(R.id.choose_address);
//        sourceAddressPick.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplication(), EditMapActivity.class);
//                startActivity(i);
//                sourceAddress.setText(static_src_address);
//                shipment.setSourceAddress(static_src_address);
//            }
//        });
        // row 3
        destAddress=(AutoCompleteTextView)findViewById(R.id.input_pickup_address);
        destAddress.setOnItemClickListener(mAutocompleteClickListener);
        destAddress.setAdapter(mAdapter);
        //shipment.setDestinationAddress(destAddress);
//        destAddress.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplication(), EditMapActivity.class);
//                startActivity(i);
//                destAddress.setText(static_dest_address);
//                shipment.setDestinationAddress(static_dest_address);
//            }
//        });
        destAddressPick = (ImageButton) findViewById(R.id.choose_pickup_address);
//        destAddressPick.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplication(), EditMapActivity.class);
//                startActivity(i);
//                destAddress.setText(static_dest_address);
//                shipment.setDestinationAddress(static_dest_address);
//            }
//        });

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
//                shipment.setSourceLat("37.3351870");
//                shipment.setSourceLong("-121.8810720");
//                shipment.setDestinationLat("37.7749290");
//                shipment.setDestinationLong("-122.4194160");
//                shipment.setSourceAddress("1 Washington Sq, San Jose, CA 95192");
//                shipment.setDestinationAddress("4900 Marie P. DeBartolo Way, Santa Clara, CA");

                ShipmentController.addNewShipment(mContext,authStore.getUserId(),shipment);
//                    Log.d("Udelvr", user.getprofilePhoto().getAbsolutePath());
//                    Intent intent = new Intent(getApplication(), CustomerMainActivity.class);
//                    startActivity(intent);



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
                        pickupDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

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
//                    File file = new File(path, String.valueOf(System
//                            .currentTimeMillis()) + ".jpg");
//                    System.out.println("bitmap: " + bm);
                    File file=getOutputFromCamera();
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
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void addedShipmentSuccess()
    {
        Toast.makeText(ApplicationContextProvider.getContext(), "Add new shipment successfully!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void OnFailedResponse() {
        Toast.makeText(ApplicationContextProvider.getContext(), "Registation Failed!", Toast.LENGTH_LONG).show();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getApplicationContext(), "Clicked: " + item.description,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
//            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
//                    place.getId(), place.getAddress(), place.getPhoneNumber(),
//                    place.getWebsiteUri()));

            // Display the third party attributions if set.
//            final CharSequence thirdPartyAttribution = places.getAttributions();
//            if (thirdPartyAttribution == null) {
//                mPlaceDetailsAttribution.setVisibility(View.GONE);
//            } else {
//                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
//                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
//            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

//    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
//                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
//        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//
//    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnected(Bundle bundle) {
        mAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");

    }
}
