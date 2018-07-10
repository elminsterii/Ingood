package com.fff.ingood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.activity.IgActivityDetailActivity;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.global.TagManager;
import com.fff.ingood.tools.ImageHelper;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private final int GAP_TAGS = 35;

    private List<IgActivity> m_lsActivity;
    private int mTagBarWidth;

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewActivity;
        TextView mTextViewActivityName;
        TextView mTextViewActivityTime;
        TextView mTextViewActivityActionAttention;
        TextView mTextViewActivityActionGoodCount;
        RelativeLayout mLayoutTags;
        RelativeLayout mLayoutActivityBookmark;
        ViewHolder(View v) {
            super(v);
            mImageViewActivity = v.findViewById(R.id.imgActivityItem);
            mTextViewActivityName = v.findViewById(R.id.textActivityName);
            mTextViewActivityTime = v.findViewById(R.id.textActivityTime);
            mTextViewActivityActionAttention = v.findViewById(R.id.textActivityActionAttention);
            mTextViewActivityActionGoodCount = v.findViewById(R.id.textActivityActionGood);
            mLayoutTags = v.findViewById(R.id.layoutActivityTags);
            mLayoutActivityBookmark = v.findViewById(R.id.layoutActivitySaveIgActivity);
        }
    }

    public ActivityListAdapter(List<IgActivity> lsActivity, Context context) {
        m_lsActivity = lsActivity;
        mContext = context;
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
        holder.mImageViewActivity.setTag(position);

        makeImage(holder, activity);
        makeActivityName(holder, activity);
        makeTime(holder, activity);
        makeAttention(holder, activity);
        makeGood(holder, activity);
        makeTags(holder, activity);
        makeListener(holder);
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

    private void makeImage(ViewHolder holder, IgActivity activity) {
        final int CORNER_LEVEL_VALUE = 100;
        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sample_activity);
        bm = ImageHelper.getRoundedCornerBitmap(bm, CORNER_LEVEL_VALUE);

        holder.mImageViewActivity.setImageBitmap(bm);
    }

    private void makeTags(ViewHolder holder, IgActivity activity) {
        TagManager.getInstance().makeTagsInLayout(holder.mLayoutTags, activity, mTagBarWidth);
    }

    private void makeActivityName(ViewHolder holder, IgActivity activity) {
        String strActivityName = activity.getName();
        holder.mTextViewActivityName.setText(strActivityName);
    }

    private void makeAttention(ViewHolder holder, IgActivity activity) {
        String strAttention = activity.getAttention();
        if(!StringTool.checkStringNotNull(strAttention))
            strAttention = "0";
        holder.mTextViewActivityActionAttention.setText(strAttention);
    }

    private void makeGood(ViewHolder holder, IgActivity activity) {
        String strGood = activity.getGood();
        if(!StringTool.checkStringNotNull(strGood))
            strGood = "0";
        holder.mTextViewActivityActionGoodCount.setText(strGood);
    }

    private void makeTime(ViewHolder holder, IgActivity activity) {
        String strTime = IgActivityHelper.makeDateStringByIgActivity(activity);
        holder.mTextViewActivityTime.setText(strTime);
    }

    private void makeListener(ViewHolder holder) {
        holder.mImageViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                IgActivity activity = m_lsActivity.get(position);

                Intent intent = new Intent(mContext, IgActivityDetailActivity.class);
                intent.putExtra(TAG_IGACTIVITY, activity);
                mContext.startActivity(intent);
            }
        });

        holder.mLayoutActivityBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - add/cancel bookmark
            }
        });
    }
}
