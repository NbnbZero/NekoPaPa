package com.example.nbnbzero.nekopapa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 *
 * Created by NbnbZero and TeriyakiMayo on 3/26/2018.
 *
 */

public class WildCatDialogFragment extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        Dialog dialog;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        int distance = (int) UserData.distanceToTarget(UserData.currentMarker.getPosition(), getActivity().getApplicationContext());
        Cat cat = UserData.catList.get(UserData.currentCatId);
        WildCat wc = UserData.wildCatList[(int)UserData.currentMarker.getZIndex()];
        mBuilder.setMessage("You want " + cat.getName() +
                " to mate with the cat(" + Cat.steminaStr(wc.getStemina()) +
                ", " + Cat.charactStr(wc.getCharacteristic()) +
                ", " + Cat.stripeStr(wc.getStripe_type()) +
                ", " + Cat.furStr(wc.getFur_color()) + ")?")
                .setPositiveButton(getResources().getString(R.string.Yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Context context = getActivity().getApplicationContext();
                                int distance = (int) UserData.distanceToTarget(UserData.currentMarker.getPosition(), getActivity().getApplicationContext());
                                Cat cat = UserData.catList.get(UserData.currentCatId);
                                WildCat wc = UserData.wildCatList[(int)UserData.currentMarker.getZIndex()];
                                if(distance > 50){
                                    UserData.toastMessage("It's Too Far Away!", context);
                                    return;
                                }

                                Boolean result = cat.mateWithWildCat(wc, context);

                                if(result){
                                    UserData.toastMessage("Succeeded!", context);
                                }else{
                                    UserData.toastMessage("Failed!", context);
                                }

                                if(UserData.currentMarker != null){
                                    UserData.currentMarker.remove();
                                }
                            }
                        });
        mBuilder.setNegativeButton(getResources().getString(R.string.No),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog = mBuilder.create();

        return dialog;
    }
}
