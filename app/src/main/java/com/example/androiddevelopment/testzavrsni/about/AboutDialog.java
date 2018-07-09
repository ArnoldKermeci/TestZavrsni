package com.example.androiddevelopment.testzavrsni.about;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.androiddevelopment.testzavrsni.R;

public class AboutDialog extends AlertDialog.Builder{

    public AboutDialog(Context context) {
        super(context);

        setTitle("AboutDialog");
        setMessage("Zavrsni zadatak 09.07.2018");
        setCancelable(false);

        setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }


    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

}