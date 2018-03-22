package com.example.nbnbzero.nekopapa;

import android.support.v4.app.Fragment;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/22/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip1
 */

public class GameSessionActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new GameSessionFragment();
    }
}
