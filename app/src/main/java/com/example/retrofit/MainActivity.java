package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    EditText email;
    EditText password;
    Button button;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email=findViewById(R.id.usrname);
        password=findViewById(R.id.pass);
        button = findViewById(R.id.btn);

        sessionManager=new SessionManager(this);
        sessionManager.checkLogin();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(MainActivity.this, "Username/Password Required", Toast.LENGTH_SHORT).show();
                }else{
                    login();
                }
            }
        });
    }


    public void login(){
        LoginRequest loginRequest= new LoginRequest();
        loginRequest.setUsername(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<List<LoginResponse> > loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call,  Response<List<LoginResponse>>response) {
                 if(response.isSuccessful()) {
                     sessionManager.createSession(email.getText().toString(), password.getText().toString());
                     Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                     final List<LoginResponse> loginResponse =response.body();
                     new Handler().postDelayed(new Runnable() {

                         @Override
                         public void run() {
                             SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
                             SharedPreferences.Editor editor = sharedPreferences.edit();
                             Gson gson = new Gson();
                             String json = gson.toJson(loginResponse);
                             editor.putString("tasklist", json);
                             editor.apply();
                             startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                         }
                     },500);
                 }else{
                     Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                 }
            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();

            }
        });
    }
}