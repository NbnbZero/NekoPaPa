package com.example.nbnbzero.nekopapa;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianz on 3/25/2018.
 */

public class CatItem {

    private String name, description;
    private int id, energy_effect, price, icon;

    public CatItem(int id, String name, int energy_effect, int price, String description) {
        this.id = id;
        this.name = name;
        this.energy_effect = energy_effect;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public int getEnergyEffect() {
        return energy_effect;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    public static List<CatItem> getAccounts(Cursor cursor){
        List<CatItem> list = new ArrayList<CatItem>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CatItem catItem = new CatItem(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4));
            list.add(catItem);
            cursor.moveToNext();
        }
        return list;
    }
}
