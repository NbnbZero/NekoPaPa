package com.example.nbnbzero.nekopapa;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianz on 3/25/2018.
 */

public class CatFurniture {

    private String name, description;
    private int id, type, price, icon, image;

    public CatFurniture(int id, String name, int type, int price, String description, int icon, int image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.icon = icon;
        this.image = image;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getIcon() {
        return icon;
    }

    public int getImage() {
        return image;
    }


    public static List<CatFurniture> getAccounts(Cursor cursor){
        List<CatFurniture> list = new ArrayList<CatFurniture>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CatFurniture catFurniture = new CatFurniture(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)));
            list.add(catFurniture);
            cursor.moveToNext();
        }
        return list;
    }
}
