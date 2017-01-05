package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KHAO on 1/5/2559.
 */
public class MyOpenHelper extends SQLiteOpenHelper{
    //Explicit
    public static final String database_name = "Coffee.db";
    private static final int database_version = 1;
    private static final String creat_order_table = "create table orderTABLE (" +
            "_id integer primary key," +
            "ReceiveID text," +
            "Name text," +
            "Surname text," +
            "Address text," +
            "Date text," +
            "Time text," +
            "Coffee text," +
            "Price text," +
            "Amount text);";


    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    }//Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creat_order_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}//MainClass
