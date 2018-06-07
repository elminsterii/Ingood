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
        m_mapServerResponseDescriptions.put(STATUS_CODE_SUCCESS_INT, context.getResources().getText(R.string.server_res_0_success).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_USER_NOT_FOUND_INT, context.getResources().getText(R.string.server_res_1_user_not_found).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT, context.getResources().getText(R.string.server_res_2_user_already_exist).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_INVALID_USER_INT, context.getResources().getText(R.string.server_res_3_invalid_user).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_NOT_OWNER_INT, context.getResources().getText(R.string.server_res_4_not_owner).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_MISSING_DATA_INT, context.getResources().getText(R.string.server_res_5_missing_data).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_JSON_WRONG_INT, context.getResources().getText(R.string.server_res_6_json_wrong).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_INVALID_DATA_INT, context.getResources().getText(R.string.server_res_7_invalid_data).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_IO_ERROR_INT, context.getResources().getText(R.string.server_res_8_io_error).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT, context.getResources().getText(R.string.server_res_9_file_not_found).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_COMMENT_NOT_FOUND_INT, context.getResources().getText(R.string.server_res_10_comment_not_found).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_ALREADT_ATTEND_INT, context.getResources().getText(R.string.server_res_11_already_attend).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_VERIFY_CODE_WRONG_INT, context.getResources().getText(R.string.server_res_12_verify_code_wrong).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_TIME_FORMAT_WRONG_INT, context.getResources().getText(R.string.server_res_13_time_format_wrong).toString());
        m_mapServerResponseDescriptions.put(STATUS_CODE_FAIL_UNKNOWN_ERROR_INT, context.getResources().getText(R.string.server_res_14_unknown_error).toString());
    }

    public static Map<Integer, String> getServerResponseDescriptions() {
        return m_mapServerResponseDescriptions;
    }

    public static final Integer STATUS_CODE_NWK_FAIL_INT = -1;
    public static final Integer STATUS_CODE_FLOW_REJECT_INT = -2;

    public static final String TAG_SERVER_RESPONSE_STATUS_CODE = "status_code";
    public static final String TAG_SERVER_RESPONSE_STATUS_DESCRIPTION = "status_description";
    public static final String STATUS_CODE_SUCCESS = "0";

    public static final Integer STATUS_CODE_SUCCESS_INT = 0;
    private static final Integer STATUS_CODE_FAIL_USER_NOT_FOUND_INT = 1;
    private static final Integer STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT = 2;
    private static final Integer STATUS_CODE_FAIL_INVALID_USER_INT = 3;
    private static final Integer STATUS_CODE_FAIL_NOT_OWNER_INT = 4;
    private static final Integer STATUS_CODE_FAIL_MISSING_DATA_INT = 5;
    private static final Integer STATUS_CODE_FAIL_JSON_WRONG_INT = 6;
    private static final Integer STATUS_CODE_FAIL_INVALID_DATA_INT = 7;
    private static final Integer STATUS_CODE_FAIL_IO_ERROR_INT = 8;
    private static final Integer STATUS_CODE_FAIL_FILE_NOT_FOUND_INT = 9;
    private static final Integer STATUS_CODE_FAIL_COMMENT_NOT_FOUND_INT = 10;
    private static final Integer STATUS_CODE_FAIL_ALREADT_ATTEND_INT = 11;
    private static final Integer STATUS_CODE_FAIL_VERIFY_CODE_WRONG_INT = 12;
    private static final Integer STATUS_CODE_FAIL_TIME_FORMAT_WRONG_INT = 13;
    private static final Integer STATUS_CODE_FAIL_UNKNOWN_ERROR_INT = 14;
}