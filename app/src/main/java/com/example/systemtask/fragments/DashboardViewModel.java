package com.example.systemtask.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.systemtask.api.ApiClient;
import com.example.systemtask.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Repository>> mResponse;
    private MutableLiveData<List<Repository>> mNextResponse;

    public DashboardViewModel() {
        mResponse = new MutableLiveData<>();
        mNextResponse = new MutableLiveData<>();
        fetchRepositories();
    }

    public MutableLiveData<List<Repository>> getResponse(){
        return mResponse;
    }
    public MutableLiveData<List<Repository>> getNextResponse(){
        return mNextResponse;
    }

    public void getNextResponse(int since){
        ApiClient.getInstance().getMyApi().getRepositories(since).enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                mNextResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {

            }
        });
    }

    private void fetchRepositories() {
        ApiClient.getInstance().getMyApi().getRepositories().enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                mResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {

            }
        });
    }
}