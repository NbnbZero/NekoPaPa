package com.example.nbnbzero.nekopapa;

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

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/22/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class GameSessionFragment extends Fragment implements View.OnClickListener {
    AppCompatImageView gaugeView;
    private Bitmap gaugeBitmap;
    private String imageFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
            File.separator + "gauge.png";
    private float perc = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gamesession, container, false);

        AppCompatImageView img = (AppCompatImageView) v.findViewById(R.id.cat_ear);
        int height = img.getLayoutParams().height;
    //    img.getLayoutParams().height = height / 2;
        System.out.println("HHHHHHHHHHHHHHHEIGHT = " + img.getLayoutParams().height);
        if (img != null) {
            img.setOnClickListener(this);
        }

        gaugeView = (AppCompatImageView) v.findViewById(R.id.gauge);
        gaugeBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gauge);

        setGauge(perc);

        return v;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cat_ear:
                System.out.println("TTTTTTTTTTTTTTTTTT");
         //       perc -= .1f;
         //       setGauge(perc);
                break;
        }
    }

    private void setGauge(float perc){
        Matrix matrix = new Matrix();
        matrix.postScale(perc, 1f);

        int width = gaugeView.getLayoutParams().width;
        int gaugeHeight = gaugeView.getLayoutParams().height;

        Bitmap newBitmap = Bitmap.createBitmap(gaugeBitmap, 0, 0, width, gaugeHeight, matrix, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        gaugeView.setImageBitmap(newBitmap);
    }

    public void reduceGauge(){
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                System.out.println("GAUGE UPDATED=================");
                if(perc >= .1f) {
                    perc -= .1f;
                }
                setGauge(perc);
            }
        });

    }
}
