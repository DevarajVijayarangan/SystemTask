package com.example.systemtask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.systemtask.R;
import com.example.systemtask.model.Repository;

public class DetailFragment extends Fragment {

    private Repository mSelectedValue;

    public static DetailFragment newInstance(Repository param1) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("Repository", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedValue = (Repository) getArguments().getSerializable("Repository");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ((TextView) view.findViewById(R.id.detailName)).setText(mSelectedValue.getName());
        ((TextView) view.findViewById(R.id.detailOwner)).setText(mSelectedValue.getOwner().getLogin());
        ((TextView) view.findViewById(R.id.detailComment)).setText(mSelectedValue.getComment());
        Glide.with(getContext())
                .load(mSelectedValue.getOwner().getAvatar_url())
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ImageView) view.findViewById(R.id.detailAvatar)));
        return view;
    }
}