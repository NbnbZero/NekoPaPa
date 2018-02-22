package com.example.nbnbzero.nekopapa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.example.nbnbzero.nekopapa.AccountDbSchema.AccountsTable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NbnbZero on 2/20/2018.
 */
public class AccountSingleton {
    private static AccountSingleton sAccount;

    private AccountDbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    private static final String INSERT_STMT = "INSERT INTO " + AccountsTable.NAME + " (name, password) VALUES (?, ?)" ;

    public static AccountSingleton get(Context context) {
        if (sAccount == null) {
            sAccount = new AccountSingleton(context);
        }
        return sAccount;
    }

    private AccountSingleton(Context context) {
        mDbHelper = new AccountDbHelper(context.getApplicationContext());
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

        List<Account> accountList = getAccounts();
        System.out.println("============================");
        System.out.println("size = " + accountList.size());
        for (Account acct: accountList) {
            System.out.println(acct.getName());
        }
        System.out.println("============================");

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

    private AccountCursorWrapper queryAccounts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AccountsTable.NAME,
                null, // columns; null selects all columns
                whereClause,
                whereArgs,
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        return new AccountCursorWrapper(cursor);
    }

    public List<Account> getAccounts() {
        List<Account> accountList = new ArrayList<>();
        AccountCursorWrapper cursor = queryAccounts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                accountList.add(cursor.getAccount());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return accountList;
    }
}
