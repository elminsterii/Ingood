package com.fff.ingood.global;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.tools.StringTool;

/**
 * Created by ElminsterII on 2018/7/3.
 */
public class DeemInfoManager {

    public enum DEEM_INFO {DEEM_NONE, DEEM_GOOD, DEEM_BAD}

    private static DeemInfoManager m_instance = null;

    private DeemInfoManager() {

    }

    public static DeemInfoManager getInstance() {
        if(m_instance == null)
            m_instance = new DeemInfoManager();
        return m_instance;
    }

    public DEEM_INFO getDeemInfo(IgActivity activity) {
        if(activity == null || !StringTool.checkStringNotNull(activity.getId()))
            return DEEM_INFO.DEEM_NONE;

        return PreferenceManager.getInstance().getDeemInfo(activity.getId());
    }

    public void setDeemInfo(IgActivity activity, DEEM_INFO deemInfo) {
        if(activity == null || !StringTool.checkStringNotNull(activity.getId()))
            return;

        PreferenceManager.getInstance().setDeemInfo(activity.getId(), deemInfo);
    }

}
