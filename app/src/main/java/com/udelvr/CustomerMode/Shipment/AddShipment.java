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
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.Map.CurrentLocationActivity;
import com.udelvr.Map.PlaceAutocompleteAdapter;
import com.udelvr.R;
import com.udelvr.RESTClient.Shipment.Shipment;
import com.udelvr.RESTClient.Shipment.ShipmentController;
import com.udelvr.RESTClient.User.UserController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// google place api

/**
 * Authored by sophiango and prasadshirsath
 */
public class AddShipment extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, Validator.ValidationListener {

    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 101;
    Button add_shipment_btn;
    ImageView timePicker, datePicker, camera, sourceAddressPick, destAddressPick;
    CircularImageView circularImageView;
    Bitmap image;
    AuthStore authStore;
    AddShipment mContext;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private Validator validator;

    private static final String TAG = "Date picker";
    // Widget GUI
    Button btnCalendar, btnTimePicker;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    @NotEmpty(message = "You must enter source address")
    private AutoCompleteTextView sourceAddress;
    @NotEmpty(message = "You must enter destination address")
    private AutoCompleteTextView destAddress;
    @NotEmpty(message = "You must enter recipient name")
    private EditText recipientsName;
    @NotEmpty(message = "You must enter package description")
    private EditText packageDesc;
    @NotEmpty(message = "You must enter package weight ")
    private EditText packageWeight;
    @NotEmpty(message = "You must enter pick up time")
    private EditText pickupTime;
    @NotEmpty(message = "You must enter pick up date")
    private EditText pickupDate ;
    private Shipment shipment;
    //private String static_src_address = "1 Washington Sq, San Jose, CA 95192";
    //private String static_dest_address = "4900 Marie P. DeBartolo Way, Santa Clara, CA";
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUND_US = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090)); // Mountain view bound

    private static String cur_address = null;
    private double source_lat, source_long, dest_lat, dest_long;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
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
        sourceAddress=(AutoCompleteTextView)findViewById(R.id.pickup_address);
//        if (shipment.getSourceAddress() != null){
//            // if shipment already has some value, reset
//            shipment.setSourceAddress(null);
//        }
        sourceAddress.setOnItemClickListener(mAutocompleteClickListener);
        mAdapter = new PlaceAutocompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUND_US, null);
        sourceAddress.setAdapter(mAdapter);
        sourceAddressPick = (ImageView) findViewById(R.id.choose_address);
        sourceAddressPick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), CurrentLocationActivity.class);
                Bundle source_bundle = new Bundle();
                source_bundle.putDouble("lat",source_lat);
                source_bundle.putDouble("long",source_long);
                i.putExtras(source_bundle);
               Log.i(TAG, "Put in bundle: " + source_lat + " " + source_long);
                startActivity(i);
            }
        });
        // row 3
        destAddress=(AutoCompleteTextView)findViewById(R.id.drop_address);
//        if (shipment.getDestinationAddress() != null){
//            // if shipment already has some value, reset
//            shipment.setDestinationAddress(null);
//        }
        destAddress.setOnItemClickListener(mAutocompleteClickListener);
        destAddress.setAdapter(mAdapter);
        destAddressPick = (ImageView) findViewById(R.id.choose_pickup_address);
        destAddressPick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                CurrentLocationActivity newClass = new CurrentLocationActivity(dest_lat,dest_long);
//                Log.i("cur souce: " + Double.valueOf(dest_lat).toString());

//                Uri streetUri = Uri.parse("google.streetview:cbll=37.337889,-121.937870");
//                Intent intent = new Intent(Intent.ACTION_VIEW,streetUri);
//                intent.setPackage("com.google.android.apps.maps");
//                startActivity(intent);

                Intent i = new Intent(getApplication(), CurrentLocationActivity.class);
                Bundle dest_bundle = new Bundle();
                dest_bundle.putDouble("lat",dest_lat);
                dest_bundle.putDouble("long",dest_long);
                i.putExtras(dest_bundle);
                Log.i(TAG, "Put in bundle: " + dest_lat + " " + dest_long);
                startActivity(i);
            }
        });

        // row 4
        packageDesc=(EditText)findViewById(R.id.item_desc);
        packageWeight=(EditText)findViewById(R.id.item_weight);
        camera = (ImageView)this.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        // row 5s
        pickupTime=(EditText)findViewById(R.id.pickup_time);
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        timePicker = (ImageView)this.findViewById(R.id.timePicker);
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
        datePicker = (ImageView)this.findViewById(R.id.datePicker);
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

                if ( shipment.getShipmentImage() == null) {
                    Toast.makeText(ApplicationContextProvider.getContext(), "Please add shipment picture.", Toast.LENGTH_LONG).show();
                } else {
                    validator.validate();
                }

//                shipment.setRecipientName(recipientsName.getText().toString());
//                shipment.setPackageDescription(packageDesc.getText().toString());
//                shipment.setPackageWeight(packageWeight.getText().toString());
//                shipment.setPickupTime(pickupTime.getText().toString());
//                shipment.setPickupDate(pickupDate.getText().toString());
//                shipment.setCustomerID(authStore.getUserId());
//                Log.i(TAG,"Ready to ship: " + shipment.getSourceAddress() + " " + shipment.getDestinationAddress());
                //ShipmentController.addNewShipment(mContext,authStore.getUserId(),shipment);
//                    Log.d("Udelvr", user.getprofilePhoto().getAbsolutePath());
//                    Intent intent = new Intent(getApplication(), CustomerMainActivity.class);
//                    startActivity(intent);

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        shipment.setRecipientName(recipientsName.getText().toString());
        shipment.setPackageDescription(packageDesc.getText().toString());
        shipment.setPackageWeight(packageWeight.getText().toString());
        shipment.setPickupTime(pickupTime.getText().toString());
        shipment.setPickupDate(pickupDate.getText().toString());
        shipment.setCustomerID(authStore.getUserId());
        Log.i(TAG,"Ready to ship: " + shipment.getSourceAddress() + " " + shipment.getDestinationAddress());
        ShipmentController.addNewShipment(mContext,authStore.getUserId(),shipment);
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

            final PlaceAutocompleteAdapter.PlaceAutocomplete item;
            final String placeId;
            item = mAdapter.getItem(position);
            placeId = String.valueOf(item.placeId);
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

            Log.i(TAG, "Place details received: " + place);
            if (shipment.getSourceAddress() == null){
                shipment.setSourceAddress(place.getAddress().toString());
                shipment.setSourceLat(Double.valueOf(place.getLatLng().latitude).toString());
                shipment.setSourceLong(Double.valueOf(place.getLatLng().longitude).toString());
                source_lat = place.getLatLng().latitude;
                source_long = place.getLatLng().longitude;
            }
            else{
                shipment.setDestinationAddress(place.getAddress().toString());
                shipment.setDestinationLat(Double.valueOf(place.getLatLng().latitude).toString());
                shipment.setDestinationLong(Double.valueOf(place.getLatLng().longitude).toString());
                dest_lat = place.getLatLng().latitude;
                dest_long = place.getLatLng().longitude;
            }
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
