package com.example.nbnbzero.nekopapa;
/**
 * Created by NbnbZero on 3/21/2018.
 */

public class CatDbSchema{
    public static final class CatsTable {
        public static final String NAME = "Cats";

        public static final class Cols {
            public static final String name = "name";
            public static final String energy = "energy";
            public static final String mood = "mood";
            public static final String stemina = "stemina";
            public static final String characteristic = "characteristic";
            public static final String stripe_type = "stripe_type";
            public static final String fur_color = "fur_color";
            public static final String lasttime_energy_consume = "lasttime_energy_consume";
            public static final String user_id = "user_id";
        }
    }
}
