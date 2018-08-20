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
import java.util.Set;

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
        int iColor = mContext.getResources().getColor(R.color.colorPrimary);
        Set<String> keys = m_mapTagColor.keySet();
        for(String key : keys) {
            if(strTag.contains(key)) {
                iColor = m_mapTagColor.get(key);
                break;
            }
        }
        return iColor;
    }

    private void initialize() {
        m_mapTagColor = new HashMap<>();

        String strTagOfficial = mContext.getResources().getText(R.string.tag_official).toString();
        int colTagOfficial = mContext.getResources().getColor(R.color.colorSlave);
        String strTagSport = mContext.getResources().getText(R.string.tag_sport).toString();
        int colTagSport = mContext.getResources().getColor(R.color.colorTagBlue);
        String strTagBall = mContext.getResources().getText(R.string.tag_ball).toString();
        int colTagBall = mContext.getResources().getColor(R.color.colorTagBlue);
        String strTagCasual = mContext.getResources().getText(R.string.tag_casual).toString();
        int colTagCasual = mContext.getResources().getColor(R.color.colorTagLiteGreen);
        String strTagLiterary = mContext.getResources().getText(R.string.tag_literary).toString();
        int colTagLiterary = mContext.getResources().getColor(R.color.colorTagBrown);
        String strTagLiterary2 = mContext.getResources().getText(R.string.tag_literary2).toString();
        int colTagLiterary2 = mContext.getResources().getColor(R.color.colorTagBrown);
        String strTagLiterary3 = mContext.getResources().getText(R.string.tag_literary3).toString();
        int colTagLiterary3 = mContext.getResources().getColor(R.color.colorTagBrown);
        String strTagArt = mContext.getResources().getText(R.string.tag_art).toString();
        int colTagArt = mContext.getResources().getColor(R.color.colorTagPurple);
        String strTagHumanities = mContext.getResources().getText(R.string.tag_humanities).toString();
        int colTagHumanities = mContext.getResources().getColor(R.color.colorTagBrown);
        String strTagSocial = mContext.getResources().getText(R.string.tag_social).toString();
        int colTagSocial = mContext.getResources().getColor(R.color.colorTagPurple);
        String strTagSocial2 = mContext.getResources().getText(R.string.tag_social2).toString();
        int colTagSocial2 = mContext.getResources().getColor(R.color.colorTagPurple);
        String strTagMind = mContext.getResources().getText(R.string.tag_mind).toString();
        int colTagMind = mContext.getResources().getColor(R.color.colorTagPink);
        String strTagResearch = mContext.getResources().getText(R.string.tag_research).toString();
        int colTagResearch = mContext.getResources().getColor(R.color.colorTagBlue);
        String strTagCare = mContext.getResources().getText(R.string.tag_care).toString();
        int colTagCare = mContext.getResources().getColor(R.color.colorTagPink);
        String strTagEnv = mContext.getResources().getText(R.string.tag_env).toString();
        int colTagEnv = mContext.getResources().getColor(R.color.colorTagGreen);
        String strTagOutdoor = mContext.getResources().getText(R.string.tag_outdoor).toString();
        int colTagOutdoor = mContext.getResources().getColor(R.color.colorTagGreen);
        String strTagOutdoor2 = mContext.getResources().getText(R.string.tag_outdoor2).toString();
        int colTagOutdoor2 = mContext.getResources().getColor(R.color.colorTagGreen);
        String strTagEducation = mContext.getResources().getText(R.string.tag_edu).toString();
        int colTagEducation = mContext.getResources().getColor(R.color.colorTagRed);
        String strTagFood = mContext.getResources().getText(R.string.tag_food).toString();
        int colTagFood = mContext.getResources().getColor(R.color.colorTagOrange);
        String strTagFood2 = mContext.getResources().getText(R.string.tag_food2).toString();
        int colTagFood2 = mContext.getResources().getColor(R.color.colorTagOrange);
        String strTagFood3 = mContext.getResources().getText(R.string.tag_food3).toString();
        int colTagFood3 = mContext.getResources().getColor(R.color.colorTagOrange);

        m_mapTagColor.put(strTagOfficial, colTagOfficial);
        m_mapTagColor.put(strTagSport, colTagSport);
        m_mapTagColor.put(strTagBall, colTagBall);
        m_mapTagColor.put(strTagCasual, colTagCasual);
        m_mapTagColor.put(strTagLiterary, colTagLiterary);
        m_mapTagColor.put(strTagLiterary2, colTagLiterary2);
        m_mapTagColor.put(strTagLiterary3, colTagLiterary3);
        m_mapTagColor.put(strTagArt, colTagArt);
        m_mapTagColor.put(strTagHumanities, colTagHumanities);
        m_mapTagColor.put(strTagSocial, colTagSocial);
        m_mapTagColor.put(strTagSocial2, colTagSocial2);
        m_mapTagColor.put(strTagMind, colTagMind);
        m_mapTagColor.put(strTagResearch, colTagResearch);
        m_mapTagColor.put(strTagCare, colTagCare);
        m_mapTagColor.put(strTagEnv, colTagEnv);
        m_mapTagColor.put(strTagOutdoor, colTagOutdoor);
        m_mapTagColor.put(strTagOutdoor2, colTagOutdoor2);
        m_mapTagColor.put(strTagEducation, colTagEducation);
        m_mapTagColor.put(strTagFood, colTagFood);
        m_mapTagColor.put(strTagFood2, colTagFood2);
        m_mapTagColor.put(strTagFood3, colTagFood3);
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
