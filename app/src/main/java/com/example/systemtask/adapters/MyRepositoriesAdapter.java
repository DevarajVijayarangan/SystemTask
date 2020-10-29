package com.example.systemtask.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.systemtask.R;
import com.example.systemtask.listeners.MyRecyclerViewAdapterListener;
import com.example.systemtask.model.Repository;

import java.util.List;

public class MyRepositoriesAdapter extends RecyclerView.Adapter<MyRepositoriesAdapter.MyViewHolder> {
    private final Context mContext;
    private final MyRecyclerViewAdapterListener mListener;
    private List<Repository> mValues;

    public MyRepositoriesAdapter(List<Repository> myDataset, Context context, MyRecyclerViewAdapterListener listener) {
        mValues = myDataset;
        mContext = context;
        mListener = listener;
    }

    @Override
    public MyRepositoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtvw_Name.setText(mValues.get(position).getName());
        holder.edTxt_Comment.setText(mValues.get(position).getComment());
        Glide.with(mContext)
                .load(mValues.get(position).getOwner().getAvatar_url())
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgVw_Avatar);
        if (null != mListener) {
            if (position == mValues.size() - 1) {
                mListener.onReachedPageEnd(position);
            }
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.navigateToDetailPage((Repository) view.getTag());
                }
            });
        }
        holder.edTxt_Comment.setTag(position);
        holder.edTxt_Comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!holder.edTxt_Comment.getText().toString().isEmpty())
                    mValues.get(position).setComment(holder.edTxt_Comment.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItems(final List<Repository> repositories) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = mValues.size();
                mValues.addAll(repositories);
                notifyItemInserted(count);
            }
        }, 100);
    }

    public List<Repository> getData() {
        return mValues;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtvw_Name;
        public ImageView imgVw_Avatar;
        public EditText edTxt_Comment;

        public MyViewHolder(View v) {
            super(v);
            txtvw_Name = v.findViewById(R.id.itemName);
            imgVw_Avatar = v.findViewById(R.id.itemAvatar);
            edTxt_Comment = v.findViewById(R.id.itemComment);
        }
    }
}