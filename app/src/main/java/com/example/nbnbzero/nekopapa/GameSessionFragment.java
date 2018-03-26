package com.example.nbnbzero.nekopapa;

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
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/22/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class GameSessionFragment extends Fragment implements View.OnClickListener {
    TextView userGoldView;
    AppCompatImageView catView;
    TextView catNameView;
    AppCompatImageView gaugeView;
    private Bitmap gaugeBitmap;
    TextView catMoodView;

    private String imageFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
            File.separator + "gauge.png";
    private float perc = 1;

    private List<Cat> catList = null;
    private int currentCatId = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gamesession, container, false);

        userGoldView = (TextView) v.findViewById(R.id.gold_panel);
        updateUserDisplay();

        catView = (AppCompatImageView) v.findViewById(R.id.cat_img);
        catNameView = (TextView) v.findViewById(R.id.cat_name_label);
        Button energyButton = (Button) v.findViewById(R.id.buy_energy_button);
        if (energyButton != null) {
            energyButton.setOnClickListener(this);
        }
        Button sellButton = (Button) v.findViewById(R.id.sell_cat_button);
        if (sellButton != null) {
            sellButton.setOnClickListener(this);
        }
        Button mapButton = (Button) v.findViewById(R.id.goto_map_button);
        if (mapButton != null) {
            mapButton.setOnClickListener(this);
        }
        Button nextButton = (Button) v.findViewById(R.id.next_cat_button);
        if (nextButton != null) {
            nextButton.setOnClickListener(this);
        }
        Button previousButton = (Button) v.findViewById(R.id.previous_cat_button);
        if (previousButton != null) {
            previousButton.setOnClickListener(this);
        }

        updateAndDisplayCatData();

        gaugeView = (AppCompatImageView) v.findViewById(R.id.gauge);
        gaugeBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gauge);
        setGauge(perc);

        catMoodView = (TextView) v.findViewById(R.id.mood_status);

        return v;
    }

    public void onClick(View view) {
        boolean displayChanged = false;
        GameSessionActivity activity;
        Account acc;
        Cat cat;
        switch (view.getId()) {
            case R.id.buy_energy_button:
                activity = (GameSessionActivity) getActivity();
                acc = activity.currentUser;
                cat = catList.get(currentCatId);
                if(acc.getGold() >= 30){
                    acc.setGold(acc.getGold() - 30);
                    acc.updateAccountToDB(getActivity());
                    cat.setEnergy(cat.getEnergy() + 10);
                    cat.updateCatToDB(activity);
                    updateUserDisplay();
                    updateAndDisplayCatData();
                }
                break;
            case R.id.sell_cat_button:
                activity = (GameSessionActivity) getActivity();
                acc = activity.currentUser;
                cat = catList.get(currentCatId);
                if(catList.size() >= 2){
                    int price = cat.catPrice();
                    cat.deleteCatInDB(activity);
                    acc.setGold(acc.getGold() + price);
                    updateUserDisplay();
                    updateAndDisplayCatData();
                    currentCatId = 0;
                }
                break;
            case R.id.next_cat_button:
                nextCatIndex();
                displayChanged = true;
                break;
            case R.id.previous_cat_button:
                previousCatIndex();
                displayChanged = true;
                break;
        }
        if(displayChanged){
            updateAndDisplayCatData();
        }
    }

    private void updateUserDisplay(){
        if(userGoldView != null){
            GameSessionActivity activity = (GameSessionActivity) getActivity();
            userGoldView.setText("Gold " + activity.currentUser.getGold());
        }
    }

    private void nextCatIndex(){
        currentCatId++;
        currentCatId = currentCatId == catList.size() ? 0 : currentCatId;
    }

    private void previousCatIndex(){
        currentCatId--;
        currentCatId = currentCatId == -1 ? catList.size() - 1 : currentCatId;
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
        System.out.println("CAT AMOUNTTTTTTTTT = " + catList.size());
        System.out.println("CAT ID = " + currentCatId);

        //set cat name
        if(catNameView != null){
            catNameView.setText(catList.get(currentCatId).getName());
        }

        //set cat image
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
                Cat cat = catList.get(currentCatId);
                boolean updated = cat.updateEnergyMood();
                if(updated){
                    cat.updateCatToDB(getActivity());
                    System.out.println("Gauge PPPPPPPPPP = " + ((float)cat.getEnergy() / (float)(cat.getStemina() * 25)));
                    System.out.println(cat.getEnergy() + " " + (float)(cat.getStemina() * 25));
                }
                setGauge((float)cat.getEnergy() / (float)(cat.getStemina() * 25));
                catMoodView.setText(Cat.moodName(cat.getMood()));
            }
        });

    }
}
