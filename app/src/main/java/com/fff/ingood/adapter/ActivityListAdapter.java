package com.fff.ingood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.TagManager;
import com.fff.ingood.logic.IgActivityImageComboLogic_IgActivityMainImageDownload;
import com.fff.ingood.logic.IgActivityLogicExecutor;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonSaveIgActivityLogic;
import com.fff.ingood.task.wrapper.PersonSaveIgActivityTaskWrapper;
import com.fff.ingood.tools.ImageHelper;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.tools.TimeHelper;

import java.util.HashMap;
import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.GlobalProperty.IGACTIVITY_MAIN_IMAGE_CORNER_LEVEL;

/**
 * Created by ElminsterII on 2018/5/29.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> implements
        PersonSaveIgActivityLogic.PersonSaveIgActivityLogicCaller
        , IgActivityImageComboLogic_IgActivityMainImageDownload.IgActivityMainImageDownloadLogicCaller {
    private List<IgActivity> m_lsActivity;
    private int mTagBarWidth;

    private Context mContext;
    private HashMap<String, ImageView> m_hashImageViews;
    private HashMap<String, Bitmap> m_hashImageBitmapsCache;

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mLayoutIgactivityItem;
        ImageView mImageViewActivity;
        ImageView mImgActivityItemMask;
        TextView mTextViewActivityItemMaskText;
        TextView mTextViewActivityName;
        TextView mTextViewActivityTime;
        TextView mTextViewActivityActionAttention;
        TextView mTextViewActivityActionGoodCount;
        RelativeLayout mLayoutTags;
        RelativeLayout mLayoutSaveIgActivity;
        boolean m_bIsSave;

        ViewHolder(View v) {
            super(v);
            mLayoutIgactivityItem = v.findViewById(R.id.layoutRelativeIgActivityItem);
            mImageViewActivity = v.findViewById(R.id.imgActivityItemImage);
            mImgActivityItemMask = v.findViewById(R.id.imgActivityItemMask);
            mTextViewActivityItemMaskText = v.findViewById(R.id.textViewActivityItemMaskText);
            mTextViewActivityName = v.findViewById(R.id.textActivityName);
            mTextViewActivityTime = v.findViewById(R.id.textActivityTime);
            mTextViewActivityActionAttention = v.findViewById(R.id.textActivityActionAttention);
            mTextViewActivityActionGoodCount = v.findViewById(R.id.textActivityActionGood);
            mLayoutTags = v.findViewById(R.id.layoutActivityTags);
            mLayoutSaveIgActivity = v.findViewById(R.id.layoutActivitySaveIgActivity);
            m_bIsSave = false;
        }
    }

    @SuppressLint("UseSparseArrays")
    public ActivityListAdapter(List<IgActivity> lsActivity, Context context) {
        m_lsActivity = lsActivity;
        mContext = context;
        m_hashImageViews = new HashMap<>();
        m_hashImageBitmapsCache = new HashMap<>();
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
        holder.mLayoutIgactivityItem.setTag(position);
        holder.mLayoutSaveIgActivity.setTag(position);

        makeImage(holder, activity, position);
        makeMask(holder, activity);
        makeActivityName(holder, activity);
        makeTime(holder, activity);
        makeAttention(holder, activity);
        makeGood(holder, activity);
        makeTags(holder, activity);
        makeSaveActivityButtonState(holder, activity);
        makeListener(holder);
    }

    @Override
    public int getItemCount() {
        return m_lsActivity.size();
    }

    public void updateActivityList(List<IgActivity> lsActivity) {
        m_lsActivity = lsActivity;
    }

    public void setTagBarWidth(int mTagBarWidth) {
        this.mTagBarWidth = mTagBarWidth;
    }

    public void clear() {
        m_hashImageBitmapsCache.clear();
        m_hashImageViews.clear();
        m_lsActivity.clear();
    }

    private void makeDefaultImage(ViewHolder holder) {
        Bitmap bm = ImageHelper.getBitmapFromVectorDrawable(mContext, R.drawable.ic_image_black_72dp);
        bm = ImageHelper.getRoundedCornerBitmap(bm, IGACTIVITY_MAIN_IMAGE_CORNER_LEVEL);

        holder.mImageViewActivity.setImageBitmap(bm);
    }

    private void makeTags(ViewHolder holder, IgActivity activity) {
        holder.mLayoutTags.removeAllViews();
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
        String strTime = TimeHelper.makeDateStringByIgActivity(activity);
        holder.mTextViewActivityTime.setText(strTime);
    }

    private void makeListener(final ViewHolder holder) {
        holder.mLayoutIgactivityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                IgActivity activity = m_lsActivity.get(position);

                Intent intent = new Intent(mContext, IgActivityDetailActivity.class);
                intent.putExtra(TAG_IGACTIVITY, activity);
                mContext.startActivity(intent);
            }
        });

        holder.mLayoutSaveIgActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                IgActivity activity = m_lsActivity.get(position);

                if(holder.m_bIsSave) {
                    saveIgActivity(PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE.SV_CANCEL_SAVE, activity.getId());
                    setUiComponentSaveIgActivity(holder, false);
                }
                else {
                    saveIgActivity(PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE.SV_SAVE, activity.getId());
                    setUiComponentSaveIgActivity(holder, true);
                }
            }
        });
    }

    private void makeSaveActivityButtonState(ViewHolder holder, IgActivity activity) {
        boolean bIsSave = false;
        String strSaveActivities = PersonManager.getInstance().getPerson().getSaveIgActivities();

        if(StringTool.checkStringNotNull(strSaveActivities)) {
            String[] arrSaveIgActivitiesIds = strSaveActivities.split(",");
            for(String strSaveIgActivityId : arrSaveIgActivitiesIds) {
                if(strSaveIgActivityId.equals(activity.getId()))
                    bIsSave = true;
            }
        }
        holder.m_bIsSave = bIsSave;
        setUiComponentSaveIgActivity(holder, bIsSave);
    }

    private void makeMask(ViewHolder holder, IgActivity activity) {
        if(activity.getStatus().equals(IgActivity.IGA_STATUS_CLOSED)) {
            holder.mTextViewActivityItemMaskText.setVisibility(View.VISIBLE);
            holder.mImgActivityItemMask.setImageDrawable(mContext.getResources().getDrawable(R.drawable.image_mask_home_activity_close));
        } else {
            holder.mTextViewActivityItemMaskText.setVisibility(View.INVISIBLE);
            holder.mImgActivityItemMask.setImageDrawable(mContext.getResources().getDrawable(R.drawable.image_mask_home_activity));
        }
    }

    private void makeImage(ViewHolder holder, IgActivity activity, int position) {
        String strPosition = Integer.toString(position);
        m_hashImageViews.put(strPosition, holder.mImageViewActivity);

        if(!m_hashImageBitmapsCache.containsKey(activity.getId())) {
            makeDefaultImage(holder);
            downloadIgActivityImage(activity, strPosition);
        } else {
            Bitmap bm = m_hashImageBitmapsCache.get(activity.getId());
            holder.mImageViewActivity.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.mImageViewActivity.setImageBitmap(bm);
        }
    }

    private void setUiComponentSaveIgActivity(ViewHolder holder, boolean bSave) {
        ImageView imgViewSaveIgActivity = (ImageView)holder.mLayoutSaveIgActivity.getChildAt(0);

        if(bSave)
            imgViewSaveIgActivity.setImageResource(R.drawable.bookmark_d_s);
        else
            imgViewSaveIgActivity.setImageResource(R.drawable.bookmark_n_s);

        holder.m_bIsSave = bSave;
    }

    private void saveIgActivity(PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE svValue, String strActivityId) {
        Person person = PersonManager.getInstance().getPerson();
        PersonLogicExecutor executor = new PersonLogicExecutor();

        executor.doSaveIgActivity(this, person.getEmail(), strActivityId, svValue);
    }

    private void downloadIgActivityImage(IgActivity activity, String strTag) {
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();
        executor.doIgActivityMainImageDownload(this, activity.getId(), strTag);
    }

    @Override
    public void returnIgActivityMainImage(Bitmap bmIgActivityImage, String strTag) {
        if(StringTool.checkStringNotNull(strTag)) {
            if(bmIgActivityImage != null) {
                int index = Integer.parseInt(strTag);
                if(index >= 0 && index < m_lsActivity.size()) {
                    IgActivity activity = m_lsActivity.get(index);
                    String strIgActivityId = activity.getId();
                    if(!m_hashImageBitmapsCache.containsKey(strIgActivityId))
                        m_hashImageBitmapsCache.put(strIgActivityId, bmIgActivityImage);
                }

                if(m_hashImageViews.containsKey(strTag)) {
                    ImageView imageView = m_hashImageViews.get(strTag);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageBitmap(bmIgActivityImage);
                }
            }
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        //do nothing
    }

    @Override
    public void saveIgActivitySuccess() {
        PersonManager.getInstance().refresh();
    }
}
