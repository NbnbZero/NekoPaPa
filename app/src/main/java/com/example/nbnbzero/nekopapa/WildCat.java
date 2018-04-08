package com.example.nbnbzero.nekopapa;

/*
* Created by NbnbZero and TeriyakiMayo on 3/26/2018.
*
**/

public class WildCat {
    private int stemina, characteristic, stripe_type, fur_color;
    private double lat, longi;

    public WildCat(int stemina, int characteristic, int stripe_type, int fur_color, double lat, double longi) {

        this.stemina = stemina;  //1-4
        this.characteristic = characteristic;   //1-3
        this.stripe_type = stripe_type;     //1-3
        this.fur_color = fur_color;     //1-3
        this.lat = lat;
        this.longi = longi;
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

    public double getLat() {
        return this.lat;
    }

    public double getLongi() {
        return this.longi;
    }

}
