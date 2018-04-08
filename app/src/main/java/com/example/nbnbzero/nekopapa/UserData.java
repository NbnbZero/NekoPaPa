package com.example.nbnbzero.nekopapa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
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
    public static int wildCatRespawnDistance = 60;
    public static GPSTracker gpsTracker = null;

    public static void updateWildCatListAccordingToCurrtPosit(Context context, GoogleMap map){
        GPSTracker gpsTracker = new GPSTracker(context);
        Location mLocation = gpsTracker.getLocation();
        if(mLocation == null){
            return;
        }
        
        if(wildCatList == null){
            generateWildCatList(context);
        }else{
            boolean existsApplicableWildCat = false;
            for(WildCat cat: wildCatList){
                LatLng latLng = new LatLng(cat.getLat(), cat.getLongi());
                if(distanceToTarget(latLng, context) <= wildCatRespawnDistance){
                    existsApplicableWildCat = true;
                    break;
                }
            }
            if(!existsApplicableWildCat){
                generateWildCatList(context);
            }
        }
        createWildCatMarkers(map, context);

    }

    public static void generateWildCatList(Context context){
        LatLng myLoc = myLocation(context);
        wildCatList = new WildCat[wildCatListLength];
        double rangeMin = -0.0008;
        double rangeMax = 0.0008;
        double latitude = myLoc.latitude;
        double longitude = myLoc.longitude;

        for(int i = 0; i< wildCatList.length; i++){
            Random rc = new Random();
            double catLat = latitude + rangeMin + (rangeMax - rangeMin) * rc.nextDouble();
            double catLong = longitude + rangeMin + (rangeMax - rangeMin) * rc.nextDouble();
            WildCat wc = new WildCat(1+rc.nextInt(4),1+rc.nextInt(3),
                    1+rc.nextInt(3),1+rc.nextInt(3),
                    catLat, catLong);
            wildCatList[i] = wc;
        }
    }

    private static void createWildCatMarkers(GoogleMap map, Context context){
        for(int i = 0; i<wildCatList.length;i++){
            LatLng catLoc = new LatLng(wildCatList[i].getLat(),wildCatList[i].getLongi());
            int result = catImgR(wildCatList[i]);
            Bitmap b = transformCatImg(result, context);
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(catLoc).zIndex(i)
                    .icon(BitmapDescriptorFactory.fromBitmap(b)));

        }
    }

    private static int catImgR(WildCat wildCat){
        int result = 0;
        switch(wildCat.getFur_color() + wildCat.getStripe_type() * 10){
            case 11:
                result = R.drawable.cat_body_pure_orange;
                break;
            case 12:
                result = R.drawable.cat_body_pure_yellow;
                break;
            case 13:
                result = R.drawable.cat_body_pure_red;
                break;
            case 21:
                result = R.drawable.cat_body_dots_orange;
                break;
            case 22:
                result = R.drawable.cat_body_dots_yellow;
                break;
            case 23:
                result = R.drawable.cat_body_dots_red;
                break;
            case 31:
                result = R.drawable.cat_body_zebra_orange;
                break;
            case 32:
                result = R.drawable.cat_body_zebra_yellow;
                break;
            case 33:
                result = R.drawable.cat_body_zebra_yellow;
                break;
            default:
                result = R.drawable.cat_body_pure_orange;
                break;
        }

        return result;
    }

    private static Bitmap transformCatImg(int result, Context context){
        Matrix matrix = new Matrix();
        matrix.postScale(0.2f, 0.2f);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), result);

        int width = bitmap.getWidth();
        int Height = bitmap.getHeight();

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, Height, matrix, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return newBitmap;
    }

    public static LatLng myLocation(Context context){
        GPSTracker gpsTracker = new GPSTracker(context);
        Location mLocation = gpsTracker.getLocation();
        double latitude = 0;
        double longitude = 0;
        if(mLocation != null){
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
        }else{
            toastMessage("GPS not applicable", context);
        }
        return new LatLng(latitude,longitude);
    }

    public static double distanceToTarget(LatLng target, Context context) {
        LatLng myloc = myLocation(context);
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
