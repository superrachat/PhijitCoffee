package nsru.demsomboon.ratchasak.phijitcoffee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    //Explict
    private ListView listView;
    private boolean statusABoolean;
    private String userString, surnameString, addressString, moneyString, idLoginString;
    private RadioGroup radioGroup;
    private RadioButton oneRadioButton, twoRadioButton, threeRadioButton, fourRadioButton;
    private int intHr = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        statusABoolean = getIntent().getBooleanExtra("Status", false);
        userString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");
        addressString = getIntent().getStringExtra("Address");
        moneyString = getIntent().getStringExtra("Money");
        idLoginString = getIntent().getStringExtra("idLogin");
        //
        listView = (ListView) findViewById(R.id.listView);
        radioGroup =(RadioGroup) findViewById(R.id.ragHr);
        oneRadioButton = (RadioButton) findViewById(R.id.radioButton);
        oneRadioButton = (RadioButton) findViewById(R.id.radioButton2);
        oneRadioButton = (RadioButton) findViewById(R.id.radioButton3);
        oneRadioButton = (RadioButton) findViewById(R.id.radioButton4);

        chooseHr();

        ConnectedJSONcoffee connectedJSONcoffee = new ConnectedJSONcoffee();
        connectedJSONcoffee.execute();
    }//MainMethod

    private void chooseHr() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                switch (i) {
                    case R.id.radioButton:
                        intHr = 1;
                        break;
                    case R.id.radioButton2:
                        intHr = 2;
                        break;
                    case R.id.radioButton3:
                        intHr = 3;
                        break;
                    case R.id.radioButton4:
                        intHr = 4;
                        break;
                }//switch



            }//onCheckChange
        });
    }

    //Creat Inner Class
    public class ConnectedJSONcoffee extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/aon/php_get_coffee_aon.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();




            }catch (Exception e){
                e.printStackTrace();
                return null;
            }




        }//DoInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("1May", "JSON==>" + s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                final String[] nameStrings = new String[jsonArray.length()];
                final String[] priceStrings = new String[jsonArray.length()];
                String[] descripStrings = new String[jsonArray.length()];
                String[] iconStrings = new String[jsonArray.length()];

                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameStrings[i] = jsonObject.getString("Name");
                    priceStrings[i] = jsonObject.getString("Price");
                    descripStrings[i] = jsonObject.getString("Description");
                    iconStrings[i] = jsonObject.getString("Image");




                }//for
                OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this,
                        nameStrings, priceStrings, descripStrings, iconStrings);
                listView.setAdapter(orderAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                        //Check User
                        if (statusABoolean) {
                            chooseAmount(nameStrings[i], priceStrings[i]);


                        } else {
                            MyAlertDialog myAlertDialog = new MyAlertDialog();
                            myAlertDialog.myDialog(OrderActivity.this,
                                    " ยังไม่ได้ Login ", " กรุณาสมัครสมาชิก ");
                        }

                    }//OnItemClick
                });


            }catch (Exception e){
                e.printStackTrace();
            }


        }//onPost
    }//AsyncTask

    private void chooseAmount(final String nameString, final String priceString) {

        CharSequence[]
                charSequences = {"1 แก้ว","2 แก้ว","3 แก้ว","4 แก้ว","5 แก้ว","6 แก้ว","7 แก้ว","8 แก้ว","9 แก้ว","10 แก้ว"};
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        builder.setCancelable(false);
        builder.setTitle(nameString + "กี่แก้ว");
        builder.setSingleChoiceItems(charSequences, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addOrderToSQLite(nameString, Integer.toString(i+1), priceString);
                dialogInterface.dismiss();

            }//OnClick
        });
        builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });


        builder.show();
    }//chooseAmount

    private void addOrderToSQLite(String nameString, String amountString, String priceString) {

        Log.d("1Mayv1", "Your Order ==>" + nameString + " = " + amountString);
        //Find DateAndTime
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        int currentHr = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinus = calendar.get(Calendar.MINUTE);
        int receiveTimeHr = currentHr + intHr;

        String strDate = dateFormat.format(date);
        String strTime = Integer.toString(receiveTimeHr) + ":" + Integer.toString(currentMinus);
        String strReceive = userString + "-" + strDate;


        Log.d("1MayV1", "Date ==>" + strDate);
        Log.d("1MayV1", "Time ==>" + strTime);

        int intMoney = Integer.parseInt(moneyString);
        int intAmount = Integer.parseInt(amountString);
        int intPrice = Integer.parseInt(priceString);
        int intCost = intAmount * intPrice;

        if (intMoney>=intCost) {
            MyManage myManage = new MyManage(this);
            myManage.addOrderSQLite(strReceive, userString, surnameString,
                    addressString, strDate, strTime, nameString,priceString, amountString);

        } else {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(this,"จำนวนเงินไม่พอ","กรุณาเติมเงิน");
        }






    }//addOrderToSQLite

    public  void clickReadOrder(View view){
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name, MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
        cursor.moveToFirst();

        if (cursor.getCount()!=0) {
            Intent intent = new Intent(OrderActivity.this, ComfirmOrder.class);
            intent.putExtra("Money", moneyString);
            intent.putExtra("idLogin", idLoginString);


            startActivity(intent);

        } else {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(this,"ไม่มีรายการสั่งซื้อ","กรุณาสั่งสินค้า");
        }

    }//clickReadOrder

}//Main Class
