package com.fff.ingood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.fff.ingood.activity.IgActivityDetailActivity;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.global.TagManager;
import com.fff.ingood.tools.ImageHelper;
import com.fff.ingood.tools.StringTool;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private final int GAP_TAGS = 40;

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
        String[] arrStrTags = activity.getTags().split(",");

        List<TextView> lsTextViewTags = new ArrayList<>();
        int iTagId = 1;
        for (String strTag : arrStrTags) {
            TextView textViewTag = new TextView(holder.mLayoutTags.getContext());
            textViewTag.setId(iTagId++);
            textViewTag.setText(strTag);
            textViewTag.setTextSize(holder.mLayoutTags.getContext().getResources().getDimension(R.dimen.tag_bar_text_size));
            textViewTag.setGravity(Gravity.CENTER_VERTICAL);

            int iColor = TagManager.getInstance().getTagColor(strTag);
            textViewTag.setTextColor(iColor);

            textViewTag.measure(0, 0);
            int iMeasuredWidth = textViewTag.getMeasuredWidth();
            int iMeasuredHeight = textViewTag.getMeasuredHeight();

            Drawable drawableTagBg = getTagBackground(iColor, iMeasuredWidth, iMeasuredHeight);
            textViewTag.setBackground(drawableTagBg);

            lsTextViewTags.add(textViewTag);
        }

        int iTagBarContain = measureHowManyTagsInTagBar(lsTextViewTags);
        adjustXaxisForTagsInTagBar(lsTextViewTags, iTagBarContain);
        paddingAllTagsForAlignCenter(lsTextViewTags);

        for(int i=0; i<iTagBarContain; i++)
            holder.mLayoutTags.addView(lsTextViewTags.get(i));
    }

    private void paddingAllTagsForAlignCenter(List<TextView> lsTextViewTags) {
        float RATIO_PADDING = 0.17f;

        for(TextView view : lsTextViewTags) {
            int iBackgroundWidth = view.getBackground().getIntrinsicWidth();
            int iPaddingX = (int)(iBackgroundWidth * RATIO_PADDING);
            view.setPadding(iPaddingX, 0, 0, 0);
        }
    }

    private void adjustXaxisForTagsInTagBar(List<TextView> lsTextViewTags, int iTagBarContain) {
        int iTagSize = lsTextViewTags.size();

        if(iTagSize == 0)
            return;

        int MAX_TAG_BAR_WIDTH = mTagBarWidth;
        int iCurXAxis = 0;

        //set X axis for each tags
        for(int i=0; i<iTagBarContain; i++) {
            iCurXAxis += GAP_TAGS;
            TextView view = lsTextViewTags.get(i);
            view.setX(iCurXAxis);
            iCurXAxis += view.getBackground().getIntrinsicWidth();
        }

        //shift all tags with remain width
        final float RATIO_SHIFT = getShiftRatio(iTagBarContain);
        int iRemainWidth = MAX_TAG_BAR_WIDTH - iCurXAxis;
        int iShiftXAxis = (int)(iRemainWidth * RATIO_SHIFT);
        for(int i=0; i<iTagBarContain; i++) {
            TextView view = lsTextViewTags.get(i);
            int iNewXAxis = (int)view.getX() + iShiftXAxis;
            view.setX(iNewXAxis);
        }
    }

    private float getShiftRatio(int iTagBarContain) {
        float iShiftRatio;

        switch(iTagBarContain) {
            case 1 :
                iShiftRatio = 0.43f;
                break;
            case 2 :
                iShiftRatio = 0.33f;
                break;
            case 3 :
                iShiftRatio = 0.29f;
                break;
            default :
                iShiftRatio = 0.27f;
                break;
        }

        return iShiftRatio;
    }

    private int measureHowManyTagsInTagBar(List<TextView> lsTextViewTags) {
        final int MARGIN_WIDTH_OF_TAG_BAR = 100;

        int iTagSize = lsTextViewTags.size();

        if(iTagSize == 0)
            return 0;

        int MAX_TAG_BAR_WIDTH = mTagBarWidth - MARGIN_WIDTH_OF_TAG_BAR;

        int bRes = 0;
        int iSumAllTagGapWidth = GAP_TAGS * (lsTextViewTags.size() - 1);
        int iSumAllTagWidth;

        for(int i=0; i<iTagSize; i++) {
            iSumAllTagWidth = 0;
            for (int j=0; j<iTagSize-i; j++)
                iSumAllTagWidth += lsTextViewTags.get(j).getBackground().getIntrinsicWidth();

            if((iSumAllTagWidth + iSumAllTagGapWidth) < MAX_TAG_BAR_WIDTH) {
                bRes = iTagSize - i;
                break;
            }
        }
        return bRes;
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
                //@@@ test
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

    private Drawable getTagBackground(int iColor, final int iTextViewWidth, final int iTextViewHeight) {
        final float RATIO_SHAPE_WIDTH = 1.5f;
        final float RATIO_SHAPE_HEIGHT = 1.5f;
        final int STROKE_WIDTH = 3;
        final float CORNER_RADIUS = 40f;

        GradientDrawable gd = new GradientDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return (int)(super.getIntrinsicWidth() * RATIO_SHAPE_WIDTH);
            }

            @Override
            public int getIntrinsicHeight() {
                return (int)(super.getIntrinsicHeight() * RATIO_SHAPE_HEIGHT);
            }
        };
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setSize(iTextViewWidth, iTextViewHeight);
        gd.setColor(Color.TRANSPARENT);
        gd.setStroke(STROKE_WIDTH, iColor);
        gd.setCornerRadius(CORNER_RADIUS);

        return gd;
    }
}
