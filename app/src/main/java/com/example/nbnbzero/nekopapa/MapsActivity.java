package com.example.nbnbzero.nekopapa;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

       gpsTracker = new GPSTracker(getApplicationContext());
       mLocation = gpsTracker.getLocation();

        latitude = 50;//mLocation.getLatitude();
        longitude = 50;//mLocation.getLongitude();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myloc = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(myloc).title("I'm here..."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));
    }

    public void onStart(){
        super.onStart();
    }
    public void onStop(){
        super.onStop();
    }
    public void onPause(){
        super.onPause();
    }
    public void onResume(){
        super.onResume();
    }

    public void onLocationChanged(Location location){

    }

    public void onConnectionSuspended(int arg0){

    }

    public void onStatusChange(String provider, int status, Bundle extras){

    }


    public void onConnected(Bundle args0){

    }

    public void onConnectionFailed(ConnectionResult result){

    }


}
