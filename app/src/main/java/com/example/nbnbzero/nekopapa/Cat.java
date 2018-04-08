package com.example.nbnbzero.nekopapa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianz on 3/25/2018.
 */

public class Cat {
    private static final int minMateEnergy = 10;

    private String name, lasttime_energy_consume;
    private int id, energy, mood, stemina, characteristic, stripe_type,
            fur_color, user_id;

    public Cat(int id, String name, int energy, int mood, int stemina, int characteristic, int stripe_type,
               int fur_color, String lasttime_energy_consume, int user_id) {
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

    public String getLasttimeEnergyConsume() {
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
        List<Cat> list = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            Cat cat = new Cat(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))),
                    cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.name)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.energy))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.mood))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.stemina))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.characteristic))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.stripe_type))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.fur_color))),
                    cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.lasttime_energy_consume)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(CatDbSchema.CatsTable.Cols.user_id))));
            list.add(cat);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean updateEnergyMood(){
        boolean updated = false;
        SimpleDateFormat fmt = DateManager.fmt;
        try{
            long lastTime = fmt.parse(this.lasttime_energy_consume).getTime();
            Date date = new Date();
            long now = date.getTime();
            long min = TimeUnit.MILLISECONDS.toMinutes(now - lastTime);
            if(min >= 1){
                //update energy
                System.out.println("Last = " + lastTime + " " + this.lasttime_energy_consume);
                System.out.println("Now = " + now + " " + fmt.format(date));
                System.out.println("CURRRRRRRR ENERGY = " + this.energy + " minute = " + min);
                this.energy -= min;
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
                this.lasttime_energy_consume = fmt.format(date);
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
        SimpleDateFormat fmt = DateManager.fmt;
        String[] temp = {name, energy + "", mood + "", stemina + "", characteristic + "",
                stripe_type + "", fur_color + "", lasttime_energy_consume,
        user_id + ""};
        return temp;
    }

    public int updateCatToDB(Context context){
        DbManagerSingleton singleton = DbManagerSingleton.get(context);
        ContentValues tempCv = DbManagerSingleton.getContentValues(CatDbSchema.CatsTable.Cols.ColNames, dataArray());
        String whereClause = "_id = ?";
        String[] whereArgs = {id + ""};
        int rows = singleton.update(CatDbSchema.CatsTable.NAME, tempCv, whereClause, whereArgs);
        System.out.println("Cat " + id + " updated!");
        return rows;
    }

    public int deleteCatInDB(Context context){
        DbManagerSingleton singleton = DbManagerSingleton.get(context);
        String whereClause = "_id = ?";
        String[] whereArgs = {id + ""};
        int rows = singleton.delete(CatDbSchema.CatsTable.NAME, whereClause, whereArgs);
        return rows;
    }

    public int catPrice(){
        return stemina * 100 + characteristic * 100 + stripe_type * 500 + fur_color * 500;
    }

    public static String steminaStr(int stemina){
        String str = "";
        switch(stemina){
            case 1:
                str = "weak";
                break;
            case 2:
                str = "normal";
                break;
            case 3:
                str = "good";
                break;
            case 4:
                str = "strong";
                break;
        }
        return str;
    }

    public static String charactStr(int charact){
        String str = "";
        switch(charact){
            case 1:
                str = "irritable";
                break;
            case 2:
                str = "introversive";
                break;
            case 3:
                str = "active";
                break;
        }
        return str;
    }

    public static String stripeStr(int st){
        String str = "";
        switch(st){
            case 1:
                str = "pure";
                break;
            case 2:
                str = "dots";
                break;
            case 3:
                str = "zebra";
                break;
        }
        return str;
    }

    public static String furStr(int fur){
        String str = "";
        switch(fur){
            case 1:
                str = "orange";
                break;
            case 2:
                str = "yellow";
                break;
            case 3:
                str = "scarlet";
                break;
        }
        return str;
    }

    public boolean mateWithWildCat(WildCat wc, Context context){
        if(this.energy <= minMateEnergy){
            return false;
        }
        this.energy -= minMateEnergy;
        this.updateCatToDB(context);
        int ra = (int) (Math.random() * 100);
        if(ra > mateSuccessRate()){
            return false;
        }

        int stemina = randNum(this.stemina, wc.getStemina());
        int charact = randNum(this.characteristic, wc.getCharacteristic());
        int stripe_type = randNum(this.stripe_type, wc.getStripe_type());
        int fur_color = randNum(this.fur_color, wc.getFur_color());

        Date date = new Date();
        String[] newCatValues = {"", 25 * stemina + "",
                "3", stemina + "", charact + "", stripe_type + "", fur_color + "",
                DateManager.fmt.format(date), user_id + ""};

        long result = insertCat(context, newCatValues);

        DbManagerSingleton singleton = DbManagerSingleton.get(context);
        String[] colName = {CatDbSchema.CatsTable.Cols.name};
        String[] values = {UserData.currentUser.getName() + "'s cat " + result};
        ContentValues tempCv = DbManagerSingleton.getContentValues(colName, values);
        String whereClause = "_id = ?";
        String[] whereArgs = {result + ""};
        int rows = singleton.update(CatDbSchema.CatsTable.NAME, tempCv, whereClause, whereArgs);



        return true;
    }

    private int mateSuccessRate(){
        int rate = 0;
        switch(mood){
            case 1:
                rate = 70;
                break;
            case 2:
                rate = 80;
                break;
            case 3:
                rate = 90;
                break;
        }
        return rate;
    }

    public static long insertCat(Context context, String[] values){
        DbManagerSingleton singleton = DbManagerSingleton.get(context);
        ContentValues tempCv = DbManagerSingleton.getContentValues(CatDbSchema.CatsTable.Cols.ColNames, values);
        return singleton.insert(CatDbSchema.CatsTable.NAME, tempCv);
    }

    private int randNum(int a, int b){
        int ra = (int) (Math.random() * 100);
        if(ra <= 50){
            return a;
        }else{
            return b;
        }
    }
}
