package com.example.nbnbzero.nekopapa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by NbnbZero on 2/20/2018.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private final static String OPT_NAME = "name";
    private final String TAG = getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login, container, false);

        mUsernameEditText = (EditText) v.findViewById(R.id.username_text);
        mPasswordEditText = (EditText) v.findViewById(R.id.password_text);

        Button loginButton = (Button) v.findViewById(R.id.login_button);
        if (loginButton != null) {
            loginButton.setOnClickListener(this);
        }
        Button registerButton = (Button) v.findViewById(R.id.register_button);
        if (registerButton != null) {
            registerButton.setOnClickListener(this);
        }
        return v;
    }

    private void checkLogin() {
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                checkLogin();
                break;
            case R.id.register_button:
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        //.add(R.id.account_fragment_container, fragment)
                        .addToBackStack("account_fragment")
                        .commit();
                break;
        }
    }
}
