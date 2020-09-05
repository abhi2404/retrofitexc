package com.example.retrofit;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){

        OkHttpClient.Builder okHttpBuilder =new OkHttpClient.Builder();

        okHttpBuilder.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Request request=chain.request();
                Request.Builder newRequest =request.newBuilder().header("Authorization","secret-Key");
                Log.v("okhttp","adding header:"+newRequest);
                return chain.proceed(newRequest.build());
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("    https://6a24a9813e0e.ngrok.io ")
                .client(okHttpBuilder.build());

        Retrofit retrofit=builder.build();
        return retrofit;
    }

    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);
        return userService;
    }
}
