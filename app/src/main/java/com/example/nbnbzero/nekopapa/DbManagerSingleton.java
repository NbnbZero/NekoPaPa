package com.example.nbnbzero.nekopapa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */
public class DbManagerSingleton {
    private static DbManagerSingleton sDbManager;

    private DbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public static DbManagerSingleton get(Context context) {
        if (sDbManager == null) {
            sDbManager = new DbManagerSingleton(context);
        }
        return sDbManager;
    }

    private DbManagerSingleton(Context context) {
        mDbHelper = new DbHelper(context.getApplicationContext());
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public Cursor query(String queryStr, String[] args){
        return mDatabase.rawQuery(queryStr, args);
    }

    public long insert(String table, ContentValues cv){
        long result = -1;
        mDatabase.beginTransaction();
        try {
            result = mDatabase.insert(table, null, cv);
            mDatabase.setTransactionSuccessful();
        }finally {
            mDatabase.endTransaction();
        }
        return result;
    }

    public int update(String table, ContentValues cv, String whereClause, String[] whereArgs){
        int rows = 0;
        mDatabase.beginTransaction();
        try {
            rows = mDatabase.update(table, cv, whereClause, whereArgs);
            mDatabase.setTransactionSuccessful();
        }finally {
            mDatabase.endTransaction();
        }
        return rows;
    }

    public int delete(String table, String whereClause, String[] whereArgs){
        int rows = 0;
        mDatabase.beginTransaction();
        try {
            rows = mDatabase.delete(table, whereClause, whereArgs);
            mDatabase.setTransactionSuccessful();
        }finally {
            mDatabase.endTransaction();
        }
        return rows;
    }

    public static ContentValues getContentValues(String[] cols, String[] values){
        ContentValues cv = new ContentValues();
        for(int i = 0; i < cols.length; i++){
            cv.put(cols[i], values[i]);
        }
        return cv;
    }
}
