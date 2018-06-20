package com.fff.ingood.global;

import android.annotation.SuppressLint;
import android.content.Context;

import com.fff.ingood.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ElminsterII on 2018/6/20.
 */
public class TagManager {

    @SuppressLint("StaticFieldLeak")
    private static TagManager m_instance = null;
    private Context mContext;

    private Map<String, Integer> m_mapTagColor;

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

    public int getTagColor(String strTag) {
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
}
