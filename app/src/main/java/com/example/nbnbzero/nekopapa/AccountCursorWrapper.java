package com.example.nbnbzero.nekopapa;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.nbnbzero.nekopapa.AccountDbSchema.AccountsTable;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class AccountCursorWrapper extends CursorWrapper {
    public AccountCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Account getAccount() {
        String name = getString(getColumnIndex(AccountsTable.Cols.NAME));
        String password = getString(getColumnIndex(AccountsTable.Cols.PASSWORD));

        Account account = new Account(name, password);

        return account;
    }
}
