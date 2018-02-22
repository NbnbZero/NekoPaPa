package com.example.nbnbzero.nekopapa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by NbnbZero on 2/22/2018.
 */

public class GameSessionFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gamesession, container, false);
        v.setOnTouchListener(this);
        return v;
    }
}
