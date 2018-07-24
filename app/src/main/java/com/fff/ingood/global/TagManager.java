package com.fff.ingood.global;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.activity.MainActivity;
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

    private static TagManager m_instance = null;
    private MainActivity mContext;

    private Map<String, Integer> m_mapTagColor;
    private int mTagBarWidth;

    private TagManager(MainActivity context) {
        mContext = context;
    }

    public static TagManager getInstance(MainActivity context) {
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

        String strTagRecently = mContext.getResources().getText(R.string.tag_recently).toString();
        int colTagRecently = mContext.getResources().getColor(R.color.colorTagRecently);
        String strTagPopularity = mContext.getResources().getText(R.string.tag_popularity).toString();
        int colTagPopularity = mContext.getResources().getColor(R.color.colorTagPopularity);
        String strTagGood = mContext.getResources().getText(R.string.tag_good).toString();
        int colTagGood = mContext.getResources().getColor(R.color.colorTagGood);
        String strTagNearly = mContext.getResources().getText(R.string.tag_nearly).toString();
        int colTagNearly = mContext.getResources().getColor(R.color.colorTagNearly);

        m_mapTagColor.put(strTagRecently, colTagRecently);
        m_mapTagColor.put(strTagPopularity, colTagPopularity);
        m_mapTagColor.put(strTagGood, colTagGood);
        m_mapTagColor.put(strTagNearly, colTagNearly);
    }

    public int makeTagsInLayout(ViewGroup layout, String[] arrStrTags, int iTagBarWidth) {
        mTagBarWidth = iTagBarWidth;

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

        return iTagBarContain;
    }

    public int makeTagsInLayout(ViewGroup layout, IgActivity activity, int iTagBarWidth) {
        mTagBarWidth = iTagBarWidth;

        String[] arrStrTags = activity.getTags().split(",");
        return makeTagsInLayout(layout, arrStrTags, iTagBarWidth);
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
        int iSumAllTagWidth;

        for(int i=0; i<iTagSize; i++) {
            iSumAllTagWidth = 0;
            for (int j=0; j<iTagSize-i; j++) {
                iSumAllTagWidth += GAP_TAGS;
                iSumAllTagWidth += lsTextViewTags.get(j).getBackground().getIntrinsicWidth();
            }

            iSumAllTagWidth += GAP_TAGS;

            if(iSumAllTagWidth < MAX_TAG_BAR_WIDTH) {
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
