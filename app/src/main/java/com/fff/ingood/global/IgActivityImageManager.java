package com.fff.ingood.global;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ElminsterII on 2018/7/28.
 */
public class IgActivityImageManager {
    public enum IMAGE_ACTION {ACT_UPLOAD, ACT_DELETE}

    private static IgActivityImageManager m_instance = null;

    private IgActivityImageManager() {

    }

    public static IgActivityImageManager getInstance() {
        if(m_instance == null)
            m_instance = new IgActivityImageManager();
        return m_instance;
    }

    public List<IMAGE_ACTION> determineImagesAction(List<Bitmap> lsImagesSrc, List<Bitmap> lsImagesTgt) {
        List<IMAGE_ACTION> lsAction = new ArrayList<>();

        int iSizeSrc = (lsImagesSrc == null) ? 0 : lsImagesSrc.size();
        int iSizeTgt = (lsImagesTgt == null) ? 0 : lsImagesTgt.size();

        if(iSizeSrc == 0
                || (iSizeTgt >= iSizeSrc)) {
            for(int i=0; i<iSizeTgt; i++)
                lsAction.add(IMAGE_ACTION.ACT_UPLOAD);
        } else if(iSizeTgt == 0) {
            for(int i=0; i<iSizeSrc; i++)
                lsAction.add(IMAGE_ACTION.ACT_DELETE);
        } else {
            //src > tgt
            int index;
            for(index=0; index<iSizeTgt; index++)
                lsAction.add(IMAGE_ACTION.ACT_UPLOAD);
            for(;index<iSizeSrc; index++)
                lsAction.add(IMAGE_ACTION.ACT_DELETE);
        }

        return lsAction;
    }
}
