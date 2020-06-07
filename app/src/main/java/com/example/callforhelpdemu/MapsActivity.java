package com.example.callforhelpdemu;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker UserCurrentLocationMarker;
    private static final int req_code = 99;
    private int radius =10000;
    private double lat,lng;

    public static final String URL_txt = "url.txt";

    TextView text ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
        {
            CheckUserLocationPermission();
        }

        //************************* Obtain the SupportMapFragment and get notified when the map is ready to be used. *************************//
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        text = findViewById(R.id.search);

        text.setText(check("search.txt"));

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isConnected(MapsActivity.this))
                {
                    conditionCheck();
                }
                else
                {
                    buildDialog(MapsActivity.this).show();
                }
            }


        });


    }



    public void onClick(View v)
    {
        switch (v.getId())
        {
            //****************** SEARCH ANY ADDRESS IN EDIT TEXT ****************************//
            case R.id.search_button_id:
            if(isConnected(MapsActivity.this))
            {
                mMap.clear();
                final EditText addressField = (EditText) findViewById(R.id.Edit_search_id);
                String address = addressField.getText().toString();

                List<Address> addList = null;
                MarkerOptions userMakerOption = new MarkerOptions();


                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(this);

                    try {
                        addList = geocoder.getFromLocationName(address, 6);
                        if (addList != null) {
                            for (int i = 0; i < addList.size(); i++) {
                                Address userAddress = addList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMakerOption.position(latLng);
                                userMakerOption.title(address);
                                userMakerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                                mMap.addMarker(userMakerOption);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        String title = marker.getTitle();
                                        saveFile(title);
                                        addressField.setText("");
                                        return false;
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(this, "Location Not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Write a Location Name", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            else
            {
                buildDialog(MapsActivity.this).show();
            }

            case R.id.google:
            if(isConnected(MapsActivity.this))
            {
                WebActivity();
                break;

            }
            else
            {
                buildDialog(MapsActivity.this).show();
            }

        }

    }

    //***************** CHECK FILE & RETURN RESPECTIVE OPTION ************************//

    public void conditionCheck()
    {
        String Hospital = "hospital", Police = "police", Fire = "fire_station";
        Object transData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces =new GetNearbyPlaces();

        try {
            String cond = check("search.txt").trim();

            if (cond.equals("Hospital".trim())) {
                mMap.clear();
                String url = getUrl(lat,lng,Hospital);
                transData[0]=mMap;
                transData[1]=url;

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String title = marker.getTitle();
                        saveFile(title);
                        return false;
                    }
                });

                getNearbyPlaces.execute(transData);
            }
            else if (cond.equals("Police Station".trim())) {
                mMap.clear();
                String url = getUrl(lat,lng,Police);
                transData[0]=mMap;
                transData[1]=url;

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String title = marker.getTitle();
                        saveFile(title);
                        return false;
                    }
                });

                getNearbyPlaces.execute(transData);
            }
            else if (cond.equals("Fire Service".trim())) {
                mMap.clear();
                String url = getUrl(lat,lng,Fire);
                transData[0]=mMap;
                transData[1]=url;

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String title = marker.getTitle();
                        saveFile(title);
                        return false;
                    }
                });

                getNearbyPlaces.execute(transData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private String getUrl(double lat, double lng, String nearBy)
    {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + lat + "," + lng);
        googleURL.append("&radius=" + radius);
        googleURL.append("&type=" +nearBy);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyDXBnkOHIBSrmBRYsfcna085l2bojti88A");


        return googleURL.toString();

    }

    public void saveFile(String loc)
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(URL_txt, MODE_PRIVATE);
            fos.write(loc.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WebActivity(){

        Intent intent = new Intent(this,web.class);
        startActivity(intent);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiCleint();
            mMap.setMyLocationEnabled(true);
        }
    }

    //**************** ASKING PERMISSION FOR ALLOW ACCESS *********************//

    public boolean CheckUserLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},req_code);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},req_code);
            }
            return false;
        }
        else
        {
            return true;
        }

    }

    //****************** PERMISSION REQUEST RESPONSE FROM USER **************************//

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case req_code:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiCleint();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
                return;
        }

    }

    //****************** GOOGLE API BUILDING CONNECTION *********************//

    protected synchronized void buildGoogleApiCleint()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    //******************** USERS CURRENT LOCATION **************************//

    @Override
    public void onLocationChanged(Location location)
    {
        lat = location.getLatitude();
        lng = location.getLongitude();
        lastlocation = location;

        if(UserCurrentLocationMarker != null)
        {
            UserCurrentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        UserCurrentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));

        if(googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        }

    }

    //************************ MOVING OBJECT LOCATION *************************//

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);

        }

    }

    //************************** READ FILE AND RETURN STRING ************************//

    private String check(String File_Name){

        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while( (text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis0 != null) {
                try {
                    fis0.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return st;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else
                return false;
        } else
            return false;
    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.warn_icon);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or Wi-Fi to access this.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder;
    }

}
