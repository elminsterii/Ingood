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
    private int mTagBarWidth;

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

    public void setTagBarWidth(int mTagBarWidth) {
        this.mTagBarWidth = mTagBarWidth;
    }

    private void makeTags(ViewHolder holder, IgActivity activity) {
        String[] arrStrTags = activity.getTags().split(",");

        int iTagId = 1;
        final int MAX_TAGS = 5;
        int iSize = arrStrTags.length > MAX_TAGS ? MAX_TAGS : arrStrTags.length;
        int iBeginX = mTagBarWidth / (iSize + 2);

        //align center for the one item.
        if(iSize == 1)
            iBeginX += (iBeginX / 5);

        for(int i=0; i<iSize; i++) {
            String strTag = arrStrTags[i];
            TextView textViewTag = new TextView(holder.mLayoutTags.getContext());
            textViewTag.setId(iTagId++);
            textViewTag.setText(strTag);
            textViewTag.setTextSize(holder.mLayoutTags.getContext().getResources().getDimension(R.dimen.tag_bar_text_size));
            textViewTag.setX(iBeginX * (i + 1));

            holder.mLayoutTags.addView(textViewTag);
        }
    }
}
