package com.example.nbnbzero.nekopapa;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class AccountFragment extends Fragment implements View.OnClickListener {
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtConfirm;
    private DbHelper mDbHelper;

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        mEtUsername = (EditText) v.findViewById(R.id.username);
        mEtPassword = (EditText) v.findViewById(R.id.password);
        mEtConfirm = (EditText) v.findViewById(R.id.password_confirm);
        Button btnAdd = (Button) v.findViewById(R.id.done_button);
        btnAdd.setOnClickListener(this);
        Button btnCancel = (Button) v.findViewById(R.id.cancel_button);
        btnCancel.setOnClickListener(this);
        Button btnExit = (Button) v.findViewById(R.id.exit_button);
        btnExit.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle(getResources().getString(R.string.account));
            }
        }
        catch (NullPointerException npe) {
            Log.e(TAG, "Could not set subtitle");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done_button:
                createAccount();
                break;
            case R.id.cancel_button:
                mEtUsername.setText("");
                mEtPassword.setText("");
                mEtConfirm.setText("");
                break;
            case R.id.exit_button:
                getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void createAccount() {
        //this.output = (TextView) this.findViewById(R.id.out_text);
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        String confirm = mEtConfirm.getText().toString();


        if ((password.equals(confirm)) && (!username.equals("")) && (!password.equals("")) && (!confirm.equals(""))) {
            DbManagerSingleton singleton = DbManagerSingleton.get(getActivity().getApplicationContext());

            SimpleDateFormat fmt = DateManager.fmt;
            Date date = new Date();
            //create new account
            String[] values = {username, password, "500", fmt.format(date)};
            long result = singleton.insert(AccountDbSchema.AccountsTable.NAME,
                    DbManagerSingleton.getContentValues(AccountDbSchema.AccountsTable.Cols.ColNames, values));
            if(result >= 0) {
                toastMessage("New record inserted");
                getActivity().getSupportFragmentManager().popBackStack();
            }else{
                accountErrorDialog("Record exists");
            }
            System.out.println("Account Insertion = " + result);

            //create cat for new account

            int user_id = (int)result;
            String[] catValues = {username + "'s cat", "25", "3", "1", "1", "1", "1", fmt.format(date), user_id + ""};
            String[] catValues2 = {username + "'s cat2", "25", "3", "1", "1", "2", "2", fmt.format(date), user_id + ""};
            result = Cat.insertCat(getActivity().getApplicationContext(), catValues);
            result = Cat.insertCat(getActivity().getApplicationContext(), catValues2);
            System.out.println("Cat Insertion = " + result);

            //print inserted cat
            String queryStr = "SELECT * FROM " + CatDbSchema.CatsTable.NAME + " WHERE _id = ? ";
            String[] whereArgs = new String[] {result + ""};
            Cursor cursor = new CursorWrapper(singleton.query(queryStr, whereArgs));
            Cat cat = Cat.getCats(cursor).get(0);
            System.out.println(cat.getId() + " " + cat.getEnergy() + " " + cat.getMood() + " " + cat.getLasttimeEnergyConsume() +
            cat.getUser_id());

        } else if ((username.equals("")) || (password.equals("")) || (confirm.equals(""))) {
            accountErrorDialog("Missing entry");
        } else if (!password.equals(confirm)) {
            accountErrorDialog("Password & Confirm Password do not match");
        } else {
            Log.e(TAG, "An unknown account creation error occurred.");
        }
    }

    private void toastMessage(String msg){
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void accountErrorDialog(String msg){
        FragmentManager manager = getFragmentManager();
        AccountErrorDialogFragment fragment = new AccountErrorDialogFragment();

        Bundle args = new Bundle();
        args.putString("msg", msg);
        fragment.setArguments(args);
        fragment.show(manager, "dialog");
    }
}
