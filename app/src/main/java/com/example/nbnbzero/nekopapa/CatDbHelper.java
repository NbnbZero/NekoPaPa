package com.example.nbnbzero.nekopapa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.nbnbzero.nekopapa.CatDbSchema.CatsTable;
/**
 * Created by NbnbZero on 3/21/2018.
 */

public class CatDbHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DATABASE_NAME = "cat.db";
    private static final int DATABASE_VERSION = 4;

    public CatDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CatDbSchema.CatsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CatDbSchema.CatsTable.Cols.id + " TEXT UNIQUE, " +
                CatDbSchema.CatsTable.Cols.name + "TEXT" +
                CatDbSchema.CatsTable.Cols.energy + "TEXT" +
                CatDbSchema.CatsTable.Cols.mood + " TEXT" +
                CatDbSchema.CatsTable.Cols.ear_type + "TEXT" +
                CatDbSchema.CatsTable.Cols.eye_type + "TEXT" +
                CatDbSchema.CatsTable.Cols.stripe_type + "TEXT" +
                CatDbSchema.CatsTable.Cols.fur_color + "TEXT" +
                CatDbSchema.CatsTable.Cols.characteristic + "TEXT" +
                CatDbSchema.CatsTable.Cols.stemina + "TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CatsTable.NAME);
        onCreate(sqLiteDatabase);
    }

}
