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
public class LoginFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity loginActivity;
    private LoginFragment loginFragment;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    Button loginButton;

    String packageName = "";

    public LoginFragmentTest(){
        super(LoginActivity.class);

    }

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        loginActivity = getActivity();
        loginFragment = new LoginFragment();
        loginActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, loginFragment, null)
                .commit();

        getInstrumentation().waitForIdleSync();

        setActivityInitialTouchMode(false);

        View v = loginFragment.getView();
        mUsernameEditText = (EditText) v.findViewById(R.id.username_text);
        mPasswordEditText = (EditText) v.findViewById(R.id.password_text);
        loginButton = (Button) v.findViewById(R.id.login_button);

        packageName = getActivity().getPackageName();
    }



    @Test
    public void testPreconditions() throws Exception {
        assertNotNull(loginActivity);
        assertNotNull(loginFragment);
        assertNotNull(mUsernameEditText);
        assertNotNull(mPasswordEditText);
        assertNotNull(loginButton);
    }

    @UiThreadTest
    public void testLoginFailure(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                System.out.println("mUsernameEditText = " + mUsernameEditText.getText());

                mUsernameEditText.setText("a");
                System.out.println("mUsernameEditText = " + mUsernameEditText.getText());

                mPasswordEditText.setText("b");

                loginButton.performClick();

                assertFalse(getActivity().isFinishing());

            }
        });

    }

    @UiThreadTest
    public void testLoginSuccess(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {

                mUsernameEditText.setText("a");

                mPasswordEditText.setText("a");

                loginButton.performClick();

                assertTrue(getActivity().isFinishing());

            }
        });

    }

    protected void tearDown() throws Exception {
        loginActivity.finish();
        super.tearDown();
    }
}
