package com.blackwell.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;

    // TODO move it to properties
    public static final String BASE_URL = "http://localhost:8080";
    private Retrofit retrofit;


    private NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public GameService getGameService() {
        return retrofit.create(GameService.class);
    }
}
