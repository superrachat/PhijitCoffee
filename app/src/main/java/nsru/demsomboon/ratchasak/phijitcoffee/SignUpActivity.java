package nsru.demsomboon.ratchasak.phijitcoffee;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
//Explicit
    private EditText nameEditText, surEditText, addEditText, phoneEditText, userEditText, passEditText;
    private String nameString, surString, addString, phoneString, userString, passString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Bind
        bindWidget();


    } //Main Method

    private class CheckUser extends AsyncTask<Void, Void, String> {

        private String urlJSON ="http://swiftcodingthai.com/aon/php_get_user_master.php";
        private  boolean statusABoolean=false;
        @Override
        protected String doInBackground(Void... voids) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                return null;
            }



        }//doinback

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (userString.equals(jsonObject.getString("User"))) {
                        statusABoolean = true;
                    }//if


                }//for

                if (statusABoolean) {
                    //Have User
                    MyAlertDialog myAlertDialog = new MyAlertDialog();
                    myAlertDialog.myDialog(SignUpActivity.this, "User มีผู้ใช้แล้ว", "กรุณาเปลี่ยน User ใหม่");
                } else {
                    //No User
                    updateValueToServer();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }//OnPost
    }//CheckUserClass


    private void bindWidget() {
        nameEditText = (EditText) findViewById(R.id.editText);
        surEditText = (EditText) findViewById(R.id.editText2);
        addEditText = (EditText) findViewById(R.id.editText3);
        phoneEditText = (EditText) findViewById(R.id.editText4);
        userEditText = (EditText) findViewById(R.id.editText5);
        passEditText = (EditText) findViewById(R.id.editText7);


    }

    public void clickSignUpSign(View view) {

        //Get Value From Edit Text
        nameString = nameEditText.getText().toString().trim();
        surString = surEditText.getText().toString().trim();
        addString = addEditText.getText().toString().trim();
        phoneString = phoneEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passString = passEditText.getText().toString().trim();
        //Check
        if (checkSpace()) {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(this, "มีช่องว่าง","กรุณากรอกทุกช่อง");

        } else {
            CheckUser checkUser = new CheckUser();
            checkUser.execute();


        }


    } // Click SignUp

    private void updateValueToServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surString)
                .add("Address", addString)
                .add("Phone", phoneString)
                .add("User", userString)
                .add("Password", passString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://swiftcodingthai.com/aon/php_add_user_master.php").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });
        Toast.makeText(this,"สมัครสมาชิกเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
    } // update

    private boolean checkSpace() {

        boolean bolResult=true;
        bolResult = nameString.equals("") || surString.equals("") ||
                addString.equals("") || phoneString.equals("") ||
                userString.equals("") || passString.equals("");


        return bolResult;
    }


} // Main Class
