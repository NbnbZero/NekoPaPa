package com.example.nbnbzero.nekopapa;

/**
 * Created by tianz on 3/25/2018.
 */

public class InventoryDbSchema {
    public static final class InventoryTable {
        public static final String NAME = "Inventory";

        public static final class Cols {
            public static final String user_id = "user_id";
            public static final String item_id = "item_id";
            public static final String amount = "amount";

            public static final String[] ColNames = {user_id, item_id, amount};
        }
    }
}
