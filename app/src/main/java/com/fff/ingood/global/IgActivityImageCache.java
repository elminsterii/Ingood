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
    private List<Bitmap> m_lsCacheImages;

    private HashMap<String, ImageView> m_hashImageViewsHolder;
    private HashMap<String, Bitmap> m_hashIgActivityMainImagesCache;

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

    public List<Bitmap> getCacheImages() {
        return new ArrayList<>(m_lsCacheImages);
    }

    public List<Bitmap> getCacheImagesByRef() {
        return m_lsCacheImages;
    }

    public void cachingImages(List<Bitmap> lsCacheImages) {
        m_lsCacheImages = lsCacheImages;
    }

    public boolean isCacheExist() {
        return (m_lsCacheImages != null) && (m_lsCacheImages.size() > 0);
    }
}
