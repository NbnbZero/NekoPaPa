package com.example.nbnbzero.nekopapa;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/22/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip1
 */

public class GameSessionActivity extends SingleFragmentActivity{
    static int count = 2;
    GameSessionFragment frag = null;
    public Account currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        currentUser = (Account) getIntent().getSerializableExtra("user_id");

        Timer mytime = new Timer();
        mytime.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                count++;
                sendDataToFragment();
            }
        }, 100, 100);

    }

    public void sendDataToFragment(){
        if(frag != null){
            frag.updateGauge();
        }
    }

    @Override
    protected Fragment createFragment() {
        frag = new GameSessionFragment();
        return frag;
    }
}
