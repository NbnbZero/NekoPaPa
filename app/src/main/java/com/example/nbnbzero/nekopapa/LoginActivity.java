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
        System.out.println("LoginActivity onPause");
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("LoginActivity onResume");
    }

    @Override
    public void onStop(){
        super.onStop();
        System.out.println("LoginActivity onStop");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("LoginActivity onRestoreInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        System.out.println("LoginActivity onSaveInstanceState");
    }

}

