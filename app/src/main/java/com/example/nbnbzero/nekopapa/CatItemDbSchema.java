package com.example.nbnbzero.nekopapa;

/**
 * Created by NbnbZero on 3/21/2018.
 */

public class CatItemDbSchema {
    public static final class CatItemTable {
        public static final String NAME = "catItem";

        public static final class Cols {
            public static final String id = "id";
            public static final String item_type = "item_type";
            public static final String stemina_effect = "stemina_effect";
            public static final String energy_effect = "energy_effect";
            public static final String price = "price";
            public static final String mood_effect = "mood_effect";
        }
    }
}
