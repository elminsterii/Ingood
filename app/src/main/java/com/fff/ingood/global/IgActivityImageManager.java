package com.fff.ingood.global;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fff.ingood.global.GlobalProperty.ARRAY_IGACTIVITY_IMAGE_NAMES;

/**
 * Created by ElminsterII on 2018/7/28.
 */
public class IgActivityImageManager {
    public enum IMAGE_ACTION {ACT_UPLOAD, ACT_DELETE}

    public class IgActivityImageAction {
        private List<IMAGE_ACTION> m_lsImagesAction;
        private List<String> m_lsImagesName;
        private boolean m_withDeleteAction;
        private boolean m_withUploadAction;

        public List<IMAGE_ACTION> getImagesAction() {
            return m_lsImagesAction;
        }

        void setImagesAction(List<IMAGE_ACTION> m_lsImagesAction) {
            this.m_lsImagesAction = m_lsImagesAction;
        }

        public List<String> getImagesName() {
            return m_lsImagesName;
        }

        void setImagesName(List<String> m_lsImagesName) {
            this.m_lsImagesName = m_lsImagesName;
        }

        public boolean withDeleteAction() {
            return m_withDeleteAction;
        }

        public void setWithDeleteAction(boolean m_withDeleteAction) {
            this.m_withDeleteAction = m_withDeleteAction;
        }

        public boolean withUploadAction() {
            return m_withUploadAction;
        }

        public void setWithUploadAction(boolean m_withUploadAction) {
            this.m_withUploadAction = m_withUploadAction;
        }
    }

    private static IgActivityImageManager m_instance = null;

    private IgActivityImageManager() {

    }

    public static IgActivityImageManager getInstance() {
        if(m_instance == null)
            m_instance = new IgActivityImageManager();
        return m_instance;
    }

    public IgActivityImageAction determineImagesAction(List<Bitmap> lsImagesSrc, List<Bitmap> lsImagesTgt) {
        IgActivityImageAction imageAction = new IgActivityImageAction();
        List<IMAGE_ACTION> lsActions = new ArrayList<>();

        int iSizeSrc = (lsImagesSrc == null) ? 0 : lsImagesSrc.size();
        int iSizeTgt = (lsImagesTgt == null) ? 0 : lsImagesTgt.size();

        if(iSizeSrc == 0
                || (iSizeTgt >= iSizeSrc)) {
            imageAction.setWithUploadAction(true);
            for(int i=0; i<iSizeTgt; i++)
                lsActions.add(IMAGE_ACTION.ACT_UPLOAD);
        } else if(iSizeTgt == 0) {
            imageAction.setWithDeleteAction(true);
            for(int i=0; i<iSizeSrc; i++)
                lsActions.add(IMAGE_ACTION.ACT_DELETE);
        } else {
            //src > tgt
            imageAction.setWithUploadAction(true);
            imageAction.setWithDeleteAction(true);
            int index;
            for(index=0; index<iSizeTgt; index++)
                lsActions.add(IMAGE_ACTION.ACT_UPLOAD);
            for(;index<iSizeSrc; index++)
                lsActions.add(IMAGE_ACTION.ACT_DELETE);
        }

        imageAction.setImagesAction(lsActions);

        List<String> lsNames = new ArrayList<>();
        if(lsActions.size() <= ARRAY_IGACTIVITY_IMAGE_NAMES.length)
            lsNames.addAll(Arrays.asList(ARRAY_IGACTIVITY_IMAGE_NAMES).subList(0, lsActions.size()));

        imageAction.setImagesName(lsNames);
        return imageAction;
    }
}
