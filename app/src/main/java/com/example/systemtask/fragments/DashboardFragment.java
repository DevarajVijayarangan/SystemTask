package com.example.systemtask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.systemtask.R;
import com.example.systemtask.adapters.MyRepositoriesAdapter;
import com.example.systemtask.listeners.MyRecyclerViewAdapterListener;
import com.example.systemtask.model.Repository;

import java.util.List;

public class DashboardFragment extends Fragment implements MyRecyclerViewAdapterListener {

    private DashboardViewModel dashboardViewModel;
    RecyclerView recyclerView;
    TextView tv_placeholder;
    ProgressBar progressBar;
    public boolean isDetailFragmentShowed = false;
    private MyRepositoriesAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = root.findViewById(R.id.rv_repos);
        tv_placeholder = root.findViewById(R.id.tv_placeholder);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setObservers();
        return root;
    }

    void setObservers(){
        dashboardViewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<List<Repository>>() {
            @Override
            public void onChanged(@Nullable List<Repository> repositories) {
                if (null != repositories && repositories.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    tv_placeholder.setVisibility(View.GONE);
                    mAdapter = new MyRepositoriesAdapter(repositories, getContext(), DashboardFragment.this);
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });
        dashboardViewModel.getNextResponse().observe(getViewLifecycleOwner(), new Observer<List<Repository>>() {
            @Override
            public void onChanged(@Nullable List<Repository> repositories) {
                progressBar.setVisibility(View.GONE);
                if (null != repositories && repositories.size() > 0) {
                    mAdapter.addItems(repositories);
                }
            }
        });
    }

    @Override
    public void navigateToDetailPage(Repository repository) {
        navigateToDetailPage(DetailFragment.newInstance(repository));
    }

    @Override
    public void onReachedPageEnd(int position) {
        dashboardViewModel.getNextResponse(mAdapter.getData().get(mAdapter.getItemCount()-1).getId());
        progressBar.setVisibility(View.VISIBLE);
    }

    private void navigateToDetailPage(Fragment newFragment) {
        isDetailFragmentShowed = true;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        if (!newFragment.isAdded()) {
            try {
                transaction.add(R.id.detailPlaceholder, newFragment, "detail");
                transaction.commit();
            } catch (Exception ignored) {}
        }
    }

    public void onBackPressed() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (isDetailFragmentShowed) {
            Fragment detailFragment = manager.findFragmentByTag("detail");
            if (detailFragment instanceof DetailFragment) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(detailFragment);
                trans.commit();
                manager.popBackStack();
            }
            isDetailFragmentShowed = false;
        } else {
            getActivity().onBackPressed();
        }
    }
}