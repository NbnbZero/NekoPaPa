package com.example.nbnbzero.nekopapa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by NbnbZero on 3/21/2018.
 */

public class CatItemDbHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DATABASE_NAME = "catItem.db";
    private static final int DATABASE_VERSION = 4;

    public CatItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CatItemDbSchema.CatItemTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CatItemDbSchema.CatItemTable.Cols.id + " TEXT UNIQUE, " +
                CatItemDbSchema.CatItemTable.Cols.price + "TEXT" +
                CatItemDbSchema.CatItemTable.Cols.energy_effect + "TEXT" +
                CatItemDbSchema.CatItemTable.Cols.item_type + " TEXT" +
                CatItemDbSchema.CatItemTable.Cols.stemina_effect + "TEXT" +
                CatItemDbSchema.CatItemTable.Cols.mood_effect + "TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CatItemDbSchema.CatItemTable.NAME);
        onCreate(sqLiteDatabase);
    }
}
