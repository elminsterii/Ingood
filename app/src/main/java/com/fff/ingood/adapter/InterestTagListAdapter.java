package com.fff.ingood.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fff.ingood.R;

import java.util.List;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class InterestTagListAdapter extends RecyclerView.Adapter<InterestTagListAdapter.ViewHolder> {
    private List<InterestData> m_lsInterestData;

    public static class InterestData {
        private int iInterestItemIconResId;
        private String strInterestItemTitle;
        private String strInterestItemDescription;
        private boolean bIsSelected = false;

        int getInterestItemIconResId() {
            return iInterestItemIconResId;
        }

        public void setInterestItemIconResId(int iInterestItemIconResId) {
            this.iInterestItemIconResId = iInterestItemIconResId;
        }

        public String getInterestItemTitle() {
            return strInterestItemTitle;
        }

        public void setInterestItemTitle(String strInterestItemTitle) {
            this.strInterestItemTitle = strInterestItemTitle;
        }

        String getInterestItemDescription() {
            return strInterestItemDescription;
        }

        public void setInterestItemDescription(String strInterestItemDescription) {
            this.strInterestItemDescription = strInterestItemDescription;
        }

        public boolean isSelected() {
            return bIsSelected;
        }

        public void toggleSelected() {
            this.bIsSelected = !this.bIsSelected;
        }
    }

    public List<InterestData> getInterestList() {
        return m_lsInterestData;
    }

    public InterestTagListAdapter(List<InterestData> lsInterestData) {
        m_lsInterestData = lsInterestData;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mRelativeLayout;
        ImageView mImageViewInterestItemIcon;
        TextView mTextViewInterestItemTitle;
        TextView mTextViewInterestItemDescription;
        ViewHolder(View v) {
            super(v);
            mRelativeLayout = v.findViewById(R.id.layoutInterestItem);
            mImageViewInterestItemIcon = v.findViewById(R.id.imageViewInterestItemIcon);
            mTextViewInterestItemTitle = v.findViewById(R.id.textViewInterestItemTitle);
            mTextViewInterestItemDescription = v.findViewById(R.id.textViewInterestItemDescription);
        }
    }

    @NonNull
    @Override
    public InterestTagListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.interest_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        InterestData interestData = m_lsInterestData.get(position);
        holder.mImageViewInterestItemIcon.setImageResource(interestData.getInterestItemIconResId());
        holder.mTextViewInterestItemTitle.setText(interestData.getInterestItemTitle());
        holder.mTextViewInterestItemDescription.setText(interestData.getInterestItemDescription());

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_lsInterestData.get(position).toggleSelected();
                notifyDataSetChanged();
            }
        });

        if(!m_lsInterestData.get(position).isSelected())
            holder.mRelativeLayout.setBackgroundResource(R.color.colorPrimary);
        else
            holder.mRelativeLayout.setBackgroundResource(R.color.colorPrimaryDark);
    }

    @Override
    public int getItemCount() {
        return m_lsInterestData.size();
    }
}
