package com.fff.ingood.task2;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class HttpProxy {

    private static final String SERVER_URL = "https://hangouttw.appspot.com";
    public static final String HTTP_POST_API_REGISTER = SERVER_URL + "/register";
    public static final String HTTP_POST_API_UNREGISTER = SERVER_URL + "/unregister";
    public static final String HTTP_POST_API_LOGIN = SERVER_URL + "/login";
    public static final String HTTP_POST_API_LOGOUT = SERVER_URL + "/logout";
    public static final String HTTP_POST_API_VERIFY_EMAIL = SERVER_URL + "/verifyemail";
    public static final String HTTP_POST_API_PERSON_QUERY = SERVER_URL + "/queryperson";
    public static final String HTTP_POST_API_PERSON_UPDATE = SERVER_URL + "/updateperson";
    public static final String HTTP_POST_API_ACTIVITY_CREATE = SERVER_URL + "/createactivity";
    public static final String HTTP_POST_API_ACTIVITY_DELETE = SERVER_URL + "/deleteactivity";
    public static final String HTTP_POST_API_ACTIVITY_QUERY = SERVER_URL + "/queryactivity";
    public static final String HTTP_POST_API_ACTIVITY_UPDATE = SERVER_URL + "/updateactivity";
    public static final String HTTP_POST_API_ACTIVITY_QUERY_BY_ATTR = SERVER_URL + "/queryactivityidby";

    public static final int HTTP_POST_TIMEOUT = 10;
    static final int HTTP_GET_TIMEOUT = 15;
}


