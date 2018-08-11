package com.fff.ingood.global;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ElminsterII on 2018/7/28.
 */
public class IgActivityImageCache {
    private static IgActivityImageCache m_instance = null;

    //for Homepage.
    private HashMap<String, ImageView> m_hashImageViewsHolder;
    private HashMap<String, Bitmap> m_hashIgActivityMainImagesCache;

    //for IgActivityDetail.
    private List<Bitmap> m_lsIgActivityImagesCache;

    private IgActivityImageCache() {

    }

    public static IgActivityImageCache getInstance() {
        if(m_instance == null) {
            m_instance = new IgActivityImageCache();
            m_instance.initialize();
        }
        return m_instance;
    }

    private void initialize() {
        m_hashImageViewsHolder = new HashMap<>();
        m_hashIgActivityMainImagesCache = new HashMap<>();
        m_lsIgActivityImagesCache = new ArrayList<>();
    }

    public void clear() {
        m_hashIgActivityMainImagesCache.clear();
        m_hashImageViewsHolder.clear();
    }

    public HashMap<String, ImageView> getHashImageViewsHolder() {
        return m_hashImageViewsHolder;
    }

    public HashMap<String, Bitmap> getHashIgActivityMainImagesCache() {
        return m_hashIgActivityMainImagesCache;
    }

    public List<Bitmap> getIgActivityImagesCache() {
        return new ArrayList<>(m_lsIgActivityImagesCache);
    }

    public List<Bitmap> getIgActivityImagesCacheByRef() {
        return m_lsIgActivityImagesCache;
    }

    public boolean isCacheExist() {
        return (m_lsIgActivityImagesCache != null) && (m_lsIgActivityImagesCache.size() > 0);
    }
}
