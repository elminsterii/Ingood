package com.fff.ingood.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fff.ingood.R;
import com.fff.ingood.data.ActivityAttr;

import java.util.List;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private List<ActivityAttr> m_lsActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewActivity;
        ViewHolder(View v) {
            super(v);
            mImageViewActivity = v.findViewById(R.id.imgActivityItem);
        }
    }

    public ActivityListAdapter(List<ActivityAttr> lsActivity) {
        m_lsActivity = lsActivity;
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
        ActivityAttr activity = m_lsActivity.get(position);
        holder.mImageViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - go to detail page of activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_lsActivity.size();
    }
}
