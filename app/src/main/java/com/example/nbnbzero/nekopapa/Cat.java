package com.example.nbnbzero.nekopapa;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tianz on 3/25/2018.
 */

public class Cat {
    private String name;
    private int id, energy, mood, stemina, characteristic, stripe_type,
            fur_color, user_id;
    private Date lasttime_energy_consume;

    public Cat(int id, String name, int energy, int mood, int stemina, int characteristic, int stripe_type,
               int fur_color, Date lasttime_energy_consume, int user_id) {
        this.id = id;
        this.name = name;
        this.energy = energy;
        this.mood = mood;
        this.stemina = stemina;
        this.characteristic = characteristic;
        this.stripe_type = stripe_type;
        this.fur_color = fur_color;
        this.lasttime_energy_consume = lasttime_energy_consume;
        this.user_id = user_id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getMood() {
        return this.mood;
    }

    public int getCharacteristic() {
        return this.characteristic;
    }

    public int getStripe_type() {
        return this.stripe_type;
    }

    public int getFur_color() {
        return this.fur_color;
    }

    public Date getLasttimeEnergyConsume() {
        return this.lasttime_energy_consume;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public static List<Cat> getCats(Cursor cursor){
        List<Cat> list = new ArrayList<Cat>();
        cursor.moveToFirst();
        SimpleDateFormat fmt = new SimpleDateFormat("DD-MM-YYYY HH:MM:SS");
        while(!cursor.isAfterLast()){
            Date dt = new Date();
            try{
                dt = fmt.parse(cursor.getString(8));
            }catch(Exception e){
                e.printStackTrace();
            }

            Cat cat = new Cat(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)),
                    dt,
                    Integer.parseInt(cursor.getString(9)));
            list.add(cat);
            cursor.moveToNext();
        }
        return null;
    }
}
