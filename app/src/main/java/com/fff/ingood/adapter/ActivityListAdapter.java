package com.fff.ingood.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.tools.StringTool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
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
            mLayoutActivityBookmark = v.findViewById(R.id.layoutActivityBookmark);
        }
    }

    public ActivityListAdapter(List<IgActivity> lsActivity, Context mContext) {
        m_lsActivity = lsActivity;
        this.mContext = mContext;
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
            textViewTag.setTypeface(null, Typeface.BOLD);
            textViewTag.setGravity(Gravity.CENTER_VERTICAL);
            textViewTag.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

            textViewTag.measure(0, 0);
            int iMeasuredWidth = textViewTag.getMeasuredWidth();
            int iTagWidth = (int) (iMeasuredWidth * 0.7);

            //adjust position with measured width
            float fAdjustX = textViewTag.getX() - (int)(iTagWidth * 0.8);
            textViewTag.setX(fAdjustX);

            textViewTag.setPadding(30,0,0,0);
            Drawable drawableTagBg = getTagBackground(mContext.getResources().getColor(R.color.colorPrimary), iTagWidth);
            textViewTag.setBackground(drawableTagBg);

            holder.mLayoutTags.addView(textViewTag);
        }
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
        String strOriginPattern = "yyyy-MM-dd HH:mm:ss";
        String strNewPattern = "yyyy-MM-dd(EEE) HH:mm";

        DateFormat dateOriginFormat = new SimpleDateFormat(strOriginPattern, Locale.getDefault());
        DateFormat dateNewFormat = new SimpleDateFormat(strNewPattern, Locale.getDefault());

        String strTimeBegin = activity.getDateBegin();
        String strTimeEnd = activity.getDateEnd();

        try {
            Date dateBegin = dateOriginFormat.parse(strTimeBegin);
            strTimeBegin = dateNewFormat.format(dateBegin);

            Date dateEnd = dateOriginFormat.parse(strTimeEnd);
            strTimeEnd = dateNewFormat.format(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String strTime = strTimeBegin + " ~ " + strTimeEnd;
        holder.mTextViewActivityTime.setText(strTime);
    }

    private void makeListener(ViewHolder holder) {
        holder.mImageViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - go to detail page of activity
            }
        });

        holder.mLayoutActivityBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - add/cancel bookmark
            }
        });
    }

    private Drawable getTagBackground(int iColor, final int iRectangleWidth) {
        final int STROKE_WIDTH = 5;
        final float CORNER_RADIUS = 40f;
        final int HEIGHT = 45;

        GradientDrawable gd = new GradientDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return super.getIntrinsicWidth() + (int)(iRectangleWidth * 1.3);
            }

            @Override
            public int getIntrinsicHeight() {
                return super.getIntrinsicHeight() + HEIGHT;
            }
        };
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setSize(iRectangleWidth, HEIGHT);
        gd.setColor(Color.TRANSPARENT);
        gd.setStroke(STROKE_WIDTH, iColor);
        gd.setCornerRadius(CORNER_RADIUS);

        return gd;
    }
}
