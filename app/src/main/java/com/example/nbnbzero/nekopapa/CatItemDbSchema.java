package com.example.nbnbzero.nekopapa;

/**
 * Created by NbnbZero on 3/21/2018.
 */

public class CatItemDbSchema {
    public static final class CatItemTable {
        public static final String NAME = "CatItems";

        public static final class Cols {
            public static final String name = "name";
            public static final String energy_effect = "energy_effect";
            public static final String price = "price";
            public static final String description = "description";
            public static final String icon = "icon";

            public static final String[] ColNames = {name, energy_effect, price, description,
                icon};
        }
    }
}
