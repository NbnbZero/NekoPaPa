package com.example.nbnbzero.nekopapa;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nbnbzero.nekopapa.AccountDbSchema.AccountsTable;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private DbManagerSingleton sDbManager;
    private DbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    private final static String OPT_NAME = "name";
    private final String TAG = getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String phoneNum = "123-456-7890";
        phoneNum = phoneNum.trim();
        System.out.println(phoneNum);
        System.out.println("LoginFragment onCreateView");
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

        if (sDbManager == null) {
            sDbManager = DbManagerSingleton.get(getActivity().getApplicationContext());
        }

        String queryStr = "SELECT * FROM " + AccountsTable.NAME + " WHERE " + AccountsTable.Cols.NAME +
                " = ? AND " + AccountsTable.Cols.PASSWORD + " = ?";
        String[] whereArgs = new String[] {username, password};
        Cursor cursor = new CursorWrapper(sDbManager.query(queryStr, whereArgs));

        if(cursor.getCount() > 0){
            cursor.moveToNext();
            System.out.println("USER_NAME ============== " + cursor.getString(cursor.getColumnIndex("_id")));
            toastMessage("Logged in successfully");
            getActivity().finish();
            startActivity(new Intent(getActivity(), GameSessionActivity.class));
        }else{
            toastMessage("User does not exist OR Password is wrong");
        }
    }

    private void toastMessage(String msg){
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                checkLogin();
                break;
            case R.id.register_button:
                FragmentManager fm = getFragmentManager();
                Fragment fragment = new AccountFragment();
                fm.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack("account_fragment")
                        .commit();
                break;
        }
    }

    public void onPause(){
        super.onPause();
        System.out.println("LoginFragment onPause");
    }

    public void onResume(){
        System.out.println("LoginFragment right before onResume");
        super.onResume();
        System.out.println("LoginFragment onResume");
    }


}
