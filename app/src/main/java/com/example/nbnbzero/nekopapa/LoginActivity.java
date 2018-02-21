package com.example.nbnbzero.nekopapa;

import android.support.v4.app.Fragment;

/**
 * Created by NbnbZero on 2/20/2018.
 */

public class LoginActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}

