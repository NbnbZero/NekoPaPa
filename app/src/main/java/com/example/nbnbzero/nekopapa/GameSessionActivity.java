package com.example.nbnbzero.nekopapa;

import android.support.v4.app.Fragment;

/**
 * Created by NbnbZero on 2/22/2018.
 */

public class GameSessionActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new GameSessionFragment();
    }
}
