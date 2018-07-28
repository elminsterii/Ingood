package com.fff.ingood.global;

import android.graphics.Bitmap;

import java.util.List;

import static com.fff.ingood.global.GlobalProperty.IGACTIVITY_IMAGE_UPLOAD_UPPER_LIMIT;

/**
 * Created by ElminsterII on 2018/7/28.
 */
public class ImageCache {
    private static final int UPPER_LIMIT_CACHE_IMAGES = IGACTIVITY_IMAGE_UPLOAD_UPPER_LIMIT;
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
        return m_lsCacheImages;
    }

    public void cachingImages(List<Bitmap> lsCacheImages) {
        m_lsCacheImages = lsCacheImages;
    }

    public boolean isCacheExist() {
        return (m_lsCacheImages != null) && (m_lsCacheImages.size() > 0);
    }
}
