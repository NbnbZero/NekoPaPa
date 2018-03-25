package com.example.nbnbzero.nekopapa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nbnbzero.nekopapa.AccountDbSchema.AccountsTable;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */
public class AccountSingleton {
    private static AccountSingleton sAccount;

    private DbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    private static final String INSERT_STMT = "INSERT INTO " + AccountsTable.NAME + " (name, password) VALUES (?, ?)" ;

    public static AccountSingleton get(Context context) {
        if (sAccount == null) {
            sAccount = new AccountSingleton(context);
        }
        return sAccount;
    }

    private AccountSingleton(Context context) {
        mDbHelper = new DbHelper(context.getApplicationContext());
        mDatabase = mDbHelper.getWritableDatabase();
    }

    private static ContentValues getContentValues(Account account) {
        ContentValues values = new ContentValues();
        values.put(AccountsTable.Cols.NAME, account.getName());
        values.put(AccountsTable.Cols.PASSWORD, account.getPassword());

        return values;
    }

    /**
     * Add a new user Account to the database. This DB logic uses code from Jake Wharton:
     * http://jakewharton.com/kotlin-is-here/ (slide 61). It's much easier in Kotlin!
     *
     * @param account
     */
    public long addAccount(Account account) {
        ContentValues contentValues = getContentValues(account);
        long result = -1;

        mDatabase.beginTransaction();
        try {
            /*
            SQLiteStatement statement = mDatabase.compileStatement(INSERT_STMT);
            statement.bindString(1, account.getName());
            statement.bindString(2, account.getPassword());
            statement.executeInsert();
            */
            ContentValues cv = new ContentValues();
            result = mDatabase.insert(AccountsTable.NAME, null, contentValues);
            System.out.println("============================");
            System.out.println("result = " + result);
            System.out.println("============================");
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }

        return result;
    }

    /**
     * Delete all user accounts from the database. This DB logic uses code from Jake Wharton:
     * http://jakewharton.com/kotlin-is-here/ (slide 61). It's much easier in Kotlin!
     */
    public void deleteAllAccounts() {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(AccountsTable.NAME, null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

}
