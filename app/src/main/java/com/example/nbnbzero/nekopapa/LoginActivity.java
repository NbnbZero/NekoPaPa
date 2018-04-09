package com.example.nbnbzero.nekopapa;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class LoginActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

}

