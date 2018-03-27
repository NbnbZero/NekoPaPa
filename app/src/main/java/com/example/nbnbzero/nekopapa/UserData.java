package com.example.nbnbzero.nekopapa;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Random;

/*
* Created by NbnbZero and TeriyakiMayo on 3/26/2018.
*
**/

public class UserData {
    public static Account currentUser = null;
    public static List<Cat> catList = null;
    public static int currentCatId = 0;
    public static WildCat[] wildCatList = null;
    public static final int wildCatListLength = 20;
    public static Marker currentMarker = null;

    public static void generateWildCatList(){
        wildCatList = new WildCat[wildCatListLength];

        for(int i = 0; i< wildCatList.length; i++){
            Random rc = new Random();
            WildCat wc = new WildCat(1+rc.nextInt(4),1+rc.nextInt(3),1+rc.nextInt(3),1+rc.nextInt(3));
            wildCatList[i] = wc;
        }
    }

    public static double distanceToTarget(LatLng target, Context context){
        GPSTracker gpsTracker = new GPSTracker(context);
        Location mLocation = gpsTracker.getLocation();
        double latitude = mLocation.getLatitude();
        double longitude = mLocation.getLongitude();
        LatLng myloc = new LatLng(latitude,longitude);
        return getDistance(myloc, target);
    }

    private static double getDistance(LatLng start, LatLng end){
        double lat1 = (Math.PI/180)*start.latitude;
        double lat2 = (Math.PI/180)*end.latitude;

        double lon1 = (Math.PI/180)*start.longitude;
        double lon2 = (Math.PI/180)*end.longitude;

        double R = 6371;

        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;

        return d*1000;
    }

    public static void toastMessage(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
