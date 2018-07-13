package com.fff.ingood.global;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by ElminsterII on 2018/7/2.
 */
public class SystemUIManager {

    public enum ACTIVITY_LIST {ACT_DEFAULT, ACT_MAIN, ACT_LOGIN, ACT_LOGINACC, ACT_REGISTRATION, ACT_HOME, ACT_IGDETAIL, ACT_IGPUBLISH}

    private static SystemUIManager m_instance = null;
    private int mCurAPIVersion;

    private static ACTIVITY_LIST mCurActivity;

    private SystemUIManager() {}

    public static SystemUIManager getInstance(ACTIVITY_LIST curActivity) {
        if(m_instance == null) {
            m_instance = new SystemUIManager();
            m_instance.initialize();
        }

        mCurActivity = curActivity;
        return m_instance;
    }

    private void initialize() {
        mCurAPIVersion = android.os.Build.VERSION.SDK_INT;
    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    public void setSystemUI(Activity activity) {
        if (mCurAPIVersion >= 21) {
            final View decorView = activity.getWindow().getDecorView();

            switch(mCurActivity) {
                case ACT_MAIN :
                    final int SYSTEM_UI_FLAG_INGOOD_MAIN = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

                    decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD_MAIN);
                    decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD_MAIN);
                            }
                        }
                    });
                    break;

                case ACT_IGDETAIL :
                    final int SYSTEM_UI_FLAG_INGOOD_IGDETAIL = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

                    decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD_IGDETAIL);
                    decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD_IGDETAIL);
                            }
                        }
                    });

                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    break;

                default :
                    final int SYSTEM_UI_FLAG_INGOOD_DEFAULT = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

                    decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD_DEFAULT);
                    decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD_DEFAULT);
                            }
                        }
                    });
                    break;
            }
        }
    }
}
