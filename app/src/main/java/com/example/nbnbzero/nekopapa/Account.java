package com.example.nbnbzero.nekopapa;

/**
 * Created by NbnbZero and TeriyakiMayo on 2/20/2018.
 * Reference: TicTacToe by Professor Adam Champion
 * Retrieved from: web.cse.ohio-state.edu/~champion.17/5236/TicTacToeNew.zip
 */

public class Account {
    private String mName;
    private String mPassword;

    public Account(String name, String password) {
        mName = name;
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public String getPassword() {
        return mPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return mName.equals(account.mName) && mPassword.equals(account.mPassword);
    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + mPassword.hashCode();
        return result;
    }
}
