package com.example.nbnbzero.nekopapa;

import android.app.Activity;
import android.content.ContentValues;
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

    public void setEnergy(int energy) {
        if(energy >= this.stemina * 25){
            energy = this.stemina * 25;
        }
        this.energy = energy;
    }

    public static List<Cat> getCats(Cursor cursor){
        List<Cat> list = new ArrayList<Cat>();
        cursor.moveToFirst();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");

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

    public boolean updateEnergyMood(){
        boolean updated = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try{
            long lastTime = this.lasttime_energy_consume.getTime();
            Date date = new Date();
            long now = date.getTime();
            long minutes = (now - lastTime) / (1000 * 60);
            if(minutes >= 1){
                //update energy
                this.energy -= minutes;
                if(this.energy < 0){
                    this.energy = 0;
                }
                //update mood
                int[] moodType = moodProbability(characteristic);
                int d = (int) (Math.random() * 100);
                if(d <= moodType[0]){
                    this.mood = 1;
                }else if(d <= moodType[1]){
                    this.mood = 2;
                }else{
                    this.mood = 3;
                }
                if(this.energy < 5){
                    this.mood = 1;
                }
                //set update time
                this.lasttime_energy_consume = date;
                updated = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return updated;
    }

    private int[] moodProbability(int chara){
        int[] result = {0, 0};
        switch(chara){
            case 1:
                result[0] = 50;
                result[1] = 70;
                break;
            case 2:
                result[0] = 20;
                result[1] = 70;
                break;
            case 3:
                result[0] = 20;
                result[1] = 50;
                break;
        }
        return result;
    }

    public static String moodName(int i){
        String moodNa = "";
        switch(i){
            case 1:
                moodNa = "Angry";
                break;
            case 2:
                moodNa = "Calm";
                break;
            case 3:
                moodNa = "Happy";
                break;
        }
        return moodNa;
    }

    public String[] dataArray(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String[] temp = {name, energy + "", mood + "", stemina + "", characteristic + "",
                stripe_type + "", fur_color + "", fmt.format(lasttime_energy_consume),
        user_id + ""};
        return temp;
    }

    public int updateCatToDB(Activity activity){
        DbManagerSingleton singleton = DbManagerSingleton.get(activity);
        ContentValues tempCv = DbManagerSingleton.getContentValues(CatDbSchema.CatsTable.Cols.ColNames, dataArray());
        String whereClause = "_id = ?";
        String[] whereArgs = {id + ""};
        int rows = singleton.update(CatDbSchema.CatsTable.NAME, tempCv, whereClause, whereArgs);
        System.out.println("Cat " + id + " updated!");
        return rows;
    }

    public int deleteCatInDB(Activity activity){
        DbManagerSingleton singleton = DbManagerSingleton.get(activity);
        String whereClause = "_id = ?";
        String[] whereArgs = {id + ""};
        int rows = singleton.delete(CatDbSchema.CatsTable.NAME, whereClause, whereArgs);
        return rows;
    }

    public int catPrice(){
        return stemina * 100 + characteristic * 100 + stripe_type * 500 + fur_color * 500;
    }
}
