package com.fff.ingood.global;

import java.util.HashMap;

/**
 * Created by ElminsterII on 2018/6/15.
 */
public class GlobalProperty {
    //Admins
    public static final HashMap<String, String> MAP_ADMIN_ACCOUNTS_AND_PW = new HashMap<>();
    public static final String ADMIN_ACCOUNT_01 = "fivefourfiveit@gmail.com";
    private static final String ADMIN_ACCOUNT_PW_01 = "545450779031";

    //Start animation
    public static final int STARTUP_ANIMATION_DURATION = 1500;

    //Register
    public static final String VERIFY_CODE_FOR_GOOGLE_SIGN = "google545450779031";
    public static final String VERIFY_CODE_FOR_FACEBOOK_SIGN = "facebook507790315454";

    //Person
    public static final int AGE_LIMITATION = 18;
    public static final int PERSON_ICON_UPLOAD_UPPER_LIMIT = 1;
    public static final int PERSON_ICON_WIDTH = 512;
    public static final int PERSON_ICON_HEIGHT = 512;
    public static final String[] ARRAY_PERSON_ICON_NAMES = {"icon00","icon01","icon02"};

    //Homepage
    public static final char CHAR_SEARCH_TEXT_HEAD_IS_EMAIL = '@';
    public static final char CHAR_SEARCH_TEXT_HEAD_IS_TAG = '#';
    public static final boolean IS_SHOW_CLOSED_IGACTIVITY = false;
    public static final String GOOD_IGACTIVITY_THRESHOLD = "1";
    public static final String POPULARITY_IGACTIVITY_THRESHOLD = "1";
    public static final int MAX_QUERY_QUANTITY_IGACTIVITY_ONCE = 10;
    public static final int IGACTIVITY_MAIN_IMAGE_CORNER_LEVEL = 62;

    //IgActivityDetail page
    public static final int IGACTIVITY_IMAGE_UPLOAD_UPPER_LIMIT = 3;
    public static final int IGACTIVITY_IMAGE_WIDTH = 512;
    public static final int IGACTIVITY_IMAGE_HEIGHT = 512;
    public static final String[] ARRAY_IGACTIVITY_IMAGE_NAMES = {"image00","image01","image02"};

    public static void initialize() {
        MAP_ADMIN_ACCOUNTS_AND_PW.put(ADMIN_ACCOUNT_01, ADMIN_ACCOUNT_PW_01);
    }
}
