package com.example.nbnbzero.nekopapa;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/22/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class GameSessionFragment extends Fragment implements View.OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gamesession, container, false);

        AppCompatImageView loginButton = (AppCompatImageView) v.findViewById(R.id.cat_ear);
        if (loginButton != null) {
            loginButton.setOnClickListener(this);
        }

        return v;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cat_ear:
                System.out.println("TTTTTTTTTTTTTTTTTT");
                break;
        }
    }
}
