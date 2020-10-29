package com.example.systemtask.api;

import com.example.systemtask.BuildConfig;
import com.example.systemtask.model.Repository;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClient {
    private static ApiClient instance = null;
    private ApiInterface myApi;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create( new GsonBuilder()
                        .create()))
                .build();
        myApi = retrofit.create(ApiInterface.class);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiInterface getMyApi() {
        return myApi;
    }


    public interface ApiInterface {

        @GET("repositories")
        Call<List<Repository>> getRepositories();

        @GET("repositories")
        Call<List<Repository>> getRepositories(@Query("since") int since);
    }
}



