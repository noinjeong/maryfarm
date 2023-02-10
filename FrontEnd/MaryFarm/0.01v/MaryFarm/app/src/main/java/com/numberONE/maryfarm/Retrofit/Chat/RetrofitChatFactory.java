package com.numberONE.maryfarm.Retrofit.Chat;

import com.numberONE.maryfarm.Retrofit.Calendar.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitChatFactory {
    private static String BASE_URL="http://192.168.31.244:8000/";

    public static RetrofitChatService create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitChatService.class);
    }
}
