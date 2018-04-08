/*
*   Created by NbnbZero and TeriyakiMayo on 3/26/2018.
*   Reference to https://www.youtube.com/watch?v=LhpDnKJpgTU
*/
package com.example.nbnbzero.nekopapa;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;

/**
 * Created by NbnbZero on 3/25/2018.
 */

public class GPSTracker extends Service implements LocationListener{

    private final Context context;

    boolean isGPSEnabled = false;
    boolean isNetWorkEnabled = false;
    
    Location location;
    protected LocationManager locationManager;

    private MapsActivity mapsActivity;

    public GPSTracker (Context context){
        this.context = context;
    }


    public Location getLocation(){


        try{
            locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            isNetWorkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                if(isGPSEnabled){
                    if(location==null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }

                if(location==null){
                    if(isNetWorkEnabled){

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,10,this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }

                    }
                }
            }

        }catch(Exception ex){

        }
        
        return location;

    }

    public void onLocationChanged(Location location){
        System.out.println("CHANGED " + mapsActivity);
        if(mapsActivity != null){

            mapsActivity.updateWildCats();
        }

        getLocation();
    }

    public void setMapsActivity(MapsActivity mapsActivity){
        this.mapsActivity = mapsActivity;
    }

    public void onStatusChanged(String Provider, int status, Bundle extras){

    }

    public void onProviderEnabled(String Provider){

    }

    public void onProviderDisabled(String Provider){

    }

    public IBinder onBind(Intent arg0){
        return null;
    }
}
