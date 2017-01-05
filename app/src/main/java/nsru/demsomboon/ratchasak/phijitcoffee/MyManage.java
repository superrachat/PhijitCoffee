package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by KHAO on 1/5/2559.
 */
public class MyManage {
    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    public static final String order_table = "orderTABLE";
    public static final String column_id = "_id";
    public static final String column_ReceiveID = "ReceiveID";
    public static final String column_Name = "Name";
    public static final String column_Surname = "Surname";
    public static final String column_Address = "Address";
    public static final String column_Date = "Date";
    public static final String column_Time = "Time";
    public static final String column_Coffee = "Coffee";
    public static final String column_Price = "Price";
    public static final String column_Amount = "Amount";


    public MyManage(Context context) {
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }

    public long addOrderSQLite(String strReceiveID,
                               String strName,
                               String strSurname,
                               String strAddress,
                               String strDate,
                               String strTime,
                               String strCoffee,
                               String strPrice,
                               String strAmount) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_ReceiveID, strReceiveID);
        contentValues.put(column_Name, strName);
        contentValues.put(column_Surname, strSurname);
        contentValues.put(column_Address, strAddress);
        contentValues.put(column_Date, strDate);
        contentValues.put(column_Time, strTime);
        contentValues.put(column_Coffee, strCoffee);
        contentValues.put(column_Price, strPrice);
        contentValues.put(column_Amount, strAmount);
        return sqLiteDatabase.insert(order_table, null, contentValues);
    }
}//Main Class
