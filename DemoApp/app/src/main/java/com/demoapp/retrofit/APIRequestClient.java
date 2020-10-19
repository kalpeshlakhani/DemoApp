package com.demoapp.retrofit;

import com.demoapp.constants.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRequestClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClientInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        client.connectTimeout(30 * 1000L, TimeUnit.MILLISECONDS);
        client.readTimeout(30 * 1000L, TimeUnit.MILLISECONDS);
        client.writeTimeout(30 * 1000L, TimeUnit.MILLISECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        return retrofit;
    }

}