package com.fileupload.retrofit;
/**
 * Created by SONI on 9/29/2018.
 */

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String BASE_URL_LOGIN = "http://192.168.137.1/Test/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        //We can put logger here

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LOGIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getXmlRetrofit() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LOGIN)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}