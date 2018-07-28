package com.fff.ingood.global;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ElminsterII on 2018/7/28.
 */
public class ImageCache {
    private static ImageCache m_instance = null;
    private List<Bitmap> m_lsCacheImages;

    private ImageCache() {

    }

    public static ImageCache getInstance() {
        if(m_instance == null)
            m_instance = new ImageCache();
        return m_instance;
    }

    public List<Bitmap> getCacheImages() {
        return new ArrayList<>(m_lsCacheImages);
    }

    public void cachingImages(List<Bitmap> lsCacheImages) {
        m_lsCacheImages = lsCacheImages;
    }

    public boolean isCacheExist() {
        return (m_lsCacheImages != null) && (m_lsCacheImages.size() > 0);
    }
}
