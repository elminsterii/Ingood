package com.fff.ingood.global;

import android.annotation.SuppressLint;
import android.content.Context;

import com.fff.ingood.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ElminsterII on 2018/6/5.
 */
public class ServerResponse {

    private static ServerResponse m_instance = null;
    private static Map<Integer, String> m_mapServerResponseDescriptions = null;

    private ServerResponse() {}

    public static ServerResponse getInstance() {
        return m_instance;
    }

    public static ServerResponse getInstance(Context context) {
        if(m_instance == null) {
            m_instance = new ServerResponse();
            m_instance.initialize(context);
        }
        return m_instance;
    }

    @SuppressLint("UseSparseArrays")
    private void initialize(Context context) {
        m_mapServerResponseDescriptions = new HashMap<>();

        //response from server
        m_mapServerResponseDescriptions.put(STATUS_CODE_SUCCESS_INT, context.getResources().getText(R.string.server_res_0_success).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_USER_NOT_FOUND_INT, context.getResources().getText(R.string.server_res_1_user_not_found).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT, context.getResources().getText(R.string.server_res_2_user_already_exist).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_INVALID_USER_INT, context.getResources().getText(R.string.server_res_3_invalid_user).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_ACTIVITY_NOT_FOUND_INT, context.getResources().getText(R.string.server_res_4_activity_not_found).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_MISSING_DATA_INT, context.getResources().getText(R.string.server_res_5_missing_data).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_JSON_WRONG_INT, context.getResources().getText(R.string.server_res_6_json_wrong).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_INVALID_DATA_INT, context.getResources().getText(R.string.server_res_7_invalid_data).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_IO_ERROR_INT, context.getResources().getText(R.string.server_res_8_io_error).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT, context.getResources().getText(R.string.server_res_9_file_not_found).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_COMMENT_NOT_FOUND_INT, context.getResources().getText(R.string.server_res_10_comment_not_found).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_ALREADT_ATTEND_INT, context.getResources().getText(R.string.server_res_11_already_attend).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_VERIFY_CODE_WRONG_INT, context.getResources().getText(R.string.server_res_12_verify_code_wrong).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_TIME_FORMAT_WRONG_INT, context.getResources().getText(R.string.server_res_13_time_format_wrong).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_SAVE_IGACTIVITY_WRONG, context.getResources().getText(R.string.server_res_14_save_igactivity_wrong).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_OFFER_MAX, context.getResources().getText(R.string.server_res_15_offer_took_max).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_UNKNOWN_ERROR_INT, context.getResources().getText(R.string.server_res_99_unknown_error).toString());

        //response from client
        m_mapServerResponseDescriptions.put(STATUS_CODE_NWK_FAIL_INT, context.getResources().getText(R.string.nwk_connection_fail).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_LOGIC_MISSING_DATA_INT, context.getResources().getText(R.string.missing_necessary_data).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_PARSING_ERROR, context.getResources().getText(R.string.parsing_data_error).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_DOWNLOAD_RESOURCE_ERROR, context.getResources().getText(R.string.download_data_error).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_GOOGLE_SIGNIN_FAIL, context.getResources().getText(R.string.google_signin_error).toString());
    }

    public static Map<Integer, String> getServerResponseDescriptions() {
        return m_mapServerResponseDescriptions;
    }

    public static final Integer STATUS_CODE_NWK_FAIL_INT = -1;
    public static final Integer STATUS_CODE_LOGIC_MISSING_DATA_INT = -2;
    public static final Integer STATUS_CODE_PARSING_ERROR = -3;
    public static final Integer STATUS_CODE_DOWNLOAD_RESOURCE_ERROR = -4;
    public static final Integer STATUS_CODE_NEVER_LOGIN = -5;
    public static final Integer STATUS_CODE_GOOGLE_SIGNIN_FAIL = -6;

    public static final String TAG_SERVER_RESPONSE_STATUS_CODE = "status_code";
    public static final String TAG_SERVER_RESPONSE_STATUS_DESCRIPTION = "status_description";
    public static final String STATUS_CODE_SUCCESS = "0";

    public static final Integer STATUS_CODE_SUCCESS_INT = 0;
    private static final Integer STATUS_CODE_FAIL_USER_NOT_FOUND_INT = 1;
    public static final Integer STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT = 2;
    public static final Integer STATUS_CODE_FAIL_INVALID_USER_INT = 3;
    private static final Integer STATUS_CODE_FAIL_ACTIVITY_NOT_FOUND_INT = 4;
    private static final Integer STATUS_CODE_FAIL_MISSING_DATA_INT = 5;
    private static final Integer STATUS_CODE_FAIL_JSON_WRONG_INT = 6;
    private static final Integer STATUS_CODE_FAIL_INVALID_DATA_INT = 7;
    private static final Integer STATUS_CODE_FAIL_IO_ERROR_INT = 8;
    public static final Integer STATUS_CODE_FAIL_FILE_NOT_FOUND_INT = 9;
    public static final Integer STATUS_CODE_FAIL_COMMENT_NOT_FOUND_INT = 10;
    private static final Integer STATUS_CODE_FAIL_ALREADT_ATTEND_INT = 11;
    private static final Integer STATUS_CODE_FAIL_VERIFY_CODE_WRONG_INT = 12;
    private static final Integer STATUS_CODE_FAIL_TIME_FORMAT_WRONG_INT = 13;
    private static final Integer STATUS_CODE_FAIL_SAVE_IGACTIVITY_WRONG = 14;
    public static final Integer STATUS_CODE_FAIL_OFFER_MAX = 15;
    public static final Integer STATUS_CODE_FAIL_UNKNOWN_ERROR_INT = 99;

    //Server response tag
    public static final String TAG_SERVER_RESPONSE_COMMON_IDS = "ids";
    public static final String TAG_SERVER_RESPONSE_PERSON_ICONS = "icons";
    public static final String TAG_SERVER_RESPONSE_IGACTIVITY_IMAGES = "images";
}
