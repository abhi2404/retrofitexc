package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    TextView textView;
    SessionManager sessionManager;
    Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager= new SessionManager(this);

        Logout=findViewById(R.id.btn_logout);
        textView=findViewById(R.id.username);

        Logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("pref",MODE_PRIVATE);
        Gson gson=new Gson();
        String json= sharedPreferences.getString("tasklist",null);
        Type type = new TypeToken<List<LoginResponse>>() {}.getType();
        List<LoginResponse> loginResponses = gson.fromJson(json,type);

        }



    @Override
    public void onBackPressed(){
        Intent a=new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    }
