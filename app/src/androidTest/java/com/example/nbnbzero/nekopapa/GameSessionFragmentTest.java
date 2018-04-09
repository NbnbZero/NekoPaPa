package com.example.nbnbzero.nekopapa;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;

/**
 * Created by tianz on 4/8/2018.
 */
public class GameSessionFragmentTest extends ActivityInstrumentationTestCase2<GameSessionActivity> {

    private GameSessionActivity gameSessionActivity;
    private GameSessionFragment gameSessionFragment;
    private TextView userGoldView;
    Button energyButton;
    private DbManagerSingleton sDbManager;

    public GameSessionFragmentTest(){
        super(GameSessionActivity.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        sDbManager = DbManagerSingleton.get(getActivity().getApplicationContext());
        String queryStr = "SELECT * FROM " + AccountDbSchema.AccountsTable.NAME + " WHERE " + AccountDbSchema.AccountsTable.Cols.NAME +
                " = ? ";
        String[] whereArgs = new String[] {"a"};
        Cursor cursor = new CursorWrapper(sDbManager.query(queryStr, whereArgs));
        cursor.moveToNext();
        Account acc = Account.getAccounts(cursor).get(0);
        UserData.currentUser = acc;
        acc.updateLoginDateAndGold();
        acc.updateAccountToDB(getActivity());

        gameSessionActivity = getActivity();
        gameSessionFragment = new GameSessionFragment();
        gameSessionActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, gameSessionFragment, null)
                .commit();

        getInstrumentation().waitForIdleSync();

        setActivityInitialTouchMode(false);

        View v = gameSessionFragment.getView();
        userGoldView = (TextView) v.findViewById(R.id.gold_panel);
        energyButton = (Button) v.findViewById(R.id.buy_energy_button);

    }



    @Test
    public void testPreconditions() throws Exception {
        assertNotNull(gameSessionActivity);
        assertNotNull(gameSessionFragment);
        assertNotNull(userGoldView);
        assertNotNull(energyButton);
    }

    @UiThreadTest
    public void testReplenishEnergy(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                int cost = 30;
                int goldBefore = Integer.parseInt((userGoldView.getText() + "").substring(5));

                energyButton.performClick();

                int goldAfter = Integer.parseInt((userGoldView.getText() + "").substring(5));

                if(goldBefore >= cost){
                    assertEquals(goldBefore - cost, goldAfter);
                }else{
                    assertEquals(goldBefore, goldAfter);
                }

            }
        });

    }


    protected void tearDown() throws Exception {
        gameSessionActivity.finish();
        super.tearDown();
    }
}