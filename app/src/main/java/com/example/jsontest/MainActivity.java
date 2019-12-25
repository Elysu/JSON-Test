package com.example.jsontest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tvName, tvNumber;
    private Button btn1, btn2, btn3;
    private ArrayList<Contacts> data = new ArrayList<>();
    private OkHttpClient okHttpClient = new OkHttpClient();
    int num;
    Contacts contacts = new Contacts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();
        tvName.setText(contacts.getName());
        tvNumber.setText(contacts.getNumber());
    }

    private void findViews() {
        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_number);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
    }

    private void setListeners() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 0;
                getData();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 1;
                getData();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 2;
                getData();
            }
        });
    }



    private void getData() {
        Request request = new Request.Builder().url("https://api.myjson.com/bins/ra3fo").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject dataObject = new JSONObject(response.body().string());
                    JSONArray dataArray = dataObject.getJSONArray("user");


                        JSONObject singleObject = dataArray.getJSONObject(num);

                        contacts.setName(singleObject.getString("name"));
                        contacts.setNumber(singleObject.getString("number"));

                        data.add(contacts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
