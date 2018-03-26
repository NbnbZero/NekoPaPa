package com.example.nbnbzero.nekopapa;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class Account implements Serializable{
    private int id;
    private String mName;
    private String mPassword;
    private int gold;
    private Date lastLogin;

    public Account(int id, String name, String password, int gold, Date lastLogin) {
        this.id = id;
        mName = name;
        mPassword = password;
        this.gold = gold;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return mName;
    }

    public String getPassword() {
        return mPassword;
    }

    public int getGold() {
        return gold;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public static List<Account> getAccounts(Cursor cursor){
        List<Account> list = new ArrayList<Account>();
        cursor.moveToFirst();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dt = new Date();
        try{
            dt = fmt.parse(cursor.getString(cursor.getColumnIndex(AccountDbSchema.AccountsTable.Cols.LAST_LOGIN)));
        }catch(Exception e){
            e.printStackTrace();
        }
        while(!cursor.isAfterLast()){
            Account account = new Account(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))),
                    cursor.getString(cursor.getColumnIndex(AccountDbSchema.AccountsTable.Cols.NAME)),
                    cursor.getString(cursor.getColumnIndex(AccountDbSchema.AccountsTable.Cols.PASSWORD)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(AccountDbSchema.AccountsTable.Cols.GOLD))),
                    dt
                    );
                    list.add(account);
            cursor.moveToNext();
        }
        return list;
    }

    public void updateLoginDateAndGold(){
        SimpleDateFormat fmt = DateManager.fmt;
        try{
            long lastTime = this.lastLogin.getTime();
            Date date = new Date();
            long now = date.getTime();
            long minutes = (now - lastTime) / (1000 * 60);
            if(minutes >= 1){
                System.out.println("ACCCCCCCCCOUNT MIN = " + minutes);
                this.gold += 100;
            }
            this.lastLogin = date;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String[] dataArray(){
        SimpleDateFormat fmt = DateManager.fmt;
        String[] temp = {mName, mPassword, gold + "", fmt.format(lastLogin)};
        return temp;
    }

    public int updateAccountToDB(Activity activity){
        DbManagerSingleton singleton = DbManagerSingleton.get(activity);
        ContentValues tempCv = DbManagerSingleton.getContentValues(AccountDbSchema.AccountsTable.Cols.ColNames, dataArray());
        String whereClause = "_id = ?";
        String[] whereArgs = {id + ""};
        int rows = singleton.update(AccountDbSchema.AccountsTable.NAME, tempCv, whereClause, whereArgs);
        System.out.println("Account " + id + " updated!");
        return rows;
    }
}
