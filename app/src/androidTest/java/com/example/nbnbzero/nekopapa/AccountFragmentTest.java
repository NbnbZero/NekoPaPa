package com.example.nbnbzero.nekopapa;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Test;

/**
 * Created by tianz on 4/8/2018.
 */

public class AccountFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity loginActivity;
    private AccountFragment accountFragment;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtConfirm;
    Button btnAdd;

    public AccountFragmentTest(){
        super(LoginActivity.class);

    }

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        loginActivity = getActivity();
        accountFragment = new AccountFragment();
        loginActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, accountFragment, null)
                .commit();

        getInstrumentation().waitForIdleSync();

        setActivityInitialTouchMode(false);

        View v = accountFragment.getView();
        mEtUsername = (EditText) v.findViewById(R.id.username);
        mEtPassword = (EditText) v.findViewById(R.id.password);
        mEtConfirm = (EditText) v.findViewById(R.id.password_confirm);
        btnAdd = (Button) v.findViewById(R.id.done_button);

    }



    @Test
    public void testPreconditions() throws Exception {
        assertNotNull(loginActivity);
        assertNotNull(accountFragment);
        assertNotNull(mEtUsername);
        assertNotNull(mEtPassword);
        assertNotNull(mEtConfirm);
        assertNotNull(btnAdd);
    }

    @UiThreadTest
    public void testAccountCreationFailed(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {

                mEtUsername.setText("abcdefg");

                mEtPassword.setText("a");

                mEtConfirm.setText("b");

                assertFalse(accountFragment.createAccount());
            }
        });

    }

    protected void tearDown() throws Exception {
        loginActivity.finish();
        super.tearDown();
    }
}
