package com.example.nbnbzero.nekopapa;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */
public class AccountDbSchema {
    public static final class AccountsTable {
        public static final String NAME = "Accounts";

        public static final class Cols {
            public static final String NAME = "name";
            public static final String PASSWORD = "password";
            public static final String GOLD = "gold";
            public static final String LAST_LOGIN = "last_login";

            public static final String[] ColNames = {NAME, PASSWORD, GOLD, LAST_LOGIN};
        }

    }
}
