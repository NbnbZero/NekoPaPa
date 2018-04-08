/*
* Created by NbnbZero and TeriyakiMayo on 3/26/2018.
*
**/

package com.example.nbnbzero.nekopapa;

import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    public GoogleMap getmMap(){
        return mMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(UserData.gpsTracker == null){
            UserData.gpsTracker = new GPSTracker(getApplicationContext());
        }

        UserData.gpsTracker.setMapsActivity(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UserData.updateWildCatListAccordingToCurrtPosit(getApplicationContext(), mMap);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        LatLng myloc = UserData.myLocation(getApplicationContext());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentManager manager = getFragmentManager();
                WildCatDialogFragment fragment = new WildCatDialogFragment();
                UserData.currentMarker = marker;
                fragment.show(manager, "dialog");
                return true;
            }
        });

    }

    public void updateWildCats(){
        if(mMap != null){
            UserData.updateWildCatListAccordingToCurrtPosit(getApplicationContext(), mMap);
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("MAP Destroyed");

    //    startActivity(new Intent(getApplicationContext(), GameSessionActivity.class));
        /*
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        */
    }

}
