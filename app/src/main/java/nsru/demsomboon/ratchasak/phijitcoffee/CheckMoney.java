package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class CheckMoney extends AppCompatActivity {
    //Explicit
    private TextView moneyTextView, nameTextView;
    private String moneyString, nameString, surnameString, addressString, idLoginString, jsonString;
    private static final String urlPHP = "http://www.swiftcodingthai.com/aon/get_user_where.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_money);

        //Bind Widget
        moneyTextView = (TextView) findViewById(R.id.textView10);
        nameTextView = (TextView) findViewById(R.id.textView12);

        //Receive Value from Intent
        idLoginString = getIntent().getStringExtra("idLogin");

        synMoney();

    }//MainMethod

    public void clickListCheckOrder(View view) {
        Intent intent = new Intent(CheckMoney.this, ShowListOrder.class);
        intent.putExtra("Name", nameString+ " " +surnameString);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        synMoney();

    }

    private void synMoney() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", idLoginString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                jsonString = response.body().string();
                Log.d("12June", "jasonString==>" + jsonString);

                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    moneyString = jsonObject.getString("Money");
                    nameString = jsonObject.getString("Name");
                    surnameString = jsonObject.getString("Surname");
                    addressString = jsonObject.getString("Address");
                    //show View
                    moneyTextView.setText(moneyString);
                    nameTextView.setText(nameString + " " + surnameString);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }


    public void clickOrderCheckMoney(View view) {
        Intent intent = new Intent(CheckMoney.this, OrderActivity.class);
        intent.putExtra("Status", true);
        intent.putExtra("Money", moneyString);
        intent.putExtra("Name", nameString);
        intent.putExtra("Surname", surnameString);
        intent.putExtra("Address", addressString);
        intent.putExtra("idLogin", idLoginString);




        startActivity(intent);
        finish();

    }//clickOrder

}//MainClass
