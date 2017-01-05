package nsru.demsomboon.ratchasak.phijitcoffee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by KHAO on 29/3/2559.
 */
public class MyAlertDialog {
    public void myDialog(Context context,
                         String StrTitle,
                         String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.icon_question);
        builder.setTitle(StrTitle);
        builder.setMessage(strMessage);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }



}//Main Class
