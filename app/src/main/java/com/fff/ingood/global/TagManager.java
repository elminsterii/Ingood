package com.fff.ingood.global;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ElminsterII on 2018/6/20.
 */
public class TagManager {
    private final int GAP_TAGS = 35;

    @SuppressLint("StaticFieldLeak")
    private static TagManager m_instance = null;
    private Context mContext;

    private Map<String, Integer> m_mapTagColor;
    private int mTagBarWidth;

    private TagManager(Context context) {
        mContext = context;
    }

    public static TagManager getInstance(Context context) {
        if(m_instance == null) {
            m_instance = new TagManager(context);
            m_instance.initialize();
        }
        return m_instance;
    }

    public static TagManager getInstance() {
        return m_instance;
    }

    private int getTagColor(String strTag) {
        if(!m_mapTagColor.containsKey(strTag))
            return mContext.getResources().getColor(R.color.colorPrimary);
        return m_mapTagColor.get(strTag);
    }

    private void initialize() {
        m_mapTagColor = new HashMap<>();

        String strTagCare = mContext.getResources().getText(R.string.tag_care).toString();
        int colTagCare = mContext.getResources().getColor(R.color.colorTagCare);
        String strTagEnvironmentalProtection = mContext.getResources().getText(R.string.tag_environmental_protection).toString();
        int colTagEnvironmentalProtection = mContext.getResources().getColor(R.color.colorTagEnvironmentalProtection);
        String strTagEducation = mContext.getResources().getText(R.string.tag_education).toString();
        int colTagEducation = mContext.getResources().getColor(R.color.colorTagEducation);
        String strTagManpower = mContext.getResources().getText(R.string.tag_manpower).toString();
        int colTagManpower = mContext.getResources().getColor(R.color.colorTagManpower);
        String strTagAnimal = mContext.getResources().getText(R.string.tag_animal).toString();
        int colTagAnimal = mContext.getResources().getColor(R.color.colorTagAnimal);

        m_mapTagColor.put(strTagCare, colTagCare);
        m_mapTagColor.put(strTagEnvironmentalProtection, colTagEnvironmentalProtection);
        m_mapTagColor.put(strTagEducation, colTagEducation);
        m_mapTagColor.put(strTagManpower, colTagManpower);
        m_mapTagColor.put(strTagAnimal, colTagAnimal);
    }

    public void makeTagsInLayout(RelativeLayout layout, IgActivity activity, int iTagBarWidth) {
        mTagBarWidth = iTagBarWidth;

        String[] arrStrTags = activity.getTags().split(",");

        List<TextView> lsTextViewTags = new ArrayList<>();
        int iTagId = 1;
        for (String strTag : arrStrTags) {
            TextView textViewTag = new TextView(layout.getContext());
            textViewTag.setId(iTagId++);
            textViewTag.setText(strTag);
            textViewTag.setTextSize(TypedValue.COMPLEX_UNIT_PX
                    , layout.getResources().getDimension(R.dimen.tag_bar_text_size));
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
            layout.addView(lsTextViewTags.get(i));
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

        //set gap for each tags
        for(int i=0; i<iTagBarContain; i++) {
            iCurXAxis += GAP_TAGS;
            TextView view = lsTextViewTags.get(i);
            view.setX(iCurXAxis);
            iCurXAxis += view.getBackground().getIntrinsicWidth();
        }

        iCurXAxis += GAP_TAGS;

        //shift all tags with remain width
        final float RATIO_SHIFT = getShiftRatio(iTagBarContain);
        int iRemainWidth = MAX_TAG_BAR_WIDTH - iCurXAxis;
        int iShiftXAxis = (int)((float)iRemainWidth * RATIO_SHIFT);
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
                iShiftRatio = 0.41f;
                break;
            case 2 :
                iShiftRatio = 0.38f;
                break;
            case 3 :
                iShiftRatio = 0.35f;
                break;
            case 4 :
                iShiftRatio = 0.32f;
                break;
            case 5 :
                iShiftRatio = 0.28f;
                break;
            default :
                iShiftRatio = 0.20f;
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

    private Drawable getTagBackground(int iColor, final int iTextViewWidth, final int iTextViewHeight) {
        final float RATIO_SHAPE_WIDTH = 1.5f;
        final float RATIO_SHAPE_HEIGHT = 1.3f;
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
