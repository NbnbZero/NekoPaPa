package com.example.nbnbzero.nekopapa;

/**
 * Created by tianz on 3/25/2018.
 */

public class CatFurnitureDbSchema {
    public static final class CatFurnitureTable {
        public static final String NAME = "CatFurnitures";

        public static final class Cols {
            public static final String name = "name";
            public static final String type = "type";
            public static final String price = "price";
            public static final String description = "description";
            public static final String icon = "icon";
            public static final String image = "image";

            public static final String[] ColNames = {name, type, price, description, icon, image};
        }
    }
}
