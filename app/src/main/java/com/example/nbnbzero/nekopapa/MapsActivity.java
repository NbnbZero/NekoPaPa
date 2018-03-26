package com.example.nbnbzero.nekopapa;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mLocation;
    private Marker me;
    double catLat, catLong;
    double rangeMin = -0.0008;
    double rangeMax = 0.0008;
    private WildCat[] wildCat = {new WildCat(1, 2, 1, 3)};

    double latitude, longitude;

    /*public MapsActivity (WildCat[] wildCat){
        this.wildCat = wildCat;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gpsTracker = new GPSTracker(getApplicationContext());
        mLocation = gpsTracker.getLocation();

        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myloc = new LatLng(latitude,longitude);
        me = mMap.addMarker(new MarkerOptions().position(myloc));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));

        Random r = new Random();
        catLat = latitude + rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        catLong = longitude + rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        LatLng catLoc = new LatLng(catLat,catLong);

        for(int i = 0; i<wildCat.length;i++){
            int result = catImgR(wildCat[i]);
            Bitmap b = setGauge(result);
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(catLoc)
                    .icon(BitmapDescriptorFactory.fromBitmap(b)));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker marker) {
                    if(!marker.equals(me)) {
                        FragmentManager manager = getFragmentManager();
                        WildCatDialogFragment fragment = new WildCatDialogFragment();

                        Bundle args = new Bundle();
                        fragment.setArguments(args);
                        fragment.show(manager, "dialog");
                    }
                    return true;
                }
            });
        }

    }

    private int catImgR(WildCat wildCat){
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

    private Bitmap setGauge(int result){
        Matrix matrix = new Matrix();
        matrix.postScale(0.2f, 0.2f);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), result);

        int width = bitmap.getWidth();
        int Height = bitmap.getHeight();

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, Height, matrix, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return newBitmap;
    }

    public double getDistance(LatLng start,LatLng end){
        double lat1 = (Math.PI/180)*start.latitude;
        double lat2 = (Math.PI/180)*end.latitude;

        double lon1 = (Math.PI/180)*start.longitude;
        double lon2 = (Math.PI/180)*end.longitude;

        double R = 6371;

        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;

        return d*1000;
    }
}
