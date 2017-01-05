package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
private EditText userEditText, passwordEditText;
    private String userString, passwordString, passwordTrueString,
            nameString, surnameString, moneyString, addressString, idString;
    private boolean userABoolean;//true ==> true, False ==> no this user
    private MyManage myManage;
    private static final String urlPromote="http://swiftcodingthai.com/aon/Image/promote1.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myManage = new MyManage(this);

        userEditText = (EditText) findViewById(R.id.editText6);
        passwordEditText = (EditText) findViewById(R.id.editText8);


        //Delete SQLITE
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.order_table, null, null);


    }  //Main Method

    public void clickAboutMe(View view){
        startActivity(new Intent(MainActivity.this,MapsActivity.class));
    }

        public void clickGuest(View view) {

            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            intent.putExtra("Status", false);
            startActivity(intent);

        }

    //Create Inner Class
    public class ConnectrdJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/aon/php_get_user_aon.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }



        }//doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("30April", "JSON ==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (userString.equals(jsonObject.getString("User"))) {
                        userABoolean = true;
                        passwordTrueString = jsonObject.getString("Password");
                        nameString = jsonObject.getString("Name");
                        surnameString = jsonObject.getString("Surname");
                        moneyString = jsonObject.getString("Money");
                        addressString = jsonObject.getString("Address");
                        idString = jsonObject.getString("id");
                    }//if

                }//for
                    //CheckUser
                if (userABoolean) {
                    //CheckPassword
                    if (passwordString.equals(passwordTrueString)) {

                        Toast.makeText(MainActivity.this,"ยินดีตอ้นรับ"+nameString+"สู่ร้านของเรา",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, CheckMoney.class);
                        intent.putExtra("Name", nameString);
                        intent.putExtra("Surname", surnameString);
                        intent.putExtra("Money", moneyString);
                        intent.putExtra("Address", addressString);
                        intent.putExtra("idLogin", idString);
                        startActivity(intent);
                        finish();

                    } else {
                        MyAlertDialog myAlertDialog = new MyAlertDialog();
                        myAlertDialog.myDialog(MainActivity.this,"Password Fail","ลองพิมพ์ ใหม่ Password ผิด");
                    }
                } else {
                    MyAlertDialog myAlertDialog = new MyAlertDialog();
                    myAlertDialog.myDialog(MainActivity.this,"ไม่มี User นี้","ไม่มี " + userString + " ในระบบของเรา");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }//OnPost



    }// ConnectedJSON Class


public void clickSignIn(View view) {
    userABoolean=false;
    userString = userEditText.getText().toString().trim();
    passwordString = passwordEditText.getText().toString().trim();
    //Check Space
    if (userString.equals("")|| passwordString.equals(""))  {
        MyAlertDialog myAlertDialog = new MyAlertDialog();
        myAlertDialog.myDialog(this, "มีช่องว่าง", "กรุณากรอกทุกช่อง");
    } else {
        //No Space
        ConnectrdJSON connectrdJSON = new ConnectrdJSON();
        connectrdJSON.execute();


    }
}



    public void clickSignUpMain(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
} //Main Class
