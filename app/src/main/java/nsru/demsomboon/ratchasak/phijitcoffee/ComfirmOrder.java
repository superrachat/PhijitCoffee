package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ComfirmOrder extends AppCompatActivity {
    //Explicit
    private TextView refTextView, dateTextView, nameAnSurTextView, timeTextView, addressTextView, totalTextView;
    private String refString, dateString, nameAnSurString, timeString, addressString, totalString, moneyString, idLoginString;
    private ListView listView;
    private String[] coffeeString, priceString, amountString, idStrings;
    private int[] priceInts, amountInts, totalInts;
    private int sumaryTotal, moneyAnInt;
    private static final String urlOrderTABLE = "http://www.swiftcodingthai.com/aon/add_order.php";
    private static final String urlEditMoney = "http://www.swiftcodingthai.com/aon/edit_money.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_order);

        // bind Widget
        bindWidget();
        //GetOrderTable
        readOrderTABLE();
        //Show view
        showView();
        //Creat ListView
        creatListView();



    }//Main method

    private void creatListView() {



        ComfirmAdapter comfirmAdapter = new ComfirmAdapter(this, coffeeString, priceString, amountString);
        listView.setAdapter(comfirmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                //Delete Order
                myDelettOrder(idStrings[i]);
                readOrderTABLE();
                creatListView();
            }//on item click
        });

        totalTextView.setText(Integer.toString(sumaryTotal) + " บาท");


    }//creatListView

    private void myDelettOrder(String idString) {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);

        sqLiteDatabase.delete(MyManage.order_table,
                MyManage.column_id +
                        "=" +
                        Integer.parseInt(idString),
                null);


    }//myDeltetOrder

    private void showView() {
        refTextView.setText("รหัสใบเสร็จ "+ refString);
        String thaiDate = myConvertThaiDate(dateString);
        dateTextView.setText("วันที่สั่ง" + thaiDate);

        nameAnSurTextView.setText("ชื่อลูกค้า" + nameAnSurString);
        timeTextView.setText("เวลารับกาแฟ"+ timeString);
        addressTextView.setText("ที่อยู่" + addressString);

    }//showView
    private String myConvertThaiDate(String dataString) {
        String tag = "5janV1", strResult = null;
        String[] monthThai = new String[]{"ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.", "พ.ค.", "มิ.ย.",
                "ก.ค.", "ส.ค.", "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค." };
        Log.d(tag, "Date ==>" + dataString);
        String[] strings = dataString.split("-");
        for (int i = 0; i < strings.length; i++) {
            Log.d(tag, "strings(" + i + ")==>" + strings[i]);

        }
        //for
        int intDate = Integer.parseInt(strings[0]);
        int intMonth = Integer.parseInt(strings[1]);
        int intYear = Integer.parseInt(strings[2]);

        strResult = Integer.toString(intDate) + " " + monthThai[intMonth - 1] +
                " " + (Integer.toString(intYear + 543)).substring(2,4);


        return strResult;
    }//MyConvert




    private void readOrderTABLE() {
        sumaryTotal = 0;
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name, MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE",null);
        cursor.moveToFirst();

        coffeeString = new String[cursor.getCount()];
        priceString = new String[cursor.getCount()];
        amountString = new String[cursor.getCount()];
        idStrings = new String[cursor.getCount()];
        priceInts = new int[cursor.getCount()];
        amountInts = new int[cursor.getCount()];
        totalInts = new int[cursor.getCount()];


        for (int i=0;i<cursor.getCount();i++){
            refString=cursor.getString(cursor.getColumnIndex(MyManage.column_ReceiveID));
            dateString = cursor.getString(cursor.getColumnIndex(MyManage.column_Date));
            nameAnSurString = (cursor.getString(cursor.getColumnIndex(MyManage.column_Name))) +
                    " " +
                    (cursor.getString(cursor.getColumnIndex(MyManage.column_Surname)));
            timeString = cursor.getString(cursor.getColumnIndex(MyManage.column_Time));
            addressString = (cursor.getString(cursor.getColumnIndex(MyManage.column_Address)));
            coffeeString[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Coffee));
            priceString[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Price));
            amountString[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Amount));
            idStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_id));

            priceInts[i] = Integer.parseInt(priceString[i]);
            amountInts[i] = Integer.parseInt(amountString[i]);
            totalInts[i] = priceInts[i] * amountInts[i];
            sumaryTotal = sumaryTotal + totalInts[i];


            cursor.moveToNext();
        }// for
        cursor.close();
    }

    private void bindWidget() {
        refTextView = (TextView) findViewById(R.id.textView19);
        dateTextView  = (TextView) findViewById(R.id.textView20);
        nameAnSurTextView  = (TextView) findViewById(R.id.textView21);
        timeTextView  = (TextView) findViewById(R.id.textView22);
        addressTextView  = (TextView) findViewById(R.id.textView23);
        totalTextView = (TextView) findViewById(R.id.textView17);
        listView = (ListView) findViewById(R.id.listView2);
    }

    public void clickConfirmOrder(View view) {
        moneyString = getIntent().getStringExtra("Money");
        moneyAnInt = Integer.parseInt(moneyString);
        if (moneyAnInt >= sumaryTotal) {
            uploadOrderToServer();
            editUserTABLEonServer();
            Intent intent = new Intent(ComfirmOrder.this, CheckMoney.class);
            intent.putExtra("idLogin", idLoginString);
            startActivity(intent);
            finish();
        } else {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(this,"เงินไม่พอ", "กรุณาลดรายการซื้อ");


        }

        //confirm
        Toast.makeText(this,"ยืนยันการสั่งซื้อแล้ว",Toast.LENGTH_LONG).show();


    }//click confirm

    private void editUserTABLEonServer() {
        idLoginString = getIntent().getStringExtra("idLogin");
        int currentMoney = moneyAnInt - sumaryTotal;

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", idLoginString)
                .add("Money", Integer.toString(currentMoney))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlEditMoney).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });



    }//EditUser

    private void uploadOrderToServer() {
        for(int i=0;i<coffeeString.length;i++){
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("ReceiveID", refString)
                    .add("NameAnSur", nameAnSurString)
                    .add("Address", addressString)
                    .add("Date", dateString)
                    .add("Time", timeString)
                    .add("Coffee", coffeeString[i])
                    .add("Amount", amountString[i])
                    .build();

            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlOrderTABLE).post(requestBody).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                }
            });


        }//for


    }//UploadToServer

    public void clickAddMore(View view) {
        finish();
    }


} // Main class
