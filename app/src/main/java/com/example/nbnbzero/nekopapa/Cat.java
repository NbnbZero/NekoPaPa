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

    public int getStemina() {
        return this.stemina;
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
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        String[] str = cursor.getColumnNames();
        for(int i = 0; i < cursor.getColumnCount(); i++){
            System.out.println(i + " " + str[i]);
        }

        while(!cursor.isAfterLast()){
            Date dt = new Date();
            try{
                dt = fmt.parse(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.lasttime_energy_consume)));
            }catch(Exception e){
                e.printStackTrace();
            }

            Cat cat = new Cat(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))),
                    cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.name)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.energy))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.mood))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.stemina))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.characteristic))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.stripe_type))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.fur_color))),
                    dt,
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.user_id))));
            list.add(cat);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean updateEnergy(){
        boolean updated = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try{
            long lastTime = this.lasttime_energy_consume.getTime();
            Date date = new Date();
            long now = date.getTime();
            long minutes = (now - lastTime) / (1000 * 60);
            System.out.println("MMMMMMM = " + minutes);
            if(minutes >= 1){
                this.energy -= minutes;
                if(this.energy < 0){
                    this.energy = 0;
                }
                this.lasttime_energy_consume = date;
                updated = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return updated;
    }
}
