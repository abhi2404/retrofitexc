package com.example.retrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int  PRIVATE_MODE=0;

    private static final  String PREF_NAME =  "LOGIN";
    private static final String LOGIN ="IS_LOGIN";
    public static final String EMAIL ="EMAIL";
    public static final String PASSWORD ="PASSWSORD";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor =sharedPreferences.edit();
    }

    public void createSession(String email, String password){
        editor.putBoolean("LOGIN",true);
        editor.putString(EMAIL,email);
        editor.putString(PASSWORD,password);
        editor.apply();
    }

    public  boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public  void checkLogin(){
        if(!this.isLoggin()){
            Intent i =new Intent(context,DashboardActivity.class);
            context.startActivity(i);
            ((MainActivity)context).finish();
        }
    }
    public HashMap<String , String> getUserDetails(){
        HashMap<String ,String> user =new HashMap<>();
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));

        return  user;
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context,MainActivity.class);
        context.startActivity(i);
        ((DashboardActivity)context).finish();
    }
}
