package com.example.nbnbzero.nekopapa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.nbnbzero.nekopapa.AccountDbSchema.AccountsTable;
/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class DbHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DATABASE_NAME = "NekoPaPa.db";
    private static final int DATABASE_VERSION = 11;

    // Class name for logging.
    private final String TAG = getClass().getSimpleName();

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + AccountsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AccountsTable.Cols.NAME + " TEXT UNIQUE, " +
                AccountsTable.Cols.PASSWORD + " TEXT, " +
                AccountsTable.Cols.GOLD + " INTEGER NOT NULL DEFAULT 0, " +
                AccountsTable.Cols.LAST_LOGIN + " TEXT NOT NULL " +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + CatDbSchema.CatsTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CatDbSchema.CatsTable.Cols.name + " TEXT, " +
                CatDbSchema.CatsTable.Cols.energy + " INTEGER NOT NULL DEFAULT 0, " +
                CatDbSchema.CatsTable.Cols.mood + " INTEGER NOT NULL DEFAULT 1, " +
                CatDbSchema.CatsTable.Cols.stemina + " INTEGER NOT NULL DEFAULT 1, " +
                CatDbSchema.CatsTable.Cols.characteristic + " INTEGER NOT NULL DEFAULT 1, " +
                CatDbSchema.CatsTable.Cols.stripe_type + " INTEGER  NOT NULL DEFAULT 1, " +
                CatDbSchema.CatsTable.Cols.fur_color + " INTEGER  NOT NULL DEFAULT 1, " +
                CatDbSchema.CatsTable.Cols.user_id + " INTEGER, " +
                CatDbSchema.CatsTable.Cols.lasttime_energy_consume + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + CatDbSchema.CatsTable.Cols.user_id + ") REFERENCES " + AccountsTable.NAME + "(_id)" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + CatItemDbSchema.CatItemTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CatItemDbSchema.CatItemTable.Cols.name + " TEXT, " +
                CatItemDbSchema.CatItemTable.Cols.energy_effect + " INTEGER NOT NULL DEFAULT 0, " +
                CatItemDbSchema.CatItemTable.Cols.price + " INTEGER NOT NULL DEFAULT 0, " +
                CatItemDbSchema.CatItemTable.Cols.description + " TEXT, " +
                CatItemDbSchema.CatItemTable.Cols.icon + " INTEGER" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + InventoryDbSchema.InventoryTable.NAME + "(" +
                InventoryDbSchema.InventoryTable.Cols.user_id + " INTEGER NOT NULL, " +
                InventoryDbSchema.InventoryTable.Cols.item_id + " INTEGER NOT NULL, " +
                InventoryDbSchema.InventoryTable.Cols.amount + " INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY (" + InventoryDbSchema.InventoryTable.Cols.user_id + ") REFERENCES " + AccountsTable.NAME + "(_id), " +
                "FOREIGN KEY (" + InventoryDbSchema.InventoryTable.Cols.item_id + ") REFERENCES " + CatItemDbSchema.CatItemTable.NAME + "(_id)" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + CatFurnitureDbSchema.CatFurnitureTable.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CatFurnitureDbSchema.CatFurnitureTable.Cols.name + " TEXT, " +
                CatFurnitureDbSchema.CatFurnitureTable.Cols.type + " INTEGER NOT NULL DEFAULT 1, " +
                CatFurnitureDbSchema.CatFurnitureTable.Cols.price + " INTEGER NOT NULL DEFAULT 0, " +
                CatFurnitureDbSchema.CatFurnitureTable.Cols.description + " TEXT, " +
                CatFurnitureDbSchema.CatFurnitureTable.Cols.icon + " INTEGER, " +
                CatFurnitureDbSchema.CatFurnitureTable.Cols.image + " INTEGER" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE " + CatFurnitureInventoryDbSchema.CatFurnitureInventoryTable.NAME + "(" +
                CatFurnitureInventoryDbSchema.CatFurnitureInventoryTable.Cols.user_id + " INTEGER NOT NULL, " +
                CatFurnitureInventoryDbSchema.CatFurnitureInventoryTable.Cols.furniture_id + " INTEGER NOT NULL, " +
                CatFurnitureInventoryDbSchema.CatFurnitureInventoryTable.Cols.equipped + " INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY (" + CatFurnitureInventoryDbSchema.CatFurnitureInventoryTable.Cols.user_id + ") REFERENCES " + AccountsTable.NAME + "(_id), " +
                "FOREIGN KEY (" + CatFurnitureInventoryDbSchema.CatFurnitureInventoryTable.Cols.furniture_id + ") REFERENCES " + CatFurnitureDbSchema.CatFurnitureTable.NAME + "(_id)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("Example", "Example: upgrading database; dropping and recreating tables.");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AccountsTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CatDbSchema.CatsTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CatItemDbSchema.CatItemTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InventoryDbSchema.InventoryTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CatFurnitureDbSchema.CatFurnitureTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CatFurnitureInventoryDbSchema.CatFurnitureInventoryTable.NAME);
        onCreate(sqLiteDatabase);
    }
}
