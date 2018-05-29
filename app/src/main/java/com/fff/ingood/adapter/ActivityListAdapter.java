package com.fff.ingood.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fff.ingood.R;

import java.util.List;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private List<String> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        ViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.imgActivityItem);
            mTextView = v.findViewById(R.id.textActivityItem);
        }
    }

    public ActivityListAdapter(List<String> lsDataset) {
        mDataset = lsDataset;
    }

    @Override
    public ActivityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
