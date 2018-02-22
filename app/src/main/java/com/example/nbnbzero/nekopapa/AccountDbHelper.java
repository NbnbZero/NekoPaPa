package com.example.nbnbzero.nekopapa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.nbnbzero.nekopapa.AccountDbSchema.AccountsTable;
/**
 * Created by NbnbZero on 2/20/2018.
 */

public class AccountDbHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DATABASE_NAME = "NekoPaPa.db";
    private static final int DATABASE_VERSION = 4;

    // Class name for logging.
    private final String TAG = getClass().getSimpleName();

    public AccountDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + AccountsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AccountsTable.Cols.NAME + " TEXT UNIQUE, " +
                AccountsTable.Cols.PASSWORD + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AccountsTable.NAME);
        onCreate(sqLiteDatabase);
    }
}
