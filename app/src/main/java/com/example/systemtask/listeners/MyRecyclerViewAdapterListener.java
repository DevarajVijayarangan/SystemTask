package com.example.systemtask.listeners;


import com.example.systemtask.model.Repository;

public interface MyRecyclerViewAdapterListener {

    void navigateToDetailPage(Repository repository);
    void onReachedPageEnd(int position);
}
