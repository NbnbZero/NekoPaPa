package com.example.nbnbzero.nekopapa;

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


    public void onPause(){
        super.onPause();
        System.out.println("LoginActivity onPause");
    }

    public void onResume(){
        super.onResume();
        System.out.println("LoginActivity onResume");
    }

}

