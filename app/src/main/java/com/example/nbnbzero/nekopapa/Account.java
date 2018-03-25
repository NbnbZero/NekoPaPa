package com.example.nbnbzero.nekopapa;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class Account {
    private int id;
    private String mName;
    private String mPassword;

    public Account(int id, String name, String password) {
        this.id = id;
        mName = name;
        mPassword = password;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return mName;
    }

    public String getPassword() {
        return mPassword;
    }

    public static List<Account> getAccounts(Cursor cursor){
        List<Account> list = new ArrayList<Account>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Account account = new Account(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
            list.add(account);
        }
        return list;
    }
}
