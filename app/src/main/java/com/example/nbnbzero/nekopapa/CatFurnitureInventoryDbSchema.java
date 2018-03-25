package com.example.nbnbzero.nekopapa;

/**
 * Created by tianz on 3/25/2018.
 */

public class CatFurnitureInventoryDbSchema {
    public static final class CatFurnitureInventoryTable {
        public static final String NAME = "CatFurnitureInventory";

        public static final class Cols {
            public static final String user_id = "user_id";
            public static final String furniture_id = "furniture_id";
            public static final String equipped = "equipped";

            public static final String[] ColNames = {user_id, furniture_id, equipped};
        }
    }
}
