package com.fff.ingood.global;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ElminsterII on 2018/7/28.
 */
public class IgActivityImageCache {
    private static IgActivityImageCache m_instance = null;
    private List<Bitmap> m_lsCacheImages;

    private IgActivityImageCache() {

    }

    public static IgActivityImageCache getInstance() {
        if(m_instance == null)
            m_instance = new IgActivityImageCache();
        return m_instance;
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
