package com.example.nbnbzero.nekopapa;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/22/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class GameSessionFragment extends Fragment implements View.OnClickListener {
    AppCompatImageView catView;
    TextView catNameView;
    AppCompatImageView gaugeView;
    private Bitmap gaugeBitmap;

    private String imageFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
            File.separator + "gauge.png";
    private float perc = 1;

    private List<Cat> catList = null;
    private int currentCatId = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gamesession, container, false);

        //Get all cats of user
        /*
        DbManagerSingleton singleton = DbManagerSingleton.get(getActivity().getApplicationContext());
        GameSessionActivity activity = (GameSessionActivity) getActivity();
        String queryStr = "SELECT * FROM " + CatDbSchema.CatsTable.NAME + " WHERE user_id = ? ";
        String[] whereArgs = new String[] {activity.currentUser.getId() + ""};
        Cursor cursor = new CursorWrapper(singleton.query(queryStr, whereArgs));
        catList = Cat.getCats(cursor);
*/
        catView = (AppCompatImageView) v.findViewById(R.id.cat_img);
//        catView.setImageResource(R.drawable.cat_body_pure_orange);
        catNameView = (TextView) v.findViewById(R.id.cat_name_label);
//        catNameView.setText(catList.get(currentCatId).getName());

        updateAndDisplayCatData();

        gaugeView = (AppCompatImageView) v.findViewById(R.id.gauge);
        gaugeBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gauge);
        setGauge(perc);

        return v;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cat_img:
                System.out.println("TTTTTTTTTTTTTTTTTT");
         //       perc -= .1f;
         //       setGauge(perc);
                break;
        }
    }

    private void updateAndDisplayCatData(){
        //retrieve all cat data
        DbManagerSingleton singleton = DbManagerSingleton.get(getActivity().getApplicationContext());
        GameSessionActivity activity = (GameSessionActivity) getActivity();
        String queryStr = "SELECT * FROM " + CatDbSchema.CatsTable.NAME + " WHERE user_id = ? ";
        String[] whereArgs = new String[] {activity.currentUser.getId() + ""};
        System.out.println("CURRRRRRRRRRRR USER ID = " + activity.currentUser.getId());
        Cursor cursor = new CursorWrapper(singleton.query(queryStr, whereArgs));
        catList = Cat.getCats(cursor);

        //set cat name
        if(catNameView != null){
            catNameView.setText(catList.get(currentCatId).getName());
        }

        if(catView != null){
            catView.setImageResource(catImgR(catList.get(currentCatId)));
        }

    }

    private int catImgR(Cat cat){
        int result = 0;
        switch(cat.getFur_color() + cat.getStripe_type() * 10){
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

    private void setGauge(float perc){
        if(perc == 0){
            perc = .01f;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(perc, 1f);


        int width = gaugeView.getLayoutParams().width;
        if(width == 0){
            width = 1;
        }

        int gaugeHeight = gaugeView.getLayoutParams().height;

        Bitmap newBitmap = Bitmap.createBitmap(gaugeBitmap, 0, 0, width, gaugeHeight, matrix, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        gaugeView.setImageBitmap(newBitmap);
    }

    public void updateGauge(){
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                /*
                System.out.println("GAUGE UPDATED=================");
                if(perc >= .1f) {
                    perc -= .1f;
                }
                setGauge(perc);
                */
                boolean updated = catList.get(currentCatId).updateEnergy();
        //        updated = false;
                if(updated){
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    DbManagerSingleton singleton = DbManagerSingleton.get(getActivity().getApplicationContext());
                    Cat cat = catList.get(currentCatId);
                    String[] catValues = {cat.getName(), cat.getEnergy() + "",
                            cat.getMood() + "", cat.getStemina() + "", cat.getCharacteristic() + "",
                            cat.getStripe_type() + "", cat.getFur_color() + "", fmt.format(cat.getLasttimeEnergyConsume()),
                            cat.getUser_id() + ""};
                    ContentValues tempCv = DbManagerSingleton.getContentValues(CatDbSchema.CatsTable.Cols.ColNames, catValues);
                    String whereClause = "_id = ?";
                    String[] whereArgs = {cat.getId() + ""};
                    int rows = singleton.update(CatDbSchema.CatsTable.NAME, tempCv, whereClause, whereArgs);
                    System.out.println("CAT UPDATED = " + rows);

                    System.out.println("Gauge PPPPPPPPPP = " + ((float)cat.getEnergy() / (float)(cat.getStemina() * 25)));
                    System.out.println(cat.getEnergy() + " " + (float)(cat.getStemina() * 25));
                    setGauge((float)cat.getEnergy() / (float)(cat.getStemina() * 25));
                }
            }
        });

    }
}
