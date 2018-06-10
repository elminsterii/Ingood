package com.fff.ingood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;

import java.util.List;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private List<IgActivity> m_lsActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewActivity;
        TextView mTextViewActivityName;
        RelativeLayout mLayoutTags;
        ViewHolder(View v) {
            super(v);
            mImageViewActivity = v.findViewById(R.id.imgActivityItem);
            mTextViewActivityName = v.findViewById(R.id.textActivityName);
            mLayoutTags = v.findViewById(R.id.layoutActivityTags);
        }
    }

    public ActivityListAdapter(List<IgActivity> lsActivity) {
        m_lsActivity = lsActivity;
    }

    @NonNull
    @Override
    public ActivityListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IgActivity activity = m_lsActivity.get(position);
        holder.mTextViewActivityName.setText(activity.getName());
        makeTags(holder, activity);

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

    public void updateActivityList(List<IgActivity> lsActivity) {
        m_lsActivity = lsActivity;
        notifyDataSetChanged();
    }

    private void makeTags(ViewHolder holder, IgActivity activity) {
        String[] arrStrTags = activity.getTags().split(",");

        int iTagId = 1;
        TextView preTextView = null;

        for(String strTag : arrStrTags) {
            TextView textViewTag = new TextView(holder.mLayoutTags.getContext());
            textViewTag.setId(iTagId++);
            textViewTag.setText(strTag);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);

            if(preTextView != null)
                params.addRule(RelativeLayout.RIGHT_OF, preTextView.getId());

            holder.mLayoutTags.addView(textViewTag, params);
            preTextView = textViewTag;
        }
    }
}
