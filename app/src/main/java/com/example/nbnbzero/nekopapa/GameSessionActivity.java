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
    GameSessionFragment gameSessionFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                if(gameSessionFragment != null){
                    gameSessionFragment.updateGauge();
                }
            }
        }, 100, 100);

    }

    @Override
    protected Fragment createFragment() {
        gameSessionFragment = new GameSessionFragment();
        return gameSessionFragment;
    }
}
